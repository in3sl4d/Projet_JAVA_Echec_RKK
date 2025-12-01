package game;

import game.forsythEdwards.ForsythEdwards;
import move.Move;
import move.coordinate.Coordinate;
import piece.King;

import java.util.ArrayList;

public class Board {

    private final static int BOARD_SIZE = 8;
    private IPiece[][] board;
    private ArrayList<IPiece> allWhitePieces;
    private ArrayList<IPiece> allBlackPieces;
    private IPiece whiteKing;
    private IPiece blackKing;

    public Board() {
        board = new IPiece[BOARD_SIZE][BOARD_SIZE];
        allWhitePieces = new ArrayList<>();
        allBlackPieces = new ArrayList<>();
    }

    private void addPieces(ArrayList<IPiece> pieces, ArrayList<Coordinate> co) {
        if(pieces.size() != co.size()) throw new IllegalArgumentException("each pawn needs to have assigned coordinate");
        for (int i = 0; i < pieces.size() - 1; i++) {
            IPiece p = pieces.get(i);
            Coordinate c = co.get(i);
            if(!c.isLegit()) throw new IllegalArgumentException("coordinate needs to be legit");
            if(p != null) putPiece(p, c.toString());
        }
    }

    public Board(String strf) {
        this();
        ForsythEdwards f = new ForsythEdwards(strf);
        addPieces(f.getPieces(), f.getCoordonateList());
    }


    private void setWhiteKing(IPiece piece) {
        whiteKing = piece;
    }

    private void setBlackKing(IPiece piece) {
        blackKing = piece;
    }

    private void putPiece(IPiece piece, String coordinate) {
        int[] c = Coordinate.stringToCoordinate(coordinate);
        if (piece instanceof King) {
            if(piece.getIsWhite()) setWhiteKing(piece);
            else setBlackKing(piece);
        }
        board[c[0]][c[1]] = piece;
        if(piece != null && piece.getIsWhite()) allWhitePieces.add(piece);
        else if (piece != null && !piece.getIsWhite()) allBlackPieces.add(piece);
    }

    private void removePiece(IPiece piece) {
        if(piece != null && piece.getIsWhite()) allWhitePieces.remove(piece);
        else if(piece != null && !piece.getIsWhite()) allBlackPieces.remove(piece);
    }

    public IPiece getPieceAt(Coordinate c) {
        return board[c.getX()][c.getY()];
    }

    public IPiece[][] getBoard() {
        return board;
    }

    public void movePieceWithoutRestriction(Move move) {
        Coordinate go = move.getGo();
        Coordinate to = move.getTo();
        IPiece p = getPieceAt(go);
        removePiece(getPieceAt(to));
        putPiece(p, to.toString());
        putPiece(null, go.toString());
    }

    public Coordinate getKingCoordinate(boolean isWhite) {
        IPiece king = isWhite ? whiteKing : blackKing;
        return getPieceCoordiante(king);
    }


    public Coordinate getPieceCoordiante(IPiece piece) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Coordinate c = new Coordinate(i, j);
                IPiece p = getPieceAt(c);
                if(p != null && p == piece) return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = BOARD_SIZE - 1; i >= 0; i--)
        {
            IPiece[] b = board[i];
            sb.append((i + 1) + "  ");
            for (IPiece p : b)
            {
                sb.append((p != null? p.toString() : "  ") + "|");
            }
            sb.append("\n");
        }
        sb.append("   ");
        for (char c = 'A'; c <= 'H'; c++) sb.append(c + "  ");
        return sb.toString();
    }

    public ArrayList<IPiece> getAllWhitePieces() {
        return allWhitePieces;
    }

    public ArrayList<IPiece> getAllBlackPieces() {
        return allBlackPieces;
    }

    public ArrayList<IPiece> getAllColorPieces(boolean isWhite) {
        return isWhite ? allWhitePieces : allBlackPieces;
    }
}