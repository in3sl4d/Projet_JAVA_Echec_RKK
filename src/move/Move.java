package move;

import move.coordinate.Coordinate;

public class Move{

    public static String CoordinateToString(int xd, int yd, int xa, int ya) {
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
}
