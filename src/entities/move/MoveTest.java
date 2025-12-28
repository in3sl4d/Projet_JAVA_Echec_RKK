package entities.move;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoveTest {

    @Test
    void coordinateToString() {
        String m = Move.coordinateToString(1, 4, 3, 4);
        Assertions.assertEquals(m, "e2 - e4");
    }

    @Test
    void stringToCoordinate() {
        Move m = new Move("e1 - e2");
        Assertions.assertEquals(m.getGo().toString(), "e1");
        Assertions.assertEquals(m.getTo().toString(), "e2");
    }
}