package entities.piece;

import entities.Board;
import entities.IPiece;
import interfaceAdapters.forsythEdwards.ForsythEdwards;
import entities.move.Move;
import entities.move.coordinate.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class PieceTest {

    @Test
    void testRookMovementEmptyBoard() {
        Board board = new Board();

        board.loadGame(new ForsythEdwards("8/8/8/8/3R4/8/8/8 w"));

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
        Board board = new Board(new ForsythEdwards("8/8/8/8/8/8/8/K7 w"));
        Coordinate a1 = new Coordinate("a1");
        IPiece king = board.getPieceAt(a1);

        Assertions.assertEquals(3, king.allMoves(board, a1).size());
    }

    @Test
    void testKingCoin(){
        Board board = new Board(new ForsythEdwards("8/7k/8/8/K7/8/8/8 b - - 0 1"));
        Coordinate a4 = new Coordinate("a4");
        IPiece king = board.getPieceAt(a4);

        Assertions.assertEquals(5, king.allMoves(board, a4).size());
    }

    @Test
    void testKingCenter()
    {
        Board board = new Board(new ForsythEdwards("8/7k/8/8/4K3/8/8/8 b - - 0 1"));
        Coordinate e4 = new Coordinate("e4");
        IPiece king = board.getPieceAt(e4);

        Assertions.assertEquals(8, king.allMoves(board, e4).size());
    }

    @Test
    void testWhitePawnStartingPosition() {
        Board board = new Board();
        board.loadGame(new ForsythEdwards("8/8/8/8/8/8/4P3/8 w"));

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
        board.loadGame(new ForsythEdwards("8/3p4/8/8/8/8/8/8 b"));

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
        board.loadGame(new ForsythEdwards("8/8/8/8/8/4p3/4P3/8 w"));

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
        board.loadGame(new ForsythEdwards("8/8/8/8/4p3/8/4P3/8 w")); // Pion noir bloque e4

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
        board.loadGame(new ForsythEdwards("8/8/8/2p1p3/3P4/8/8/8 w"));

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
        board.loadGame(new ForsythEdwards("8/8/8/8/4p3/4P3/8/8 w"));

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
        board.loadGame(new ForsythEdwards("8/8/8/4P3/3P4/8/8/8 w"));

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
        board.loadGame(new ForsythEdwards("8/8/8/8/3N4/8/8/8 w"));

        Coordinate center = new Coordinate("d4");
        IPiece knight = board.getPieceAt(center);

        Assertions.assertEquals(8, knight.allMoves(board, center).size());
    }

    @Test
    void testKnightCorner() {
        Board board = new Board();
        board.loadGame(new ForsythEdwards("8/8/8/8/8/8/8/N7 w"));

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
        board.loadGame(new ForsythEdwards("8/8/8/8/8/8/PP6/N7 w"));

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

    @Test
    void testBishopFreeMovement() {
        Board board = new Board();
        board.loadGame(new ForsythEdwards("8/8/8/8/3B4/8/8/8 w"));

        Coordinate center = new Coordinate("d4");
        IPiece bishop = board.getPieceAt(center);

        ArrayList<Move> moves = bishop.allMoves(board, center);

        // Diagonales complètes : 7 cases (a1-h8) + 6 cases (a7-g1) = 13 cases
        Assertions.assertEquals(13, moves.size());
    }

    @Test
    void testBishopBlockedAndCapture() {

        Board board = new Board();
        board.loadGame(new ForsythEdwards("8/8/8/4P3/3B4/2p5/8/8 w"));

        Coordinate start = new Coordinate("d4");
        IPiece bishop = board.getPieceAt(start);
        ArrayList<Move> moves = bishop.allMoves(board, start);

        boolean canGoToFriend = moves.stream().anyMatch(m -> m.getTo().toString().equals("e5"));
        boolean canGoBehindFriend = moves.stream().anyMatch(m -> m.getTo().toString().equals("f6"));

        Assertions.assertFalse(canGoToFriend, "Ne doit pas manger son ami en e5");
        Assertions.assertFalse(canGoBehindFriend, "Ne doit pas sauter par-dessus son ami en f6");

        boolean canCaptureEnemy = moves.stream().anyMatch(m -> m.getTo().toString().equals("c3"));
        boolean canGoBehindEnemy = moves.stream().anyMatch(m -> m.getTo().toString().equals("b2"));

        Assertions.assertTrue(canCaptureEnemy, "Doit pouvoir manger l'ennemi en c3");
        Assertions.assertFalse(canGoBehindEnemy, "S'arrête après la capture (ne va pas en b2)");
    }

    @Test
    void testQueenCenterEmptyBoard() {
        Board board = new Board();
        board.loadGame(new ForsythEdwards("8/8/8/8/3Q4/8/8/8 w"));

        Coordinate center = new Coordinate("d4");
        IPiece queen = board.getPieceAt(center);

        ArrayList<Move> moves = queen.allMoves(board, center);

        Assertions.assertEquals(27, moves.size(), "La reine au centre doit avoir 27 mouvements");
    }

    @Test
    void testQueenCorner() {
        Board board = new Board();
        board.loadGame(new ForsythEdwards("8/8/8/8/8/8/8/Q7 w"));

        Coordinate corner = new Coordinate("a1");
        IPiece queen = board.getPieceAt(corner);

        ArrayList<Move> moves = queen.allMoves(board, corner);

        Assertions.assertEquals(21, moves.size());
    }

    @Test
    void testQueenMixedCaptureAndBlock() {
        Board board = new Board();
        board.loadGame(new ForsythEdwards("8/6p1/8/3P4/3Q4/8/8/8 w"));

        Coordinate start = new Coordinate("d4");
        IPiece queen = board.getPieceAt(start);
        ArrayList<Move> moves = queen.allMoves(board, start);

        boolean canGoUp = moves.stream().anyMatch(m -> m.getTo().toString().equals("d5"));
        boolean canGoThrough = moves.stream().anyMatch(m -> m.getTo().toString().equals("d6"));

        Assertions.assertFalse(canGoUp, "Bloquée par le pion ami devant elle");
        Assertions.assertFalse(canGoThrough, "Ne peut pas sauter par-dessus l'ami");

        boolean canCapture = moves.stream().anyMatch(m -> m.getTo().toString().equals("g7"));
        boolean canGoBehind = moves.stream().anyMatch(m -> m.getTo().toString().equals("h8"));

        Assertions.assertTrue(canCapture, "Doit pouvoir manger en diagonale (g7)");
        Assertions.assertFalse(canGoBehind, "S'arrête après la capture (pas de h8)");
    }

}