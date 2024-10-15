**Tic-Tac-Toe In-Memory Game**
**Description**
This project implements a simple Tic-Tac-Toe game that can be played locally in memory, either between two human players or between a human and a bot. 
The game is developed using Java and runs in the console, allowing players to take turns by entering row and column positions.

The project includes different bot difficulty levels:

Easy: Random valid moves.
Medium: Tries to block the opponent from winning.
Hard: Attempts to win first and block the opponent if no winning move is possible.
Features
Local play in memory without external storage.
Two player modes: Human vs. Human or Human vs. Bot.
Configurable bot difficulty levels: Easy, Medium, Hard.
Simple and interactive console-based game interface.


**Prerequisites**
Java 21 or higher installed on your machine.
A code editor or IDE (optional, but recommended for running the code).
How to Run
Clone the repository or download the source code:

bash
Copy code
git clone https://github.com/your-repo/tictactoe.git
Navigate to the project directory:

bash
Copy code
cd tictactoe
Compile the Java files:

css
Copy code
javac -d bin src/*.java
Run the game:

bash
Copy code
java -cp bin Main
How to Play
Game setup: You will be asked whether you want to play against another player or the bot.

Choose 1 for Human vs. Human.
Choose 2 for Human vs. Bot and select the bot difficulty level.
Take turns: Players take turns entering their moves by specifying the row and column (both starting from 0).

For example, to place a mark in the top-left corner, enter 0 0.
Winning the game: A player wins by placing three of their symbols (X or O) in a row, column, or diagonal. If all cells are filled without a winner, the game ends in a draw.

Undo move (optional): If you wish to undo a move, this feature is implemented for custom rules.


**Bot Strategy Overview**
Easy Bot: Randomly selects any available cell.
Medium Bot: Checks if the opponent is about to win and blocks the move. Otherwise, makes a random valid move.
Hard Bot: Tries to win by prioritizing its winning moves, then blocks the opponent’s winning move, prioritizes the center, then corners, and finally makes a random move.
