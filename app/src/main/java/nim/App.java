package nim;

import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Game game = new Game(new Random());
        UserInterface ui = new UserInterface(System.out, new Scanner(System.in), game,
                new Niminator(game));
        ui.playerCount();
        ui.start(ui.getPlayerCount());
    }
}
