package piece;

import game.Board;
import game.IPiece;
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
        ArrayList<Move> moves = new ArrayList<>();
        IPiece actuelle = null;
        int x =  coord.getX(), y = coord.getY();
        int cx, cy;
        int[] m = {1, -1, 0, 0};
        int[] mi = {0, 0, 1, -1};
        for (int i = 0; i < m.length; i++)
        {
            cx = x;
            cy = y;
            while(true){
                cx += m[i];
                cy += mi[i];
                if(Coordinate.coordonateIsLegit(cx, cy))
                {
                    Coordinate c = new Coordinate(cx, cy);
                    IPiece p = board.getPieceAt(c);
                    if(p == null)
                    {
                        moves.add(new Move(coord, c));
                    }
                    else if(p.getIsWhite() == isWhite) break;
                    else {
                        moves.add(new Move(coord, c));
                        break;
                    }
                }
                else
                {
                    break;
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
        return "R" + (isWhite ? "W" : "B");
    }

}
