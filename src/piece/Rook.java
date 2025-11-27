package piece;

import game.Board;
import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public class Rook implements IPiece {

    private boolean isWhite;

    public Rook(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public ArrayList<Move> allMoves(Board board, Coordinate coord) {
        IPiece[][] boardCopy = board.getBoard();
        ArrayList<Move> moves = new ArrayList<>();
        IPiece actuelle = null;
        int[] c = { coord.getX(), coord.getY() };
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
