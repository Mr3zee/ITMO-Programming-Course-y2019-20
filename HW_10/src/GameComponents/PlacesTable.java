package GameComponents;

import java.util.*;

public class PlacesTable {
    private final List<PlayerInfo> players;

    public PlacesTable(List<PlayerInfo> players) {
        this.players = players;
        clear();
    }

    public void setResults(int player1, int player2, int resultPlayer) {
        players.get(player1).addToResult(resultPlayer);
        players.get(player2).addToResult(Calculate.opposite(resultPlayer));
    }

    public int getPlayerResult(int id) {
        return players.get(id).getResult();
    }

    public void clear() {
        for (PlayerInfo player : players) {
            player.setResult(0);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        List<PlayerInfo> list = new ArrayList<>();
        copy(players, list);
        list.sort(new ComparePairs());
        int place = 1;
        for (int i = 0; i < list.size(); i++) {
            if (i > 0 && list.get(i - 1).getResult() != list.get(i).getResult()) {
                place++;
            }
            stringBuilder.append(place).append(" place: ").append(list.get(i).getName()).append(" with result ").append(list.get(i).getResult()).append('\n');
        }
        return stringBuilder.toString();
    }

    private void copy(List<PlayerInfo> players, List<PlayerInfo> list) {
        for (PlayerInfo player : players) {
            list.add(new PlayerInfo(player));
        }
    }

    private static class ComparePairs implements Comparator<PlayerInfo> {
        @Override
        public int compare(PlayerInfo pair1, PlayerInfo pair2) {
            return  pair2.getResult() - pair1.getResult();
        }
    }
}
