package GameComponents;

public class ResultsTable {
    private final int[][] table;
    private final int numberOfPlayers;

    public ResultsTable(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        table = new int[numberOfPlayers][numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            for (int j = 0; j < numberOfPlayers; j++) {
                table[i][j] = -1;
            }
        }
    }

    public ResultsTable(ResultsTable resultsTable) {
        this.numberOfPlayers = resultsTable.getNumberOfPlayers();
        table = new int[numberOfPlayers][numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            for (int j = 0; j < numberOfPlayers; j++) {
                table[i][j] = resultsTable.get(i, j);
            }
        }
    }

    public void setResults(GameResult gameResult) {
        setResults(gameResult.getPlayer1(), gameResult.getPlayer2(), gameResult.getResult());
    }

    public void setResults(int player1, int player2, int result) {
        table[player1][player2] = result;
        table[player2][player1] = Calculate.opposite(result);
    }

    public void add(ResultsTable table) {
        for (int i = 0; i < numberOfPlayers; i++) {
            for (int j = 0; j < numberOfPlayers; j++) {
                this.table[i][j] = this.table[i][j] == -1 ? table.get(i, j) : this.table[i][j] + table.get(i, j);
            }
        }
    }

    public int get(int i, int j) {
        return table[i][j];
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    @Override
    public String toString() {
        StringBuilder finalTable = new StringBuilder(" Against (ID) | ");
        for (int i = 0; i < numberOfPlayers; i++) {
            finalTable.append(i + 1).append(' ');
        }
        finalTable.append(" | Summary:\n").append(" Players (ID) \\");
        finalTable.append("__".repeat(numberOfPlayers + 1)).append("|_").append("__".repeat(4)).append('\n');
        for (int i = 0; i < numberOfPlayers; i++) {
            finalTable.append(" ".repeat(12)).append(i + 1).append(" | ");
            int sum = 0;
            for (int j = 0; j < numberOfPlayers; j++) {
                sum += table[i][j] == -1 ? 0 : table[i][j];
                if (table[i][j] == -1) {
                    finalTable.append('-').append(' ');
                } else {
                    finalTable.append(table[i][j]).append(' ');
                }
            }
            finalTable.append(" | ").append(sum).append("\n");
        }
        return finalTable.toString();
    }
}