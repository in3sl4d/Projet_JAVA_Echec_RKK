package game.forsythEdwards;

import move.coordinate.Coordinate;
import game.IPiece;
import piece.PieceFactory;

import java.util.ArrayList;

public class ForsythEdwards {

    private ArrayList<Coordinate> coordonateList;
    private ArrayList<IPiece> pieces;

    private boolean isWhiteTurn = true;

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
            pieces.add(PieceFactory.createPiece(String.valueOf(type), isWhite));
            coordonateList.add(new Coordinate(x, y));
            y++;

        }

        String[] s = fen.split(" ");
        if (s[1].equals("b")) isWhiteTurn = false;
    }

    public ArrayList<IPiece> getPieces() { return pieces; }
    public ArrayList<Coordinate> getCoordonateList() { return coordonateList; }
    public boolean isWhiteTurn() { return isWhiteTurn; }
}
