package test;

import usercase.Game;
import interfaceAdapters.forsythEdwards.ForsythEdwards;
import entities.move.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void testIsChecked() {
        Game game = new Game(new ForsythEdwards("4r3/8/8/8/1k6/8/8/4K3 w - - 0 1"));

        Assertions.assertTrue(game.isChecked(true));
        Assertions.assertFalse(game.isChecked(false));
    }

    @Test
    void testPinning() {
        Game game = new Game(new ForsythEdwards("4r3/8/8/8/1k6/8/4R3/4K3 w - - 0 1"));

        Move illegalSideMove = new Move("e2 - d2");
        Assertions.assertFalse(game.canMovePiece(illegalSideMove), "Mouvement latéral interdit (clouage)");

        Move legalVerticalMove = new Move("e2 - e4");
        Assertions.assertTrue(game.canMovePiece(legalVerticalMove), "Mouvement vertical autorisé");
    }

    @Test
    void testTurnSwitch() {
        Game game = new Game(new ForsythEdwards("K7/8/8/8/8/8/7r/7k b - - 0 1"));

        Assertions.assertFalse(game.getBoard().isWhiteTurn());

        game.play(new Move("h2 - h3"));

        Assertions.assertTrue(game.getBoard().isWhiteTurn(), "Le tour doit passer aux blancs");
    }
}