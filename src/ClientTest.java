import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientTest {
    public static void main(String[]args) throws UnknownHostException
    {
        Client one = new Client(1000, InetAddress.getLocalHost().getHostAddress());
    }
}
