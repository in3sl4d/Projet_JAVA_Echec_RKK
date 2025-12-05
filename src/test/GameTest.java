package test;

import static org.junit.jupiter.api.Assertions.*;
import game.Game;
import move.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameTest {

    @Test
    void testIsChecked() {
        Game game = new Game("4r3/8/8/8/1k6/8/8/4K3 w - - 0 1");

        // Le roi blanc (true) devrait être en échec
        Assertions.assertTrue(game.isChecked(true), "Le roi blanc devrait être en échec par la tour noire");
        // Le roi noir (false) ne l'est pas
        Assertions.assertFalse(game.isChecked(false), "Le roi noir ne devrait pas être en échec");
    }

    @Test
    void testPinning( ) {
        Game game = new Game("4r3/8/8/8/1k6/8/4R3/4K3 b - - 0 1");

        // En réalité, testons un coup illégal spécifique via canMovePiece
        Move illegalSideMove = new Move("e2 - d3");
        Assertions.assertFalse(game.canMovePiece(illegalSideMove), "La tour ne devrait pas pouvoir bouger latéralement car elle est clouée");

        Move legalVerticalMove = new Move("e2 - e4");
        Assertions.assertTrue(game.canMovePiece(legalVerticalMove), "La tour devrait pouvoir avancer vers la menace");
    }

    @Test
    void testTurnSwitch() {
        // Vérifie que le tour change après un coup
        Game game = new Game("K7/8/8/8/8/8/7r/7k w - - 0 1"); // Tour blanche A1, Roi E1
        Assertions.assertTrue(game.getBoard().isWhiteTurn());

        game.play(new Move("h2 - h3"));

        Assertions.assertFalse(game.getBoard().isWhiteTurn(), "Ce doit être au tour des noirs maintenant");
    }

}