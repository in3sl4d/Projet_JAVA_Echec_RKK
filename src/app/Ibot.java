package app;

import domain.move.Move;

public interface Ibot {

    Move bestMove(Game game, boolean isWhite);

}
