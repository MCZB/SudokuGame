// Importações necessária
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Classe principal SudokuGUI que herda de JFrame e implementa ActionListener
public class SudokuGUI extends JFrame implements ActionListener {

    private static final int SIZE = 9;

    private SudokuBoard sudokuBoard; // Instância do objeto SudokuBoard
    private JLabel[][] boardLabels; // Representação visual do tabuleiro
    private JLabel selectedLabel; // Representa o rótulo selecionado no momento
    private boolean[][] cellGenerated;



    public SudokuGUI() {
        // Cria um novo objeto SudokuBoard
        sudokuBoard = new SudokuBoard();
        cellGenerated = new boolean[SIZE][SIZE];


        // Configura a janela principal
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cria o menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu newGameMenu = new JMenu("New Game");
        JMenuItem easyGameMenuItem = new JMenuItem("Easy");
        easyGameMenuItem.addActionListener(this);
        newGameMenu.add(easyGameMenuItem);
        JMenuItem mediumGameMenuItem = new JMenuItem("Medium");
        mediumGameMenuItem.addActionListener(this);
        newGameMenu.add(mediumGameMenuItem);
        JMenuItem hardGameMenuItem = new JMenuItem("Hard");
        hardGameMenuItem.addActionListener(this);
        newGameMenu.add(hardGameMenuItem);
        fileMenu.add(newGameMenu);
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JButton solveAllButton = new JButton("Solve All");
        solveAllButton.setPreferredSize(new Dimension(100, 30));
        solveAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (sudokuBoard.solveBoard()) {
                    updateBoardLabels();
                    checkForCompletion();
                } else {
                    JOptionPane.showMessageDialog(null, "No solution found");
                }
            }
        });

        // Cria o painel do tabuleiro
        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        boardLabels = new JLabel[SIZE][SIZE];
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
        Color selectedColor = new Color(200, 230, 255);
        MouseListener cellClickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedLabel != null) {
                    selectedLabel.setBackground(null);
                    selectedLabel.setOpaque(false);
                }
                selectedLabel = (JLabel) e.getSource();
                selectedLabel.setOpaque(true);
                selectedLabel.setBackground(selectedColor);
            }
        };
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                boardLabels[row][col] = new JLabel(" ", SwingConstants.CENTER);
                boardLabels[row][col].setFont(font);

                // Cria a borda superior e inferior grossa
                int top = 1, bottom = 1, left = 1, right = 1;
                if (row == 0) {
                    top = 3;
                } else if (row == SIZE - 1) {
                    bottom = 3;
                }

                // Cria a borda lateral
                if (col == 0) {
                    left = 3;
                } else if (col == SIZE - 1) {
                    right = 3;
                } else if (col == 2 || col == 5) {
                    right = 2;
                }

                boardLabels[row][col].addMouseListener(cellClickListener);
                boardLabels[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                boardPanel.add(boardLabels[row][col]);
            }
        }

        updateBoardLabels();

        // Cria o painel dos botões numerados
        JPanel numberPanel = new JPanel(new GridLayout(1, SIZE));
        for (int i = 1; i <= SIZE; i++) {
            JButton button = new JButton(Integer.toString(i));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedLabel != null) {
                        JButton clickedButton = (JButton) e.getSource();
                        int value = Integer.parseInt(clickedButton.getText());
                        for (int row = 0; row < SIZE; row++) {
                            for (int col = 0; col < SIZE; col++) {
                                if (boardLabels[row][col] == selectedLabel) {
                                    // Verifique se a célula pode ser alterada
                                    if (!cellGenerated[row][col]) {
                                        // Defina o valor da célula do objeto SudokuBoard
                                        sudokuBoard.setCellValue(row, col, value);
                                        // Atualiza os rótulos do tabuleiro
                                        updateBoardLabels();
                                        // Verifica se o jogo foi concluído
                                        checkForCompletion();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Não é possível alterar um valor gerado previamente.");
                                    }
                            }
                        }
                    }
                }
                }
            });
            numberPanel.add(button);
        }

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(solveAllButton);

        // Adiciona os componentes à janela principal
        add(boardPanel, BorderLayout.CENTER);
        add(numberPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Adiciona os componentes à janela principal
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Exibe a janela principal
        pack();
        setSize(getWidth() * 2, getHeight() * 2);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Easy":
                sudokuBoard.clearBoard(); // limpa o tabuleiro anterior
                sudokuBoard.generateBoard(SudokuBoard.Difficulty.EASY); // gera um novo tabuleiro fácil
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        cellGenerated[row][col] = sudokuBoard.getCellValue(row, col) != 0;
                    }
                }
                updateBoardLabels();
                break;
            case "Medium":
                sudokuBoard.clearBoard(); // limpa o tabuleiro anterior
                sudokuBoard.generateBoard(SudokuBoard.Difficulty.MEDIUM); // gera um novo tabuleiro médio
                updateBoardLabels();
                break;
            case "Hard":
                sudokuBoard.clearBoard(); // limpa o tabuleiro anterior
                sudokuBoard.generateBoard(SudokuBoard.Difficulty.HARD); // gera um novo tabuleiro difícil
                updateBoardLabels();
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Solve":
                if (sudokuBoard.solveBoard()) {
                    updateBoardLabels();
                    checkForCompletion();
                } else {
                    JOptionPane.showMessageDialog(this, "No solution found");
                }
                break;
        }
    }

    // Método para verificar se o jogo foi concluído
    public void checkForCompletion() {
        if (sudokuBoard.isComplete()) {
            JOptionPane.showMessageDialog(this, "Parabéns! Você completou o Tabuleiro!");
        }
    }

    // Método para atualizar os rótulos do tabuleiro com os valores das células
    private void updateBoardLabels() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = sudokuBoard.getCellValue(row, col);
                if (value == 0) {
                    boardLabels[row][col].setText(" ");
                } else {
                    boardLabels[row][col].setText(Integer.toString(value));
                }
            }
        }
    }

    // Método para definir o objeto SudokuBoard da classe SudokuGUI
    public void setSudokuBoard(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }
}
