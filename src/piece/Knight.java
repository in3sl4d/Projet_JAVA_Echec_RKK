package piece;

import game.Board;
import game.IPiece;
import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(isWhite, PieceType.KNIGHT);
    }


    @Override
    public ArrayList<Move> allMoves(Board board, Coordinate coord) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] captureOffsetsX = {2, 2, -2, -2, 1, 1, -1, -1};
        int[] captureOffsetsY = {1, -1, 1, -1, 2, -2, 2, -2};
        int x = coord.getX(), y = coord.getY();


        for( int i = 0; i < captureOffsetsX.length; i++ ) {
            int xForward = x + captureOffsetsX[i];
            int yForward = y + captureOffsetsY[i];
            if(Coordinate.coordonateIsLegit(xForward, yForward)) {
                Coordinate c = new Coordinate(xForward, yForward);
                IPiece p = board.getPieceAt(c);
                if(p == null || p.getIsWhite() != super.getIsWhite()) {
                    moves.add(new Move(coord, c));
                }
            }
        }
        return moves;
    }
}
