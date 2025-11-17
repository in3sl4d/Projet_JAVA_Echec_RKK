package piece;

import game.Board;
import move.Move;
import java.util.ArrayList;

public class King implements IPiece {

    private boolean isWhite;

    public King(boolean isWhite) {
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
        return "K" + (isWhite ? "W" : "B");
    }

}
