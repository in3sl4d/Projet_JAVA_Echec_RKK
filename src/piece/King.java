package piece;

import game.Board;
import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public class King implements IPiece {

    private boolean isWhite;

    public King(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public ArrayList<Move> allMoves(Board board, Coordinate coord) {
        return null;
    }

    @Override
    public boolean getIsWhite() {
        return isWhite;
    }

    public String toString()
    {
        return "K" + (isWhite ? "W" : "B");
    }

}
