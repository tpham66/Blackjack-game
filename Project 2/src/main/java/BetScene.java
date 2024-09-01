import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class BetScene {
    private final Scene scene;
    public BetScene(Stage stage) {
        AnchorPane layout = new AnchorPane();
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(60,151,41,1), CornerRadii.EMPTY, Insets.EMPTY)));

        // Make bet box
        Rectangle r1 = new Rectangle(354,275);
        r1.setFill(Color.rgb(255,255,255,1));
        AnchorPane.setTopAnchor(r1,278.0);
        AnchorPane.setLeftAnchor(r1,38.0);
        r1.setArcWidth(20);
        r1.setArcHeight(20);
        layout.getChildren().add(r1);

        // Add title
        Label title = new Label("Bet");
        title.setFont(Font.font("Arial", FontWeight.BOLD,50));
        title.setPrefWidth(286);
        title.setPrefHeight(66);
        title.setAlignment(Pos.CENTER);
        title.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        title.setStyle("-fx-text-fill: #1e1e1e;");
        layout.getChildren().add(title);
        AnchorPane.setLeftAnchor(title,72.0);
        AnchorPane.setTopAnchor(title,294.0);

        // Add description
        Text des = new Text("Choose your bet this round");
        des.setTextAlignment(TextAlignment.CENTER);
        des.setFont(Font.font(16));
        des.setWrappingWidth(240);
        layout.getChildren().add(des);
        AnchorPane.setTopAnchor(des,360.0);
        AnchorPane.setLeftAnchor(des,95.0);

        // Add blank
        TextField betAmount = new TextField("$");
        betAmount.setBackground(new Background(new BackgroundFill(Color.BLACK,new CornerRadii(5),new Insets(17,31.88,16.32,32.67))));
        betAmount.setPrefWidth(232);
        betAmount.setPrefHeight(71.6);
        betAmount.setPadding(new Insets(17,31.88,16.34,32.67));
        betAmount.setFont(Font.font(32));
        betAmount.setStyle("-fx-text-inner-color: #ffffff");
        betAmount.setAlignment(Pos.CENTER);
        layout.getChildren().add(betAmount);
        AnchorPane.setTopAnchor(betAmount,442.0);
        AnchorPane.setLeftAnchor(betAmount,99.0);

        // 'Next' button
        Text next = new Text("Next");
        next.setFill(Color.rgb(255,255,255,1));
        next.setFont(Font.font(32));
        next.setStyle("-fx-underline: true");

        // 'Back' button
        Text back = new Text("Back");
        back.setFill(Color.rgb(255,255,255,1));
        back.setFont(Font.font(32));
        back.setStyle("-fx-underline: true");

        VBox nextBox = new VBox(next);
        VBox backBox = new VBox(back);
        nextBox.setAlignment(Pos.CENTER);
        backBox.setAlignment(Pos.CENTER);
        nextBox.setPrefWidth(100);
        backBox.setPrefWidth(100);

        HBox buttons = new HBox(20,backBox,nextBox);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPrefWidth(432);
        layout.getChildren().add(buttons);
        AnchorPane.setTopAnchor(buttons,575.0);
        // warning
        Text warning = new Text();
        warning.setFill(Color.BLACK);
        warning.setTextAlignment(TextAlignment.CENTER);
        warning.setFont(Font.font(12));
        layout.getChildren().add(warning);
        AnchorPane.setTopAnchor(warning,420.0);
        AnchorPane.setLeftAnchor(warning,140.0);

        // total balance
        Text balance = new Text("$" + Blackjack.getGame().totalWinnings);
        balance.setFont(Font.font(50));
        balance.setFill(Color.WHITE);
        balance.setTextAlignment(TextAlignment.CENTER);
        layout.getChildren().add(balance);
        StackPane balanceStack = new StackPane(balance);
        layout.getChildren().add(balanceStack);
        balanceStack.setAlignment(Pos.CENTER);
        balanceStack.setPrefWidth(432);
        AnchorPane.setTopAnchor(balanceStack,199.0);

        // Hover next button
        next.setOnMouseEntered(mouseEvent -> {
            next.setFont(Font.font("Arial",FontWeight.BOLD,32));
        });

        // Exit next button
        next.setOnMouseExited(mouseEvent -> {
            next.setFont(Font.font(32));
        });

        // Hover back button
        back.setOnMouseEntered(mouseEvent -> {
            back.setFont(Font.font("Arial",FontWeight.BOLD,32));
        });

        // Exit back button
        back.setOnMouseExited(mouseEvent -> {
            back.setFont(Font.font(32));
        });

        // click on 'Next'
        next.setOnMouseClicked(actionEvent -> {
            double userBet = -1;

            // First, try to parse the balance text to a double, ignoring the first character assumed to be a dollar sign.
            try {
                userBet = Double.parseDouble(betAmount.getText().substring(1));
            } catch (NumberFormatException e) {
                warning.setText("The text is not a valid number!!!");
            }

            // After attempting to parse the number, check the parsed value to decide on the message to display.
            if (userBet == 0) {
                warning.setText("Your bet cannot be 0!!!");
            } else if (userBet > 0) {
                // If the balance is positive, proceed to initialize the balance and potentially move to the next scene.
                if (Blackjack.getGame().totalWinnings - userBet < 0) {
                    warning.setText("You don't have enough money!!!");
                } else {
                    Blackjack.getGame().placeBet(userBet);
                    Blackjack.goToGameScene();
                }
            } else {
                // If the userBalance is still -1 (not set or invalid), display a message asking for a valid amount.
                // This check avoids displaying the "Please choose a valid amount" message if another message has already been set.
                if(warning.getText().isEmpty()){
                    warning.setText("Please place a bet!!!");
                }
            }
        });

        // always have dollar sign
        betAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("$")) {
                betAmount.setText("$" + newValue.replaceAll("[^\\d.]", "")); // Allow numbers and decimal point
            } else {
                String numericPart = newValue.substring(1).replaceAll("[^\\d.]", ""); // Remove non-digits and keep decimal point
                // Handle multiple decimal points
                int firstDecimalPoint = numericPart.indexOf(".");
                if (firstDecimalPoint != -1) {
                    String beforeDecimal = numericPart.substring(0, firstDecimalPoint);
                    String afterDecimal = numericPart.substring(firstDecimalPoint + 1).replace(".", ""); // Remove extra decimal points
                    betAmount.setText("$" + beforeDecimal + "." + afterDecimal);
                } else {
                    betAmount.setText("$" + numericPart);
                }
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            Blackjack.getGame().totalWinnings = 0.0;
            Blackjack.goToBalanceChoice();
        });

        // tap on the text field to make it editable
        betAmount.setOnMouseClicked(mouseEvent -> {
            betAmount.clear();
            Blackjack.getGame().placeBet(0.0);
        });

        scene = new Scene(layout, 430,932);
        stage.setScene(scene);
        // mvn clean compile exec:java
    }

    public Scene getScene() {
        return scene;
    }
}
