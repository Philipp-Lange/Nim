package nim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.security.SecureRandom;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    void testCalculateGameOver() {
        Game game = new Game(new Random());
        game.take(0, game.getPiles().get(0).getSize());
        game.take(1, game.getPiles().get(1).getSize());
        game.take(2, game.getPiles().get(2).getSize());
        game.setCurrentPlayer("Bob");
        assertTrue(game.isGameOver());
        assertEquals("Bob, there are no counters left, so you WIN!", game.getGameOverMessage());
    }

    @Test
    void testCaluclateGameOverWithMercyRule() {
        Random randomNumberMock = mock(SecureRandom.class, withSettings().withoutAnnotations());
        when(randomNumberMock.nextInt(10)).thenReturn(9);
        Game game = new Game(randomNumberMock);
        game.take(0, game.getPiles().get(0).getSize());
        game.take(1, game.getPiles().get(1).getSize());
        game.take(2, (game.getPiles().get(2).getSize() - 1));
        game.setPlayer1("Alice");
        game.setCurrentPlayer("Alice");
        game.setPlayer2("Bob");
        assertTrue(game.isGameOver());
        assertEquals("Alice, you must take the last remaining counter, so you lose. Bob wins!",
                game.getGameOverMessage());
    }

    @Test
    void testSwitchCurrentPlayer() {
        Game game = new Game(new Random());
        game.setPlayer1("A");
        game.setPlayer2("B");
        game.setCurrentPlayer("A");
        game.switchCurrentPlayer();
        assertEquals("B", game.getCurrentPlayer());
        game.switchCurrentPlayer();
        assertEquals("A", game.getCurrentPlayer());
    }

    @Test
    void testGameHasThreePiles() {
        Game game = new Game(new Random());
        assertEquals(3, game.getPiles().size());
    }

    @Test
    void testTakeFromGivenPile() {
        Game game = new Game(new Random());
        int initialSize = game.getPiles().get(0).getSize();
        game.take(0, 1);
        int finalSize = game.getPiles().get(0).getSize();
        int expectedSize = initialSize - 1;
        assertEquals(expectedSize, finalSize);
    }

    @Test
    void testTakeFromInvalidPile() {
        Game game = new Game(new Random());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.take(3, 1);
        });
        String expectedMEssage = "Please choose either pile A, B, or C.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMEssage, actualMessage);
    }
}
