
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application{

	Button sendButton; // click to send message
	Text newUser; // Click this text to make new account
	Text warningError; // warning clients when they enter existing name
    TextField userName; // Enter username here
	TextField textField; // text is typed here
	ComboBox<String> activeClients, allUsers;
	Text toAllUsers = new Text("To all users ");
	ObservableList<String> observableClients;
	HashMap<String, Scene> sceneMap;
	BorderPane clientPane;
	Client clientConnection;
	ListView<String> chatView;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		clientConnection = new Client(data->{
				Platform.runLater(()->{

					if (data instanceof Message) {
						Message message = (Message) data;
						switch(message.getType()) {
							case CREATE_USER:
								if (Objects.equals(message.getContent(), "Existing name")) {
									warningError.setText("Name already exists");
									warningError.setVisible(true);
								} else {
									clientConnection.setUsername(message);
									primaryStage.setScene(sceneMap.get("dashboard"));
									primaryStage.setTitle("Dashboard");
									clientConnection.requestClient("current clients in server");
								}
								break;
							case REQUEST_CLIENT:
								List<String> listClients = message.getActiveClients();
								observableClients.setAll(listClients);
								break;
							case DIRECT_MESSAGE:
								chatView.getItems().add(message.getSender() + " send to you: " + message.getContent());
								break;
							case USER_LEFT:
								observableClients.remove(message.getSender());
								break;
							case USER_JOINED:
								if (!Objects.equals(message.getSender(), clientConnection.getUserName())) {
									observableClients.add(message.getSender());
								}
								break;
							case ALL_CLIENTS_MESSAGE:
								chatView.getItems().add(message.getSender() + " send to all: " + message.getContent());
								break;
							default:
								break;
						}
					}
			});
		});

		clientConnection.start();


		sceneMap = new HashMap<>();

		sceneMap.put("login", loginGUI());
		sceneMap.put("dashboard", dashboardGUI());

		observableClients = FXCollections.observableArrayList();

		newUser.setOnMouseClicked(mouseEvent -> {
            String chosenName = userName.getText().trim();
			if(!chosenName.isEmpty()) {
				clientConnection.send(new Message(Message.MessageType.CREATE_USER, chosenName));
            } else {
                warningError.setText("Username cannot be empty");
                warningError.setVisible(true);
            }
		});

		// handle the list of active clients
		observableClients.addListener((ListChangeListener.Change<? extends String> c) -> {
			while(c.next()) {
				if (c.wasAdded() || c.wasRemoved()) {
					activeClients.getItems().setAll(observableClients);
				}
			}
		});

		// handle choosing recipient to chat
		activeClients.setOnAction(actionEvent -> {
			textField.setPromptText("Send message to " + activeClients.getValue());
		});

		allUsers.setOnAction(event -> {
			if(allUsers.getValue() != null) {
				if (Objects.equals(allUsers.getValue(), "Yes")) {
					textField.setPromptText("Send message to all users");
				} else {
					textField.setPromptText("Choose recipient");
				}
			}
		});

		// handle clicking on to 'Send' button
		sendButton.setOnMouseClicked(mouseEvent -> {
			String textToSend = textField.getText().trim();
			String recipient = activeClients.getValue();
			String isAllUsers = allUsers.getValue();
			if (!textToSend.isEmpty()) {
				// You can send messages to either a group and a client,
				// or to all users
				if (Objects.equals(isAllUsers, "Yes")) {
					clientConnection.sendMessageToAll(textToSend);
					chatView.getItems().add("You sent to all: " + textToSend);
				} else {
					if (recipient != null) {
						clientConnection.directMessage(recipient, textToSend);
						chatView.getItems().add("You sent to " + recipient + ": " + textToSend);
					}
				}
				textField.clear();
			}
		});

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


		primaryStage.setScene(sceneMap.get("login"));
		primaryStage.setTitle("Login Page");
		primaryStage.show();

	}


	public Scene loginGUI() {
		clientPane = new BorderPane();
		clientPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		// Add logo to the app
		ImageView logo = new ImageView(new Image("images/yarn.png"));
		logo.setFitWidth(200);
		logo.setFitHeight(200);

		// App name
		Label appName = new Label("mess!");
		appName.setFont(Font.font("Arial", FontWeight.BOLD,50));
		appName.setTextFill(Color.WHITE);

        // Warning
		warningError = new Text();
        warningError.setFill(Color.WHITE);
        warningError.setFont(new Font("Arial", 14));
        warningError.setVisible(false);

		// Text field to enter username
		userName = new TextField();
		userName.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10), new Insets(5, 30, 5, 30))));
		userName.setPromptText("Username");
		userName.setFont(new Font("Arial", 18));
		userName.setStyle("-fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 1; -fx-border-radius: 10;");

        // Click this to create new account
		newUser = new Text("Create new account");
        newUser.setUnderline(true);
        newUser.setFont(Font.font("Arial", 18));
        newUser.setFill(Color.WHITE);

		VBox appBox = new VBox(12, logo, appName);
		appBox.setAlignment(Pos.CENTER);
		VBox userBox = new VBox(2, warningError, userName);
		userBox.setAlignment(Pos.CENTER);
		userBox.setPadding(new Insets(30, 120, 30, 120));
		VBox infoBox = new VBox(12, userBox, newUser);
		infoBox.setAlignment(Pos.CENTER);
		VBox clientBox = new VBox(27, appBox, infoBox);
		clientBox.setAlignment(Pos.CENTER);

		clientPane.setCenter(clientBox);
		return new Scene(clientPane, 1440, 1024);
	}

	public Scene dashboardGUI() {
		clientPane = new BorderPane();
		clientPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		Label chats = new Label("Chats");
		chats.setFont(Font.font("Arial", FontWeight.BOLD, 32));
		chats.setTextFill(Color.WHITE);

		HBox topBox = new HBox( chats);
		topBox.setAlignment(Pos.CENTER);
		topBox.setPadding(new Insets(34));

		ImageView icon1 = new ImageView(new Image("images/chat.png"));
		icon1.setFitHeight(39);
		icon1.setFitWidth(39);
		Text toClient = new Text("To: ");
		toClient.setFill(Color.WHITE);
		toClient.setFont(Font.font("Arial", 20));
		activeClients = new ComboBox<>();
		activeClients.setStyle("-fx-background-color: #000000; -fx-border-color: #ffffff; -fx-border-radius: 5; -fx-font-size: 16;");
		activeClients.setPrefWidth(200);
		activeClients.setMinWidth(150);
		activeClients.setPrefHeight(35);
		activeClients.setOnMouseEntered(mouseEvent -> {
			toClient.setFill(Color.rgb(100,99,246));
		});
		activeClients.setOnMouseExited(mouseEvent -> {
			toClient.setFill(Color.WHITE);
		});
		HBox clientBox = new HBox(17, icon1, toClient, activeClients);
		clientBox.setAlignment(Pos.CENTER_LEFT);

		ImageView icon2 = new ImageView(new Image("images/chat.png"));
		icon2.setFitHeight(39);
		icon2.setFitWidth(39);
		toAllUsers.setFill(Color.WHITE);
		toAllUsers.setFont(Font.font("Arial", 20));
		allUsers = new ComboBox<>();
		allUsers.getItems().addAll("Yes","No");
		allUsers.setStyle("-fx-background-color: #000000; -fx-border-color: #ffffff; -fx-border-radius: 5; -fx-font-size: 16;");
		allUsers.setPrefWidth(80);
		allUsers.setPrefHeight(35);
		HBox allUsersBox = new HBox(17, icon2, toAllUsers, allUsers);
		allUsersBox.setAlignment(Pos.CENTER_LEFT);
		allUsers.setOnMouseEntered(mouseEvent -> {
			toAllUsers.setFill(Color.rgb(100,99,246));
		});
		allUsers.setOnMouseExited(mouseEvent -> {
			toAllUsers.setFill(Color.WHITE);
		});

		VBox optionBox = new VBox(47, clientBox, allUsersBox);
		optionBox.setAlignment(Pos.TOP_LEFT);
		optionBox.setPadding(new Insets(34));

		chatView = new ListView<>();
		chatView.setPrefWidth(800);
		chatView.setMaxHeight(500);

		textField = new TextField();
		textField.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10), new Insets(5, 30, 5, 30))));
		textField.setPromptText("Text here");
		textField.setFont(new Font("Arial", 18));
		textField.setStyle("-fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 1; -fx-border-radius: 10;");

		sendButton = new Button("Send");
		sendButton.setFont(new Font("Arial", 20));
		sendButton.setTextFill(Color.WHITE);
		sendButton.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(5, 10, 5, 10))));
		sendButton.setStyle("-fx-border-radius: 15; -fx-border-color: #ffffff; -fx-border-width: 1;");
		sendButton.setOnMouseEntered(mouseEvent -> {
			sendButton.setTextFill(Color.rgb(100,99,246));
			sendButton.setStyle("-fx-border-radius: 15; -fx-border-color: #6463f6; -fx-border-width: 1;");
		});
		sendButton.setOnMouseExited(mouseEvent -> {
			sendButton.setTextFill(Color.WHITE);
			sendButton.setStyle("-fx-border-radius: 15; -fx-border-color: #ffffff; -fx-border-width: 1;");
		});
		VBox chatBox = new VBox(35, chatView, textField, sendButton);
		chatBox.setAlignment(Pos.TOP_CENTER);

		HBox contentBox = new HBox(100, optionBox, chatBox);
		contentBox.setAlignment(Pos.TOP_CENTER);

		clientPane.setTop(topBox);
		clientPane.setCenter(contentBox);

		return new Scene(clientPane, 1440, 1024);
	}
}