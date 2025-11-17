package game;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = BOARD_SIZE - 1; i >= 0; i--)
        {
            IPiece[] b = board[i];
            for (IPiece p : b)
            {
                sb.append((p != null? p.toString() : "  ") + "|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}