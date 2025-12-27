package usercase;

import entities.move.Move;

public interface Ibot {

    Move bestMove(Game game, boolean isWhite);

}
