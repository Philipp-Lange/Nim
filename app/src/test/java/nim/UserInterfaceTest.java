package nim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class UserInterfaceTest {
        @Test
        void testBaseCase() {
                Random randomNumberMock = mock(SecureRandom.class, withSettings().withoutAnnotations());
                when(randomNumberMock.nextInt(10)).thenReturn(9);
                ByteArrayOutputStream outSpy = new ByteArrayOutputStream();
                String input = "Alice" + "\r\nBob" + "\r\nA" + "\r\n10" + "\r\nB" + "\r\n10" + "\r\nC" + "\r\n10";
                System.setIn(new ByteArrayInputStream(input.getBytes()));
                Game game = new Game(randomNumberMock);
                UserInterface ui = new UserInterface(new PrintStream(outSpy), new Scanner(System.in), game,
                                new Niminator(game));
                ui.start(2);
                String expected = "Player 1, enter your name: Player 2, enter your name: \r\n" +
                                "A: 10    B: 10    C: 10    \r\n" +
                                "\r\n" +
                                "Alice, choose a pile: How many to remove from pile A: \r\n" +
                                "A: 0    B: 10    C: 10    \r\n" + //
                                "\r\n" +
                                "Bob, choose a pile: How many to remove from pile B: \r\n" +
                                "A: 0    B: 0    C: 10    \r\n" + //
                                "\r\n" + //
                                "Alice, choose a pile: How many to remove from pile C: \r\n" +
                                "Bob, there are no counters left, so you WIN!";
                assertEquals(expected, outSpy.toString());
        }

        @Test
        void testInvalidPile() {
                Random randomNumberMock = mock(SecureRandom.class, withSettings().withoutAnnotations());
                when(randomNumberMock.nextInt(10)).thenReturn(9);
                ByteArrayOutputStream outSpy = new ByteArrayOutputStream();
                String input = "Alice" + "\r\nBob" + "\r\nD" + "\r\n10" + "\r\nA" + "\r\n10" + "\r\nB" + "\r\n10"
                                + "\r\nC"
                                + "\r\n10";
                System.setIn(new ByteArrayInputStream(input.getBytes()));
                Game game = new Game(randomNumberMock);
                UserInterface ui = new UserInterface(new PrintStream(outSpy), new Scanner(System.in), game,
                                new Niminator(game));
                ui.start(2);
                String expected = "Player 1, enter your name: Player 2, enter your name: \r\n" +
                                "A: 10    B: 10    C: 10    \r\n" +
                                "\r\n" +
                                "Alice, choose a pile: How many to remove from pile D: Please choose either pile A, B, or C.\r\n"
                                +
                                "Alice, choose a pile: How many to remove from pile A: \r\n" +
                                "A: 0    B: 10    C: 10    \r\n" +
                                "\r\n" +
                                "Bob, choose a pile: How many to remove from pile B: \r\n" +
                                "A: 0    B: 0    C: 10    \r\n" +
                                "\r\n" +
                                "Alice, choose a pile: How many to remove from pile C: \r\n" +
                                "Bob, there are no counters left, so you WIN!";
                assertEquals(expected, outSpy.toString());
        }

        @Test
        void testInvalidAmount() {
                Random randomNumberMock = mock(SecureRandom.class, withSettings().withoutAnnotations());
                when(randomNumberMock.nextInt(10)).thenReturn(9);
                ByteArrayOutputStream outSpy = new ByteArrayOutputStream();
                String input = "Alice" + "\r\nBob" + "\r\nA" + "\r\n-1" + "\r\nA" + "\r\n10" + "\r\nB" + "\r\n10"
                                + "\r\nC"
                                + "\r\n10";
                System.setIn(new ByteArrayInputStream(input.getBytes()));
                Game game = new Game(randomNumberMock);
                UserInterface ui = new UserInterface(new PrintStream(outSpy), new Scanner(System.in), game,
                                new Niminator(game));
                ui.start(2);
                String expected = "Player 1, enter your name: Player 2, enter your name: \r\n" +
                                "A: 10    B: 10    C: 10    \r\n" +
                                "\r\n" +
                                "Alice, choose a pile: How many to remove from pile A: You must choose a positive amount.\r\n"
                                +
                                "Alice, choose a pile: How many to remove from pile A: \r\n" +
                                "A: 0    B: 10    C: 10    \r\n" +
                                "\r\n" +
                                "Bob, choose a pile: How many to remove from pile B: \r\n" +
                                "A: 0    B: 0    C: 10    \r\n" +
                                "\r\n" +
                                "Alice, choose a pile: How many to remove from pile C: \r\n" +
                                "Bob, there are no counters left, so you WIN!";
                assertEquals(expected, outSpy.toString());
        }

        @Test
        void testPlayerCount() {
                ByteArrayOutputStream outSpy = new ByteArrayOutputStream();
                String input = "2";
                System.setIn(new ByteArrayInputStream(input.getBytes()));
                Game game = new Game(new Random());
                UserInterface ui = new UserInterface(new PrintStream(outSpy), new Scanner(System.in), game,
                                new Niminator(game));
                ui.playerCount();
                String expected = "How many players: ";
                assertEquals(expected, outSpy.toString());
        }

        @Test
        void testPlayerCountWithInvalidInput() {
                ByteArrayOutputStream outSpy = new ByteArrayOutputStream();
                String input = "Alice" + "\r\n-10" + "\r\n10" + "\r\n2";
                System.setIn(new ByteArrayInputStream(input.getBytes()));
                Game game = new Game(new Random());
                UserInterface ui = new UserInterface(new PrintStream(outSpy), new Scanner(System.in), game,
                                new Niminator(game));
                ui.playerCount();
                String expected = "How many players: Player count must be a number.\r\n" +
                                "How many players: Can only have one or two players.\r\n" +
                                "How many players: Can only have one or two players.\r\n" +
                                "How many players: ";
                assertEquals(expected, outSpy.toString());
        }

        @Test
        void testSinglePlayer() {
                Random randomNumberMock = mock(SecureRandom.class, withSettings().withoutAnnotations());
                when(randomNumberMock.nextInt(10)).thenReturn(9);
                ByteArrayOutputStream outSpy = new ByteArrayOutputStream();
                String input = "Alice" + "\r\nC" + "\r\n10";
                System.setIn(new ByteArrayInputStream(input.getBytes()));
                Game game = new Game(randomNumberMock);
                UserInterface ui = new UserInterface(new PrintStream(outSpy), new Scanner(System.in), game,
                                new Niminator(game));
                ui.start(1);
                String expected = "Player 1, enter your name: \r\n" + //
                                "A: 10    B: 10    C: 10    \r\n" + //
                                "\r\n" + //
                                "Niminator chose pile A\r\n" + //
                                "Niminator removed 10 from pile A\r\n" + //
                                "\r\n" + //
                                "A: 0    B: 10    C: 10    \r\n" + //
                                "\r\n" + //
                                "Alice, choose a pile: How many to remove from pile C: \r\n" + //
                                "A: 0    B: 10    C: 0    \r\n" + //
                                "\r\n" + //
                                "Niminator chose pile B\r\n" + //
                                "Niminator removed 9 from pile B\r\n" + //
                                "\r\n" + //
                                "A: 0    B: 1    C: 0    \r\n" + //
                                "\r\n" + //
                                "Alice, you must take the last remaining counter, so you lose. Niminator wins!";
                assertEquals(expected, outSpy.toString());
        }
}
