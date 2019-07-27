package dev.alexvieira.jogodaveia.models;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import dev.alexvieira.jogodaveia.utils.StringUtility;

public class Game {
    private static final String TAG = Game.class.getSimpleName();
    private static final int BOARD_SIZE = 3;

    public Player player1;
    public Player player2;

    public Player currentPlayer = player1;
    public Cell[][] cells;

    public MutableLiveData<Player> winner = new MutableLiveData<>();

    public Game(String playerOne, String playerTwo) {
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        player1 = new Player(playerOne, "x");
        player2 = new Player(playerTwo, "o");
        currentPlayer = player1;
    }

    public boolean hasGameEnded() {
        if (hasThreeSameHorizontalCells() || hasThreeSameVerticalCells() || hasThreeSameDiagonalCells()) {
            winner.setValue(currentPlayer);
            return true;
        }
        if (isBoardFull()) {
            winner.setValue(null);
            return true;
        }
        return false;
    }

    public boolean hasThreeSameHorizontalCells() {
        try {
            for (int i = 0; i < BOARD_SIZE; i++) if (areEqual(cells[i][0], cells[i][1], cells[i][2])) return true;
            return false;
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public boolean hasThreeSameVerticalCells() {
        try {
            for (int i = 0; i < BOARD_SIZE; i++) if (areEqual(cells[0][i], cells[1][i], cells[2][i])) return true;
            return false;
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public boolean hasThreeSameDiagonalCells() {
        try {
            return areEqual(cells[0][0], cells[1][1], cells[2][2]) || areEqual(cells[0][2], cells[1][1], cells[2][0]);
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public boolean isBoardFull() {
        for (Cell[] row : cells) for (Cell cell : row) if (cell == null || cell.isEmpty()) return false;
        return true;
    }

    private boolean areEqual(Cell... cells) {
        if (cells == null || cells.length == 0) return false;
        for (Cell cell : cells)
            if (cell == null || StringUtility.isNullOrEmpty(cell.getPlayer().getValue())) return false;
        Cell comparisonBase = cells[0];
        for (int i = 1; i < cells.length; i++)
            if (!comparisonBase.getPlayer().getValue().equals(cells[i].getPlayer().getValue())) return false;
        return true;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    public void reset() {
        player1 = null;
        player2 = null;
        currentPlayer = null;
        cells = null;
    }
}
