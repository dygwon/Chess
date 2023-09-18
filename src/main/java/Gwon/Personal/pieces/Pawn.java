package Gwon.Personal.pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(String side) { super("Pawn", "p", side); }

    @Override
    protected List<int[]> getPossiblePositions(Piece[][] board, int startRowIdx, int startColIdx) {
        /* Returns all the valid moves but does not check for obstructions or for out-of-bounds indices */
        List<int[]> possiblePositions = new ArrayList<>();

        // direction of movement dependent on what side pawn starts on
        int direction = this.getSide().equals("White") ? -1 : 1;

        // pawns can move two spaces on first move
        if (this.getNumMoves() == 0)
            possiblePositions.add(new int[] { startRowIdx + (direction * 2), startColIdx });

        // forward space not out of bounds
        if (startRowIdx + direction < board.length && startRowIdx + direction >= 0)
            possiblePositions.add(new int[] { startRowIdx + direction, startColIdx });

        // capture not out of bounds
        if (startColIdx + 1 < board[0].length)
            possiblePositions.add(new int[] { startRowIdx + direction, startColIdx + 1});
        if (startColIdx - 1 >= 0)
            possiblePositions.add(new int[] { startRowIdx + direction, startColIdx - 1});

        return possiblePositions;
    }

    @Override
    public boolean isValidMove(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx) {

        // check if the given move is possible given what pawns can do
        List<int[]> possiblePositions = this.getPossiblePositions(board, startRowIdx, startColIdx);
        boolean isPositionPossible = possiblePositions
                .stream()
                .anyMatch(e -> Arrays.equals(e, new int[] { endRowIdx, endColIdx }));
        if (!isPositionPossible) {
            return false;
        }

        // diagonal moves only allowed for a capture
        if (Math.abs(endColIdx - startColIdx) == 1 && board[endRowIdx][endColIdx] == null) {
            System.out.println("Pawns can only move diagonally to capture");
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
        // keep this check agnostic to direction
        for (int i = Math.min(startRowIdx, endRowIdx) + 1; i < Math.max(startRowIdx, endRowIdx); i++) {
            if (board[i][startColIdx] != null) {
                return true;
            }
        }
        return false;
    }
}
