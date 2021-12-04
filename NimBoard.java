import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class NimBoard extends AbstractBoard {

    int stone;

    NimBoard(int stone) {
        this.stone = stone;

    }

    boolean iszero(int n, Player.ID p) {
        if (n == 0 && nextTurn != p)
            return true;
        return false;

    }

    boolean testWin(Player.ID p) {
        return iszero(stone, p);
    }

    boolean didP1Win() {
        return testWin(Player.ID.P1);// 2に変えると1
    } // P1の勝ちか？

    boolean didP2Win() {
        return testWin(Player.ID.P2);
    } // P2の勝ちか？

    public boolean isEndOfGame() {
        return didP1Win() || didP2Win();
    }

    public Player.ID winner() {
        if (didP1Win()) {// 2に変えると1
            return Player.ID.P1;// 2に変えると2
        } else if (didP2Win()) {
            return Player.ID.P2;
        } else {
            return Player.ID.NONE;
        }
    }

    public List<Integer> legalMoves() {
        List<Integer> result = new LinkedList<Integer>();
        for (int i = 1; i <= 3; i++) {
            if (stone - i >= 0)
                result.add(i);
        }
        return result;
    }

    public Object boardState() {
        return stone;
    }

    public void put(int m) {
        history.push(m);
        stone = stone - m;
        flipTurn(); // 手番を反転する
    }

    public void unput() {
        int m = history.pop();
        stone = stone + m;
        flipTurn(); // 手番を反転する
    }

    public void print(PrintStream out) {
        System.out.println(stone + "個残っています.");
    }
}