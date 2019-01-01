import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class Client {
    Socket socket;
    public Client(int port, String address)
    {
        try
        {
            socket = new Socket(address, port);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
