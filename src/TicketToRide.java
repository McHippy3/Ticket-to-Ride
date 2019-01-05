import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class TicketToRide extends Application {
    private Stage primaryStage;
    private Host host;
    private Client client;

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

        //Drawing Center (board and command panel)
        VBox centerBox = new VBox(25.0);
        centerBox.setAlignment(Pos.CENTER);
        ImageView board = new ImageView(new Image("res/game_board.jpg"));
        board.setFitWidth(1037);
        board.setFitHeight(691);

        //Command Panel
        GridPane commandPanel = new GridPane();

        commandPanel.setPrefSize(1296,250);
        commandPanel.setPadding(new Insets(10));
        commandPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        centerBox.getChildren().addAll(board, commandPanel);
        rootNode.setCenter(centerBox);
        BorderPane.setMargin(board, new Insets(10));

        //Left Side Tiles
        VBox leftBox = new VBox(25.0);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(25));

        //Player 1
        GridPane player1Tile = new GridPane();
        player1Tile.setAlignment(Pos.CENTER);
        player1Tile.setPrefSize(252,500);
        player1Tile.setPadding(new Insets(10));
        player1Tile.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        player1Tile.add(new Text("PLAYER 1"), 0, 0, 2, 2);

        //Player 2
        GridPane player2Tile = new GridPane();
        player2Tile.setAlignment(Pos.CENTER);
        player2Tile.setPrefSize(252,500);
        player2Tile.setPadding(new Insets(10));
        player2Tile.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        player2Tile.add(new Text("PLAYER 2"), 0, 0, 2, 2);

        leftBox.getChildren().addAll(player1Tile, player2Tile);
        rootNode.setLeft(leftBox);

        //Right Side Tiles
        VBox rightBox = new VBox(25.0);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(25));

        //Player 3
        GridPane player3Tile = new GridPane();
        player3Tile.setAlignment(Pos.CENTER);
        player3Tile.setPrefSize(252,500);
        player3Tile.setPadding(new Insets(10));
        player3Tile.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        player3Tile.add(new Text("PLAYER 3"), 0, 0, 2, 2);

        //Player 4
        GridPane player4Tile = new GridPane();
        player4Tile.setAlignment(Pos.CENTER);
        player4Tile.setPrefSize(252,500);
        player4Tile.setPadding(new Insets(10));
        player4Tile.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        player4Tile.add(new Text("PLAYER 4"), 0, 0, 2, 2);

        rightBox.getChildren().addAll(player3Tile, player4Tile);
        rootNode.setRight(rightBox);

        //Setting Up Primary Stage and Scene
        primaryStage.setScene(new Scene(rootNode));
        primaryStage.setMaximized(true);
    }

    private void setOnlineBoardScene()
    {
        //Root Node
        BorderPane rootNode = new BorderPane();

        //Drawing Center (board and command panel)
        VBox centerBox = new VBox(25.0);
        centerBox.setAlignment(Pos.CENTER);
        ImageView board = new ImageView(new Image("res/game_board.jpg"));
        board.setFitWidth(1037);
        board.setFitHeight(691);

        //Command Panel
        GridPane commandPanel = new GridPane();

        commandPanel.setPrefSize(1296,250);
        commandPanel.setPadding(new Insets(10));
        commandPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        centerBox.getChildren().addAll(board, commandPanel);
        rootNode.setCenter(centerBox);
        BorderPane.setMargin(board, new Insets(10));

        //Left Side Tiles
        VBox leftBox = new VBox(25.0);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(25));

        //Player 1
        GridPane player1Tile = new GridPane();
        player1Tile.setPrefSize(252,500);
        player1Tile.setPadding(new Insets(10));
        player1Tile.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        player1Tile.add(new Text(client.getAllPlayers()[0].getName()), 0, 0, 2, 2);

        //Player 2
        GridPane player2Tile = new GridPane();
        player2Tile.setPrefSize(252,500);
        player2Tile.setPadding(new Insets(10));
        player2Tile.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        player2Tile.add(new Text(client.getAllPlayers()[1].getName()), 0, 0, 2, 2);

        leftBox.getChildren().addAll(player1Tile, player2Tile);
        rootNode.setLeft(leftBox);

        //Right Side Tiles
        VBox rightBox = new VBox(25.0);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(25));

        //Player 3
        GridPane player3Tile = new GridPane();
        player3Tile.setPrefSize(252,500);
        player3Tile.setPadding(new Insets(10));
        player3Tile.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        player3Tile.add(new Text(client.getAllPlayers()[2].getName()), 0, 0, 2, 2);

        //Player 4
        GridPane player4Tile = new GridPane();
        player4Tile.setPrefSize(252,500);
        player4Tile.setPadding(new Insets(10));
        player4Tile.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        player4Tile.add(new Text(client.getAllPlayers()[3].getName()), 0, 0, 2, 2);

        rightBox.getChildren().addAll(player3Tile, player4Tile);
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
                (WindowEvent e) -> host.close()
        );

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
        nameField.setText("David"); //TODO Change Later
        nameField.setMaxWidth(100);

        TextField portField = new TextField();
        portField.setPromptText("Port Number");
        portField.setText("1000"); //TODO Change Later
        portField.setMaxWidth(100);

        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setText("192.168.56.1"); //TODO Change Later
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
        /*Media loadingMusic = new Media(new File("src/res/Elevator-music.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(loadingMusic);
        player.play();*/

        primaryStage.setScene(new Scene(stackPane));



        //Changes scene once all players have connected
        Thread loadingThread = new Thread(() -> {
            while(!client.getGameStarted()) {
                try {
                    Thread.sleep(1);
                }
                catch (InterruptedException e){}
            }
            //player.stop();
            startOnline();
        });

        //Terminate thread if application exits
        loadingThread.setDaemon(true);

        loadingThread.start();
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

    private void startOnline()
    {
        Platform.runLater(
                () -> setOnlineBoardScene()
        );
    }
}
