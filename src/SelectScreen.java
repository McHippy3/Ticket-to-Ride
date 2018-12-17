import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SelectScreen extends Application {
    public void start(Stage primaryStage){
        //items holds the heading and the selection options
        VBox items = new VBox(10.0);

        //
        HBox selectionOptions = new HBox();

        //Stage and Scene Setup
        Scene s = new Scene(items);
        primaryStage.show();
        primaryStage.setScene(s);
        primaryStage.sizeToScene();
    }
}
