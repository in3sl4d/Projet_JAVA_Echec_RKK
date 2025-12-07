package piece;

import game.IPiece;

public abstract class Piece implements IPiece {

    private boolean isWhite;
    private String repre;

    public Piece(boolean isWhite, String repre) {
        this.isWhite = isWhite;
        this.repre = repre;
    }

    @Override
    public boolean getIsWhite() {
        return isWhite;
    }

    public String toString()
    {
        return repre + (isWhite ? "W" : "B");
    }
}
