import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Player player;
    private Player[]  allPlayers;
    private Boolean closed, gameStarted;

    public Client(int port, String address, String clientName) throws IOException
    {
        this.closed = false;
        this.gameStarted = false;
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
                int num = (int) in.readObject();
                player = new Player(clientName, num, new DestinationCard(1), new DestinationCard(2));
                System.out.println("Player #" + player.getNumber());
                break;
            }
            catch(ClassNotFoundException e){}
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
                    Object input = in.readObject();
                    if(input instanceof String && input.equals("GAME_START"))
                        gameStarted = true;
                    if(input instanceof Player[])
                    {
                        allPlayers = (Player[]) input;
                        for(Player pl: allPlayers)
                            System.out.println(pl.getName() + " " + pl.getPoints());
                    }
                    else if(input.toString().equals("PLAY_TURN"))
                    {
                        playTurn();
                    }
                    else if(input.toString().equals("GAME_END"))
                    {
                        System.out.println("Game Ended");
                        close();
                    }
                    Thread.sleep(1);
                }
                catch(Exception e)
                {}
        }
    }

    public void playTurn()
    {
        String userIn = ClientTest.prompt(player.getName() + " Add Points: ");
        if(userIn.equals("QUIT"))
            close();
        int addedPoints = Integer.parseInt(userIn);
        player.setPoints(player.getPoints() + addedPoints);
        update();
    }

    //Run at the end of each this current player's turn
    public void update()
    {
        try
        {
            out.writeObject(player);
            out.reset();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public Player getPlayer()
    {
        return player;
    }

    public Player[] getAllPlayers()
    {
        return allPlayers;
    }

    public Boolean getGameStarted()
    {
        return gameStarted;
    }

    void close()
    {
        try
        {
            System.out.println("Closing Connection");
            out.writeObject("QUIT");
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
