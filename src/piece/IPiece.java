package piece;

import move.Move;
import game.Board;

import java.util.ArrayList;

public interface IPiece {

    ArrayList<Move> allMoves(Board board);

    boolean getIsWhite();


}