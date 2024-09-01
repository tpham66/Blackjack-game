import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StartGame{
    private final Scene scene;

    public StartGame(Stage stage) {
        AnchorPane layout = new AnchorPane();

        // Set background color
        BackgroundFill backgroundfill = new BackgroundFill(Color.rgb(60, 151, 41, 1), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background = new Background(backgroundfill);
        layout.setBackground(background);


        // Set logo
        ImageView logo = new ImageView(new Image("image/symbol/logo.png"));
        logo.setFitWidth(334);
        logo.setFitHeight(334);
        layout.getChildren().add(logo);
        AnchorPane.setLeftAnchor(logo,48.0);
        AnchorPane.setTopAnchor(logo,200.0);

        // Set name of the game
        Label name = new Label("BLACKJACK");
        name.setFont(Font.font("Arial", FontWeight.BOLD,50));
        name.setPrefWidth(335);
        name.setPrefHeight(61);
        name.setAlignment(Pos.CENTER);
        name.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        name.setStyle("-fx-text-fill: #fff;");
        layout.getChildren().add(name);
        AnchorPane.setLeftAnchor(name,56.0);
        AnchorPane.setTopAnchor(name,549.0);

        // Set button
        Button startButton = new Button("Start");
        startButton.setPrefWidth(142);
        startButton.setPrefHeight(72);
        startButton.setFont(Font.font(32));
        startButton.setTextFill(Color.rgb(255,255,255,1));
        startButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        startButton.setStyle("-fx-border-color: #fff; -fx-border-width: 7px; -fx-border-radius: 10");
        layout.getChildren().add(startButton);
        StackPane buttonPane = new StackPane(startButton);
        buttonPane.setAlignment(Pos.CENTER);
        layout.getChildren().add(buttonPane);
        buttonPane.setPrefWidth(432);
        buttonPane.setPrefHeight(100);
        AnchorPane.setTopAnchor(buttonPane,658.0);

        // Tap the button to go to the next scene
        startButton.setOnAction(actionEvent -> Blackjack.readRules());

        // Hover the 'Start'
        startButton.setOnMouseEntered(mouseEvent -> {
            startButton.setPrefWidth(178);
            startButton.setPrefHeight(90);
            startButton.setFont(Font.font(40));
        });
        startButton.setOnMouseExited(mouseEvent -> {
            startButton.setPrefWidth(142);
            startButton.setPrefHeight(72);
            startButton.setFont(Font.font(32));
        });

        scene = new Scene(layout, 430, 932);
        stage.setScene(scene);
    }

    public Scene getScene() {
        return scene;
    }
}
