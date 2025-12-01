package game.forsythEdwards;

import move.coordinate.Coordinate;
import game.IPiece;
import piece.PieceFactorie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForsythEdwards {

    private ArrayList<Coordinate> coordonateList;
    private ArrayList<IPiece> pieces;

    public ForsythEdwards(String fen) {

        coordonateList = new ArrayList<>();
        pieces = new ArrayList<>();

        int x = 7;
        int y = 0;

        char[] chars = fen.toCharArray();

        for (char c : chars) {

            if (c == ' ') break;
            if (c == '/') {
                x--;y = 0;
                continue;
            }
            if (Character.isDigit(c)) {
                y += Character.getNumericValue(c);
                continue;
            }
            boolean isWhite = Character.isUpperCase(c);
            char type = Character.toLowerCase(c);
            pieces.add(PieceFactorie.createPiece(String.valueOf(type), isWhite));
            coordonateList.add(new Coordinate(x, y));
            y++;

        }
    }

    public ArrayList<IPiece> getPieces() { return pieces; }
    public ArrayList<Coordinate> getCoordonateList() { return coordonateList; }
}
