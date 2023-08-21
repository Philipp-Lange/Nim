package nim;

public class Niminator {
    private String targetPile;
    private int amount;
    private Game game;

    public Niminator(Game game) {
        this.targetPile = "";
        this.amount = 0;
        this.game = game;
    }

    public void calculateNextMove() {
        if (this.isEndGame()) {
            int largestPile = this.findLargetPile();
            this.targetPile = String.valueOf((char) (65 + largestPile));
            int sumSansLargestPile = this.calculatePileSum() - this.game.getPiles().get(largestPile).getSize();
            if (sumSansLargestPile == 2 || sumSansLargestPile == 0) {
                this.amount = this.game.getPiles().get(largestPile).getSize() - 1;
            }
            if (sumSansLargestPile == 1) {
                this.amount = this.game.getPiles().get(largestPile).getSize();
            }
            return;
        }

        int totalNimSum = calculateNimSum();
        int nimSumA = this.game.getPiles().get(0).getSize() ^ totalNimSum;
        if (nimSumA < this.game.getPiles().get(0).getSize()) {
            this.targetPile = "A";
            this.amount = this.game.getPiles().get(0).getSize() - nimSumA;
            return;
        }
        int nimSumB = this.game.getPiles().get(1).getSize() ^ totalNimSum;
        if (nimSumB < this.game.getPiles().get(1).getSize()) {
            this.targetPile = "B";
            this.amount = this.game.getPiles().get(1).getSize() - nimSumB;
            return;
        }
        int nimSumC = this.game.getPiles().get(2).getSize() ^ totalNimSum;
        if (nimSumC < this.game.getPiles().get(2).getSize()) {
            this.targetPile = "C";
            this.amount = this.game.getPiles().get(2).getSize() - nimSumC;
            return;
        }
    }

    public int getAmount() {
        return this.amount;
    }

    public String getTargetPile() {
        return this.targetPile;
    }

    public String toString() {
        return "Niminator";
    }

    public boolean shouldGoFirst() {
        int nimSum = this.calculateNimSum();
        if (nimSum == 0) {
            return false;
        }
        return true;
    }

    private boolean isEndGame() {
        int a = this.game.getPiles().get(0).getSize();
        int b = this.game.getPiles().get(1).getSize();
        int c = this.game.getPiles().get(2).getSize();

        if (a < 2 && b < 2 && c >= 2) {
            return true;
        }

        if (a < 2 && b >= 2 && c < 2) {
            return true;
        }

        if (a >= 2 && b < 2 && c < 2) {
            return true;
        }
        return false;
    }

    private int calculatePileSum() {
        return this.game.getPiles().get(0).getSize() + this.game.getPiles().get(1).getSize()
                + this.game.getPiles().get(2).getSize();
    }

    private int calculateNimSum() {
        return this.game.getPiles().get(0).getSize() ^ this.game.getPiles().get(1).getSize()
                ^ this.game.getPiles().get(2).getSize();
    }

    private int findLargetPile() {
        int index = 0;
        int largestSeen = 0;
        for (int i = 0; i < this.game.getPiles().size(); i++) {
            int pileSize = this.game.getPiles().get(i).getSize();
            if (pileSize > largestSeen) {
                largestSeen = pileSize;
                index = i;
            }
        }
        return index;
    }
}
