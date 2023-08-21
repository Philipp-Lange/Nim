package nim;

import java.util.Random;

public class Pile {
    private int size;
    private Random random;

    public Pile(Random random) {
        this.random = random;
        this.size = initializeSize();
    }

    public Pile(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public void take(int amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("You must choose a positive amount.");
        }

        if (this.size == 0) {
            throw new IllegalArgumentException("You cannot take from an empty pile.");
        }

        if (amount > this.size) {
            throw new IllegalArgumentException("You cannot take more than is in the pile.");
        }

        this.size -= amount;
    }

    private int initializeSize() {
        return random.nextInt(10) + 1;
    }
}
