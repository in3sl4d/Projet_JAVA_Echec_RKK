package domain;

import domain.move.Move;
import domain.move.coordinate.Coordinate;
import domain.piece.PieceType;

import java.util.ArrayList;

public interface IPiece {

    ArrayList<Move> allMoves(Board board, Coordinate coord);

    boolean getIsWhite();

    String toString();

    boolean canBeChecked();

    PieceType getType();
}