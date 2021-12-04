import java.io.PrintStream;
import java.util.*;

public class GomokuBoard extends AbstractBoard {
    Player.ID[] board; // 3x3 の２次元配列でなく，(x,y)座標をx+3yに配置した１次元配列を使う

    GomokuBoard() {
        board = new Player.ID[8 * 8];
        for (int i = 0; i < 8 * 8; i++) {
            board[i] = Player.ID.NONE;
        }

    }

    // posから，vector方向にプレイヤーpのコマが３つ並んでいればtrue
    boolean five(int pos, int vector, Player.ID p) {
        for (int i = 0; i < 5; i++) {
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
        for (int i = 0; i < 32; i++) {
            if (five(i, 8, p))
                flag = true;
        }
        // 横
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (five(8 * i + j, 1, p))
                    flag = true;
            }
        }
        // 斜め(9足していく)0~27
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (five(8 * i + j, 8 + 1, p))
                    flag = true;
            }
        }
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (five(8 * i + j, -7, p))
                    flag = true;
            }
        }

        return flag;

        /*
         * // 縦 … +3すると，y座標を1増やすことになる three(0, 3, p) || three(1, 3, p) || three(2, 3, p)
         * || // 横 … +1すると，x座標を1増やすことになる three(0, 1, p) || three(3, 1, p) || three(6, 1,
         * p) || // 斜め … +3 + +1 と， -3 + +1 three(0, 3 + 1, p) || three(6, -2, p);
         */
    }

    boolean didP1Win() {
        return testWin(Player.ID.P1);
    } // P1の勝ちか？

    boolean didP2Win() {
        return testWin(Player.ID.P2);
    } // P2の勝ちか？

    public boolean isEndOfGame() {
        return didP1Win() || didP2Win() || history.size() == 64;
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
        for (int i = 0; i < 8 * 8; i++) {
            if (board[i] == Player.ID.NONE) {
                result.add(i);
            }
        }
        return result;
    }

    public Object boardState() {
        return board;
    }

    public void put(int m) {
        history.push(m);
        board[m] = nextTurn;
        flipTurn(); // 手番を反転する
    }

    public void unput() {
        int m = history.pop();
        board[m] = Player.ID.NONE;
        flipTurn(); // 手番を反転する
    }

    public void print(PrintStream out) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int num = x + y * 8;
                switch (board[num]) {
                case P1:
                    out.print(" " + " O ");
                    break;
                case P2:
                    out.print(" " + " X ");
                    break;
                case NONE:
                    if (num < 10)
                        out.print("(" + "0" + num + ")");
                    else
                        out.print("(" + num + ")");
                    break;
                }
                if (x < 8) {
                    out.print("|");
                }
            }
            out.println();
            if (y < 8) {
                out.println("----+----+----+----+----+----+----+----+");
            }

        }
        out.println("10未満の数は碁盤の番号の右の0を消しても一の位の数字として選択されます.");
    }
}