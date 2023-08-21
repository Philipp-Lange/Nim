package nim;

import java.io.PrintStream;
import java.util.Scanner;

public class UserInterface {
    private Game game;
    private PrintStream printStream;
    private Scanner scanner;
    private int playerCount;
    private Niminator niminator;

    public UserInterface(PrintStream printStream, Scanner scanner, Game game, Niminator niminator) {
        this.game = game;
        this.printStream = printStream;
        this.scanner = scanner;
        this.niminator = niminator;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public void playerCount() {
        String input = "";
        while (true) {
            this.printStream.print("How many players: ");
            input = scanner.nextLine();

            if (!this.isInteger(input)) {
                this.printStream.println("Player count must be a number.");
                continue;
            }
            int players = Integer.parseInt(input);
            if (players != 1 && players != 2) {
                this.printStream.println("Can only have one or two players.");
                continue;
            }
            this.playerCount = players;
            break;
        }
    }

    public void start(int playerCount) {
        if (playerCount == 1) {
            this.printStream.print("Player 1, enter your name: ");
            String player1 = scanner.nextLine();
            this.game.setPlayer1(player1);
            this.game.setCurrentPlayer(player1);
            this.game.setPlayer2(this.niminator.toString());
            if (this.niminator.shouldGoFirst()) {
                this.game.setCurrentPlayer(this.niminator.toString());
            }
            this.printStream.println();

            while (!this.game.isGameOver()) {
                this.gameLoopOnePlayer();
            }
        }

        if (playerCount == 2) {
            this.printStream.print("Player 1, enter your name: ");
            String player1 = scanner.nextLine();
            this.game.setPlayer1(player1);
            this.game.setCurrentPlayer(player1);
            this.printStream.print("Player 2, enter your name: ");
            this.game.setPlayer2(scanner.nextLine());
            this.printStream.println();

            while (!game.isGameOver()) {
                this.gameLoopTwoPlayers();
            }
        }
        if (this.game.getGameOverMessage().contains("the last remaining counter")) {
            this.printPiles();
        }

        this.printStream.print(this.game.getGameOverMessage());
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void gameLoopOnePlayer() {
        this.printPiles();
        if (!this.game.getCurrentPlayer().equals("Niminator")) {
            this.playerTakesFromPile(this.game.getCurrentPlayer());
        } else {
            this.niminatorTakesFromPile();
        }
        this.game.switchCurrentPlayer();
        this.printStream.println();
    }

    private void gameLoopTwoPlayers() {
        this.printPiles();
        this.playerTakesFromPile(this.game.getCurrentPlayer());
        this.game.switchCurrentPlayer();
        this.printStream.println();
    }

    private void niminatorTakesFromPile() {
        this.niminator.calculateNextMove();
        this.printStream.println("Niminator chose pile " + this.niminator.getTargetPile());
        this.printStream.println(
                "Niminator removed " + this.niminator.getAmount() + " from pile " + this.niminator.getTargetPile());
        int index = this.niminator.getTargetPile().charAt(0) - 65;
        this.game.take(index, this.niminator.getAmount());
    }

    private void printPiles() {
        int charDecimal = 65;
        for (Pile pile : this.game.getPiles()) {
            this.printStream.print((char) charDecimal + ": " + pile.getSize() + "    ");
            charDecimal++;
        }
        this.printStream.println("\r\n");
    }

    private void playerTakesFromPile(String player) {
        while (true) {
            this.printStream.print(player + ", choose a pile: ");
            char pileLabel = this.scanner.nextLine().charAt(0);
            int index = pileLabel - 65;
            this.printStream.print("How many to remove from pile " + pileLabel + ": ");
            int amount = Integer.valueOf(this.scanner.nextLine());
            try {
                this.game.take(index, amount);
                break;
            } catch (Exception e) {
                this.printStream.println(e.getMessage());
                continue;
            }
        }

    }
}
