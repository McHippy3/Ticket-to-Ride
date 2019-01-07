import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TicketToRide extends Application {
    private Stage primaryStage;
    private Host host;
    private Client client;
    private MediaPlayer player;

    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        //Open with select screen
        setSelectScreen();


    }

    public static void main(String[]args){
        launch(args);}

    private void setSelectScreen()
    {
        //items holds the heading and the selection options
        VBox items = new VBox(10.0);
        items.setSpacing(10.0);
        items.setAlignment(Pos.CENTER);
        ImageView logo = new ImageView(new Image("res/logo.jpg"));

        //Select offline or online
        HBox selectionOptions = new HBox();
        selectionOptions.setSpacing(10.0);
        selectionOptions.setAlignment(Pos.CENTER);

        //Online button
        Button onlineButton = new Button("ONLINE");
        onlineButton.setPadding(new Insets(3,10,3,10));
        onlineButton.setOnMouseClicked(
                (MouseEvent e) -> setOnlineSelectScene()
        );

        //Offline button
        Button offlineButton = new Button("OFFLINE");
        offlineButton.setPadding(new Insets(3,10,3,10));
        offlineButton.setOnMouseClicked(
                (MouseEvent e) -> startOffline()
        );

        //Adding to parent
        selectionOptions.getChildren().addAll(onlineButton, offlineButton);
        items.getChildren().addAll(logo, selectionOptions);

        //Stage
        primaryStage.setScene(new Scene(items));
        primaryStage.setHeight(300);
        primaryStage.setWidth(400);
        primaryStage.show();
        primaryStage.setTitle("Ticket to Ride");
        primaryStage.getIcons().add(new Image("res/icon.png"));
    }

    private void setOfflineBoardScene()
    {
        //Root Node
        BorderPane rootNode = new BorderPane();
        rootNode.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        //Drawing Center (board and command panel)
        VBox centerBox = new VBox(25.0);
        centerBox.setAlignment(Pos.CENTER);
        ImageView board = new ImageView(new Image("res/game_board.jpg"));
        board.setFitWidth(1037);
        board.setFitHeight(691);

        //Command Panel
        GridPane commandPanel = new GridPane();
        commandPanel.setAlignment(Pos.TOP_CENTER);
        commandPanel.setPrefSize(1296,250);
        commandPanel.setPadding(new Insets(10));
        commandPanel.setVgap(25);
        commandPanel.setHgap(25);
        commandPanel.setBackground(new Background(new BackgroundFill(new Color(0.95, 0.95, 0.95, 0.75), CornerRadii.EMPTY, Insets.EMPTY)));

        //Equally distributing columns
        for(int i = 0; i < 5; i++)
        {
            ColumnConstraints c = new ColumnConstraints();
            c.setPercentWidth(20);
            commandPanel.getColumnConstraints().add(c);
        }

        //Command Panel content
        Text label1 = new Text("SELECT CARD");
        label1.setFont(new Font(25));
        commandPanel.add(label1, 0,0,1,1);
        GridPane.setHalignment(label1, HPos.CENTER);

        TextField addField = new TextField();
        addField.setPromptText("Add Points");
        commandPanel.add(addField, 0,1);

        Button addButton = new Button("Add Points");
        addButton.setPadding(new Insets(3,10,3,10));
        commandPanel.add(addButton, 0, 2);

        //Command Panel Column Two: Display Cards Available
        Deck deck = new Deck();
        ImageView[] cardImages = new ImageView[5];
        for(int i = 0; i < 5; i++)
        {
            cardImages[i] = new ImageView(deck.getTop().getImage());
            cardImages[i].setFitWidth(177);
            cardImages[i].setFitHeight(114);
            GridPane.setHalignment(cardImages[i], HPos.CENTER);
        }
        commandPanel.add(cardImages[0], 1, 0);
        commandPanel.add(cardImages[1], 2, 0);
        commandPanel.add(cardImages[2], 3, 0);
        commandPanel.add(cardImages[3], 1, 1, 2, 1);
        commandPanel.add(cardImages[4], 2, 1, 2, 1);

        //Adding to Center
        centerBox.getChildren().addAll(board, commandPanel);
        rootNode.setCenter(centerBox);
        BorderPane.setMargin(board, new Insets(10));

        //Player Tiles
        GridPane[] playerTiles = new GridPane[4];
        for(int i = 0; i < playerTiles.length; i++)
        {
            playerTiles[i] = new GridPane();
            playerTiles[i].setAlignment(Pos.CENTER);
            playerTiles[i].setPrefSize(252, 500);
            playerTiles[i].setPadding(new Insets((10)));
            playerTiles[i].setVgap(5);
            playerTiles[i].setHgap(5);
            playerTiles[i].add(new Text("Player " + (i + 1)), 0, 0, 2, 1);
            playerTiles[i].add(new Text("Points: " + i), 0, 1, 1, 1);

            //Setting Color
            BackgroundFill backgroundFill;
            switch(i)
            {
                case 0:
                    backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                case 1:
                    backgroundFill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                case 2:
                    backgroundFill = new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                default:
                    backgroundFill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
            }
            playerTiles[i].setBackground(new Background(backgroundFill));
        }

        //Left Side Tiles
        VBox leftBox = new VBox(25.0);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(25));


        leftBox.getChildren().addAll(playerTiles[0], playerTiles[1]);
        rootNode.setLeft(leftBox);

        //Right Side Tiles
        VBox rightBox = new VBox(25.0);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(25));

        rightBox.getChildren().addAll(playerTiles[2], playerTiles[3]);
        rootNode.setRight(rightBox);

        //Setting Up Primary Stage and Scene
        primaryStage.setScene(new Scene(rootNode));
        primaryStage.setMaximized(true);
    }

    private void setOnlineBoardScene()
    {
        //Root Node
        BorderPane rootNode = new BorderPane();
        rootNode.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        //Drawing Center (board and command panel)
        VBox centerBox = new VBox(25.0);
        centerBox.setAlignment(Pos.CENTER);
        ImageView board = new ImageView(new Image("res/game_board.jpg"));
        board.setFitWidth(1037);
        board.setFitHeight(691);

        //Command Panel
        GridPane commandPanel = new GridPane();
        commandPanel.setBackground(new Background(new BackgroundFill(new Color(0.95, 0.95, 0.95, 0.75), CornerRadii.EMPTY, Insets.EMPTY)));
        commandPanel.setPrefSize(1296,250);
        commandPanel.setPadding(new Insets(10));
        commandPanel.setHgap(25);
        commandPanel.setVgap(25);
        commandPanel.setAlignment(Pos.TOP_CENTER);

        //Equally distributing columns
        for(int i = 0; i < 5; i++)
        {
            ColumnConstraints c = new ColumnConstraints();
            c.setPercentWidth(20);
            commandPanel.getColumnConstraints().add(c);
        }

        //Command Panel Column One
        Text label1 = new Text("SELECT CARD");
        label1.setFont(new Font(25));
        commandPanel.add(label1, 0,0,1,1);
        GridPane.setHalignment(label1, HPos.CENTER);

        TextField addField = new TextField();
        addField.setPromptText("Add Points");
        commandPanel.add(addField, 0,1);

        Button addButton = new Button("Add Points");
        addButton.setPadding(new Insets(3,10,3,10));
        commandPanel.add(addButton, 0, 2);
        addButton.setOnMouseClicked(
                (MouseEvent e) -> {
                    //Send addField's content and end if there is no error
                    client.playTurn(addField.getText());
                });

        //Disable if not currently the player's turn
        if(!client.getIsPlayerTurn())
        {
            addField.setDisable(true);
            addButton.setDisable(true);
        }

        //Command Panel Column Two: Display Cards Available
        ImageView[] cardImages = new ImageView[5];
        for(int i = 0; i < 5; i++)
        {
            cardImages[i] = new ImageView(client.getAvailableCards()[i].getImage());
            cardImages[i].setFitWidth(177);
            cardImages[i].setFitHeight(114);
            GridPane.setHalignment(cardImages[i], HPos.CENTER);
        }
        commandPanel.add(cardImages[0], 1, 0);
        commandPanel.add(cardImages[1], 2, 0);
        commandPanel.add(cardImages[2], 3, 0);
        commandPanel.add(cardImages[3], 1, 1, 2, 1);
        commandPanel.add(cardImages[4], 2, 1, 2, 1);

        //Adding to Root
        centerBox.getChildren().addAll(board, commandPanel);
        rootNode.setCenter(centerBox);
        BorderPane.setMargin(board, new Insets(10));

        //Player Tiles
        GridPane[] playerTiles = new GridPane[client.getAllPlayers().length];
        for(int i = 0; i < playerTiles.length; i++)
        {
            playerTiles[i] = new GridPane();
            playerTiles[i].setPrefSize(252, 500);
            playerTiles[i].setPadding(new Insets((10)));
            playerTiles[i].setVgap(5);
            playerTiles[i].setHgap(5);
            playerTiles[i].add(new Text(client.getAllPlayers()[i].getName()), 0, 0, 2, 1);
            playerTiles[i].add(new Text("Points: " + client.getAllPlayers()[i].getPoints()), 0, 1, 1, 1);

            //Setting Color
            BackgroundFill backgroundFill;
            switch(i)
            {
                case 0:
                    backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                case 1:
                    backgroundFill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                case 2:
                    backgroundFill = new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                default:
                    backgroundFill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
            }
            playerTiles[i].setBackground(new Background(backgroundFill));
        }

        //Left Side Tiles
        VBox leftBox = new VBox(25.0);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(25));

        leftBox.getChildren().addAll(playerTiles[0], playerTiles[1]);
        rootNode.setLeft(leftBox);

        //Right Side Tiles
        VBox rightBox = new VBox(25.0);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(25));

        rightBox.getChildren().addAll(playerTiles[2], playerTiles[3]);
        rootNode.setRight(rightBox);

        //Setting Up Primary Stage and Scene
        primaryStage.setScene(new Scene(rootNode));
        primaryStage.setMaximized(true);
    }

    private void setOnlineSelectScene()
    {
        //Choose between hosting, joining or going back to select screen
        VBox items = new VBox(25);
        items.setAlignment(Pos.CENTER);

        //Host button
        Button createButton = new Button("CREATE");
        createButton.setPadding(new Insets(3,10,3,10));
        createButton.setOnMouseClicked(
                (MouseEvent e) -> setCreateScene()
        );

        //Join button
        Button joinButton = new Button("JOIN");
        joinButton.setPadding(new Insets(3,10,3,10));
        joinButton.setOnMouseClicked(
                (MouseEvent e) -> setJoinScene()
        );

        //Back button
        Button backButton = new Button("BACK");
        backButton.setPadding(new Insets(3,10,3,10));
        backButton.setOnMouseClicked(
                (MouseEvent e) -> setSelectScreen()
        );

        items.getChildren().addAll(createButton, joinButton, backButton);

        primaryStage.setScene(new Scene(items));
    }

    private void setCreateScene()
    {
        //Creating root and server
        VBox items = new VBox(25.0);
        items.setAlignment(Pos.CENTER);
        host = new Host(1000, 4);
        host.start();

        //Display Header, Port Number and IP Address
        Text header = new Text("Waiting for Players...");
        header.setFont(new Font(25.0));
        Text portText = new Text("Port: " + 1000);
        Text addressText = new Text("Address: " + host.getAddress());

        //Button to start searching for clients and button to back out
        Button backButton = new Button("BACK");
        backButton.setOnMouseClicked(
                (MouseEvent e) -> {
                    host.close();
                    setOnlineSelectScene();
                });

        //Adding to root
        items.getChildren().addAll(header, portText, addressText, backButton);

        primaryStage.setScene(new Scene(items));

        //Closes server when the window is closed
        primaryStage.setOnCloseRequest(
                (WindowEvent e) -> {
                    host.close();
                    System.exit(0);
                });

    }

    private void setJoinScene()
    {
        //Creating root
        VBox items = new VBox(15.0);
        items.setAlignment(Pos.CENTER);

        //Header
        Text header = new Text("Join Game");
        header.setFont(new Font(25.0));

        //Text Fields
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setMaxWidth(100);

        TextField portField = new TextField();
        portField.setPromptText("Port Number");
        portField.setText("1000"); //TODO Change Later
        portField.setMaxWidth(100);

        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        try {
            addressField.setText(InetAddress.getLocalHost().getHostAddress()); //TODO Change Later
        }
        catch(UnknownHostException e)
        {
            addressField.clear();
        }
        addressField.setMaxWidth(100);

        //Buttons
        Button joinButton = new Button("JOIN");
        joinButton.setPadding(new Insets(3,10,3,10));
        joinButton.setOnMouseClicked(
                (MouseEvent e) -> {
                    if(!portField.getText().equals("") && !addressField.getText().equals(""))
                        createClient(Integer.parseInt(portField.getText()), addressField.getText(), nameField.getText());
                });
        Button backButton = new Button("BACK");
        backButton.setPadding(new Insets(3,10,3,10));
        backButton.setOnMouseClicked(
                (MouseEvent e) -> {
                    setOnlineSelectScene();
                });

        //Add to root and display stage
        items.getChildren().addAll(header, nameField, portField, addressField, joinButton, backButton);
        primaryStage.setScene(new Scene(items));
    }

    //Loading screen before all players have joined
    private void setWaitingScene()
    {
        Text t1 = new Text("Waiting for Players...");
        t1.setFont(new Font(25));
        StackPane stackPane = new StackPane(t1);
        stackPane.setAlignment(Pos.CENTER);

        //Plays generic elevator music
        //TODO Uncomment
        Media loadingMusic = new Media(new File("src/res/Elevator-music.mp3").toURI().toString());
        player = new MediaPlayer(loadingMusic);
        player.play();

        primaryStage.setScene(new Scene(stackPane));

        startOnlineGame();
    }

    private void createClient(int portNumber, String address, String name)
    {
        try
        {
            client = new Client(portNumber, address, name);
            client.start();
            //Close thread when closing
            primaryStage.setOnCloseRequest((WindowEvent e) -> {
                client.close();
                System.exit(0);
            });
            setWaitingScene();
        }
        catch(IOException e)
        {

            showPopUp("Unable to Connect");
        }
    }

    private void showPopUp(String message)
    {
        //Content
        VBox items = new VBox(15.0);
        items.setAlignment(Pos.CENTER);
        items.setPadding(new Insets(25, 0, 25, 0));
        Text popUpMessage = new Text(message);
        Button okButton = new Button("OK");

        items.getChildren().addAll(popUpMessage, okButton);

        //Creating new stage
        Stage popUp = new Stage();
        popUp.setScene(new Scene(items));
        popUp.setWidth(200);
        popUp.setHeight(100);
        popUp.show();
        popUp.toFront();

        //Button Functions
        okButton.setOnMouseClicked(
                (MouseEvent e) -> popUp.close()
        );
    }

    private void startOffline()
    {
        setOfflineBoardScene();
    }

    private void startOnlineGame()
    {
        Thread updateThread = new Thread(() -> {
            //Wait to be notified that the game has started
            while(!client.getGameOngoing())
            {
                try
                {
                    Thread.sleep(1);
                }
                catch(InterruptedException e){}
            }

            //Stop playing generic elevator music
            player.stop();

            //Update GUI whenever the game state changes
            while(client.getGameOngoing()) {
                while (!client.getGameStateChanged()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {}
                }
                System.out.println("Game State Changed");
                Platform.runLater(() -> setOnlineBoardScene());
                client.setGameStateChanged(false);
            }
            });

        updateThread.setDaemon(true);
        updateThread.start();
    }
}
