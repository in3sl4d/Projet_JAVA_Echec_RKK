package infrastructure.ia;

import app.Game;
import app.Ibot;
import domain.move.Move;

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
