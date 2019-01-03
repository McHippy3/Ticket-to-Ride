import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    private Socket socket;
    private Scanner consoleIn;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Boolean closed = false;


    public Client(int port, String address)
    {
        try
        {
            System.out.println("Attempting Connection on Port " + port + ", Address: " + address);
            socket = new Socket(address, port);
            System.out.println("Connected");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            consoleIn = new Scanner(System.in);

            //Start Thread to read in from server
            new Thread(this).start();

            //Read input and send it to server
            do
            {
                if(consoleIn.hasNextLine())
                {
                    String s = consoleIn.nextLine();
                    if(s.equals("QUIT"))
                        closed = true;
                    out.writeObject(s);
                }
                else { Thread.sleep(1); }
            }
            while(!closed);

            //Close
            in.close();
            out.close();
            socket.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    //Read information
    public void run()
    {
        while(!closed)
        {
                try
                {
                    //Throws EOF Exception if there is nothing to read
                    Object input = in.readObject();
                    System.out.println(input);
                    Thread.sleep(1);
                }
                catch(Exception e)
                {

                }
        }
    }
}
