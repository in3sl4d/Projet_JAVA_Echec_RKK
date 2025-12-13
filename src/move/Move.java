package move;

import move.coordinate.Coordinate;

public class Move{

    private Coordinate go;
    private Coordinate to;

    private Move nextMove = null;

    public void setNextMove(Move nextMove) {
        this.nextMove = nextMove;
    }

    public Move getNextMove() {
        return nextMove;
    }

    public Move(Coordinate go, Coordinate to) {
        this.go = go;
        this.to = to;
    }

    public Move(int xGo, int yGo, int xTo, int yTo) {
        this.go = new Coordinate(xGo, yGo);
        this.to = new Coordinate(xTo, yTo);
    }

    public Move(String moveString){
        Coordinate[] c;
        try
        {
            c = StringToCoordinate(moveString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.go = c[0];
        this.to = c[1];
    }

    public static Coordinate[] StringToCoordinate(String str) {
        if(str.length() != 7) throw new stringMoveNotValidExecption("Move need to make 7 of lenght");
        Coordinate go = null, to = null;
        try
        {
            go = new Coordinate(str.substring(0, 2));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try
        {
            to = new Coordinate(str.substring(5, 7));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Coordinate[]{go, to};
    }

    public static String coordinateToString(int xd, int yd, int xa, int ya) {
        String d = "", a = "";
        try
        {
            d = Coordinate.coordinatetoString(xd, yd);
        } catch (Exception e)
            {
                e.printStackTrace();
            }
        try
        {
            a = Coordinate.coordinatetoString(xa, ya);
        } catch (Exception e)
            {
                e.printStackTrace();
            }
        return d + " - " + a;
    }

    public static String coordinateToString(Coordinate go, Coordinate to) {
        return coordinateToString(go.getX(), go.getY(), to.getX(), to.getY());
    }

    public String toString(){
        return coordinateToString(go, to);
    }

    public Coordinate getGo() {
        return go;
    }

    public Coordinate getTo() {
        return to;
    }

    public boolean isSame(Move m) {
        return toString().equals(m.toString());
    }
}
