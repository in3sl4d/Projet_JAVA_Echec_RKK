package game;

import move.Move;
import move.coordinate.Coordinate;

import java.util.ArrayList;

public class Game {

    private final Board board;

    public Game() {
        board = new Board();
    }

    public Game(Board board) {
        this.board = new Board(board);
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

    public ArrayList<Move> allLegalMoves(Coordinate c) {
        IPiece p = board.getPieceAt(c);
        ArrayList<Move> legalMoves = new ArrayList<>();
        if(p == null) return legalMoves;
        for (Move m : p.allMoves(board, c))
        {
            if(!willBeChecked(p.getIsWhite(), m)) legalMoves.add(m);
        }
        return legalMoves;
    }

    public ArrayList<Move> allLegalMoves(boolean isWhite) {
        ArrayList<Move> legalMoves = new ArrayList<>();
        ArrayList<IPiece> pieces = board.getAllColorPieces(isWhite);
        for (IPiece p : pieces) {
            legalMoves.addAll(allLegalMoves(board.getPieceCoordiante(p)));
        }
        return legalMoves;
    }

    public ArrayList<Move> allLegalMoves() {
        ArrayList<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(allLegalMoves(false));
        legalMoves.addAll(allLegalMoves(true));
        return legalMoves;
    }

    public boolean canMovePiece(Move m) {
        IPiece piece = board.getPieceAt(m.getGo());
        if(piece == null) return false;
        ArrayList<Move> allLegalMove = allLegalMoves(m.getGo());
        if(allLegalMove == null) return false;
        for (Move m2 : allLegalMove) {
            if(m.isSame(m2)) return true;
        }
        return false;
    }

    public void play(Move m) {
        if(!canMovePiece(m)) throw new IllegalMoveExecption("can't move piece");
        board.movePieceWithoutRestriction(m);
        board.changeTurn();
    }

}
