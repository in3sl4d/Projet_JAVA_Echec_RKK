package infrastructure.ia;

import domain.Board;
import domain.IPiece;
import domain.move.coordinate.Coordinate;
import domain.piece.PieceType;

import java.util.HashMap;

public class Evaluate {

    private static final double WEIGHT_MATERIAL = 1.0;   // Le plus important (ne pas perdre de pièces)
    private static final double WEIGHT_POSITION = 0.8;   // Très important (contrôler le centre)
    private static final double WEIGHT_MOBILITY = 0.1;   // Très faible ! (juste pour départager deux bons coups)
    private static final double WEIGHT_STRUCTURE = 0.5;

    private static final int ISOLATED_PAWN_PENALTY = -15;
    private static final int DOUBLED_PAWN_PENALTY = -10;
    private static final int PASSED_PAWN_BONUS = 50;
    private static final int BISHOP_PAIR_BONUS = 50;

    private static HashMap<String, Integer> value;

    static {
        value = new HashMap<>();
        value.put("p", 100);
        value.put("n", 320);
        value.put("b", 330);
        value.put("r", 500);
        value.put("q", 900);
        value.put("k", 20000);
    }

    private static int getPieceScore(Board b, boolean isWhite)
    {
        int score = 0;
        for(int i = 0; i < Board.BOARD_SIZE; i++)
            for (int j = 0; j < Board.BOARD_SIZE; j++)
            {
                Coordinate c = new Coordinate(i, j);
                IPiece p = b.getPieceAt(c);
                if(p != null && p.getIsWhite() == isWhite) score += value.get(p.toString()) + PieceSquareTables.getPstValue(p, c);
            }
        return score;
    }

    private static int evaluateMobility(Board b, boolean isWhite) {
        int score = 0;
        for (IPiece p : b.getAllColorPieces(isWhite)) score += p.allMoves(b, b.getPieceCoordiante(p)).size() * 5;
        return score;
    }

    private static int evaluatePawnStructure(Board b, boolean isWhite) {
        int score = 0;
        int[] pawnsPerCol = new int[Board.BOARD_SIZE];

        for (IPiece p : b.getAllColorPieces(isWhite)) {
            if (p.getType() == PieceType.PAWN) {
                Coordinate c = b.getPieceCoordiante(p);
                pawnsPerCol[c.getX()]++;
            }
        }

        for (IPiece p : b.getAllColorPieces(isWhite)) {
            if (p.getType() == PieceType.PAWN) {
                Coordinate c = b.getPieceCoordiante(p);
                int x = c.getX();
                int y = c.getY();

                if (pawnsPerCol[x] > 1) {
                    score += DOUBLED_PAWN_PENALTY;
                }

                boolean leftSupport = (x > 0 && pawnsPerCol[x - 1] > 0);
                boolean rightSupport = (x < 7 && pawnsPerCol[x + 1] > 0);
                if (!leftSupport && !rightSupport) {
                    score += ISOLATED_PAWN_PENALTY;
                }

                if (isPassedPawn(b, c, isWhite)) {
                    int rankBonus = isWhite ? (7 - y) * 10 : y * 10;
                    score += PASSED_PAWN_BONUS + rankBonus;
                }
            }
        }
        return score;
    }

    private static boolean isPassedPawn(Board b, Coordinate c, boolean isWhite) {
        int x = c.getX();
        int y = c.getY();
        IPiece p = b.getPieceAt(c);

        int where = (p.getIsWhite() == isWhite) ? 1 : -1;

        x += where;

        while (Coordinate.coordonateIsLegit(x, y)) {

            Coordinate cc = new Coordinate(x, y);
            IPiece front = b.getPieceAt(cc);
            if (front != null && front.getIsWhite() != isWhite && front.toString().equalsIgnoreCase("p")) return false;

            if (Coordinate.coordonateIsLegit(x, y + 1)) {
                Coordinate ccd = new Coordinate(x, y + 1);
                IPiece right = b.getPieceAt(ccd);
                if (right != null && right.getIsWhite() != isWhite && right.toString().equalsIgnoreCase("p")) return false;
            }

            if (Coordinate.coordonateIsLegit(x, y - 1)) {
                Coordinate ccg = new Coordinate(x, y - 1);
                IPiece left = b.getPieceAt(ccg);
                if (left != null && left.getIsWhite() != isWhite && left.toString().equalsIgnoreCase("p")) return false;
            }

            x += where;
        }

        return true;
    }

    private static int evaluateKingSafety(Board b, boolean isWhite) {
        int score = 0;
        IPiece king = null;
        Coordinate kPos = null;

        for (IPiece p : b.getAllColorPieces(isWhite)) {
            if (p.getType() == PieceType.KING) {
                king = p;
                kPos = b.getPieceCoordiante(p);
                break;
            }
        }
        if (kPos == null) return -10000;

        int direction = isWhite ? -1 : 1;

        // Note: Adapte la direction selon ton système de coordonnées (0 en haut ou en bas ?)
        // Ici je suppose que Y change quand on avance.

        // Vérifier les 3 cases devant le roi
        for (int dx = -1; dx <= 1; dx++) {
            int checkX = kPos.getX() + direction;
            int checkY = kPos.getY() + dx;

            if (checkX >= 0 && checkX < 8 && checkY >= 0 && checkY < 8) {
                IPiece shield = b.getPieceAt(new Coordinate(checkX, checkY));
                if (shield != null && shield.toString().equalsIgnoreCase("p") && shield.getIsWhite() == isWhite) {
                    score += 20; // Bonus pour chaque pion du bouclier
                } else {
                    score -= 10; // Malus si le bouclier est troué
                }
            }
        }
        return score;
    }

    private static int evaluateRooks(Board b, boolean isWhite) {
        int score = 0;
        for (IPiece p : b.getAllColorPieces(isWhite)) {
            if (p.getType() == PieceType.ROOK) {
                Coordinate c = b.getPieceCoordiante(p);
                if (isFileOpen(b, c.getX())) {
                    score += 40;
                } else if (isFileSemiOpen(b, c.getX(), isWhite)) {
                    score += 15;
                }
            }
        }
        return score;
    }

    private static boolean isFileOpen(Board b, int col) {
        for (int row = 0; row < 8; row++) {
            IPiece p = b.getPieceAt(new Coordinate(col, row));
            if (p != null && p.getType() == PieceType.PAWN) return false;
        }
        return true;
    }

    private static boolean isFileSemiOpen(Board b, int col, boolean isWhite) {
        for (int row = 0; row < 8; row++) {
            IPiece p = b.getPieceAt(new Coordinate(col, row));
            if (p != null && p.getType() == PieceType.PAWN && p.getIsWhite() == isWhite) return false;
        }
        return true;
    }

    private static int evaluateBishopPair(Board b, boolean isWhite) {
        int bishopCount = 0;
        for (IPiece p : b.getAllColorPieces(isWhite)) {
            if (p.getType() == PieceType.BISHOP) {
                bishopCount++;
            }
        }
        // Si on a les deux fous, gros bonus !
        return (bishopCount >= 2) ? BISHOP_PAIR_BONUS : 0;
    }

    public static int evaluate(Board b)
    {
        double whiteScore = WEIGHT_MATERIAL * getPieceScore(b, true)  + WEIGHT_MOBILITY * evaluateMobility(b, true)  +
                 WEIGHT_STRUCTURE * evaluatePawnStructure(b, true) + evaluateKingSafety(b, true)
                + evaluateRooks(b, true) + evaluateBishopPair(b, true);

        double blackScore = WEIGHT_MATERIAL * getPieceScore(b, false)  + WEIGHT_MOBILITY * evaluateMobility(b, false)
                + WEIGHT_STRUCTURE * evaluatePawnStructure(b, false) + evaluateKingSafety(b, false)
                + evaluateRooks(b, false) + evaluateBishopPair(b, false);

        int finalScore = (int)(whiteScore - blackScore);
        return b.isWhiteTurn() ? finalScore : -finalScore;
    }

}
