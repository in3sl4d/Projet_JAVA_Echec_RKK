package piece;

import game.Board;
import game.IPiece;
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
        ArrayList<Move> moves = new ArrayList<>();
        IPiece actuelle = null;
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
                    if(p.getIsWhite() != isWhite) moves.add(new Move(coord, c));
                }
            }
        }
        return moves;
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
