package nim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class PileTest {
    @Test
    void testPileSize() {
        Pile pile = new Pile(new Random());
        assertTrue(pile.getSize() > 0);
        assertTrue(pile.getSize() < 11);
    }

    @Test
    void testTakeBaseCase() {
        Pile pile = new Pile(new Random());
        int initialSize = pile.getSize();
        pile.take(initialSize);
        assertEquals(0, pile.getSize());
    }

    @Test
    void testTakeCannotBeNegative() {
        Pile pile = new Pile(new Random());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pile.take(-1);
        });
        String expectedMessage = "You must choose a positive amount.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testTakeCannotTakeFromEmptyPile() {
        Pile pile = new Pile(new Random());
        pile.take(pile.getSize());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pile.take(1);
        });
        String expectedMessage = "You cannot take from an empty pile.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testTakeCannotTakeMoreThanPileSize() {
        Pile pile = new Pile(new Random());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pile.take(pile.getSize() + 1);
        });
        String expectedMessage = "You cannot take more than is in the pile.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
