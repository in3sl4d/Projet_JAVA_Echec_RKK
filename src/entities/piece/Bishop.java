package entities.piece;

import entities.Board;
import entities.move.Move;
import entities.move.coordinate.Coordinate;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite, PieceType.BISHOP);
    }


    @Override
    public ArrayList<Move> allMoves(Board board, Coordinate coord) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] m = {1, 1, -1, -1};
        int[] mi = {1, -1, 1, -1};
        moves.addAll(super.getMovesDirection(m, mi, coord, board));
        return moves;
    }
}
