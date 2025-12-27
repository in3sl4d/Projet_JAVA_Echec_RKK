package test;

import entities.Board;
import interfaceAdapters.forsythEdwards.ForsythEdwards;
import entities.IPiece;
import entities.move.coordinate.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import entities.piece.Rook;

import java.util.ArrayList;

class ForsythEdwardsTest {

    @Test
    void testFenParsing() {
        String fen = "r5k1/8/8/8/3R4/8/8/K7 w";
        ForsythEdwards parser = new ForsythEdwards(fen);

        ArrayList<IPiece> pieces = parser.getPieces();
        ArrayList<Coordinate> coords = parser.getCoordonateList();

        Assertions.assertEquals(pieces.size(), coords.size());
        Assertions.assertEquals(4, pieces.size()); // 2 rois, 2 tours

        // Vérifions la tour blanche en d4
        int indexRook = -1;
        for(int i = 0; i < coords.size(); i++) {
            if(coords.get(i).toString().equals("d4")) {
                indexRook = i;
                break;
            }
        }

        Assertions.assertNotEquals(-1, indexRook, "Il doit y avoir une pièce en d4");
        Assertions.assertTrue(pieces.get(indexRook) instanceof Rook);
        Assertions.assertTrue(pieces.get(indexRook).getIsWhite());
    }

    @Test
    void testboardToFen()
    {
        Board board = new Board("r5k1/8/8/8/3R4/8/8/K7 w");
        Assertions.assertEquals("r5k1/8/8/8/3R4/8/8/K7 w - - 0 1", ForsythEdwards.boardToFen(board));
    }

}