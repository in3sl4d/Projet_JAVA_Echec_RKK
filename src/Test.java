import usercase.Game;
import usercase.Ibot;
import interfaceAdapters.forsythEdwards.ForsythEdwards;
import interfaceAdapters.Bot;

public class Test {

    public static void main(String[] args)
    {
        Game g = new Game(new ForsythEdwards("r1bqkr2/ppp3pp/4p3/8/1P2P1PP/8/P4P2/nN3K1R w - - 0 1"));
        Ibot bot = new Bot(4);
        System.out.println(bot.bestMove(g, true));
    }

}
