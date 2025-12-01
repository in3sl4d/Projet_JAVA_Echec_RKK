import game.Game;
import move.Move;
import move.coordinate.Coordinate;

public class Test {

    public static void main(String[] args)
    {
        Game g = new Game("4k3/2R5/4K3/8/8/8/8/8 b - - 0 1");
        System.out.println(g.toString());
        System.out.println(g.willBeCheckedMate(false, new Move("C7 - C8")));
    }

}
