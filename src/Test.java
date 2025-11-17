import game.Game;
import move.Move;

public class Test {

    public static void main(String[] args)
    {
        Game g = new Game();
        System.out.println(g.toString());
        System.out.println(Move.CoordinateToString(1, 4, 3, 4));
    }

}
