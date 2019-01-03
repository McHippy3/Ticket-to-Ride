public class Test {
    public static void main(String[] args)
    {
        DestinationCard c = new DestinationCard((int)(Math.random() * 15));
        System.out.println(c.getDest1());
        System.out.println(c.getDest2());
        System.out.println(c.getPointValue());
    }
}
