package test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientTest {

    private static Client c;
    private static String name;
    public static void main(String[]args)
    {
        try
        {
            name = prompt("Name: ");
            c = new Client(1000, InetAddress.getLocalHost().getHostAddress(), name);
            c.start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String prompt(String s)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print(s);
        return sc.nextLine();
    }
}
