package Gwon.Personal;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.initializeBoard();
        board.printBoard();

        // make some moves
        board.movePiece("D2", "D3");
        board.printBoard();
        board.movePiece("E7", "E5");
        board.printBoard();
    }
}