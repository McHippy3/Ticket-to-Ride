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
    private GameState gameState;
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
        Deck deck = null;
        Card[] availableCards = null;
        gameState = null;
        if(!server.isClosed())
        {
            System.out.println("Game Has Started");
            gameOngoing = true;
            //Initializing Deck and available cards
            deck = new Deck();
            availableCards = new Card[5];
            for(int i = 0; i < 5; i++)
                availableCards[i] = deck.getTop();
            gameState = new GameState(playerList, availableCards);
            for(ConnectionThread ct: connectionThreads)
            {
                ct.write(gameState);
                ct.write("GAME_START");
            }
        }

        //Close server
        close();

        //Handling turns
        int currentTurn = 0;
        while(gameOngoing)
        {

            //Preventing game from progressing until a response has been received
            connectionThreads.get(currentTurn).setResponseReceived(false);
            connectionThreads.get(currentTurn).write("PLAY_TURN");

            //Checking for cards taken
            for(int i = 0; i < 5; i++)
                if(availableCards[i] == null)
                    availableCards[i] = deck.getTop();

            //Update players
            gameState = connectionThreads.get(currentTurn).getGameState();
            for(ConnectionThread ct: connectionThreads)
            {
                ct.write(gameState);
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