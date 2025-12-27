package app;

import domain.IPiece;
import domain.move.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGameLoader {

    ArrayList<IPiece> getPieces();
    ArrayList<Coordinate> getCoordonateList();
    boolean isWhiteTurn();
    HashMap<String, Boolean> getCanRock();

}
