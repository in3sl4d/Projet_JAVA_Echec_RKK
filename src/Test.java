import game.Game;
import move.Move;
import move.coordinate.Coordinate;

public class Test {

    public static void main(String[] args)
    {
        Game g = new Game();
        System.out.println(g.toString());
        System.out.println(g.isChecked(true));
        g.getBoard().movePieceWithoutRestriction(new Move("A2 - H2"));
        System.out.println(g.toString());
        System.out.println(g.isChecked(true));
    }

}
