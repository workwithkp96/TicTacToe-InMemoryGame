package models;

import strategies.bot.BotPlayingStrategy;
import strategies.bot.BotPlayingStrategyFactory;

public class BotPlayer extends Player {
    private final BotPlayingStrategy botPlayingStrategy;

    public BotPlayer(Long id, String name , Symbol symbol , BotDifficultyLevel botDifficultyLevel) {
        super(id, name, symbol);
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botDifficultyLevel);
        this.setPlayerType(PlayerType.BOT);
    }
    public Move makeMove(Board board){
        Move move =  botPlayingStrategy.makeMove(board, this);
        move.setPlayer(this);
        return move;
    }
}
