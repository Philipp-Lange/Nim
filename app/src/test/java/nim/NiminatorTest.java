package nim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class NiminatorTest {
    @Test
    void testBinaryConversion() {
        assertEquals("100", Integer.toBinaryString(4));
    }

    @Test
    void testXOR() {
        assertEquals(2, 3 ^ 4 ^ 5);
    }

    @Test
    void testShouldGoFirst() {
        Game game = mock(Game.class);
        List<Pile> piles = new ArrayList<Pile>();
        piles.add(new Pile(3));
        piles.add(new Pile(4));
        piles.add(new Pile(5));
        when(game.getPiles()).thenReturn(piles);
        Niminator niminator = new Niminator(game);
        assertTrue(niminator.shouldGoFirst());
    }

    @Test
    void testShouldNotGoFirst() {
        Game game = mock(Game.class);
        List<Pile> piles = new ArrayList<Pile>();
        piles.add(new Pile(2));
        piles.add(new Pile(5));
        piles.add(new Pile(7));
        when(game.getPiles()).thenReturn(piles);
        Niminator niminator = new Niminator(game);
        assertFalse(niminator.shouldGoFirst());
    }

    @Test
    void testCalculateNextMove1() {
        Game game = mock(Game.class);
        List<Pile> piles = new ArrayList<Pile>();
        piles.add(new Pile(3));
        piles.add(new Pile(4));
        piles.add(new Pile(5));
        when(game.getPiles()).thenReturn(piles);
        Niminator niminator = new Niminator(game);
        niminator.calculateNextMove();
        assertEquals("A", niminator.getTargetPile());
        assertEquals(2, niminator.getAmount());
    }

    @Test
    void testCalculateNextMove2() {
        Game game = mock(Game.class);
        List<Pile> piles = new ArrayList<Pile>();
        piles.add(new Pile(1));
        piles.add(new Pile(4));
        piles.add(new Pile(3));
        when(game.getPiles()).thenReturn(piles);
        Niminator niminator = new Niminator(game);
        niminator.calculateNextMove();
        assertEquals("B", niminator.getTargetPile());
        assertEquals(2, niminator.getAmount());
    }

    @Test
    void testCalculateNextMoveWhenOnlyOnePileHasMoreThanOne1() {
        Game game = mock(Game.class);
        List<Pile> piles = new ArrayList<Pile>();
        piles.add(new Pile(1));
        piles.add(new Pile(1));
        piles.add(new Pile(2));
        when(game.getPiles()).thenReturn(piles);
        Niminator niminator = new Niminator(game);
        niminator.calculateNextMove();
        assertEquals("C", niminator.getTargetPile());
        assertEquals(1, niminator.getAmount());
    }

    @Test
    void testCalculateNextMoveWhenOnlyOnePileHasMoreThanOne2() {
        Game game = mock(Game.class);
        List<Pile> piles = new ArrayList<Pile>();
        piles.add(new Pile(0));
        piles.add(new Pile(1));
        piles.add(new Pile(9));
        when(game.getPiles()).thenReturn(piles);
        Niminator niminator = new Niminator(game);
        niminator.calculateNextMove();
        assertEquals("C", niminator.getTargetPile());
        assertEquals(9, niminator.getAmount());
    }

    @Test
    void testCalculateNextMoveWhenOnlyOnePileHasMoreThanOne3() {
        Game game = mock(Game.class);
        List<Pile> piles = new ArrayList<Pile>();
        piles.add(new Pile(0));
        piles.add(new Pile(0));
        piles.add(new Pile(2));
        when(game.getPiles()).thenReturn(piles);
        Niminator niminator = new Niminator(game);
        niminator.calculateNextMove();
        assertEquals("C", niminator.getTargetPile());
        assertEquals(1, niminator.getAmount());
    }
}
