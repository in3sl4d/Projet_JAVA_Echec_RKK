package piece;

import game.Board;
import move.Move;

import java.util.ArrayList;

public class Rook implements IPiece {

    private boolean isWhite;

    public Rook(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public ArrayList<Move> allMoves(Board board) {
        return null;
    }

    @Override
    public boolean getIsWhite() {
        return isWhite;
    }

    public String toString()
    {
        return "R" + (isWhite ? "W" : "B");
    }

}
