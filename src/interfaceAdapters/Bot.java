package interfaceAdapters;

import usercase.Game;
import usercase.Ibot;
import entities.move.Move;

public class Bot implements Ibot {

    private int depth;

    public Bot(int depth) {
        this.depth = depth;
    }

    @Override
    public Move bestMove(Game game, boolean isWhite) {
        return MoveSearch.getBestMove(game, depth);
    }

}
