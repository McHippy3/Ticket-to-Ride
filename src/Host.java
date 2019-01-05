import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Host extends Thread
{
    private ServerSocket server;
    private String address;
    private ArrayList<ConnectionThread> connectionThreads = new ArrayList<>();
    private int numOfPlayers;
    static Boolean gameOngoing = false;
    private Player[] playerList;
    private int port;

    public Host(int port, int numOfPlayers)
    {
        this.numOfPlayers = numOfPlayers;
        playerList = new Player[numOfPlayers];
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

    public void run()
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
        while(connectionThreads.size() < numOfPlayers && !server.isClosed())
        {
            try
            {
                System.out.println("Waiting for Client");
                Socket s = server.accept();
                ConnectionThread client = new ConnectionThread(s, connectionThreads);
                playerList[connectionThreads.size()] = client.getPlayer();
                connectionThreads.add(client);
                System.out.println("Client " + connectionThreads.size() + " Connected");
                client.start();
            }
            catch(Exception e){
                System.out.println("Server Closed Prematurely");
            }
        }


        //Start game once all players have connected
        if(!server.isClosed())
        {
            System.out.println("Game Has Started");
            gameOngoing = true;
            for(ConnectionThread ct: connectionThreads)
            {
                ct.write(playerList);
                ct.write("GAME_START");
            }
        }

        //Close server
        close();

        //Initialize Players
        if(gameOngoing) {
            for (int i = 0; i < numOfPlayers; i++) {
                playerList[i] = connectionThreads.get(i).getPlayer();
            }
        }

        //Handling turns
        int currentTurn = 0;
        while(gameOngoing)
        {

            //Preventing game from progressing until a response has been received
            connectionThreads.get(currentTurn).setResponseReceived(false);
            connectionThreads.get(currentTurn).write("PLAY_TURN");

            //Update players
            playerList[currentTurn] = connectionThreads.get(currentTurn).getPlayer();
            for(ConnectionThread ct: connectionThreads)
            {
                ct.write(playerList);
            }

            //Print out current game state
            for(ConnectionThread ct: connectionThreads)
                System.out.println(ct.getPlayer().getName() + ": " + ct.getPlayer().getPoints());

            //Change turn
            currentTurn++;
            if(currentTurn == numOfPlayers)
                currentTurn = 0;
        }

        for(ConnectionThread ct: connectionThreads)
            synchronized (ct) {
                ct.write("GAME_END");
            }

        System.out.println("Game Ended");
        System.exit(0);
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
    public ArrayList<ConnectionThread> getHostThreads()
    {
        return connectionThreads;
    }

    public String getAddress()
    {
        return address;
    }
}