package piece;

import game.IPiece;

public class PieceFactory {

    public static IPiece createPiece(String pieceName, boolean isWhite) {
        if(pieceName == null || pieceName.isEmpty()) return null;
        switch (pieceName) {
            case "k":
                return new King(isWhite);
            case "r":
                return new Rook(isWhite);
            case "p":
                return new Pawn(isWhite);
            case "n":
                return new Knight(isWhite);
            default:
                return null;
        }
    }

}
