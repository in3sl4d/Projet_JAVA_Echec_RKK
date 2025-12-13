package game;

import move.Move;
import move.coordinate.Coordinate;
import piece.PieceType;

import java.util.ArrayList;

public interface IPiece {

    ArrayList<Move> allMoves(Board board, Coordinate coord);

    boolean getIsWhite();

    String toString();

    boolean canBeChecked();

    PieceType getType();
}