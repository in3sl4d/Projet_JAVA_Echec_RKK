package move.coordinate;

public class Coordinate {

    public static String coordinatetoString(int x, int y) {
        if(x > 7 || x < 0 || y > 7 || y < 0) throw new CoordinateNotValidExecption("Coordinates must be between 0 and 7");
        char h = ((char) ('A' + y));
        String c = Integer.toString(x + 1);
        return h + c;
    }

    public static int[] stringToCoordinate(String coord) {
        int[] r = new int[2];
        if (coord.length() != 2) throw new CoordinateNotValidExecption("Coordinates must be 2 leghts (exemple 'A1')");
        try
        {
            r[0] = Integer.parseInt(coord.substring(1, 2)) - 1;
        } catch (Exception e) {
            throw new CoordinateNotValidExecption("the seconde digit of the coordinate must be a digit");
        }
        r[1] = coord.charAt(0) - 'A';
        return r;
    }

}
