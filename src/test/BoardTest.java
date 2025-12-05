package test;

import game.Board;
import game.IPiece;
import move.coordinate.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.King;
import piece.Rook;

class BoardTest {

    @Test
    void testBoardInitializationWithFen() {
        String fen = "r3k3/8/8/3R4/8/8/8/R3K3 w";

        Board board = new Board(fen);

        Coordinate cA1 = new Coordinate("a1");
        IPiece pieceA1 = board.getPieceAt(cA1);

        Assertions.assertNotNull(pieceA1, "La case A1 ne doit pas être vide");
        Assertions.assertTrue(pieceA1 instanceof Rook, "A1 doit être une Tour");
        Assertions.assertTrue(pieceA1.getIsWhite(), "La Tour en A1 doit être blanche");

        Coordinate cB1 = new Coordinate("b1");
        Assertions.assertNull(board.getPieceAt(cB1), "La case B1 doit être vide");

        Coordinate cA8 = new Coordinate("a8");
        IPiece pieceA8 = board.getPieceAt(cA8);
        Assertions.assertNotNull(pieceA8);
        Assertions.assertFalse(pieceA8.getIsWhite(), "La Tour en A8 doit être noire");

        // 4. Vérifier la position précise (D5 - milieu) pour être sûr que x/y ne sont pas inversés
        Coordinate cD5 = new Coordinate("d5");
        IPiece pieceD5 = board.getPieceAt(cD5);
        Assertions.assertNotNull(pieceD5, "Il doit y avoir une tour en D5");
        Assertions.assertTrue(pieceD5 instanceof Rook);
    }

}