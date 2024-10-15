package strategies.bot;

import models.*;

import java.util.List;

public class MediumBotPlayingStrategy implements BotPlayingStrategy {
    @Override
    public Move makeMove(Board board, BotPlayer botPlayer) {
        Symbol botSymbol = botPlayer.getSymbol();
        // 1. Check if the opponent can win in the next move
        for (List<Cell> row : board.getGrid()) {
            for (Cell cell : row) {
                if (cell.getCellState().equals(CellState.EMPTY)) {
                    // Simulate opponent move
                    cell.setSymbol(opponentSymbol(botSymbol));
                    if (board.isWinningMove(new Move(cell,botPlayer))) {
                        // Block opponent
                        cell.setSymbol(null); // Reset cell
                        return new Move(new Cell(cell.getRow(), cell.getCol()), botPlayer);
                    }
                    // Reset cell after simulation
                    cell.setSymbol(null);
                }
            }
        }

        // 2. If no threat found, make any available move
        for (List<Cell> row : board.getGrid()) {
            for (Cell cell : row) {
                if (cell.getCellState().equals(CellState.EMPTY)) {
                    return new Move(new Cell(cell.getRow(), cell.getCol()), null);
                }
            }
        }
        return null;
    }

    private Symbol opponentSymbol(Symbol botSymbol) {
        return botSymbol.equals(Symbol.X) ? Symbol.O : Symbol.X;
    }
}
