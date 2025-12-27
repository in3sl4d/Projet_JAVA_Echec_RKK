package usercase;

import entities.IPiece;
import entities.move.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGameLoader {

    ArrayList<IPiece> getPieces();
    ArrayList<Coordinate> getCoordonateList();
    boolean isWhiteTurn();
    HashMap<String, Boolean> getCanRock();

}
