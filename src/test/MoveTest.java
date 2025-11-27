package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import move.Move;

class MoveTest {

    @Test
    void coordinateToString() {
        String m = Move.coordinateToString(1, 4, 3, 4);
        Assertions.assertEquals(m, "E2 - E4");
    }

    @Test
    void stringToCoordinate() {
        Move m = new Move("A1 - A2");
        Assertions.assertEquals(m.getGo().toString(), "A1");
        Assertions.assertEquals(m.getTo().toString(), "A2");
    }
}