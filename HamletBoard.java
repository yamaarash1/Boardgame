import java.io.PrintStream;
import java.util.*;

public class HamletBoard extends AbstractBoard {
    Player.ID[] board; // 3x3 の２次元配列でなく，(x,y)座標をx+3yに配置した１次元配列を使う

    HamletBoard() {
        board = new Player.ID[6 * 6];
        for (int i = 0; i < 6 * 6; i++) {
            board[i] = Player.ID.NONE;
        }
    }

    // posから，vector方向にプレイヤーpのコマが３つ並んでいればtrue
    boolean four(int pos, int vector, Player.ID p) {
        for (int i = 0; i < 4; i++) {
            if (board[pos] != p) {
                return false;
            }
            pos += vector;
        }
        return true;
    }

    boolean testWin(Player.ID p) {
        boolean flag = false;
        // 縦
        for (int i = 0; i < 18; i++) {
            if (four(i, 6, p))
                flag = true;
        }
        // 横
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                if (four(6 * i + j, 1, p))
                    flag = true;
            }
        }
        // 斜め
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (four(6 * j + i, 6 + 1, p))
                    flag = true;
            }
        }
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                if (four(6 * j + i, 5, p))
                    flag = true;
            }
        }
        return flag;
    }

    boolean didP1Win() {
        return testWin(Player.ID.P1);
    } // P1の勝ちか？

    boolean didP2Win() {
        return testWin(Player.ID.P2);
    } // P2の勝ちか？

    boolean isEndOfGamePlus() {
        boolean flag = false;
        int n = 0;
        for (int i = 0; i < 6; i++) {
            if (board[i] != Player.ID.NONE)
                n++;
        }
        if (n == 5)
            flag = true;
        return flag;
    }

    public boolean isEndOfGame() {
        return didP1Win() || didP2Win() || isEndOfGamePlus();

    }

    public Player.ID winner() {
        if (didP1Win()) {
            return Player.ID.P1;
        } else if (didP2Win()) {
            return Player.ID.P2;
        } else {
            return Player.ID.NONE;
        }
    }

    public List<Integer> legalMoves() { // 空いている盤の位置のリストを返す
        List<Integer> result = new LinkedList<Integer>();
        for (int i = 0; i < 6; i++) {
            if (board[i] == Player.ID.NONE)
                result.add(i);
        }
        return result;
    }

    public Object boardState() {
        return board;
    }

    public void put(int m) {
        int n = 0;
        while (board[m + 6 * n] == Player.ID.NONE && n < 5) {
            n++;
        }
        n--;
        if (board[m + 6 * 5] == Player.ID.NONE)
            n++;
        history.push(m + 6 * n);
        board[m + 6 * n] = nextTurn;

        flipTurn(); // 手番を反転する
    }

    public void unput() {
        int m = history.pop();
        board[m] = Player.ID.NONE;
        flipTurn(); // 手番を反転する
    }

    public void print(PrintStream out) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int num = x + y * 6;
                switch (board[num]) {
                case P1:
                    out.print(" O ");
                    break;
                case P2:
                    out.print(" X ");
                    break;
                case NONE:
                    if (y < 5 && board[num + 6] != Player.ID.NONE)
                        out.print("(" + x + ")");
                    else if (y == 5)
                        out.print("(" + x + ")");
                    else
                        out.print("   ");
                    break;
                }

                if (x < 5) {
                    out.print("|");
                }
            }
            out.println();
            if (y < 6) {
                out.println("---+---+---+---+---+---");
            }
        }
    }
}
