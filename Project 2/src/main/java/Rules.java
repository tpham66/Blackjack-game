import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

public class Rules {
    private final Scene scene;
    public Rules(Stage stage) {
        AnchorPane layout = new AnchorPane();
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(60, 151, 41, 1), CornerRadii.EMPTY, Insets.EMPTY)));

        // Make rule box
        Rectangle ruleBox = new Rectangle(354, 456);
        ruleBox.setFill(Color.rgb(255, 255, 255, 1));
        AnchorPane.setTopAnchor(ruleBox, 203.0);
        AnchorPane.setLeftAnchor(ruleBox, 38.0);
        ruleBox.setArcWidth(20);
        ruleBox.setArcHeight(20);
        layout.getChildren().add(ruleBox);

        // Add title
        Text title = new Text("How to play");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setTextAlignment(TextAlignment.CENTER);
        layout.getChildren().add(title);
        AnchorPane.setLeftAnchor(title, 69.0);
        AnchorPane.setTopAnchor(title, 231.0);

        // Add small symbol
        ImageView spadeIcon = new ImageView(new Image("image/symbol/spade.png"));
        spadeIcon.setFitWidth(69);
        spadeIcon.setFitHeight(69);
        layout.getChildren().add(spadeIcon);
        AnchorPane.setTopAnchor(spadeIcon, 287.0);
        AnchorPane.setLeftAnchor(spadeIcon, 181.0);

        // Add rules
        TextFlow rules = new TextFlow();
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
        layout.getChildren().add(rules);
        AnchorPane.setTopAnchor(rules, 365.0);
        AnchorPane.setLeftAnchor(rules, 71.0);

        // 'Next'
        Text next = new Text("Next");
        next.setFill(Color.rgb(255, 255, 255, 1));
        next.setFont(Font.font(32));
        next.setStyle("-fx-underline: true");
        VBox nextBox = new VBox(next);
        nextBox.setPrefWidth(432);
        nextBox.setPrefHeight(100);
        nextBox.setAlignment(Pos.CENTER);
        layout.getChildren().add(nextBox);
        AnchorPane.setTopAnchor(nextBox, 685.0);

        // click on 'Next'
        next.setOnMouseClicked(actionEvent -> Blackjack.goToBalanceChoice());

        // Hover next button
        next.setOnMouseEntered(mouseEvent -> {
            next.setFont(Font.font("Arial",FontWeight.BOLD,32));
        });

        // Exit next button
        next.setOnMouseExited(mouseEvent -> {
            next.setFont(Font.font(32));
        });

        scene = new Scene(layout, 430, 932);
        stage.setScene(scene);
    }

    public Scene getScene() {
        return scene;
    }
}
