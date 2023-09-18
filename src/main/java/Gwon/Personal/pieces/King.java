package Gwon.Personal.pieces;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King (String player) {
        super("King", "K", player);
    }

    @Override
    protected List<int[]> getPossiblePositions(Piece[][] board, int startRowIdx, int startColIdx) {
        List<int[]> possiblePositions = new ArrayList<>();

        return possiblePositions;
    }

    @Override
    public boolean isValidMove(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx) {
        return false;
    }

    @Override
    public boolean isObstructed(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx) {
        return false;
    }
}
