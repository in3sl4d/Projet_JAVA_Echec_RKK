package domain.piece;

import domain.Board;
import domain.IPiece;
import domain.move.Move;
import domain.move.coordinate.Coordinate;

import java.util.ArrayList;

public class King extends Piece implements IPiece {

    public King(boolean isWhite) {
        super(isWhite, PieceType.KING);
    }

    @Override
    public ArrayList<Move> allMoves(Board board, Coordinate coord) {
        ArrayList<Move> moves = new ArrayList<>();
        int x =  coord.getX(), y = coord.getY();
        int[] xx = {1, 1, 1, 0 ,0 ,-1 ,-1 ,-1};
        int[] yy = {0, -1, 1, 1 ,-1 ,0, -1 ,1};
        for (int i = 0; i < xx.length; i++) {
            int cx = x + xx[i], cy = y + yy[i];
            if(Coordinate.coordonateIsLegit(cx, cy)) {
                Coordinate c = new Coordinate(cx, cy);
                if (board.getPieceAt(c) == null) moves.add(new Move(coord, c));
                else {
                    IPiece p = board.getPieceAt(c);
                    if(p.getIsWhite() != super.getIsWhite()) moves.add(new Move(coord, c));
                }
            }
        }

        if(board.canCastle("K"))
        {
            if(board.getPieceAt(new Coordinate(x, 5)) == null && board.getPieceAt(new Coordinate(x, 6)) == null)
            {
                Move kingMove = new Move(coord, new Coordinate(x, 6));

                Move rookMove = new Move(new Coordinate(x, 7), new Coordinate(x, 5));

                kingMove.setNextMove(rookMove);
                moves.add(kingMove);
            }
        }

        //faire les autre rock
        return moves;
    }

    @Override
    public boolean canBeChecked() {
        return true;
    }
}
