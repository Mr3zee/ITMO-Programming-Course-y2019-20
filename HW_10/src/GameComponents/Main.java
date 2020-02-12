package GameComponents;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int boardHeight = Integer.parseInt(args[0]);
        int boardWidth = Integer.parseInt(args[1]);
        int boardLength = Integer.parseInt(args[2]);
        int numberOfHumanPlayers = Integer.parseInt(args[3]);
        int numberOfRandomPlayers = Integer.parseInt(args[4]);
        int numberOfSeqPlayers = Integer.parseInt(args[5]);
        int circles = Integer.parseInt(args[6]);


        final BoardConfiguration boardConfiguration = new BoardConfiguration(boardHeight, boardWidth, boardLength);
        List<PlayerInfo> players = setPlayers(numberOfHumanPlayers, numberOfRandomPlayers, numberOfSeqPlayers);
        Tournament tournament = new Tournament(players, circles, true, true);
        TournamentResults tournamentResults = tournament.playTournament(new MNKBoard(boardConfiguration));
    }

    private static List<PlayerInfo> setPlayers(int numberOfHumanPlayers, int numberOfRandomPlayers, int numberOfSeqPlayers) {
        List<PlayerInfo> players = new ArrayList<>();
        for (int i = 0; i < numberOfHumanPlayers; i++) {
            players.add(new PlayerInfo("HumanPlayer " + (i + 1), new HumanPlayer()));
        }
        for (int i = 0; i < numberOfRandomPlayers; i++) {
            players.add(new PlayerInfo("RandomPlayer " + (i + 1), new RandomPlayer()));
        }
        for (int i = 0; i < numberOfSeqPlayers; i++) {
            players.add(new PlayerInfo("SeqPlayer " + (i + 1), new SeqPlayer()));
        }
        return players;
    }
}