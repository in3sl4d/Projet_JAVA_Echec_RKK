package game;

import move.Move;

public interface Ibot {

    Move bestMove(Game game, boolean isWhite);

}
