package Gwon.Personal.pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {
    public Knight(String player) {
        super("Knight", "k", player);
    }

    @Override
    protected List<int[]> getPossiblePositions(Piece[][] board, int startRowIdx, int startColIdx) {
        List<int[]> possiblePositions = new ArrayList<>();

        if (startRowIdx + 2 < board.length) {                                           //  |-
            if (startColIdx + 1 < board[0].length)                                      //  |
                possiblePositions.add(new int[] { startRowIdx + 2, startColIdx + 1 });
            if (startColIdx - 1 >= 0)                                                   // -|
                possiblePositions.add(new int[] { startRowIdx + 2, startColIdx - 1 });  //  |
        }
        if (startRowIdx - 2 >= 0) {                                                     //  |
            if (startColIdx + 1 < board[0].length)                                      //  |-
                possiblePositions.add(new int[] { startRowIdx - 2, startColIdx + 1 });
            if (startColIdx - 1 >= 0)                                                   //  |
                possiblePositions.add(new int[] { startRowIdx - 2, startColIdx - 1 });  // -|
        }
        if (startRowIdx + 1 < board.length) {
            if (startColIdx + 2 < board[0].length)                                      //  |--
                possiblePositions.add(new int[] { startRowIdx + 1, startColIdx + 2 });
            if (startColIdx - 2 >= 0)                                                   //--|
                possiblePositions.add(new int[] { startRowIdx + 1, startColIdx - 2 });
        }
        if (startRowIdx - 1 >= 0) {
            if (startColIdx + 2 < board[0].length)                                      //  |--
                possiblePositions.add(new int[] { startRowIdx - 1, startColIdx + 2 });
            if (startColIdx - 2 >= 0)                                                   //--|
                possiblePositions.add(new int[] { startRowIdx - 1, startColIdx - 2 });
        }

        return possiblePositions;
    }

    @Override
    public boolean isValidMove(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx) {

        // check if the given move is possible given what knights can do
        List<int[]> possiblePositions = this.getPossiblePositions(board, startRowIdx, startColIdx);
        boolean isPositionPossible = possiblePositions
                .stream()
                .anyMatch(e -> Arrays.equals(e, new int[] { endRowIdx, endColIdx }));

        return isPositionPossible;
    }

    @Override
    public boolean isObstructed(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx) {
        return false;
    }
}
