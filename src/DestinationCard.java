import java.io.*;

public class DestinationCard {
    private String dest1;
    private String dest2;
    private int pointValue;

    public DestinationCard(int line)
    {
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("destinations_list.txt")));
            for (int i = 0; i < line; i++)
                in.readLine();
            String s = in.readLine();
            String[] array = s.split(" ");
            dest1 = array[0];
            dest2 = array[1];
            pointValue = Integer.parseInt(array[2]);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getDest1() {
        return dest1;
    }

    public String getDest2() {
        return dest2;
    }

    public int getPointValue() {
        return pointValue;
    }
}
