import game.Game;
import move.Move;
import move.coordinate.Coordinate;

public class Test {

    public static void main(String[] args)
    {
        Game g = new Game();
        System.out.println(g.toString());
        g.getBoard().movePieceWithoutRestriction(new Move("A1 - E5"));
        System.out.println();
        System.out.println(g.toString());
        for (Move m : g.getBoard().getPieceAt(new Coordinate("A2")).allMoves(g.getBoard(), new Coordinate("A2"))) {
            System.out.println(m.toString());
        }
    }

}
