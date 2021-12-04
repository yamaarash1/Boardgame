import java.util.*;

public class SimplePlayer implements Player {
    String playerName;
    Scanner scanner;

    SimplePlayer(String name) {
        playerName = name;
        scanner = new Scanner(System.in);
    }

    public String getName() {
        return playerName;
    }

    public int move(Board board) {
        List<Integer> moves = board.legalMoves();
        int selection;
        while (true) {
            System.out.print("選択してください: ");
            String str = scanner.next();
            try {
                selection = Integer.parseInt(str);
                if (moves.contains(selection)) {
                    break;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println(str + "は選べません．");
        }
        return selection;
    }
}