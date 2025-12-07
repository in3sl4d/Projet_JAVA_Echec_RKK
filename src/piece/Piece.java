package piece;

import game.Board;
import game.IPiece;
import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public abstract class Piece implements IPiece {

    private boolean isWhite;
    private String repre;

    public Piece(boolean isWhite, String repre) {
        this.isWhite = isWhite;
        this.repre = repre;
    }

    @Override
    public boolean getIsWhite() {
        return isWhite;
    }

    public String toString()
    {
        return repre + (isWhite ? "W" : "B");
    }

    protected ArrayList<Move> getMovesDirection(int[] xDirection, int[] yDirection, Coordinate coord, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int cx, cy;
        int x = coord.getX(), y = coord.getY();
        for (int i = 0; i < xDirection.length; i++)
        {
            cx = x;
            cy = y;
            while(true){
                cx += xDirection[i];
                cy += yDirection[i];
                if(Coordinate.coordonateIsLegit(cx, cy))
                {
                    Coordinate c = new Coordinate(cx, cy);
                    IPiece p = board.getPieceAt(c);
                    if(p == null)
                    {
                        moves.add(new Move(coord, c));
                    }
                    else if(p.getIsWhite() == this.getIsWhite()) break;
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
}
