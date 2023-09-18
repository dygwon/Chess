package Gwon.Personal.pieces;

import java.util.List;

abstract public class Piece {
    private final String icon;
    private final String type;
    private int numMoves = 0;
    private final String player;

    public Piece(String type, String icon, String side) {
        this.type = type;
        this.icon = icon;
        this.player = side;
    }

    public String getType() {
        return this.type;
    }

    public String getIcon() {
        return this.icon;
    }

    public void incrementMoves() {
        this.numMoves++;
    }

    public int getNumMoves() {
        return this.numMoves;
    }

    public String getSide() { return this.player; }

    abstract protected List<int[]> getPossiblePositions(Piece[][] board, int startRowIdx, int startColIdx);

    abstract public boolean isValidMove(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx);

    abstract public boolean isObstructed(Piece[][] board, int startRowIdx, int startColIdx, int endRowIdx, int endColIdx);
}
