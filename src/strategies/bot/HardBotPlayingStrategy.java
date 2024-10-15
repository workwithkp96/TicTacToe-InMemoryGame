package strategies.bot;

import models.*;

import java.util.List;

public class HardBotPlayingStrategy implements BotPlayingStrategy {

    @Override
    public Move makeMove(Board board, BotPlayer botPlayer) {
        Symbol botSymbol = botPlayer.getSymbol();
        // 1. Try to win in this move
        for (List<Cell> row : board.getGrid()) {
            for (Cell cell : row) {
                if (cell.getCellState().equals(CellState.EMPTY)) {
                    cell.setSymbol(botSymbol);
                    if (board.isWinningMove(new Move(cell, botPlayer))) {
                        // If bot can win, take the move
                        return new Move(new Cell(cell.getRow(), cell.getCol()), botPlayer);
                    }
                    // Reset cell after simulation
                    cell.setSymbol(null);
                }
            }
        }

        // 2. Block opponent's winning move
        Symbol opponent = opponentSymbol(botSymbol);
        for (List<Cell> row : board.getGrid()) {
            for (Cell cell : row) {
                if (cell.getCellState().equals(CellState.EMPTY)) {
                    cell.setSymbol(opponent);
                    if (board.isWinningMove(new Move(cell, botPlayer))) {
                        // Block opponent's winning move
                        return new Move(new Cell(cell.getRow(), cell.getCol()), botPlayer);
                    }
                    // Reset cell after simulation
                    cell.setSymbol(null);
                }
            }
        }

        // 3. Prioritize center if available
        int center = board.getSize() / 2;
        if (board.getGrid().get(center).get(center).getCellState().equals(CellState.EMPTY)) {
            return new Move(new Cell(center, center), botPlayer);
        }

        // 4. Prioritize corners
        int boardSize = board.getSize();
        int[][] corners = {{0, 0}, {0, boardSize - 1}, {boardSize - 1, 0}, {boardSize - 1, boardSize - 1}};
        for (int[] corner : corners) {
            int row = corner[0];
            int col = corner[1];
            if (board.getGrid().get(row).get(col).getCellState().equals(CellState.EMPTY)) {
                return new Move(new Cell(row, col), botPlayer);
            }
        }

        // 5. Fallback to any available move
        for (List<Cell> row : board.getGrid()) {
            for (Cell cell : row) {
                if (cell.getCellState().equals(CellState.EMPTY)) {
                    return new Move(new Cell(cell.getRow(), cell.getCol()), botPlayer);
                }
            }
        }

        return null;
    }

    private Symbol opponentSymbol(Symbol botSymbol) {
        return botSymbol == Symbol.X ? Symbol.O : Symbol.X;
    }
}

