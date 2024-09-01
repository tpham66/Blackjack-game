import javafx.application.Application;
import javafx.stage.Stage;

public class Blackjack extends Application {
    private static Stage primaryStage;
    private static BlackjackGame game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Blackjack.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        game = new BlackjackGame();
        startGame();
    }

    public static BlackjackGame getGame() {
        return game;
    }

    public static void startGame() {
        StartGame startGame = new StartGame(primaryStage);
        primaryStage.setScene(startGame.getScene());
        primaryStage.show();
    }

    public static void readRules() {
        Rules rules = new Rules(primaryStage);
        primaryStage.setScene(rules.getScene());
        primaryStage.show();
    }

    public static void goToBalanceChoice() {
        BalanceScene balanceScene = new BalanceScene(primaryStage);
        primaryStage.setScene(balanceScene.getScene());
        primaryStage.show();
    }

    public static void goToBetScene() {
        BetScene betScene = new BetScene(primaryStage);
        primaryStage.setScene(betScene.getScene());
        primaryStage.show();
    }

    public static void goToGameScene() {
        GameScene gameScene = new GameScene(primaryStage);
        primaryStage.setScene(gameScene.getScene());
        primaryStage.show();
    }

}
