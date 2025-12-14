package move.coordinate;

public class Coordinate {

    private final int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(String c)
    {
        int[] s = stringToCoordinate(c);
        this.x = s[0];
        this.y = s[1];
    }

    // verifie que c'est bien une coordonnee du board (Il faudra definir des constante pour supprimer les nombres magiques)
    public static boolean coordonateIsLegit(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    // Conversion coordonnee vers string
    public static String coordinatetoString(int x, int y) {
        if(x > 7 || x < 0 || y > 7 || y < 0) throw new CoordinateNotValidExecption("Coordinates must be between 0 and 7");
        char h = ((char) ('a' + y));
        String c = Integer.toString(x + 1);
        return h + c;
    }


    // Conversion string vers coordonnee
    public static int[] stringToCoordinate(String coord) {
        int[] r = new int[2];
        if (coord.length() != 2) throw new CoordinateNotValidExecption("Coordinates must be 2 leghts (exemple 'A1')");

        coord = coord.toLowerCase();

        try
        {
            r[0] = Integer.parseInt(coord.substring(1, 2)) - 1;
        } catch (Exception e) {
            throw new CoordinateNotValidExecption("the seconde digit of the coordinate must be a digit");
        }
        r[1] = coord.charAt(0) - 'a';
        return r;
    }


    // Verifie egalite entre coordonees
    public boolean isSame(Coordinate c) {
        if(c == null) return false;
        return this.x == c.x && this.y == c.y;
    }

    public String toString(){
        return coordinatetoString(x, y);
    }

    public boolean isLegit() {
        return coordonateIsLegit(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
