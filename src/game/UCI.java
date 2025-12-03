package game;

import move.Move;
import move.coordinate.Coordinate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class UCI {

    // Position RKk par défaut
    private static final String START_FEN = "8/3K2R1/8/8/8/8/8/3k4 w - - 0 1";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialisation de départ
        Game game = new Game(START_FEN);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] tokens = line.split("\\s+");
            String command = tokens[0];

            switch (command) {
                case "uci":
                    System.out.println("id name ProjetRkk");
                    System.out.println("id author Equipe Java");
                    System.out.println("uciok");
                    break;

                case "isready":
                    System.out.println("readyok");
                    break;

                case "ucinewgame":
                    game = new Game(START_FEN);
                    break;

                case "position":
                    // CORRECTION ICI : On récupère le jeu mis à jour par la méthode
                    game = handlePosition(game, tokens);
                    break;

                case "go":
                    handleGo(game);
                    break;

                case "quit":
                    return;
            }
        }
    }

    // La méthode renvoie maintenant un objet Game
    private static Game handlePosition(Game currentGame, String[] tokens) {
        Game gameToUpdate = currentGame;
        int movesIndex = -1;

        // 1. Gérer la position de base (startpos ou fen)
        if (tokens[1].equals("startpos")) {
            // On recrée proprement un nouveau jeu
            gameToUpdate = new Game(START_FEN);

            // On cherche où commencent les coups
            for (int i = 2; i < tokens.length; i++) {
                if (tokens[i].equals("moves")) {
                    movesIndex = i + 1;
                    break;
                }
            }
        }
        else if (tokens[1].equals("fen")) {
            // On reconstruit la chaîne FEN
            StringBuilder fen = new StringBuilder();
            int i = 2;
            while (i < tokens.length && !tokens[i].equals("moves")) {
                fen.append(tokens[i]).append(" ");
                i++;
            }

            // On crée le jeu avec ce FEN spécifique
            gameToUpdate = new Game(fen.toString().trim());

            if (i < tokens.length && tokens[i].equals("moves")) {
                movesIndex = i + 1;
            }
        }

        // 2. Appliquer les coups s'il y en a
        if (movesIndex != -1 && movesIndex < tokens.length) {
            for (int i = movesIndex; i < tokens.length; i++) {
                applyUciMove(gameToUpdate, tokens[i]);
            }
        }

        // 3. IMPORTANT : On renvoie le jeu mis à jour au main
        return gameToUpdate;
    }

    private static void applyUciMove(Game game, String uciMove) {
        try {
            String start = uciMove.substring(0, 2);
            String end = uciMove.substring(2, 4);

            Coordinate go = new Coordinate(start);
            Coordinate to = new Coordinate(end);
            Move m = new Move(go, to);

            // On joue le coup sur le plateau
            game.getBoard().movePieceWithoutRestriction(m);
            // On inverse le tour manuellement car movePieceWithoutRestriction ne le fait pas
            game.getBoard().changeTurn();

        } catch (Exception e) {
            System.out.println("info string Erreur move non valide: " + uciMove);
        }
    }

    private static void handleGo(Game game) {
        // Récupération du tour actuel via le Board
        boolean isWhite = game.getBoard().isWhiteTurn();

        // Debug info pour voir ce qui se passe dans l'interface
        System.out.println("info string Calcul pour : " + (isWhite ? "BLANCS" : "NOIRS"));

        ArrayList<Move> allLegalMoves = game.allLegalMoves(isWhite);

        if (!allLegalMoves.isEmpty()) {
            Move randomMove = allLegalMoves.get(new Random().nextInt(allLegalMoves.size()));
            System.out.println("bestmove " + moveToUciString(randomMove));
        } else {
            // Si aucun coup, c'est mat ou pat
            System.out.println("bestmove 0000");
        }
    }

    private static String moveToUciString(Move m) {
        return m.getGo().toString() + m.getTo().toString();
    }
}