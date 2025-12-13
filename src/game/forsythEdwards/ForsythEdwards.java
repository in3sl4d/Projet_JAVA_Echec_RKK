package game.forsythEdwards;

import game.Board;
import game.IGameLoader;
import move.coordinate.Coordinate;
import game.IPiece;
import piece.PieceFactory;
import piece.PieceType;

import java.util.ArrayList;
import java.util.HashMap;

public class ForsythEdwards implements IGameLoader {

    private ArrayList<Coordinate> coordonateList;
    private ArrayList<IPiece> pieces;

    private boolean isWhiteTurn = true;
    private HashMap<String, Boolean> canRock;

    private static HashMap<String, PieceType> pieceTypes;
    private static HashMap<PieceType, String> typePieces;

    static {
        pieceTypes = new HashMap<>();
        pieceTypes.put("p", PieceType.PAWN);
        pieceTypes.put("q", PieceType.QUEEN);
        pieceTypes.put("n", PieceType.KNIGHT);
        pieceTypes.put("b", PieceType.BISHOP);
        pieceTypes.put("r", PieceType.ROOK);
        pieceTypes.put("k", PieceType.KING);

        typePieces = new HashMap<>();
        typePieces.put(PieceType.PAWN, "p");
        typePieces.put(PieceType.QUEEN, "q");
        typePieces.put(PieceType.KNIGHT, "n");
        typePieces.put(PieceType.BISHOP, "b");
        typePieces.put(PieceType.ROOK, "r");
        typePieces.put(PieceType.KING, "k");
    }

    public ForsythEdwards(String fen) {
        canRock = new HashMap<>();

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
            pieces.add(PieceFactory.createPiece(pieceTypes.get(String.valueOf(type)), isWhite));
            coordonateList.add(new Coordinate(x, y));
            y++;

        }

        String[] s = fen.split(" ");
            if (s[1].equals("b")) isWhiteTurn = false;
        if (s.length >= 3) {
            canRock.put("K", s[2].contains("K"));
            canRock.put("Q", s[2].contains("Q"));
            canRock.put("k", s[2].contains("k"));
            canRock.put("q", s[2].contains("q"));
        }
    }

    public ArrayList<IPiece> getPieces() { return pieces; }
    public ArrayList<Coordinate> getCoordonateList() { return coordonateList; }
    public boolean isWhiteTurn() { return isWhiteTurn; }
    public HashMap<String, Boolean> getCanRock() { return canRock; }

    public static String boardToFen(Board board) {
        StringBuilder fen = new StringBuilder();
        IPiece[][] grid = board.getBoard();

        // 1. Placement des pièces
        // On parcourt de la ligne 7 (rang 8) à la ligne 0 (rang 1)
        for (int x = 7; x >= 0; x--) {
            int emptyCount = 0;

            for (int y = 0; y < 8; y++) {
                IPiece piece = board.getPieceAt(new Coordinate(x, y));

                if (piece == null) {
                    emptyCount++;
                } else {
                    // Si on avait des cases vides avant, on écrit le chiffre
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    // On ajoute le caractère de la pièce
                    String letter = typePieces.get(piece.getType());
                    fen.append(piece.getIsWhite() ? letter.toUpperCase() : letter);
                }
            }

            // Fin de la ligne : s'il reste des cases vides
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }

            // Ajouter un séparateur '/' sauf pour la dernière ligne
            if (x > 0) {
                fen.append("/");
            }
        }

        // 2. Tour de jeu
        fen.append(" ");
        fen.append(board.isWhiteTurn() ? "w" : "b");

        // 3. Métadonnées par défaut (Roque, En-passant, Half-move, Full-move)
        // Comme ces infos ne sont pas dans votre classe Board, on met des tirets/zéros.
        fen.append(" - - 0 1");

        return fen.toString();
    }
}
