package GameComponents;

import java.io.PrintStream;
import java.util.*;

public class Tournament {
    private final PrintStream out;
    private final int numberOfPlayers;
    private final int circles;
    private final List<List<Game>> games;
    private final List<PlayerInfo> players;
    private boolean showLog;

    public Tournament(final List<PlayerInfo> players, final PrintStream out, final int circles, boolean showLog, boolean showGameLog) {
        this.players = players;
        numberOfPlayers = players.size();
        this.out = out;
        this.circles = circles;
        this.showLog = showLog;
        games = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            List<Game> gameList = new ArrayList<>();
            for (int j = i + 1; j < numberOfPlayers; j++) {
                gameList.add(new Game(players.get(i).getType(), players.get(j).getType(), showGameLog, out));
            }
            games.add(gameList);
        }
    }

    public Tournament(final List<PlayerInfo> players, int circles, boolean showLog, boolean showGameLog) {
        this(players, System.out, circles, showLog, showGameLog);
    }

    public TournamentResults playTournament(final GameBoard gameBoard) {
//        long l = System.nanoTime();
        TournamentResults tournamentResults = new TournamentResults(numberOfPlayers, players, circles);
        for (int f = 0; f < circles; f++) {
            ResultsTable currentTable = new ResultsTable(numberOfPlayers);
            int i = 0;
            for (List<Game> gameList : games) {
                int j = i + 1;
                for (Game game : gameList) {
                    GameResult gameResult = new GameResult(i, j++, Calculate.points(game.playTheGame(gameBoard.newBoard())));
                    tournamentResults.setResults(gameResult);
                    currentTable.setResults(gameResult);
                }
                i++;
            }
            tournamentResults.addResultsTable(currentTable);
            showLog(currentTable.toString());
        }
        showLog("\n Results:\n\n" + tournamentResults.getResultsTable().toString());
        out.println(tournamentResults.getPlacesTable().toString());
        return tournamentResults;
//        out.println("Time: " + ((System.nanoTime() - l) / 1000000) + "ms");
    }

    private void showLog(String log) {
        if (showLog) {
            out.println(log);
        }
    }
}
