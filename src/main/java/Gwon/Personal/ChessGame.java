package Gwon.Personal;

import Gwon.Personal.pieces.*;

import java.util.*;

public class ChessGame {
    private final Piece[][] board = new Piece[8][8];
    private static final Map<String, Integer> POS_TO_ROW_IDX = new HashMap<>();
    private static final Map<String, Integer> POS_TO_COL_IDX = new HashMap<>();
    private Player[] players = new Player[2];
    private int currPlayerIdx = 0;
    private Scanner in = new Scanner(System.in);

    // initialize conversions
    static {
        POS_TO_ROW_IDX.put("8", 0);
        POS_TO_ROW_IDX.put("7", 1);
        POS_TO_ROW_IDX.put("6", 2);
        POS_TO_ROW_IDX.put("5", 3);
        POS_TO_ROW_IDX.put("4", 4);
        POS_TO_ROW_IDX.put("3", 5);
        POS_TO_ROW_IDX.put("2", 6);
        POS_TO_ROW_IDX.put("1", 7);

        POS_TO_COL_IDX.put("A", 0);
        POS_TO_COL_IDX.put("B", 1);
        POS_TO_COL_IDX.put("C", 2);
        POS_TO_COL_IDX.put("D", 3);
        POS_TO_COL_IDX.put("E", 4);
        POS_TO_COL_IDX.put("F", 5);
        POS_TO_COL_IDX.put("G", 6);
        POS_TO_COL_IDX.put("H", 7);
    }

    public void go() {
        this.initializePlayers();
        this.initializeBoard();

        while (!this.isCheckmate()) {
            this.printBoard();
            System.out.println(this.players[this.currPlayerIdx] + "'s turn");
            String[] move = this.getUserMove();

            if (!this.takeTurn(move[0], move[1])) {
                System.out.println("Not a valid move. Please try again");
            } else {
                this.currPlayerIdx = this.currPlayerIdx == 0 ? 1 : 0;  // update player's turn
            }
        }

        // declare the winner
        // ask if the user wants to play again
    }

    public void printBoard() {
        int rowStart = 8;
        for (int row = 0; row < this.board.length; row++) {
            if (row == 0) {
                System.out.println("\t\t  Black");
            }

            for (int col = 0; col < this.board[row].length; col++) {

                // print the row positions
                if (col == 0) {
                    System.out.print((rowStart - row) + "\t ");
                }

                if (this.board[row][col] != null) {
                    System.out.print(this.board[row][col].getIcon() + " ");
                } else { // this is a filler for empty spaces
                    System.out.print("- ");
                }
            }
            if (row == 7) {
                System.out.print("\n\t\t  White");
            }

            System.out.println();
        }
        System.out.println();
        System.out.print("\t ");

        // print the column positions
        char colStart = 'A';
        for (int i = 0; i < this.board.length; i++) {
            System.out.print(colStart++ + " ");
        }

        System.out.println();
        System.out.println();
    }

    public void initializePlayers() {
        players[0] = new Player("White");
        players[1] = new Player("Black");
    }

    public void initializeBoard() {
        for (Player player : this.players) {
            String side = player.getSide();
            int pawnRow = player.getPawnRow(), backRow = player.getBackRow();

            // initialize pawns
            for (int col = 0; col < 8; col++) {
                Piece pawn = new Pawn(side);
                this.board[pawnRow][col] = pawn;
            }

            // initialize back row
            this.board[backRow][0] = new Rook(side);
            this.board[backRow][1] = new Knight(side);
            this.board[backRow][2] = new Bishop(side);
            this.board[backRow][3] = new Queen(side);
            this.board[backRow][4] = new King(side);
            this.board[backRow][5] = new Bishop(side);
            this.board[backRow][6] = new Knight(side);
            this.board[backRow][7] = new Rook(side);
        }
    }

    public String[] getUserMove() {
        System.out.print("Enter start position: ");
        String startPos = in.nextLine();
        System.out.print("Enter end position: ");
        String endPos = in.nextLine();
        return new String[] { startPos, endPos };
    }

    public boolean isCheckmate() {
        return false;
    }

    public boolean takeTurn(String startPos, String endPos) {

        // get position indexes
        int[] posIdxes = this.getPosIdxes(startPos, endPos);
        int startRowIdx = posIdxes[0], startColIdx = posIdxes[1],
                endRowIdx = posIdxes[2], endColIdx = posIdxes[3];

        // verify input is valid
        if (!this.isValidInput(startRowIdx, startColIdx, endRowIdx, endColIdx)) {
            return false;
        }

        // get the piece and validate the move
        Piece piece = this.board[startRowIdx][startColIdx];
        Piece capturedPiece = this.board[endRowIdx][endColIdx];
        if (piece.isValidMove(this.board, startRowIdx, startColIdx, endRowIdx, endColIdx)) {

            // take the captured piece
            if (capturedPiece != null) {
                Player player = this.players[this.currPlayerIdx];
                player.addCapturedPiece(capturedPiece);
            }

            // move the current player's piece
            this.board[endRowIdx][endColIdx] = piece;
            this.board[startRowIdx][startColIdx] = null; // remove the original position of the piece
            piece.incrementMoves();

            // give the last move
            System.out.println("Your move: " + piece.getType() + " to " + endPos);
            return true;
        }
        return false;
    }

    private boolean isOutOfBounds(int rowIdx, int colIdx) {
        if (rowIdx < 0 || rowIdx >= this.board.length
            || colIdx < 0 || colIdx >= this.board[rowIdx].length) {
            return true;
        }
        return false;
    }

    private boolean isValidInput(int startRowIdx, int startColIdx, int endRowIdx, int endColIdx) {
        if (startRowIdx == endRowIdx && startColIdx == endColIdx) {  // make sure there is an attempt to move a piece
            System.out.println("You didn't move the piece!");
            return false;
        } else if (this.isOutOfBounds(startRowIdx, startColIdx)  // check if start or end positions out of bounds
                || this.isOutOfBounds(endRowIdx, endColIdx)) {
            System.out.println("Out of bounds");
            return false;
        } else if (this.board[startRowIdx][startColIdx] == null) {  // selected a piece to start?
            System.out.println("Please select a valid starting piece");
            return false;
        }

        Player player = this.players[this.currPlayerIdx];
        Piece piece = this.board[startRowIdx][startColIdx];
        Piece capturePiece = this.board[endRowIdx][endColIdx];
        if (!piece.getSide().equals(player.getSide())) {  // selected the right player's piece?
            System.out.println("Please select your own piece");
            return false;
        } else if (capturePiece != null && player.getSide().equals(capturePiece.getSide())) {
            // capturing your own piece?
            System.out.println("Cannot capture your own piece");
            return false;
        }

        return true;
    }

    private int[] getPosIdxes(String startPos, String endPos) {
        // get each component of a position
        String startRowPos = String.valueOf(startPos.charAt(1)).toUpperCase();
        String startColPos = String.valueOf(startPos.charAt(0)).toUpperCase();
        String endRowPos = String.valueOf(endPos.charAt(1)).toUpperCase();
        String endColPos = String.valueOf(endPos.charAt(0)).toUpperCase();

        // convert components to their array index values
        int startRowIdx = POS_TO_ROW_IDX.get(startRowPos);
        int startColIdx = POS_TO_COL_IDX.get(startColPos);
        int endRowIdx = POS_TO_ROW_IDX.get(endRowPos);
        int endColIdx = POS_TO_COL_IDX.get(endColPos);

        return new int[]{ startRowIdx, startColIdx, endRowIdx, endColIdx };
    }
}
