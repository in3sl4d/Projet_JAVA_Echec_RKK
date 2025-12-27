package entities;

import entities.move.Move;
import entities.move.coordinate.Coordinate;
import entities.piece.PieceType;

import java.util.ArrayList;

public interface IPiece {

    ArrayList<Move> allMoves(Board board, Coordinate coord);

    boolean getIsWhite();

    String toString();

    boolean canBeChecked();

    PieceType getType();
}