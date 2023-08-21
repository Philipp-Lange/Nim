package nim;

import java.util.ArrayList;

import java.util.List;

import java.util.Random;

public class Game {
    private List<Pile> piles;
    private Random random;
    private String player1;
    private String player2;
    private String currentPlayer;
    private String gameOverMessage;

    public Game(Random random) {
        this.piles = new ArrayList<>();
        this.random = random;
        initializePiles();
        this.player1 = "";
        this.player2 = "";
        this.gameOverMessage = null;
    }

    public List<Pile> getPiles() {
        return this.piles;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public String getGameOverMessage() {
        return this.gameOverMessage;
    }

    public boolean isGameOver() {
        int sum = 0;
        for (Pile pile : this.piles) {
            sum += pile.getSize();
        }

        if (sum == 0) {
            setGameOverMessage(0);
            return true;
        }

        if (sum == 1) {
            setGameOverMessage(1);
            return true;
        }

        return false;
    }

    public void setCurrentPlayer(String player) {
        this.currentPlayer = player;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void switchCurrentPlayer() {
        if (this.currentPlayer.equals(player1)) {
            this.currentPlayer = player2;
        } else {
            this.currentPlayer = player1;
        }
    }

    public void take(int index, int amount) {
        if (index < 0 || index >= this.piles.size()) {
            throw new IllegalArgumentException("Please choose either pile A, B, or C.");
        }
        this.piles.get(index).take(amount);
    }

    private void initializePiles() {
        for (int i = 0; i < 3; i++) {
            this.piles.add(new Pile(random));
        }
    }

    private void setGameOverMessage(int pileSum) {
        String message = "";
        if (pileSum == 0) {
            message = this.currentPlayer + ", there are no counters left, so you WIN!";
        }
        if (pileSum == 1) {
            message = this.currentPlayer + ", you must take the last remaining counter, so you lose. ";
            this.switchCurrentPlayer();
            message += this.currentPlayer + " wins!";
        }
        this.gameOverMessage = message;
    }
}
