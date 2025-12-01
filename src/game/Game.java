package game;

import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public class Game {

    private Board board;

    public Game() {
        board = new Board();
    }

    public Game(String str) {board = new Board(str);}

    public String toString()
    {
        return board.toString();
    }

    public Board getBoard() {
        return board;
    }

    public boolean isChecked(boolean isWhite, Board board) {
        Coordinate rc = board.getKingCoordinate(isWhite);
        ArrayList<IPiece> allP = board.getAllColorPieces(!isWhite);
        for (IPiece p : allP) {
            Coordinate c = board.getPieceCoordiante(p);
            if(c == null) continue;
            for (Move m : p.allMoves(board, c))
            {
                if(m.getTo().isSame(rc)) return true;
            }
        }
        return false;
    }

    public boolean isChecked(boolean isWhite) {
        return isChecked(isWhite, this.board);
    }

    public boolean willBeChecked(boolean isWhite, Move m) {
        Board boardCopy = new Board(board);
        boardCopy.movePieceWithoutRestriction(m);
        return isChecked(isWhite, boardCopy);
    }

    public boolean willBeCheckedMate(boolean isWhite, Move m) {
        Board boardCopy = new Board(board);
        boardCopy.movePieceWithoutRestriction(m);

        for (IPiece p : boardCopy.getAllColorPieces(isWhite)) {
            Coordinate c = boardCopy.getPieceCoordiante(p);
            if(c == null) continue;
            for(Move m2 : p.allMoves(boardCopy, c))
            {
                Board next = new Board(boardCopy);
                next.movePieceWithoutRestriction(m2);
                if(!isChecked(isWhite, next)) return false;
            }
        }

        return true;
    }
}
