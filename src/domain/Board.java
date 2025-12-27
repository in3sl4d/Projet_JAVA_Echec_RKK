package domain;

import app.IGameLoader;
import infrastructure.ia.forsythEdwards.ForsythEdwards;
import domain.move.Move;
import domain.move.coordinate.Coordinate;
import domain.piece.PieceType;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    public final static int BOARD_SIZE = 8;
    private IPiece[][] board;
    private ArrayList<IPiece> allWhitePieces;
    private ArrayList<IPiece> allBlackPieces;
    private boolean isWhiteTurn = true;
    private HashMap<String, Boolean> canCastle;

    public Board() {
        board = new IPiece[BOARD_SIZE][BOARD_SIZE];
        allWhitePieces = new ArrayList<>();
        allBlackPieces = new ArrayList<>();
    }

    public Board(IGameLoader gameLoader)
    {
        this();
        addPieces(gameLoader.getPieces(), gameLoader.getCoordonateList());
        isWhiteTurn = gameLoader.isWhiteTurn();
        canCastle = gameLoader.getCanRock();
    }

    public Board(String strf) {
        this();
        ForsythEdwards f = new ForsythEdwards(strf);
        addPieces(f.getPieces(), f.getCoordonateList());
        isWhiteTurn = f.isWhiteTurn();
    };

    public Board(Board other) {
        this.board = new IPiece[8][8];
        this.allWhitePieces = new ArrayList<>();
        this.allBlackPieces = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                IPiece p = other.board[i][j];
                this.board[i][j] = p;
                if (p != null) {
                    if (p.getIsWhite()) allWhitePieces.add(p);
                    else allBlackPieces.add(p);

                }
            }
        }
    }

    public void changeTurn(){ isWhiteTurn = !isWhiteTurn; }

    private void addPieces(ArrayList<IPiece> pieces, ArrayList<Coordinate> co) {
        if(pieces.size() != co.size()) throw new IllegalArgumentException("each pawn needs to have assigned coordinate");
        for (int i = 0; i < pieces.size(); i++) {
            IPiece p = pieces.get(i);
            Coordinate c = co.get(i);
            if(!c.isLegit()) throw new IllegalArgumentException("coordinate needs to be legit");
            if(p != null) putPiece(p, c.toString());
        }
    }

    private void putPiece(IPiece piece, String coordinate) {
        int[] c = Coordinate.stringToCoordinate(coordinate);
        board[c[0]][c[1]] = piece;
        if (piece != null) {
            ArrayList<IPiece> list = piece.getIsWhite() ? allWhitePieces : allBlackPieces;
            if (!list.contains(piece)) {
                list.add(piece);
            }
        }
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
        if(move.getNextMove() != null) {
            movePieceWithoutRestriction(move.getNextMove());
        }
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

    private void reset()
    {
        board = new IPiece[BOARD_SIZE][BOARD_SIZE];
        allWhitePieces = new ArrayList<>();
        allBlackPieces = new ArrayList<>();
    }

    public void loadGame(IGameLoader gameLoader) {
        reset();
        addPieces(gameLoader.getPieces(), gameLoader.getCoordonateList());
        isWhiteTurn = gameLoader.isWhiteTurn();
    }

    public ArrayList<IPiece> getAllColorPieces(boolean isWhite) {   return isWhite ? allWhitePieces : allBlackPieces; }

    public boolean isWhiteTurn() { return isWhiteTurn; }

    public ArrayList<IPiece> getAllPieces(){
        ArrayList<IPiece> allPieces = new ArrayList<>();
        allPieces.addAll(allWhitePieces);
        allPieces.addAll(allBlackPieces);
        return allPieces;
    }

    public boolean canCastle(String Castel) {
        return false;
    }

    public void setCastlingRight(String Castel, boolean isRight) {
        if(canCastle.containsKey(Castel)) {
            canCastle.replace(Castel, isRight);
            return;
        }
        canCastle.put(Castel, isRight);
    }

    public boolean isControled(Coordinate c, boolean byColor) {
        ArrayList<IPiece> allPieces = getAllColorPieces(byColor);
        for (IPiece piece : allPieces)
            if(piece.getType() != PieceType.KING && piece.getType() != PieceType.PAWN)
                for(Move m : piece.allMoves(this, getPieceCoordiante(piece)))if(m.getTo().equals(c)) return true;


        return false;
    }
}