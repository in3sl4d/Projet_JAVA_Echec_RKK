package test;

import game.Board;
import game.Game;
import game.IPiece;
import move.Move;
import move.coordinate.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Rook;

import java.util.ArrayList;

class PieceTest {

    @Test
    void testRookMovementEmptyBoard() {
        Board board = new Board();

        board.loadFen("8/8/8/8/3R4/8/8/8 w");

        Coordinate center = new Coordinate("d4");
        IPiece p = board.getPieceAt(center);

        ArrayList<Move> moves = p.allMoves(board, center);

        // Une tour au centre d'un plateau vide a 14 mouvements possibles (7 verticaux + 7 horizontaux)
        Assertions.assertEquals(14, moves.size());
    }

    @Test
    void testRookBlockedByFriendly() {
        Board board = new Board("8/8/8/8/8/R7/8/R7 w");

        Coordinate a1 = new Coordinate("a1");
        IPiece rookA1 = board.getPieceAt(a1);

        ArrayList<Move> moves = rookA1.allMoves(board, a1);

        boolean canGoToA3 = moves.stream().anyMatch(m -> m.getTo().toString().equals("a3"));
        boolean canGoToA4 = moves.stream().anyMatch(m -> m.getTo().toString().equals("a4"));
        boolean canGoToA2 = moves.stream().anyMatch(m -> m.getTo().toString().equals("a2"));

        Assertions.assertFalse(canGoToA3);
        Assertions.assertFalse(canGoToA4);
        Assertions.assertTrue(canGoToA2);
    }

    @Test
    void testKingMovement() {
        Board board = new Board("8/8/8/8/8/8/8/K7 w");
        Coordinate a1 = new Coordinate("a1");
        IPiece king = board.getPieceAt(a1);

        Assertions.assertEquals(3, king.allMoves(board, a1).size());
    }

    @Test
    void testWhitePawnStartingPosition() {
        Board board = new Board();
        board.loadFen("8/8/8/8/8/8/4P3/8 w");

        Coordinate start = new Coordinate("e2");
        IPiece pawn = board.getPieceAt(start);
        ArrayList<Move> moves = pawn.allMoves(board, start);

        Assertions.assertEquals(2, moves.size(), "Un pion au départ devrait avoir 2 mouvements si la voie est libre");

        boolean canGoOneStep = moves.stream().anyMatch(m -> m.getTo().toString().equals("e3"));
        boolean canGoTwoSteps = moves.stream().anyMatch(m -> m.getTo().toString().equals("e4"));

        Assertions.assertTrue(canGoOneStep, "Devrait pouvoir avancer de 1 case");
        Assertions.assertTrue(canGoTwoSteps, "Devrait pouvoir avancer de 2 cases au départ");
    }

    @Test
    void testBlackPawnStartingPosition() {
        Board board = new Board();
        board.loadFen("8/3p4/8/8/8/8/8/8 b");

        Coordinate start = new Coordinate("d7");
        IPiece pawn = board.getPieceAt(start);
        ArrayList<Move> moves = pawn.allMoves(board, start);

        Assertions.assertEquals(2, moves.size());

        boolean canGoOneStep = moves.stream().anyMatch(m -> m.getTo().toString().equals("d6"));
        boolean canGoTwoSteps = moves.stream().anyMatch(m -> m.getTo().toString().equals("d5"));

        Assertions.assertTrue(canGoOneStep, "Le pion noir devrait descendre de 1");
        Assertions.assertTrue(canGoTwoSteps, "Le pion noir devrait descendre de 2 au départ");
    }

    @Test
    void testPawnBlockedStraight() {
        // Scénario : Pion blanc en e2, bloqué par un pion noir en e3.
        // Attendu : 0 mouvement (ne peut ni avancer, ni sauter).
        Board board = new Board();
        board.loadFen("8/8/8/8/8/4p3/4P3/8 w");

        Coordinate start = new Coordinate("e2");
        IPiece pawn = board.getPieceAt(start);
        ArrayList<Move> moves = pawn.allMoves(board, start);

        Assertions.assertEquals(0, moves.size(), "Le pion devrait être totalement bloqué");
    }

    @Test
    void testPawnBlockedOnDoubleStep() {
        // Scénario : Pion blanc en e2, case e3 libre, mais case e4 occupée.
        // Attendu : 1 mouvement (e3) seulement. Pas de saut par-dessus.
        Board board = new Board();
        board.loadFen("8/8/8/8/4p3/8/4P3/8 w"); // Pion noir bloque e4

        Coordinate start = new Coordinate("e2");
        IPiece pawn = board.getPieceAt(start);
        ArrayList<Move> moves = pawn.allMoves(board, start);

        boolean canGoOneStep = moves.stream().anyMatch(m -> m.getTo().toString().equals("e3"));
        boolean canGoTwoSteps = moves.stream().anyMatch(m -> m.getTo().toString().equals("e4"));

        Assertions.assertTrue(canGoOneStep);
        Assertions.assertFalse(canGoTwoSteps, "Ne peut pas aller en e4 car la case est occupée");
    }

    @Test
    void testPawnCaptureLogic() {
        Board board = new Board();
        board.loadFen("8/8/8/2p1p3/3P4/8/8/8 w");

        Coordinate start = new Coordinate("d4");
        IPiece pawn = board.getPieceAt(start);
        ArrayList<Move> moves = pawn.allMoves(board, start);

        Assertions.assertEquals(3, moves.size());

        boolean captureLeft = moves.stream().anyMatch(m -> m.getTo().toString().equals("c5"));
        boolean moveForward = moves.stream().anyMatch(m -> m.getTo().toString().equals("d5"));
        boolean captureRight = moves.stream().anyMatch(m -> m.getTo().toString().equals("e5"));

        Assertions.assertTrue(captureLeft, "Devrait pouvoir manger à gauche");
        Assertions.assertTrue(moveForward, "Devrait pouvoir avancer");
        Assertions.assertTrue(captureRight, "Devrait pouvoir manger à droite");
    }

    @Test
    void testPawnCannotCaptureStraight() {
        Board board = new Board();
        board.loadFen("8/8/8/8/4p3/4P3/8/8 w");

        Coordinate start = new Coordinate("e3");
        IPiece pawn = board.getPieceAt(start);
        ArrayList<Move> moves = pawn.allMoves(board, start);

        boolean attacksStraight = moves.stream().anyMatch(m -> m.getTo().toString().equals("e4"));
        Assertions.assertFalse(attacksStraight, "Un pion ne peut pas manger tout droit");
        Assertions.assertEquals(0, moves.size());
    }

    @Test
    void testPawnCannotCaptureFriendly() {
        Board board = new Board();
        board.loadFen("8/8/8/4P3/3P4/8/8/8 w");

        Coordinate start = new Coordinate("d4");
        IPiece pawn = board.getPieceAt(start);
        ArrayList<Move> moves = pawn.allMoves(board, start);

        boolean attacksFriendly = moves.stream().anyMatch(m -> m.getTo().toString().equals("e5"));
        Assertions.assertFalse(attacksFriendly, "Ne peut pas manger un allié");
    }

    @Test
    void testKnightMovement() {
        // Scénario : Cavalier blanc au centre (d4).
        // Attendu : 8 mouvements possibles sur un plateau vide.
        Board board = new Board();
        board.loadFen("8/8/8/8/3N4/8/8/8 w");

        Coordinate center = new Coordinate("d4");
        IPiece knight = board.getPieceAt(center);

        Assertions.assertEquals(8, knight.allMoves(board, center).size());
    }

    @Test
    void testKnightCorner() {
        Board board = new Board();
        board.loadFen("8/8/8/8/8/8/8/N7 w");

        Coordinate corner = new Coordinate("a1");
        IPiece knight = board.getPieceAt(corner);

        ArrayList<Move> moves = knight.allMoves(board, corner);
        Assertions.assertEquals(2, moves.size());

        boolean b3 = moves.stream().anyMatch(m -> m.getTo().toString().equals("b3"));
        boolean c2 = moves.stream().anyMatch(m -> m.getTo().toString().equals("c2"));

        Assertions.assertTrue(b3);
        Assertions.assertTrue(c2);
    }

    @Test
    void testKnightJumping() {
        Board board = new Board();
        board.loadFen("8/8/8/8/8/8/PP6/N7 w");

        Coordinate start = new Coordinate("a1");
        IPiece knight = board.getPieceAt(start);
        ArrayList<Move> moves = knight.allMoves(board, start);

        boolean canJumpToB3 = moves.stream().anyMatch(m -> m.getTo().toString().equals("b3"));

        // Cela va maintenant passer à TRUE car b3 est vide
        Assertions.assertTrue(canJumpToB3, "Le cavalier doit pouvoir aller en b3 même s'il est entouré en a2/b2");

        // Vérifions aussi l'autre mouvement possible (c2)
        boolean canJumpToC2 = moves.stream().anyMatch(m -> m.getTo().toString().equals("c2"));
        Assertions.assertTrue(canJumpToC2);
    }


}