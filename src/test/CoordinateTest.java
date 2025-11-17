package test;

import move.coordinate.Coordinate;
import org.junit.jupiter.api.Assertions;

class CoordinateTest {

    @org.junit.jupiter.api.Test
    void coordinatetoString() {
        int[][]c = {{0, 0},{0, 1},{1, 0},{1, 1}};
        String[] expected = {"A1", "B1", "A2", "B2"};
        for (int i = 0; i < expected.length; i++)
            Assertions.assertEquals(Coordinate.coordinatetoString(c[i][0], c[i][1]), expected[i]);
    }

    @org.junit.jupiter.api.Test
    void stringToCoordinate() {
        String[] s = {"A1", "A2", "B1", "B2"};
        int[][] expected = {{0, 0},{1, 0},{0, 1},{1, 1}};
        for(int i = 0; i < s.length; i++) {
            int[] r = Coordinate.stringToCoordinate(s[i]);
            Assertions.assertEquals(r[0], expected[i][0]);
            Assertions.assertEquals(r[1], expected[i][1]);
        }
    }
}