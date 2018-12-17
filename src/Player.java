import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class Player extends Application {

    public void start(Stage primaryStage){
        //Root Node
        BorderPane rootNode = new BorderPane();

        //Drawing Board
        ImageView board = new ImageView(new Image("res/game_board.jpg"));
        board.setFitWidth(1296);
        board.setFitHeight(864);
        rootNode.setCenter(board);

        //Setting Up Primary Stage and Scene
        Scene boardScene = new Scene(rootNode);
        primaryStage.setScene(boardScene);
        primaryStage.setTitle("Ticket to Ride FX");
        primaryStage.show();
        primaryStage.setFullScreen(true);
        primaryStage.setMaximized(true);
    }

    public static void main(String[]args){
        launch(args);
    }
}
