package strategies.game;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;

public class DiagonalWinningStrategy implements WinningStrategy {
    private HashMap<Symbol, Integer> mainDiagonalCounts = new HashMap<>();
    private HashMap<Symbol, Integer> antiDiagonalCounts = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        int boardSize = board.getSize();

        // Check if the move is on the main diagonal (row == col)
        if (row == col) {
            if (!mainDiagonalCounts.containsKey(symbol)) {
                mainDiagonalCounts.put(symbol, 0);
            }
            mainDiagonalCounts.put(symbol, mainDiagonalCounts.get(symbol) + 1);

            // If all cells on the main diagonal are filled by the same symbol
            if (mainDiagonalCounts.get(symbol) == boardSize) {
                return true;
            }
        }

        // Check if the move is on the anti-diagonal (row + col == boardSize - 1)
        if (row + col == boardSize - 1) {
            if (!antiDiagonalCounts.containsKey(symbol)) {
                antiDiagonalCounts.put(symbol, 0);
            }
            antiDiagonalCounts.put(symbol, antiDiagonalCounts.get(symbol) + 1);

            // If all cells on the anti-diagonal are filled by the same symbol
            if (antiDiagonalCounts.get(symbol) == boardSize) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        int boardSize = board.getSize();

        // Undo for main diagonal
        if (row == col && mainDiagonalCounts.containsKey(symbol)) {
            mainDiagonalCounts.put(symbol, mainDiagonalCounts.get(symbol) - 1);
        }

        // Undo for anti-diagonal
        if (row + col == boardSize - 1 && antiDiagonalCounts.containsKey(symbol)) {
            antiDiagonalCounts.put(symbol, antiDiagonalCounts.get(symbol) - 1);
        }
    }

}
