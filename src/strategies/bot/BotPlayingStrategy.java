package strategies.bot;

import models.Board;
import models.BotPlayer;
import models.Move;

public interface BotPlayingStrategy {
    public Move makeMove(Board board, BotPlayer botPlayer);
}
// Easy : first empty cell
// Medium : Try to avoid the loss
// Hard : figure out certain game-plays to win the game