package Gwon.Personal.pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(String player) {
        super("Bishop", "B", player);
    }

    @Override
    protected List<int[]> getPossiblePositions(Piece[][] board, int startRowIdx, int startColIdx) {
        List<int[]> possiblePositions = new ArrayList<>();

        // check upper diagonal positions
        int colCounter = 1;
        for (int rowIdx = startRowIdx + 1; rowIdx < board.length; rowIdx++) {
            if (startColIdx + colCounter < board[0].length)  // right diagonal
                possiblePositions.add(new int[] { rowIdx, startColIdx + colCounter });
            if (startColIdx - colCounter >= 0) {  // left diagonal
                possiblePositions.add(new int[] { rowIdx, startColIdx - colCounter});
            }
            colCounter++;
        }

        // check lower diagonal positions
        colCounter = 1;
        for (int rowIdx = startRowIdx - 1; rowIdx >= 0; rowIdx--) {
            if (startColIdx + colCounter < board[0].length)  // right diagonal
                possiblePositions.add(new int[] { rowIdx, startColIdx + colCounter });
            if (startColIdx - colCounter >= 0) {  // left diagonal
                possiblePositions.add(new int[] { rowIdx, startColIdx - colCounter});
            }
            colCounter++;
        }

        return possiblePositions;
    }

    @Override
    public boolean isValidMove(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx) {

        // check if the given move is possible given what bishops can do
        List<int[]> possiblePositions = this.getPossiblePositions(board, startRowIdx, startColIdx);
        boolean isPositionPossible = possiblePositions
                .stream()
                .anyMatch(e -> Arrays.equals(e, new int[] { endRowIdx, endColIdx }));
        if (!isPositionPossible) {
            return false;
        }

        // are there any pieces in the way?
        if (this.isObstructed(board, startRowIdx, startColIdx, endRowIdx, endColIdx)) {
            System.out.println("Another piece is in the way");
            return false;
        }

        return true;
    }

    @Override
    public boolean isObstructed(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx) {

        // determine what direction the diagonal is going in
        int rowDirection = endRowIdx > startRowIdx ? 1 : -1;
        int colDirection = endColIdx > startColIdx ? 1 : -1;

        // start searching one position away from the start
        int rowIdx = startRowIdx, colIdx = startColIdx;

        // the distance between the start and end positions will be the same for rows and columns
        for (int i = 0; i < Math.abs(startRowIdx - endRowIdx) - 1; i++) {  // no need to check the end position
            rowIdx += rowDirection;  // no need to check the start position
            colIdx += colDirection;
            if (board[rowIdx][colIdx] != null) return true;
        }

        return false;
    }
}
