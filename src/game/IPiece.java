package game;

import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public interface IPiece {

    ArrayList<Move> allMoves(Board board, Coordinate coord);

    boolean getIsWhite();


}