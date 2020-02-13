package GameComponents;

public class Calculate {
    public static int points(int gameResult) {
        return gameResult == 0 ? 1 : gameResult == 1 ? 3 : 0;
    }

    public static int opposite(int result) {
        return result == 3 ? 0 : result == 1 ? 1 : 3;
    }
}
