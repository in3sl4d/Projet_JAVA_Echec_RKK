package piece;

import game.IPiece;

public class PieceFactorie {

    public static IPiece createPiece(String pieceName, boolean isWhite) {
        if(pieceName == null || pieceName.isEmpty()) return null;
        if(pieceName.equals("k")) return new King(isWhite);
        else if (pieceName.equals("r")) return new Rook(isWhite);
        return null;
    }

}
