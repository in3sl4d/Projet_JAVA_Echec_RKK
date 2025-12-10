package piece;

import game.Board;
import game.IPiece;
import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public class Rook extends Piece implements IPiece {


    public Rook(boolean isWhite) {
        super(isWhite, "r");
    }

    @Override
    public ArrayList<Move> allMoves(Board board, Coordinate coord) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] m = {1, -1, 0, 0};
        int[] mi = {0, 0, 1, -1};
        moves.addAll(super.getMovesDirection(m, mi, coord, board));
        return moves;
    }


}
