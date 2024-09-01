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

public class BalanceScene {
    private final Scene scene;
    public BalanceScene(Stage stage) {
        AnchorPane layout = new AnchorPane();
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(60,151,41,1), CornerRadii.EMPTY, Insets.EMPTY)));

        // Make rule box
        Rectangle r1 = new Rectangle(354,275);
        r1.setFill(Color.rgb(255,255,255,1));
        AnchorPane.setTopAnchor(r1,278.0);
        AnchorPane.setLeftAnchor(r1,38.0);
        r1.setArcWidth(20);
        r1.setArcHeight(20);
        layout.getChildren().add(r1);

        // Add title
        Label title = new Label("Start");
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
        Text des = new Text("Choose your starting amount of money");
        des.setFont(Font.font(16));
        des.setTextAlignment(TextAlignment.CENTER);
        des.setWrappingWidth(240);
        layout.getChildren().add(des);
        AnchorPane.setTopAnchor(des,360.0);
        AnchorPane.setLeftAnchor(des,95.0);

        // Add blank
        TextField balance = new TextField("$");
        balance.setBackground(new Background(new BackgroundFill(Color.BLACK,new CornerRadii(5),new Insets(17,31.88,16.32,32.67))));
        balance.setPrefWidth(232);
        balance.setPrefHeight(71.6);
        balance.setPadding(new Insets(17,31.88,16.34,32.67));
        balance.setFont(Font.font(32));
        balance.setStyle("-fx-text-inner-color: #ffffff");
        balance.setAlignment(Pos.CENTER);
        layout.getChildren().add(balance);
        AnchorPane.setTopAnchor(balance,442.0);
        AnchorPane.setLeftAnchor(balance,99.0);

        // 'Next'
        Text next = new Text("Next");
        next.setFill(Color.rgb(255,255,255,1));
        next.setFont(Font.font(32));
        next.setStyle("-fx-underline: true");
        VBox nextBox = new VBox(next);
        nextBox.setPrefWidth(432);
        nextBox.setPrefHeight(100);
        nextBox.setAlignment(Pos.CENTER);
        layout.getChildren().add(nextBox);
        AnchorPane.setTopAnchor(nextBox, 575.0);
        // warning
        Text warning = new Text();
        warning.setFill(Color.BLACK);
        warning.setTextAlignment(TextAlignment.CENTER);
        warning.setFont(Font.font(12));
        layout.getChildren().add(warning);
        AnchorPane.setTopAnchor(warning,420.0);
        AnchorPane.setLeftAnchor(warning,140.0);

        // Hover next button
        next.setOnMouseEntered(mouseEvent -> {
            next.setFont(Font.font("Arial",FontWeight.BOLD,32));
        });

        // Exit next button
        next.setOnMouseExited(mouseEvent -> {
            next.setFont(Font.font(32));
        });

        // click on 'Next'
        next.setOnMouseClicked(actionEvent -> {
            double userBalance = -1;

            // First, try to parse the balance text to a double, ignoring the first character assumed to be a dollar sign.
            try {
                userBalance = Double.parseDouble(balance.getText().substring(1));
            } catch (NumberFormatException e) {
                warning.setText("The text is not a valid number!!!");
            }

            // After attempting to parse the number, check the parsed value to decide on the message to display.
            if (userBalance == 0) {
                warning.setText("Starting amount cannot be 0!!!");
            } else if (userBalance > 0) {
                // If the balance is positive, proceed to initialize the balance and potentially move to the next scene.
                Blackjack.getGame().totalWinnings = userBalance;
                Blackjack.goToBetScene();
            } else {
                // If the userBalance is still -1 (not set or invalid), display a message asking for a valid amount.
                // This check avoids displaying the "Please choose a valid amount" message if another message has already been set.
                if(warning.getText().isEmpty()){
                    warning.setText("Please choose a valid amount!!!");
                }
            }
        });

        // always have dollar sign
        balance.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("$")) {
                balance.setText("$" + newValue.replaceAll("[^\\d.]", "")); // Allow numbers and decimal point
            } else {
                String numericPart = newValue.substring(1).replaceAll("[^\\d.]", ""); // Remove non-digits and keep decimal point
                // Handle multiple decimal points
                int firstDecimalPoint = numericPart.indexOf(".");
                if (firstDecimalPoint != -1) {
                    String beforeDecimal = numericPart.substring(0, firstDecimalPoint);
                    String afterDecimal = numericPart.substring(firstDecimalPoint + 1).replace(".", ""); // Remove extra decimal points
                    balance.setText("$" + beforeDecimal + "." + afterDecimal);
                } else {
                    balance.setText("$" + numericPart);
                }
            }
        });

        // tap on the text field to make it editable
        balance.setOnMouseClicked(mouseEvent -> {
            balance.clear();
            Blackjack.getGame().totalWinnings = 0.0;
        });

        scene = new Scene(layout, 430,932);
        stage.setScene(scene);
    }

    public Scene getScene() {
        return scene;
    }
}
