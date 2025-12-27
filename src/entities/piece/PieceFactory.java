package entities.piece;

import entities.IPiece;

public class PieceFactory {

    public static IPiece createPiece(PieceType type, boolean isWhite) {
        if(type == null) return null;
        switch (type) {
            case KING:
                return new King(isWhite);
            case ROOK:
                return new Rook(isWhite);
            case PAWN:
                return new Pawn(isWhite);
            case KNIGHT:
                return new Knight(isWhite);
            case BISHOP:
                return new Bishop(isWhite);
            case QUEEN:
                return new Queen(isWhite);
            default:
                return null;
        }
    }

}
