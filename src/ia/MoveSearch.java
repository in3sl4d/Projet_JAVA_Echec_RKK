package ia;

import game.Board;
import game.Game;
import move.Move;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveSearch {

    // On définit l'infini (un peu moins que Integer.MAX_VALUE pour éviter les bugs d'addition)
    private static final int INFINITY = 10000000;

    // Pour les statistiques de performance
    public static int positionsEvaluated = 0;

    /**
     * Méthode principale à appeler pour obtenir le meilleur coup
     * @param game L'état actuel du jeu
     * @param depth La profondeur de calcul (ex: 3 ou 4)
     * @return Le meilleur mouvement trouvé
     */
    public static Move getBestMove(Game game, int depth) {
        positionsEvaluated = 0;
        long start = System.currentTimeMillis();

        ArrayList<Move> legalMoves = game.allLegalMoves();

        // Optimisation simple : mélanger les coups pour varier le jeu si scores égaux
        Collections.shuffle(legalMoves);

        Move bestMove = null;
        int bestScore = -INFINITY;

        // C'est le tour de qui ? (Pour l'affichage)
        boolean isWhite = game.getBoard().isWhiteTurn();

        System.out.println("--- IA (" + (isWhite ? "Blancs" : "Noirs") + ") réfléchit (Prof: " + depth + ") ---");

        for (Move move : legalMoves) {

            // 1. SIMULATION : On crée un futur virtuel
            // On utilise le constructeur que je t'ai fait ajouter dans Game
            Game futureGame = new Game(game.getBoard());

            // On joue le coup dans le futur virtuel
            futureGame.playAI(move);

            // 2. RÉCURSION : On demande la valeur de ce futur
            // Note le "-" devant alphaBeta : c'est le principe du NegaMax
            // (Mon score = - (Score de l'adversaire))
            int score = -alphaBeta(futureGame, depth - 1, -INFINITY, INFINITY);

            // System.out.println("Coup: " + move + " Score: " + score); // Decommenter pour debug

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("Meilleur coup : " + bestMove + " (Score: " + bestScore + ")");
        System.out.println("Positions analysées : " + positionsEvaluated + " en " + time + "ms");

        return bestMove;
    }

    /**
     * Algorithme Alpha-Beta (NegaMax)
     */
    private static int alphaBeta(Game game, int depth, int alpha, int beta) {
        // 1. Condition d'arrêt : Profondeur atteinte
        if (depth == 0) {
            positionsEvaluated++;
            return Evaluate.evaluate(game.getBoard());
        }

        ArrayList<Move> moves = game.allLegalMoves();

        // 2. Condition d'arrêt : Fin de partie (Mat ou Pat)
        if (moves.isEmpty()) {
            positionsEvaluated++;
            boolean isWhiteTurn = game.getBoard().isWhiteTurn();

            if (game.isChecked(isWhiteTurn)) {
                // Echec et Mat !
                // On retourne un score très bas, mais on ajoute la profondeur
                // pour que l'IA préfère mater en 1 coup qu'en 3 coups.
                return -INFINITY + depth;
            }
            // Pat (Match nul)
            return 0;
        }

        // 3. Boucle de recherche
        for (Move move : moves) {

            // Simulation
            Game futureGame = new Game(game.getBoard());
            futureGame.playAI(move);

            // Récursion avec inversion des bornes Alpha/Beta
            int score = -alphaBeta(futureGame, depth - 1, -beta, -alpha);

            // Élagage (Couper la branche)
            if (score >= beta) {
                return beta; // Coupure Beta
            }
            if (score > alpha) {
                alpha = score;
            }
        }
        return alpha;
    }
}