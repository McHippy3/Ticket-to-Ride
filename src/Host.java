import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Host
{
    private ServerSocket server;
    private static final int maxClients = 4;
    private ArrayList<HostClientThread> hostClientThreads = new ArrayList<>();

    public Host(int port)
    {
        //Starting the server
        try
        {
            server = new ServerSocket(port);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //Accepting clients
        while(hostClientThreads.size() <= maxClients)
        {
            try
            {
                hostClientThreads.add(new HostClientThread(server.accept()));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void close()
    {
        try
        {
            server.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
class HostClientThread extends Thread
{
    private Socket client;

    public HostClientThread(Socket client)
    {
        this.client = client;
    }
}