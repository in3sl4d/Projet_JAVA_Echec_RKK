import game.Game;
import game.Ibot;
import ia.Bot;
import ia.Evaluate;
import ia.MoveSearch;
import move.Move;
import move.coordinate.Coordinate;

public class Test {

    public static void main(String[] args)
    {
        Game g = new Game("r2qk2r/2P2ppp/4b3/B1np1n2/4P1Q1/p3N3/4BPPP/1R3RK1 w - - 0 1");
        Ibot bot = new Bot(5);
        System.out.println(bot.bestMove(g, true));
    }

}
