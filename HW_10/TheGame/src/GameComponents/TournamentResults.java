package GameComponents;

import java.util.ArrayList;
import java.util.List;

public class TournamentResults {
    private final int numberOfPlayers;
    private final int circles;
    private final ResultsTable resultsTable;
    private final List<ResultsTable> tables;
    private final PlacesTable placesTable;

    public TournamentResults(final int numberOfPlayers, final List<PlayerInfo> players, final int circles) {
        this.circles = circles;
        this.numberOfPlayers = numberOfPlayers;
        resultsTable = new ResultsTable(numberOfPlayers);
        tables = new ArrayList<>();
        placesTable = new PlacesTable(players);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setResults(GameResult gameResult) {
        placesTable.setResults(gameResult.getPlayer1(), gameResult.getPlayer2(), gameResult.getResult());
    }

    public void addResultsTable(ResultsTable resultsTable) {
        tables.add(new ResultsTable(resultsTable));
        this.resultsTable.add(resultsTable);
    }

    public ResultsTable getResultsTable() {
        return resultsTable;
    }

    public List<ResultsTable> getTables() {
        return tables;
    }

    public PlacesTable getPlacesTable() {
        return placesTable;
    }

    public int getNumberOfCircles() {
        return circles;
    }

    public int getPlayerResult(int id) {
        checkId(id, numberOfPlayers);
        return placesTable.getPlayerResult(id - 1);
    }

    public int getGameResult(int player1, int player2, int circle) {
        checkId(player1, numberOfPlayers, player2, numberOfPlayers, circle, circles);
        return tables.get(circle - 1).get(player1 - 1, player2 - 1);
    }

    public int getGamesResult(int player1, int player2) {
        checkId(player1, numberOfPlayers, player2, numberOfPlayers);
        return resultsTable.get(player1 - 1, player2 - 1);
    }

    public ResultsTable getResultsTableForCircle(int circle) {
        checkId(circle, circles);
        return tables.get(circle - 1);
    }

    private void checkId(int id, int border) {
        if (id > border || id < 0) {
            throw new IllegalArgumentException("\nNo such player/circle id found: " + id +
                    "\nNumber of players: " + getNumberOfPlayers() +
                    "\nNumber of circles: " + getNumberOfCircles());
        }
    }

    private void checkId(int id1, int border1, int id2, int border2) {
        checkId(id1, border1);
        checkId(id2, border2);
    }

    private void checkId(int id1, int border1, int id2, int border2, int id3, int border3) {
        checkId(id1, border1);
        checkId(id2, border2);
        checkId(id3, border3);
    }
}
