package game;

import move.Move;
import move.coordinate.Coordinate;
import piece.IPiece;

import java.util.ArrayList;

public class Game {

    private Board board;

    public Game() {
        board = new Board();
    }

    public String toString()
    {
        return board.toString();
    }

    public Board getBoard() {
        return board;
    }

    public boolean isChecked(boolean isWhite) {
        Coordinate rc = board.getKingCoordinate(isWhite);
        ArrayList<IPiece> allP = board.getAllColorPieces(!isWhite);
        for (IPiece p : allP) {
            Coordinate c = board.getPieceCoordiante(p);
            for (Move m : p.allMoves(board, c))
            {
                if(m.getTo().isSame(rc)) return true;
            }
        }
        return false;
    }
}
