package Gwon.Personal;

import Gwon.Personal.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String side;
    private List<Piece> capturedPieces = new ArrayList<>();
    private final int backRow;
    private final int pawnRow;

    public Player(String side) {
        this.side = side;

        if (side.equals("Black")) {
            this.backRow = 0;
            this.pawnRow = 1;
        } else {
            this.backRow = 7;
            this.pawnRow = 6;
        }
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void addCapturedPiece(Piece piece) {
        capturedPieces.add(piece);
    }

    public String getSide() {
        return side;
    }

    public int getBackRow() {
        return backRow;
    }

    public int getPawnRow() {
        return pawnRow;
    }

    @Override
    public String toString() {
        return side;
    }
}
