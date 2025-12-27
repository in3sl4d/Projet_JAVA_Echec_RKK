package entities.piece;

import entities.Board;
import entities.IPiece;
import entities.move.Move;
import entities.move.coordinate.Coordinate;

import java.util.ArrayList;

public class Pawn extends Piece implements IPiece {

    public Pawn(boolean isWhite) {
        super(isWhite, PieceType.PAWN);
    }

    @Override
    public ArrayList<Move> allMoves(Board board, Coordinate coord) {
        ArrayList<Move> moves = new ArrayList<>();
        int x = coord.getX();
        int y = coord.getY();

        int direction = getIsWhite() ? 1 : -1;
        int startRow = getIsWhite() ? 1 : 6;

        int xForward = x + direction;
        if (Coordinate.coordonateIsLegit(xForward, y)) {
            Coordinate target = new Coordinate(xForward, y);
            if (board.getPieceAt(target) == null) {
                moves.add(new Move(coord, target));

                if (x == startRow) {
                    int xDouble = x + (direction * 2);
                    Coordinate targetDouble = new Coordinate(xDouble, y);
                    if (Coordinate.coordonateIsLegit(xDouble, y) && board.getPieceAt(targetDouble) == null) {
                        moves.add(new Move(coord, targetDouble));
                    }
                }
            }
        }

        int[] captureOffsets = {1, -1};

        for (int offset : captureOffsets) {
            int captureY = y + offset;
            int captureX = x + direction;

            if (Coordinate.coordonateIsLegit(captureX, captureY)) {
                Coordinate target = new Coordinate(captureX, captureY);
                IPiece pieceToCapture = board.getPieceAt(target);

                if (pieceToCapture != null && pieceToCapture.getIsWhite() != this.getIsWhite()) {
                    moves.add(new Move(coord, target));
                }
            }
        }

        return moves;
    }



}
