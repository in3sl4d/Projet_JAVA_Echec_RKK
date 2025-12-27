package entities.piece;

import entities.Board;
import entities.IPiece;
import entities.move.Move;
import entities.move.coordinate.Coordinate;

import java.util.ArrayList;

public abstract class Piece implements IPiece {

    private boolean isWhite;
    private PieceType type;

    public Piece(boolean isWhite, PieceType type) {
        this.isWhite = isWhite;
        this.type = type;
    }

    @Override
    public boolean getIsWhite() {
        return isWhite;
    }

    public PieceType getType()
    {
        return type;
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

    public boolean canBeChecked() {
        return false;
    }

    @Override
    public String toString() {
        switch (type) {
            case PAWN: return "p";
            case ROOK: return "r";
            case KNIGHT: return "n";
            case BISHOP: return "b";
            case QUEEN: return "q";
            case KING: return "k";
            default: return "?";
        }
    }
}
