import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class SudokuBoard {

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    private static final int SIZE = 9;
    private static final int REGION_SIZE = 3;
    private int[][] board;

    public SudokuBoard() {
        board = new int[9][9];
    }

    public void playGame(Scanner scanner) {

        System.out.println("Escolha a dificuldade:");
        System.out.println("1. Fácil");
        System.out.println("2. Médio");
        System.out.println("3. Difícil");
        System.out.print("Digite a opção (1-3): ");
        int choice = scanner.nextInt(); // Lê a opção escolhida pelo jogador

        Difficulty difficulty; // Variável para armazenar a dificuldade escolhida
        switch (choice) { // Define a dificuldade com base na opção escolhida pelo jogador
            case 1:
                difficulty = Difficulty.EASY;
                break;
            case 2:
                difficulty = Difficulty.MEDIUM;
                break;
            case 3:
                difficulty = Difficulty.HARD;
                break;
            default:
                System.out.println("Opção inválida. Escolhendo a dificuldade padrão: Médio");
                difficulty = Difficulty.MEDIUM; // Define a dificuldade padrão como Médio
        }

        generateBoard(difficulty);
        System.out.println("Tabuleiro gerado:");
        printBoard();
    }

    public void generateBoard(Difficulty difficulty) {
        // Cria uma instância de Random para gerar números aleatórios
        Random random = new Random(System.currentTimeMillis());

        // Limpa o tabuleiro antes de gerar um novo
        clearBoard();

        // Gera um tabuleiro completo com números aleatório
        boolean boardValid = generateFullBoard(0, 0, random);

        // Se não foi possível gerar um tabuleiro válido, imprime mensagem de erro e
        // retorna
        if (!boardValid) {
            System.out.println("Não foi possível gerar um tabuleiro válido. Tente novamente.");
            return;
        }

        // Determina a quantidade de células a serem removidas com base na dificuldade
        // selecionada
        int numberOfCellsToRemove;
        switch (difficulty) {
            case EASY:
                numberOfCellsToRemove = 36 + (int) (Math.random() * 10); // Entre 36 e 45 células removidas
                break;
            case MEDIUM:
                numberOfCellsToRemove = 46 + (int) (Math.random() * 10); // Entre 46 e 55 células removidas
                break;
            case HARD:
                numberOfCellsToRemove = 56 + (int) (Math.random() * 10); // Entre 56 e 65 células removidas
                break;
            default:
                numberOfCellsToRemove = 46 + (int) (Math.random() * 10); // Se a dificuldade não for especificada, usar
                                                                         // nível médio (46-55 células removidas)
        }
        // Remove a quantidade de células determinada aleatoriamente
        removeNumbersFromBoard(numberOfCellsToRemove);
    }

    private boolean generateFullBoard(int row, int col, Random random) {

        // Se todas as linhas foram preenchidas, retorna true (tabuleiro completo)
        if (row == SIZE) {
            return true;
        }

        // Calcula a próxima linha e coluna a serem preenchidas
        int nextRow = col == SIZE - 1 ? row + 1 : row;
        int nextCol = (col + 1) % SIZE;

        // Gera uma lista de valores possíveis e a embaralha
        List<Integer> values = new ArrayList<>();
        for (int value = 1; value <= SIZE; value++) {
            values.add(value);
        }
        Collections.shuffle(values, random);

        // Tenta preencher a célula atual com cada valor possível em ordem aleatória
        for (int value : values) {
            if (isValidMove(row, col, value)) {
                board[row][col] = value;

                // Se foi possível preencher a célula atual e continuar preenchendo o resto do
                // tabuleiro, retorna true
                if (generateFullBoard(nextRow, nextCol, random)) {
                    return true;
                }
            }
        }
        // Se não foi possível preencher a célula atual com nenhum valor válido, limpa a
        // célula e retorna false
        board[row][col] = 0;
        return false;
    }

    private void removeNumbersFromBoard(int numberOfCellsToRemove) {

        // Cria uma lista de posições possíveis e a embaralha
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);

        for (int i = 0; i < numberOfCellsToRemove; i++) {
            int pos = positions.get(i);
            int row = pos / SIZE;
            int col = pos % SIZE;
            board[row][col] = 0;
        }
    }

    public void printBoard() {

        // Itera pelas linhas e colunas do tabuleiro
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) {
                // Imprime uma linha horizontal a cada 3 linhas
                System.out.println("---------------------");
            }
            for (int col = 0; col < 9; col++) {
                // Imprime uma barra vertical a cada 3 colunas
                if (col % 3 == 0 && col != 0) {
                    System.out.print("| ");
                }
                // Imprime o valor da célula atual
                System.out.print(board[row][col] + " ");
            }
            // Imprime uma nova linha após cada linha do tabuleiro
            System.out.println();
        }
    }

    public boolean isComplete() {
        // Itera por todas as células do tabuleiro
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // Se alguma célula estiver vazia, retorna false
                if (getCellValue(row, col) == 0) {
                    return false;
                }
            }
        }
        // Se todas as células estiverem preenchidas, retorna true
        return true;
    }

    public void enterValue(Scanner scanner) {

        // Solicita ao usuário a linha, coluna e valor a serem inseridos
        System.out.print("Digite a linha (0-8): ");
        int row = scanner.nextInt();
        System.out.print("Digite a coluna (0-8): ");
        int col = scanner.nextInt();
        System.out.print("Digite o valor (1-9): ");
        int value = scanner.nextInt();

        // Verifica se a entrada é válida e, se for, preenche a célula correspondente
        if (row >= 0 && row < 9 && col >= 0 && col < 9 && value >= 1 && value <= 9) {
            board[row][col] = value;
        } else {
            System.out.println("Entrada inválida. Por favor, tente novamente.");
        }
    }

    public boolean isValidMove(int row, int col, int value) {

        // Verifica se o valor já está presente em alguma célula na mesma linha ou coluna
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == value || board[i][col] == value) {
                return false;
            }
        }
        
        // Verifica se o valor já está presente em alguma célula na mesma região (bloco 3x3)
        int rowStart = row / 3 * 3;
        int colStart = col / 3 * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[rowStart + i][colStart + j] == value) {
                    return false;
                }
            }
        }
        // Se o valor não estiver presente em nenhuma célula conflitante, retorna true
        return true;
    }

    public void showCongratulationsDialog() {

// Imprime uma mensagem de parabéns ao usuário ao completar o tabuleiro corretamente
        System.out.println("Parabéns! Você completou o tabuleiro corretamente!");
    }

    public boolean solveBoard() {

        // Cria uma instância de SudokuSolver com o tabuleiro atual
        SudokuSolver solver = new SudokuSolver(board);
        // Tenta resolver o tabuleiro e, se for bem-sucedido, atualiza o tabuleiro com a solução encontrada
        if (solver.solve()) {
            board = solver.getSolution();
            return true;
        }
        // Se não for possível resolver o tabuleiro, retorna false
        return false;
    }

    public void clearBoard() {
        // Limpa todas as células do tabuleiro, preenchendo-as com 0
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                setCellValue(row, col, 0);
            }
        }
    }

    public int getCellValue(int row, int col) {
        // Retorna o valor presente na célula especificada
        return board[row][col];
    }

    public void setCellValue(int row, int col, int value) {
        // Define o valor da célula especificada
        board[row][col] = value;
    }

    public boolean isBoardValid() {
        // Verifica se todas as células do tabuleiro foram preenchidas
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        // Verifica se não há valores repetidos em cada linha, coluna e região
        for (int row = 0; row < SIZE; row++) {
            boolean[] used = new boolean[SIZE];
            for (int col = 0; col < SIZE; col++) {
                int value = board[row][col];
                if (used[value - 1]) {
                    return false;
                }
                used[value - 1] = true;
            }
        }

        for (int col = 0; col < SIZE; col++) {
            boolean[] used = new boolean[SIZE];
            for (int row = 0; row < SIZE; row++) {
                int value = board[row][col];
                if (used[value - 1]) {
                    return false;
                }
                used[value - 1] = true;
            }
        }

        for (int regionRow = 0; regionRow < SIZE; regionRow += REGION_SIZE) {
            for (int regionCol = 0; regionCol < SIZE; regionCol += REGION_SIZE) {
                boolean[] used = new boolean[SIZE];
                for (int row = regionRow; row < regionRow + REGION_SIZE; row++) {
                    for (int col = regionCol; col < regionCol + REGION_SIZE; col++) {
                        int value = board[row][col];
                        if (used[value - 1]) {
                            return false;
                        }
                        used[value - 1] = true;
                    }
                }
            }
        }
        return true;
    }

    class SudokuSolver {

        private static final int SIZE = 9;
        private int[][] board;
    
        public SudokuSolver(int[][] originalBoard) {
    
            board = new int[SIZE][SIZE];
    
            // copia os valores da matriz original para a matriz do jogo
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    board[i][j] = originalBoard[i][j];
                }
            }
        }
    
        public boolean solve() {
    
            return solve(0, 0); // começa a resolver o jogo na posição 0,0
        }
    
        private boolean solve(int row, int col) {
    
            if (row == 9) { // todas as linhas foram preenchidas
                return true; // retorna true, já que o jogo foi resolvido com sucesso
            }
    
            if (board[row][col] != 0) { // se a posição já foi preenchida, pula para a próxima posição
                return solve(col == 8 ? row + 1 : row, (col + 1) % 9);
            }
    
            for (int value = 1; value <= 9; value++) {
    
                if (isValidMove(row, col, value)) { // se a jogada é válida
                    board[row][col] = value; // adiciona o valor na posição
                    if (solve(col == 8 ? row + 1 : row, (col + 1) % 9)) { // tenta resolver o restante do jogo
    
                        return true; // retorna true, já que o jogo foi resolvido com sucesso
                    }
    
                    board[row][col] = 0; // remove o valor adicionado na posição, já que não levou a uma solução
                }
            }
    
            return false; // não foi possível resolver o jogo
        }
    
        private boolean isValidMove(int row, int col, int value) {
    
            for (int i = 0; i < 9; i++) {
                if (board[row][i] == value || board[i][col] == value) { // verifica se o valor já existe na linha ou coluna
                    return false; // retorna false, já que a jogada é inválida
                }
            }
    
            int rowStart = row / 3 * 3; // posição inicial da submatriz de 3x3 que contém a posição (row, col)
            int colStart = col / 3 * 3;
    
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[rowStart + i][colStart + j] == value) { // verifica se o valor já existe na submatriz
                        return false; // retorna false, já que a jogada é inválida
                    }
                }
            }
    
            return true; // a jogada é válida
        }
    
        public int[][] getSolution() {
            int[][] solution = new int[9][9];
            for (int i = 0; i < 9; i++) {
                System.arraycopy(board[i], 0, solution[i], 0, 9); // copia a matriz atual para a matriz de solução
            }
            return solution; // retorna a cópia
        }
    
        public void printSolution() {
            for (int i = 0; i < 9; i++) {
    
                if (i % 3 == 0 && i != 0) { // adiciona uma linha vertical entre as submatrizes
                    System.out.print("| ");
                }
    
                System.out.print(board[i][i] + " "); // imprime o valor da posição
            }
            System.out.println(); // pula para a próxima linha
        }
    }
    
    public boolean canBeCompleted() {
    
        SudokuSolver solver = new SudokuSolver(board);
    
        return solver.solve(); // retorna true se o jogo pode ser resolvido
    }
    

    public static void main(String[] args) {

        SudokuBoard sudokuBoard = new SudokuBoard();

        Scanner scanner = new Scanner(System.in);

        boolean newGame = true;

        while (newGame) {
            newGame = false;

            sudokuBoard.playGame(scanner);

            boolean gameInProgress = true;

            while (gameInProgress) {

                System.out.println("\nOpções:");
                System.out.println("1. Inserir valor");
                System.out.println("2. Resolver tabuleiro");
                System.out.println("3. Verificar tabuleiro");
                System.out.println("4. Trocar de dificuldade");
                System.out.println("5. Completar tabuleiro");
                System.out.print("Digite a opção (1-5): ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:

                        sudokuBoard.enterValue(scanner);

                        System.out.println("\nTabuleiro atualizado:");
                        sudokuBoard.printBoard();

                        if (sudokuBoard.isBoardValid()) {
                            boolean isBoardComplete = true;
                            for (int row = 0; row < 9 && isBoardComplete; row++) {
                                for (int col = 0; col < 9; col++) {
                                    if (sudokuBoard.getCellValue(row, col) == 0) {
                                        isBoardComplete = false;
                                        break;
                                    }
                                }
                            }
                            if (isBoardComplete) {

                                System.out.println("Parabéns! Você completou o tabuleiro!");
                                gameInProgress = false;
                            }
                        }
                        break;
                    case 2:

                        if (sudokuBoard.solveBoard()) {

                            System.out.println("\nSolução encontrada:");
                            sudokuBoard.printBoard();
                        } else {
                            System.out.println("Não foi possível encontrar uma solução.");
                        }
                        break;
                    case 3:

                        if (sudokuBoard.isBoardValid()) {
                            System.out.println("O tabuleiro está válido.");
                        } else {
                            System.out.println("O tabuleiro está inválido.");
                        }
                        break;
                    case 4:

                        newGame = true;
                        gameInProgress = false;
                        break;
                    case 5:

                        if (sudokuBoard.solveBoard()) {

                            System.out.println("\nTabuleiro completado automaticamente:");
                            sudokuBoard.printBoard();

                            sudokuBoard.showCongratulationsDialog();
                        } else {
                            System.out.println("Não foi possível completar o tabuleiro.");
                        }
                        break;

                    default:

                        System.out.println("Opção inválida. Por favor, tente novamente.");
                }
            }
        }
    }
}
