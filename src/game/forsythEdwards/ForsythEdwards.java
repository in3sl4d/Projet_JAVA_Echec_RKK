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
    private static String[] allPS = {"k", "r"};

    public ForsythEdwards(String strF) {
        int x = 7;
        int y = 0;
        List<String> allp = Arrays.asList(allPS);
        coordonateList = new ArrayList<>();
        pieces = new ArrayList<>();
        char[] chars = strF.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            String s = "" + chars[i];
            if(s.equals(" ")) break;
            if(allp.contains(s.toLowerCase())) {
                pieces.add(PieceFactorie.createPiece(s.toLowerCase(), Character.isUpperCase(chars[i + 1])));
                coordonateList.add(new Coordinate(x, y));
                i++;y++;
                if(y >= 7){ x --; y = 0; }
            } else if (s.equals("/")) {
                ++i;
            }
            if(Character.isDigit(chars[i])) {
                for(int j = 0; j < Integer.parseInt(s); j++) {
                    y++;
                    if(y >= 7)x --; y = 0;
                }
            }
        }
    }

    public ArrayList<IPiece> getPieces() {
        return pieces;
    }

    public ArrayList<Coordinate> getCoordonateList() {
        return coordonateList;
    }
}
