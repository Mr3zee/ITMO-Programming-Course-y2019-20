package tests;

public class Colors {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public static String paintBlack(String string) {
        return ANSI_BLACK + string + ANSI_RESET;
    }

    public static String paintRed(String string) {
        return ANSI_RED + string + ANSI_RESET;
    }

    public static String paintGreen(String string) {
        return ANSI_GREEN + string + ANSI_RESET;
    }

    public static String paintYellow(String string) {
        return ANSI_YELLOW + string + ANSI_RESET;
    }

    public static String paintBlue(String string) {
        return ANSI_BLUE + string + ANSI_RESET;
    }

    public static String paintPurple(String string) {
        return ANSI_PURPLE + string + ANSI_RESET;
    }

    public static String paintCyan(String string) {
        return ANSI_CYAN + string + ANSI_RESET;
    }

    public static String paintWhite(String string) {
        return ANSI_WHITE + string + ANSI_RESET;
    }
}
