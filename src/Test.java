import game.Game;
import game.Ibot;
import game.forsythEdwards.ForsythEdwards;
import ia.Bot;
import ia.Evaluate;
import ia.MoveSearch;
import move.Move;
import move.coordinate.Coordinate;

public class Test {

    public static void main(String[] args)
    {
        Game g = new Game(new ForsythEdwards("r1bqkr2/ppp3pp/4p3/8/1P2P1PP/8/P4P2/nN3K1R w - - 0 1"));
        Ibot bot = new Bot(4);
        System.out.println(bot.bestMove(g, true));
    }

}
