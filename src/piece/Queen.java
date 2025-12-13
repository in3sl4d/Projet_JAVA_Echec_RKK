package piece;

import game.Board;
import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite, PieceType.QUEEN);
    }


    @Override
    public ArrayList<Move> allMoves(Board board, Coordinate coord) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] m = {1, 1, -1, -1, 1, -1, 0, 0};
        int[] mi = {1, -1, 1, -1, 0, 0, 1, -1};
        moves.addAll(super.getMovesDirection(m, mi, coord, board));
        return moves;
    }
}
