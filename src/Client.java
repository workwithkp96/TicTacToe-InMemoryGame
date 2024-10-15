import Controllers.GameController;
import models.*;
import strategies.game.ColWinningStrategy;
import strategies.game.DiagonalWinningStrategy;
import strategies.game.RowWinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // GameController can only be one and multiple games can be controlled
        // this will be automatically SB
        while(true){
            GameController gameController = new GameController();
            Scanner sc = new Scanner(System.in);
            List<Player> players = new ArrayList<>();
            System.out.println("Please select game mode: \n1. Single-Player \n2. Multi-Player");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:
                    startSinglePlayerGame(sc,players);
                    break;
                case 2:
                    startMultiplayerGame(sc,players);
                    break;
                default:
                    throw new RuntimeException("Please select correct game mode");
            }

            Game game = gameController.startGame(3 , players , List.of(new RowWinningStrategy(), new ColWinningStrategy(), new DiagonalWinningStrategy()));
            gameController.displayBoard(game);
            while(gameController.checkState(game).equals(GameState.IN_PROGRESS)) {
                gameController.makeMove(game);
                gameController.displayBoard(game);
                System.out.println("Do you want to Undo ? [Y/N]");
                String undoAnswer = scanner.nextLine();
                if(undoAnswer.equals("Y")) {
                    gameController.undo(game);
                    System.out.println("Undo is successful!");
                    gameController.displayBoard(game);
                }

            }

            if(gameController.checkState(game).equals(GameState.SUCCESS)) {
                System.out.println(gameController.getWinner(game).getName() +  " You win!");
            } else if (gameController.checkState(game).equals(GameState.DRAW)) {
                System.out.println("Nobody Wins");
            }
        }
    }

    private static void startMultiplayerGame(Scanner sc,List<Player> players){
        System.out.println("Please enter Player 1 name:");
        String playerName1 = sc.nextLine();
        System.out.println("Please enter your symbol " + Symbol.X + " " + Symbol.O);
        String symbol1 = sc.nextLine();
        players.add(new HumanPlayer(1L , playerName1, symbol1.toUpperCase().equals(Symbol.X.toString())? Symbol.X : Symbol.O));

        System.out.println("Please enter Player 2 name : ");
        String playerName2 = sc.nextLine();
        System.out.println("Please enter your symbol " + Symbol.X + " " + Symbol.O);
        String symbol2 = sc.nextLine();
        players.add(new HumanPlayer(1L , playerName1, symbol2.toUpperCase().equals(Symbol.X.toString())? Symbol.X : Symbol.O));
    }

    private static void startSinglePlayerGame(Scanner sc,List<Player> players){
        System.out.println("Please enter Player 1 name : ");
        String playerName1 = sc.nextLine();
        System.out.println("Please enter your symbol " + Symbol.X + " " + Symbol.O);
        String symbol1 = sc.nextLine();
        players.add(new HumanPlayer(1L , playerName1, symbol1.toUpperCase().equals(Symbol.X.toString()) ? Symbol.X : Symbol.O));

        System.out.println("Please select game difficulty level: \n1. EASY\n2. MEDIUM\n3. HARD");
        int choice = sc.nextInt();
        Symbol botSymbol = symbol1.toUpperCase().equals(Symbol.X.toString()) ? Symbol.O : Symbol.X;
        switch (choice){
            case 1:
                players.add(new BotPlayer(2L , "Bot" , botSymbol, BotDifficultyLevel.EASY));
                break;
            case 2:
                players.add(new BotPlayer(2L , "Bot" , botSymbol, BotDifficultyLevel.MEDIUM));
                break;
            case 3:
                players.add(new BotPlayer(2L , "Bot" , botSymbol, BotDifficultyLevel.HARD));
                break;
            default:
                throw new RuntimeException("Please select correct difficulty level");
        }
    }
}

