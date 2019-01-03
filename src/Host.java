import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class Host
{
    private ServerSocket server;
    private String address;
    private ArrayList<HostThread> hostThreads = new ArrayList<>();
    private int numOfPlayers;
    private int port;

    public Host(int port, int numOfPlayers)
    {
        this.numOfPlayers = numOfPlayers;
        this.port = port;
        try
        {
            this.address = InetAddress.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
    }

    public void start()
    {
        //Creating the server
        try
        {
            server = new ServerSocket(port);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Opening Host on Port " + port + ", Address: " + address);

        //Waiting for Clients
        while(hostThreads.size() < numOfPlayers)
        {
            try
            {
                System.out.println("Waiting for Client");
                Socket s = server.accept();
                HostThread client = new HostThread(s, hostThreads);
                hostThreads.add(client);
                System.out.println("Client " + hostThreads.size() + " Connected");
                client.start();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        close();
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

    //Getter
    public ArrayList<HostThread> getHostThreads()
    {
        return hostThreads;
    }

    public String getAddress()
    {
        return address;
    }
}

class HostThread extends Thread
{
    private Socket client;
    private ArrayList<HostThread> hostThreads;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int clientNumber;

    public HostThread(Socket client, ArrayList<HostThread> hostThreads)
    {
        this.client = client;
        try
        {
            this.out = new ObjectOutputStream(this.client.getOutputStream());
            this.in = new ObjectInputStream(this.client.getInputStream());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        this.hostThreads = hostThreads;
        this.clientNumber = hostThreads.size() + 1;
        System.out.println("Client " + clientNumber + " Initialized");
    }

    public void run()
    {
        while(true)
        {
            try
            {
                //Conserving Performance
                Thread.sleep(1);

                //Reading client input
                Object objIn = in.readObject();
                if(objIn.toString().equals("QUIT"))
                {
                    close();
                    break;
                }
                else
                    {
                        synchronized (this) {
                            for(int i = 0; i < hostThreads.size(); i++)
                            {
                                if(hostThreads.get(i) != this)
                                {
                                    hostThreads.get(i).write(clientNumber+ ": "+ objIn);
                                }
                            }
                        }
                    }
            }
            catch(Exception e) {}
        }

    }

    public void write(Object obj)
    {
        try
        {
            out.writeObject(obj);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        try
        {
            synchronized (this) {
                for(int i = 0; i < hostThreads.size(); i++)
                {
                    if(hostThreads.get(i) != this)
                    {
                        hostThreads.get(i).write("Client " + clientNumber + " Disconnected");
                    }
                }
            }
            client.close();
            in.close();
            out.close();
            hostThreads.remove(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}