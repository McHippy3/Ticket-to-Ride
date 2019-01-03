import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UI extends Application {
    private Stage primaryStage;

    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        //Open with select screen
        setSelectScreen();


    }

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
                (MouseEvent e) -> setOnlineScene()
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

    private void setBoardScene()
    {
        //Root Node
        BorderPane rootNode = new BorderPane();

        //Drawing Board
        ImageView board = new ImageView(new Image("res/game_board.jpg"));
        board.setFitWidth(1296);
        board.setFitHeight(864);
        rootNode.setCenter(board);

        //Setting Up Primary Stage and Scene
        primaryStage.setScene(new Scene(rootNode));
        primaryStage.setMaximized(true);
    }

    private void setOnlineScene()
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
        Host host = new Host(1000, 4);

        //Display Header, Port Number and IP Address
        Text header = new Text("Create Game");
        header.setFont(new Font(25.0));
        Text portText = new Text("Port: " + 1000);
        Text addressText = new Text("Address: " + host.getAddress());

        //Button to start searching for clients and button to back out
        Button startButton = new Button("START");
        startButton.setOnMouseClicked(
                (MouseEvent e) -> host.start()
        );
        Button backButton = new Button("BACK");
        backButton.setOnMouseClicked(
                (MouseEvent e) -> setOnlineScene()
        );

        //Adding to root
        items.getChildren().addAll(header, portText, addressText, startButton, backButton);

        primaryStage.setScene(new Scene(items));

    }

    private void setJoinScene()
    {
        //Creating root
        VBox items = new VBox(25.0);
        items.setAlignment(Pos.CENTER);

        //Header
        Text header = new Text("Join Game");
        header.setFont(new Font(25.0));

        //Text Fields
        TextField portField = new TextField();
        portField.setPromptText("Port Number");
        portField.setMaxWidth(100);
        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setText("192.168.56.1"); //Change Later
        addressField.setMaxWidth(100);

        //Buttons
        Button joinButton = new Button("JOIN");
        joinButton.setPadding(new Insets(3,10,3,10));
        joinButton.setOnMouseClicked(
                (MouseEvent e) -> createClient(Integer.parseInt(portField.getText()), addressField.getText())
        );
        Button backButton = new Button("BACK");
        backButton.setPadding(new Insets(3,10,3,10));
        backButton.setOnMouseClicked(
                (MouseEvent e) -> setOnlineScene()
        );

        //Add to root and display stage
        items.getChildren().addAll(header, portField, addressField, joinButton, backButton);
        primaryStage.setScene(new Scene(items));
    }

    private void createClient(int portNumber, String address)
    {
        Client c = new Client(1000, address);
    }

    private void startOffline()
    {
        setBoardScene();
    }

    private void startOnline()
    {
        setOnlineScene();
    }
}
