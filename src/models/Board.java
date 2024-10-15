package models;

import strategies.game.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> grid;
    private final List<WinningStrategy> winningStrategies;
    public Board(List<WinningStrategy> winningStrategies) {
        this.size = 3;
        this.grid = new ArrayList<>();
        // create the grid
        for(int i = 0; i < size; i++) {
            grid.add(new ArrayList<>());
            for(int j = 0; j < size; j++) {
                grid.get(i).add(new Cell( i , j));
            }
        }

        this.winningStrategies = winningStrategies;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getGrid() {
        return grid;
    }

    public void setGrid(List<List<Cell>> grid) {
        this.grid = grid;
    }

    public void displayBoard(){
        for(List<Cell> row : grid){
            for(Cell cell : row){
                cell.displayCell();
            }
            System.out.println();
        }
    }

    public boolean isWinningMove(Move move){
        boolean possibleWinningMove=false;
        for(WinningStrategy winningStrategy : winningStrategies){
            if(winningStrategy.checkWinner(this,move))
                possibleWinningMove = true;
            winningStrategy.handleUndo(this,move);
        }
        return possibleWinningMove;
    }
}
