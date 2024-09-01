import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Objects;

public class GameScene {
    private final Scene scene;
    private TextFlow rules() {
        TextFlow rules = new TextFlow();
        rules.setVisible(false);
        Text t1 = new Text("Aim: ");
        Text t2 = new Text("Start: ");
        Text t3 = new Text("Options: ");
        Text t4 = new Text("Bust: ");
        Text t5 = new Text("Dealer plays: ");
        Text t6 = new Text("Winning: ");
        Text t7 = new Text("Blackjack: ");
        Text t8 = new Text("Get closer to 21 than the dealer\nwithout busting.\n");
        Text t9 = new Text("Player and dealer each get two\ncards. Player sees both; one dealer\ncard is hidden.\n");
        Text t10 = new Text("Hit (take another card) or\nstay (keep current hand).\n");
        Text t11 = new Text("Over 21, you lose.\n");
        Text t12 = new Text("Hits under 17; stays on\n17 and above.\n");
        Text t13 = new Text("Higher hand wins. Tie =\ndraw.\n");
        Text t14 = new Text("Ace + 10-value card beats\nall, except against dealer blackjack.");
        t1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t2.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t3.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t4.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t5.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t6.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t7.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        t8.setFont(Font.font("Arial", 16));
        t9.setFont(Font.font("Arial", 16));
        t10.setFont(Font.font("Arial", 16));
        t11.setFont(Font.font("Arial", 16));
        t12.setFont(Font.font("Arial", 16));
        t13.setFont(Font.font("Arial", 16));
        t14.setFont(Font.font("Arial", 16));
        rules.getChildren().addAll(t1, t8, t2, t9, t3, t10, t4, t11, t5, t12, t6, t13, t7, t14);
        rules.setPrefWidth(288);
        rules.setPrefWidth(390);
        return rules;
    }
    private Button makeButton(String buttonName) {
        Button button = new Button(buttonName);
        button.setPrefWidth(142);
        button.setPrefHeight(72);
        button.setFont(Font.font(32));
        button.setTextFill(Color.rgb(255,255,255,1));
        button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        button.setStyle("-fx-border-color: #fff; -fx-border-width: 7px; -fx-border-radius: 10");
        return button;
    }
    private void showNotification(Text notice, String message, Runnable onComplete) {
        notice.setText(message);
        notice.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(actionEvent -> {
            notice.setVisible(false);
            onComplete.run();
        });
        pause.play();
    }
    private TextFlow showResult(String winner) {
        TextFlow result = new TextFlow();
        result.setPrefWidth(291);
        result.setPrefHeight(318);
        result.setPadding(new Insets(20,74,10,73));
        result.setBackground(new Background(new BackgroundFill(Color.web("#2B6E1C"),new CornerRadii(30),Insets.EMPTY)));
        result.setTextAlignment(TextAlignment.CENTER);
        Text whoWon = new Text();
        whoWon.setFill(Color.WHITE);
        whoWon.setFont(Font.font("Arial",FontWeight.BOLD,50));
        if (Objects.equals(winner, "player")) {
            whoWon.setText("You Won!");
        } else if (Objects.equals(winner, "dealer")) {
            whoWon.setText("You Lost!");
        } else {
            whoWon.setText("Draw!");
        }
        Text totalWinnings = new Text(Blackjack.getGame().totalWinnings + "\n\n");
        totalWinnings.setFont(Font.font("Arial",FontWeight.BOLD,32));
        totalWinnings.setFill(Color.WHITE);
        Text t1 = new Text("Balance\n");
        t1.setFont(Font.font("Arial",32));
        t1.setFill(Color.WHITE);
        Text restart = new Text("Start new round");
        restart.setFont(Font.font("Arial",16));
        restart.setStyle("-fx-underline: true");
        restart.setFill(Color.WHITE);

        // Add all text to one flow
        result.getChildren().addAll(whoWon,new Text("\n\n"),t1,totalWinnings,restart);
        if (Blackjack.getGame().totalWinnings == 0) {
            restart.setText("Restart");
            restart.setOnMouseClicked(mouseEvent -> Blackjack.startGame());
        } else {
            restart.setOnMouseClicked(mouseEvent -> Blackjack.goToBetScene());
        }
        return result;
    }
    public GameScene(Stage stage) {
        AnchorPane layout = new AnchorPane();
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(60,151,41,1), CornerRadii.EMPTY, Insets.EMPTY)));

        // RULES
        // Make rule box
        Rectangle ruleBox = new Rectangle(354, 456);
        ruleBox.setFill(Color.rgb(255, 255, 255, 1));
        AnchorPane.setTopAnchor(ruleBox, 203.0);
        AnchorPane.setLeftAnchor(ruleBox, 38.0);
        ruleBox.setArcWidth(20);
        ruleBox.setArcHeight(20);
        layout.getChildren().add(ruleBox);
        ruleBox.setVisible(false);
        // Add small symbol
        ImageView spadeIcon = new ImageView(new Image("image/symbol/spade.png"));
        spadeIcon.setVisible(false);
        spadeIcon.setFitWidth(69);
        spadeIcon.setFitHeight(69);
        layout.getChildren().add(spadeIcon);
        AnchorPane.setTopAnchor(spadeIcon, 287.0);
        AnchorPane.setLeftAnchor(spadeIcon, 181.0);
        // Add rules
        TextFlow rules = rules();
        layout.getChildren().add(rules);
        AnchorPane.setTopAnchor(rules, 365.0);
        AnchorPane.setLeftAnchor(rules, 71.0);
        // title
        Text title = new Text("How to play");
        title.setVisible(false);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setTextAlignment(TextAlignment.CENTER);
        layout.getChildren().add(title);
        AnchorPane.setLeftAnchor(title, 69.0);
        AnchorPane.setTopAnchor(title, 231.0);

        // 'Close' the rules box
        Text close = new Text("Close");
        close.setFill(Color.rgb(255, 255, 255, 1));
        close.setWrappingWidth(90);
        close.setFont(Font.font(32));
        close.setStyle("-fx-underline: true");
        layout.getChildren().add(close);
        AnchorPane.setTopAnchor(close, 685.0);
        AnchorPane.setLeftAnchor(close, 179.0);
        close.setVisible(false);
        close.setDisable(true);

        // re-read the rules
        ImageView ruleSymbol = new ImageView(new Image("image/symbol/rules.png",50,50,true,true));
        AnchorPane.setTopAnchor(ruleSymbol,20.0);
        AnchorPane.setLeftAnchor(ruleSymbol,20.0);
        layout.getChildren().addAll(ruleSymbol);
        ruleSymbol.setOnMouseClicked(mouseEvent -> {
            layout.getChildren().forEach(child -> {
                child.setVisible(false);
                child.setDisable(true);
            });
            close.setDisable(false);
            close.setVisible(true);
            rules.setVisible(true);
            title.setVisible(true);
            spadeIcon.setVisible(true);
            ruleBox.setVisible(true);
        });

        // close the rules box
        close.setOnMouseClicked(mouseEvent -> {
            layout.getChildren().forEach(child -> {
                child.setVisible(true);
                child.setDisable(false);
            });
            close.setDisable(true);
            close.setVisible(false);
            rules.setVisible(false);
            title.setVisible(false);
            spadeIcon.setVisible(false);
            ruleBox.setVisible(false);
        });

        // Hover close button
        close.setOnMouseEntered(mouseEvent -> {
            close.setFont(Font.font("Arial",FontWeight.BOLD,32));
        });

        // Exit close button
        close.setOnMouseExited(mouseEvent -> {
            close.setFont(Font.font(32));
        });

        // Add Hit and Stay buttons
        Button hitButton = makeButton("Hit");
        Button stayButton = makeButton("Stay");
        VBox stayPane = new VBox(stayButton);
        VBox hitPane = new VBox(hitButton);
        stayPane.setAlignment(Pos.CENTER);
        hitPane.setAlignment(Pos.CENTER);
        stayPane.setPrefWidth(216);
        hitPane.setPrefWidth(216);
        HBox buttonPane = new HBox(hitPane,stayPane);
        layout.getChildren().add(buttonPane);
        buttonPane.setPrefWidth(432);
        buttonPane.setPrefHeight(100);
        AnchorPane.setTopAnchor(buttonPane,678.0);

        // Add text to the score box
        Text t1 = new Text("Total");
        Text t2 = new Text("Bet");
        t1.setFont(Font.font(20));
        t2.setFont(Font.font(20));
        t1.setFill(Color.BLACK);
        t2.setFill(Color.BLACK);
        Text winnings = new Text("$" + Blackjack.getGame().totalWinnings);
        Text bet = new Text("$" + Blackjack.getGame().currentBet);
        winnings.setFont(Font.font(32));
        bet.setFont(Font.font(32));
        winnings.setFill(Color.WHITE);
        bet.setFill(Color.WHITE);
        TextFlow userScore = new TextFlow(t1,new Text("\n"),winnings,new Text("\n\n"),t2,new Text("\n"),bet);
        userScore.setPadding(new Insets(20,50,20,50));
        userScore.setBackground(new Background(new BackgroundFill(Color.web("#2B6E1C"),new CornerRadii(30),Insets.EMPTY)));
        userScore.setTextAlignment(TextAlignment.LEFT);
        layout.getChildren().add(userScore);
        AnchorPane.setTopAnchor(userScore,309.0);
        AnchorPane.setLeftAnchor(userScore,-22.0);

        Blackjack.getGame().newRound();

        // Allocate the positions of dealer's hand and player's hand
        TilePane dealer = new TilePane();
        TilePane player = new TilePane();
        dealer.setAlignment(Pos.CENTER);
        player.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(dealer,player);
        dealer.setPrefWidth(432);
        player.setPrefWidth(432);
        dealer.setVgap(10);
        player.setVgap(10);
        dealer.setPadding(new Insets(20,20,20,20));
        player.setPadding(new Insets(20,20,20,20));
        AnchorPane.setTopAnchor(dealer,113.0);
        AnchorPane.setBottomAnchor(player,250.0);
        dealer.getChildren().add(new ImageView(new Image("image/deck card/deck.png",70,70,true,true)));// first card of dealer is hidden
        for (Card c: Blackjack.getGame().bankerHand) {
            dealer.getChildren().add(0,new ImageView(new Image(c.getImage(),70,70,true,true)));
        }
        dealer.getChildren().remove(dealer.getChildren().size() - 2);
        for (Card c: Blackjack.getGame().playerHand) {
            player.getChildren().add(0,new ImageView(new Image(c.getImage(),70,70,true,true)));
        }

        // Allocate the deck to the center-right
        ImageView deckImage = new ImageView(new Image("image/deck card/deck.png",70,70,true,true));
        layout.getChildren().add(deckImage);
        AnchorPane.setTopAnchor(deckImage,357.0);
        AnchorPane.setLeftAnchor(deckImage,315.0);

        // turn notification
        Text turnNot = new Text();
        turnNot.setFont(Font.font("Arial", FontWeight.BOLD,32));
        turnNot.setFill(Color.WHITE);
        StackPane notStack = new StackPane(turnNot);
        layout.getChildren().add(notStack);
        notStack.setPrefWidth(432);
        AnchorPane.setTopAnchor(notStack,221.0);

        // Evaluate before the game whether there is blackjack
        if (Blackjack.getGame().gameLogic.handTotal(Blackjack.getGame().bankerHand) == 21 || Blackjack.getGame().gameLogic.handTotal(Blackjack.getGame().playerHand) == 21) {
            Blackjack.getGame().totalWinnings += Blackjack.getGame().evaluateWinnings();
            Blackjack.getGame().currentBet = 0.0;
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            hitButton.setDisable(true);
            stayButton.setDisable(true);
            pause.setOnFinished(actionEvent1 -> {
                userScore.setVisible(false);
                deckImage.setVisible(false);
                dealer.getChildren().set(Blackjack.getGame().bankerHand.size() - 1, new ImageView(new Image(Blackjack.getGame().bankerHand.get(0).getImage(),70,70,true,true)));
                TextFlow result = showResult(Blackjack.getGame().gameLogic.whoWon(Blackjack.getGame().playerHand, Blackjack.getGame().bankerHand));
                layout.getChildren().add(result);
                AnchorPane.setTopAnchor(result,237.0);
                AnchorPane.setLeftAnchor(result,69.0);
            });
            pause.play();
        } else {
            // Show notification when the user enter game scene
            hitButton.setDisable(true);
            stayButton.setDisable(true);
            showNotification(turnNot, "Player's turn", () -> {
            });
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(actionEvent -> {
                hitButton.setDisable(false);
                stayButton.setDisable(false);
            });
            pause.play();
        }

        // Click 'Hit' button
        hitButton.setOnAction(actionEvent -> {
            Blackjack.getGame().playerHand.add(Blackjack.getGame().theDealer.drawOne());
            player.getChildren().add(0,new ImageView(new Image(Blackjack.getGame().playerHand.get(Blackjack.getGame().playerHand.size() - 1).getImage(),70,70,true,true)));
            if (Blackjack.getGame().gameLogic.handTotal(Blackjack.getGame().playerHand) > 21) {
                Blackjack.getGame().totalWinnings += Blackjack.getGame().evaluateWinnings();
                Blackjack.getGame().currentBet = 0.0;
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                hitButton.setDisable(true);
                stayButton.setDisable(true);
                pause.setOnFinished(actionEvent1 -> {
                    userScore.setVisible(false);
                    deckImage.setVisible(false);
                    dealer.getChildren().set(Blackjack.getGame().bankerHand.size() - 1, new ImageView(new Image(Blackjack.getGame().bankerHand.get(0).getImage(),70,70,true,true)));
                    TextFlow result = showResult(Blackjack.getGame().gameLogic.whoWon(Blackjack.getGame().playerHand, Blackjack.getGame().bankerHand));
                    layout.getChildren().add(result);
                    AnchorPane.setTopAnchor(result,237.0);
                    AnchorPane.setLeftAnchor(result,69.0);
                });
                pause.play();
            }
        });

        // Click 'Stay' button
        stayButton.setOnAction(actionEvent -> {
            hitButton.setDisable(true);
            stayButton.setDisable(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            showNotification(turnNot, "Dealer's turn", () -> {
                PauseTransition addCard = new PauseTransition(Duration.seconds(1));
                addCard.setOnFinished(actionEvent1 -> {
                    if (Blackjack.getGame().gameLogic.evaluateBankerDraw(Blackjack.getGame().bankerHand)) {
                        Blackjack.getGame().bankerHand.add(Blackjack.getGame().theDealer.drawOne());
                        ImageView newImage = new ImageView(new Image(Blackjack.getGame().bankerHand.get(Blackjack.getGame().bankerHand.size() - 1).getImage(), 70, 70, true, true));
                        dealer.getChildren().add(0, newImage);
                        pause.playFromStart();
                        addCard.playFromStart();
                    }
                    else {
                        Blackjack.getGame().totalWinnings += Blackjack.getGame().evaluateWinnings();
                        Blackjack.getGame().currentBet = 0.0;
                    }
                });
                addCard.play();
                pause.play();
            });
            pause.setOnFinished(actionEvent1 -> {
                userScore.setVisible(false);
                deckImage.setVisible(false);
                dealer.getChildren().set(Blackjack.getGame().bankerHand.size() - 1, new ImageView(new Image(Blackjack.getGame().bankerHand.get(0).getImage(),70,70,true,true)));
                TextFlow result = showResult(Blackjack.getGame().gameLogic.whoWon(Blackjack.getGame().playerHand, Blackjack.getGame().bankerHand));
                layout.getChildren().add(result);
                AnchorPane.setTopAnchor(result,237.0);
                AnchorPane.setLeftAnchor(result,69.0);
            });
        });

        // Hover hit button
        hitButton.setOnMouseEntered(mouseEvent -> {
            hitButton.setPrefWidth(178);
            hitButton.setPrefHeight(90);
            hitButton.setFont(Font.font(40));
        });

        // Exit hitButton
        hitButton.setOnMouseExited(mouseEvent -> {
            hitButton.setPrefWidth(142);
            hitButton.setPrefHeight(72);
            hitButton.setFont(Font.font(32));
        });

        // Hover stay button
        stayButton.setOnMouseEntered(mouseEvent -> {
            stayButton.setPrefWidth(178);
            stayButton.setPrefHeight(90);
            stayButton.setFont(Font.font(40));
        });

        // Exit stayButton
        stayButton.setOnMouseExited(mouseEvent -> {
            stayButton.setPrefWidth(142);
            stayButton.setPrefHeight(72);
            stayButton.setFont(Font.font(32));
        });

        scene = new Scene(layout, 430,932);
        stage.setScene(scene);
    }
    public Scene getScene() {
        return scene;
    }
}
