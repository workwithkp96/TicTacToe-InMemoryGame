package models;

import strategies.game.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {

    private Board board;
    private List<Player> players;
    private Player winner;
    // this is used to treat index as turn flag in list of players
    private int nextPlayerIndex;
    private List<Move> moves;
    private GameState gameState;
    private final List<WinningStrategy> winningStrategies;

    private Game(Builder builder){
        this.players = builder.players;
        this.winner = null;
        this.nextPlayerIndex = 0;
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
        this.winningStrategies = builder.winningStrategies;
        board = new Board(this.winningStrategies);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public void display(){
        board.displayBoard();
    }

    public boolean validateMove(Move move){
        int r = move.getCell().getRow();
        int c = move.getCell().getCol();

        if(r < 0 || r > board.getSize() - 1 || c < 0 || c > board.getSize() - 1){
            return false;
        }

        //checking if Cell is not already filled
        return board.getGrid().get(r).get(c).getCellState().equals(CellState.EMPTY);
    }

    public void makeMove(){
        Player currentPlayer = players.get(nextPlayerIndex);

        System.out.println("It's " + currentPlayer.getName() + "'s turn! Please make your move");

        Move move = currentPlayer.makeMove(board);

        if (!validateMove(move)) {
            System.out.println("Invalid Move! Please try again");
            return;
        }

        int r = move.getCell().getRow();
        int c = move.getCell().getCol();

        Cell cellToChange = board.getGrid().get(r).get(c);
        cellToChange.setSymbol(currentPlayer.getSymbol());
        cellToChange.setCellState(CellState.FILLED);

        move.setCell(cellToChange);
        moves.add(move);

        nextPlayerIndex++;
        nextPlayerIndex %= players.size();

        // we need to check if someone has won the game or not
        if(checkWinner(move)){
            setWinner(currentPlayer);
            setGameState(GameState.SUCCESS);
        } else if (moves.size() ==  board.getSize() * board.getSize()){
            setWinner(null);
            setGameState(GameState.DRAW);
        }
    }

    public boolean checkWinner(Move move){
        // Going through every winning strategies available to find the winner
        for(WinningStrategy winningStrategy : winningStrategies){
            if(winningStrategy.checkWinner(this.board , move)){
                return true;
            }
        }
        return false;
    }

    public void undo(){
        // reverse the last move
        if(moves.isEmpty()){
            System.out.println("Nothing to Undo! Please make a move first");
            return;
        }

        Move lastMove = moves.getLast();
        moves.removeLast();

        lastMove.getCell().setCellState(CellState.EMPTY);
        lastMove.getCell().setSymbol(null);

        nextPlayerIndex--;
        nextPlayerIndex = (nextPlayerIndex + players.size()) % players.size();


        // decrease the frequencies in the hashmap
        for (WinningStrategy winningStrategy : winningStrategies) {
            winningStrategy.handleUndo(board , lastMove);
        }

    }

    public static class Builder{
        private int dimension;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public void validate(){
            // validations
            // 1. count of players = dimension - 1
            playerCountValidation();
            // 2. Bot count <= 1
            botCountValidation();
            // 3. Every player has different symbol
            differentSymbolValidation();

        }

        public void playerCountValidation(){
            if(players.size() != dimension - 1){
                throw new RuntimeException("Incorrect number of players");
            }
        }

        public void botCountValidation(){
            int botCount = 0;
            for(Player player : players){
                if(player.getPlayerType().equals(PlayerType.BOT))
                    botCount+=1;
            }
            if(botCount > 1){
                throw new RuntimeException("Too many Bot players");
            }
        }

        public void differentSymbolValidation(){
            HashSet<Symbol> symbols = new HashSet<>();
            for(Player player : players){
                if(symbols.contains(player.getSymbol()))
                    throw new RuntimeException("Same symbol for multiple players");

                symbols.add(player.getSymbol());
            }
        }

        public Game build(){this.validate();
           return new Game(this);
        }
    }
}