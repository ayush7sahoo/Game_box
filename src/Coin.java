import java.util.Random;

public class Coin {
    private final Random random = new Random();

    public boolean toss() {
        return random.nextBoolean();
    }
}
