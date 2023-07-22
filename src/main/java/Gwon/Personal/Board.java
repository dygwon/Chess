package Gwon.Personal;

import java.util.Map;
import java.util.HashMap;

public class Board {
    private final Piece[][] board = new Piece[8][8];
    private final Map<String, Integer> posToRowIdx = new HashMap<>();
    private final Map<String, Integer> posToColIdx = new HashMap<>();
    public Board() {
        initializeBoard();
        posToRowIdx.put("8", 0);
        posToRowIdx.put("7", 1);
        posToRowIdx.put("6", 2);
        posToRowIdx.put("5", 3);
        posToRowIdx.put("4", 4);
        posToRowIdx.put("3", 5);
        posToRowIdx.put("2", 6);
        posToRowIdx.put("1", 7);

        posToColIdx.put("A", 0);
        posToColIdx.put("B", 1);
        posToColIdx.put("C", 2);
        posToColIdx.put("D", 3);
        posToColIdx.put("E", 4);
        posToColIdx.put("F", 5);
        posToColIdx.put("G", 6);
        posToColIdx.put("H", 7);
    }

    public void printBoard() {
        int rowStart = 8;
        for (int row = 0; row < this.board.length; row++) {
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
    }

    public void initializeBoard() {
        /*
        *
        * inner numbers refer to array indices where
        * rows are the outer array and columns
        * are the inner array
        *
        * markings outside indices refer to
        * corresponding chess positions
        *
        * 8  0
        * 7  1
        * 6  2
        * 5  3
        * 4  4
        * 3  5
        * 2  6
        * 1  70  1  2  3  4  5  6  7
        *     A  B  C  D  E  F  G  H
        *
        *     Starting layout
        * [[R, K, B, Q, K, B, K, R]
        *  [p, p, p, p, p, p, p, p]
        *  [ ,  ,  ,  ,  ,  ,  ,  ]
        *  [ ,  ,  ,  ,  ,  ,  ,  ]
        *  [ ,  ,  ,  ,  ,  ,  ,  ]
        *  [ ,  ,  ,  ,  ,  ,  ,  ]
        *  [p, p, p, p, p, p, p, p]
        *  [R, K, B, Q, K, B, K, R]]
        * */

        for (int row = 0; row < this.board.length; row++) {
            if (row == 0 || row == 7) { // back rows
                this.board[row][0] = new Rook();
                this.board[row][1] = new Knight();
                this.board[row][2] = new Bishop();
                this.board[row][3] = new Queen();
                this.board[row][4] = new King();
                this.board[row][5] = new Bishop();
                this.board[row][6] = new Knight();
                this.board[row][7] = new Rook();
            } else if (row == 1 || row == 6) { // pawns
                for (int col = 0; col < this.board[row].length; col++) {
                    this.board[row][col] = new Pawn();
                }
            }

        }
    }

    public void movePiece(String startPos, String endPos) {
        // get each component of a position
        String startRowPos = String.valueOf(startPos.charAt(1));
        String startColPos = String.valueOf(startPos.charAt(0));
        String endRowPos = String.valueOf(endPos.charAt(1));
        String endColPos = String.valueOf(endPos.charAt(0));

        // convert components to their array index values
        int startRowIdx = posToRowIdx.get(startRowPos);
        int startColIdx = posToColIdx.get(startColPos);
        int endRowIdx = posToRowIdx.get(endRowPos);
        int endColIdx = posToColIdx.get(endColPos);

        // move the piece
        this.board[endRowIdx][endColIdx] = this.board[startRowIdx][startColIdx];
        this.board[startRowIdx][startColIdx] = null; // remove the original position of the piece

        // give the last move
        System.out.println("Your move: " + this.board[endRowIdx][endColIdx].getType() + " to " + endPos);

    }
}
