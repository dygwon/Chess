package Gwon.Personal;

abstract public class Piece {
    private final String icon;
    private final String type;
    public Piece(String type, String icon) {
        this.type = type;
        this.icon = icon;
    }

    public String getType() {
        return this.type;
    }

    public String getIcon() {
        return this.icon;
    }
}
