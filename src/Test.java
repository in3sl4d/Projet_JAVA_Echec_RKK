import game.Game;
import move.Move;

public class Test {

    public static void main(String[] args)
    {
        Game g = new Game();
        System.out.println(g.toString());
        g.getBoard().movePieceWithoutRestriction(new Move("A1 - H8"));
        System.out.println();
        System.out.println(g.toString());
    }

}
