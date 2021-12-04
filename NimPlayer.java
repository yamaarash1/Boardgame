import java.util.*;

public class NimPlayer implements Player {
    String name;
    int stone;
    Scanner scanner;

    int check = 1;

    NimPlayer(String name) {
        this.name = name;
        this.stone = stone;
        scanner = new Scanner(System.in);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int move(Board board) {
        List<Integer> moves = board.legalMoves();
        stone = (int) board.boardState();
        if (stone == 30) {
            check = 0;
        }
        int selection = 2;
        if (check == 1) {
            if (stone == 1)
                selection = 1;
            if (stone == 2)
                selection = 2;
            if (stone == 3)
                selection = 3;
            if (stone > 3) {
                if ((moves.contains(selection)) && (stone) % 4 == 1) {
                    selection = 1;
                } else if ((moves.contains(selection)) && (stone) % 4 == 2) {
                    selection = 2;
                } else if ((moves.contains(selection)) && (stone) % 4 == 3) {
                    selection = 3;
                } else {
                    selection = 1;
                }
            }
        } else {
            if (stone == 1)
                selection = 1;
            if (stone == 2)
                selection = 2;
            if (stone == 3)
                selection = 3;
            if (stone > 3) {
                if ((moves.contains(selection)) && (stone) % 3 == 0) {
                    selection = 2;
                } else if ((moves.contains(selection)) && (stone) % 3 == 1) {
                    selection = 3;
                } else if ((moves.contains(selection)) && (stone) % 3 == 2) {
                    selection = 1;
                }
            }
        }
        return selection;

    }
}
