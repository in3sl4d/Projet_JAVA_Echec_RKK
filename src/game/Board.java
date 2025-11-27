package game;

import move.Move;
import move.coordinate.Coordinate;
import piece.IPiece;
import piece.King;
import piece.Rook;

import java.awt.*;

public class Board {

    private final static int BOARD_SIZE = 8;
    private IPiece[][] board;

    public Board() {
        board = new IPiece[BOARD_SIZE][BOARD_SIZE];
        putPiece(new King(true), "A1");
        putPiece(new Rook(false), "A2");
    }

    private void putPiece(IPiece piece, String coordinate) {
        int[] c = Coordinate.stringToCoordinate(coordinate);
        board[c[0]][c[1]] = piece;
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
        putPiece(p, to.toString());
        putPiece(null, go.toString());
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
}