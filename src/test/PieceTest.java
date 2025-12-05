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

}