import javafx.application.Application;

public class Main {
    public static void main(String[]args){
        Application.launch(SelectScreen.class, args);
        Host host = new Host(1000);
        host.close();
    }
}
