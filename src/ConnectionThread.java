import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class ConnectionThread extends Thread
{
    private Socket client;
    private ArrayList<ConnectionThread> connectionThreads;
    private Player player;
    private GameState gameState;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int clientNumber;
    private Boolean responseReceived = true;

    public ConnectionThread(Socket client, ArrayList<ConnectionThread> connectionThreads)
    {
        this.client = client;
        try
        {
            this.out = new ObjectOutputStream(this.client.getOutputStream());
            this.in = new ObjectInputStream(this.client.getInputStream());
            this.connectionThreads = connectionThreads;
            this.clientNumber = connectionThreads.size() + 1;
            System.out.println("Client " + clientNumber + " Initialized");

            //Send client number
            out.writeObject(clientNumber);
            out.reset();

            //Setting up initial player
            while(true)
            {
                try
                {
                    player = (Player) in.readObject();
                    break;
                }
                catch(ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
                responseReceived = true;
                if(objIn instanceof String && objIn.toString().equals("QUIT"))
                {
                    //End game
                    connectionThreads.remove(this);
                    Host.gameOngoing = false;
                    break;
                }
                else
                    {
                        gameState = (GameState) objIn;
                        player = gameState.getPlayer(player.getNumber());
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
            out.reset();

            //End once response to write has been received
            while(!responseReceived)
            {
                Thread.sleep(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Player getPlayer()
    {
        return player;
    }

    public GameState getGameState(){ return gameState; }

    public Boolean getResponseReceived()
    {
        return responseReceived;
    }

    public void setResponseReceived(Boolean b)
    {
        responseReceived = b;
    }

    public void close()
    {
        try
        {
            client.close();
            in.close();
            out.close();
            connectionThreads.remove(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}