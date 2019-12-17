package GameComponents;

public class PlayerInfo {
    private final String name;
    private final Player type;
    private int result;

    public PlayerInfo(String name, Player type, int result) {
        this.name = name;
        this.type = type;
        this.result = result;
    }

    public PlayerInfo(String name, Player type) {
        this(name, type, 0);
    }

    public PlayerInfo(PlayerInfo playerInfo) {
        this(playerInfo.getName(), playerInfo.getType(), playerInfo.getResult());
    }

    public String getName() {
        return name;
    }

    public Player getType() {
        return type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void addToResult(int add) {
        result += add;
    }
}
