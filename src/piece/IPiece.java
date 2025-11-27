package piece;

import move.Move;
import game.Board;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public interface IPiece {

    ArrayList<Move> allMoves(Board board, Coordinate coord);

    boolean getIsWhite();


}