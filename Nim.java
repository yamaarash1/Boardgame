public class Nim extends SimpleGame {
    Nim(String[] args) {
        super();
        map.put("--n", "30"); // 石の数のデフォルト値
        // map.put("--p1", "nim"); // プレイヤー１はデフォルトでNimPlayer
        map.put("--p2", "nim"); // --p2を指定しないと，プレイヤー2はGeneralPlayerになる．
        parseArgs(args);
    }

    @Override
    Player makePlayer(String type, String name) {
        if (type.equals("nim")) {
            return new NimPlayer(name);
        } else {
            return super.makePlayer(type, name);
        }
    }

    void play() {
        play(new NimBoard(Integer.parseInt(map.get("--n"))));
    }

    public static void main(String[] args) {
        (new Nim(args)).play();
    }
}