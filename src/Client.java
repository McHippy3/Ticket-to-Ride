import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Player player;
    private Player[]  allPlayers;
    private Boolean closed, gameOngoing, gameStateChanged, isPlayerTurn;

    public Client(int port, String address, String clientName) throws IOException
    {
        this.closed = false;
        this.gameOngoing = false;
        this.gameStateChanged = false;
        this.isPlayerTurn = false;
        //TODO change later

        //Connecting to host
        System.out.println("Attempting Connection on Port " + port + ", Address: " + address);
        socket = new Socket(address, port);
        System.out.println("Connected");
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        //Instantiating player
        while(true)
        {
            try
            {
                Thread.sleep(1);
                int num = (int) in.readObject();
                player = new Player(clientName, num, new DestinationCard(1), new DestinationCard(2));
                System.out.println("Player #" + player.getNumber());
                break;
            }
            catch(Exception e){}
        }

        //Sending initial player data
        out.writeObject(player);
        out.reset();
    }

    //Continuously reads from server, usually for an update to the game state
    public void run()
    {
        while(!closed)
        {
                try
                {
                    //Throws EOF Exception if there is nothing to read
                    //Notify whenever host sends a message
                    Thread.sleep(1);
                    Object input = in.readObject();
                    gameStateChanged = true;
                    if(input instanceof String && input.equals("GAME_START"))
                    {
                        gameOngoing = true;
                    }
                    if(input instanceof Player[])
                    {
                        allPlayers = (Player[]) input;
                        for(Player pl: allPlayers)
                            System.out.println(pl.getName() + " " + pl.getPoints());
                    }
                    else if(input.toString().equals("PLAY_TURN"))
                    {
                        isPlayerTurn = true;
                    }
                    else if(input.toString().equals("GAME_END"))
                    {
                        System.out.println("Game Ended");
                        gameOngoing = false;
                        close();
                    }
                }
                catch(Exception e)
                {}
        }
    }

    public void playTurn(String userIn)
    {
        if(userIn.equals("QUIT"))
        {
            close();
        }
        else
            {
                try {
                    int addedPoints = Integer.parseInt(userIn);
                    player.setPoints(player.getPoints() + addedPoints);
                    update();
                    isPlayerTurn = false;
                }
                catch(Exception e)
                {}
            }
    }

    //Run at the end of each this current player's turn
    public void update()
    {
        try
        {
            out.writeObject(player);
            out.reset();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Player getPlayer() { return player; }

    public Player[] getAllPlayers() { return allPlayers; }

    public Boolean getGameOngoing() { return gameOngoing; }

    public void setGameOngoing(Boolean b)
    {
        gameOngoing = b;
    }

    public Boolean getGameStateChanged(){return gameStateChanged;}

    public void setGameStateChanged(Boolean b){gameStateChanged = b;}

    public Boolean getIsPlayerTurn(){
        return isPlayerTurn;
    }

    public void setIsPlayerTurn(Boolean b){
        isPlayerTurn = b;
    }

    void close()
    {
        try
        {
            System.out.println("Closing Connection");
            out.writeObject("QUIT");
            gameOngoing = false;
            closed = true;
            in.close();
            out.close();
            socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
