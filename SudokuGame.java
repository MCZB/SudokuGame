public class SudokuGame {

    private SudokuGUI SudokuGUI;
    private SudokuBoard SudokuBoard;

    public SudokuGame() {
        SudokuGUI = new SudokuGUI();
        SudokuBoard = new SudokuBoard();

        // Atribuir o tabuleiro à interface gráfica
        SudokuGUI.setSudokuBoard(SudokuBoard);

        // Exibir a interface gráfica
        SudokuGUI.setVisible(true);
    }

    public static void main(String[] args) {
        new SudokuGame();
    }
}
