import java.util.ArrayList;

public class Player
{
    private int points;
    private String color;
    private DestinationCard[] destinationCards = new DestinationCard[3];
    private ArrayList<Card> hand = new ArrayList<>();



    public Player(String color, DestinationCard destCard1, DestinationCard destCard2, DestinationCard destCard3){
        this.color = color;
        points = 0;
        destinationCards[0] = destCard1;
        destinationCards[1] = destCard2;
        destinationCards[2] = destCard3;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points){this.points += points;}

    public void setPoints(int points) {
        this.points = points;
    }

    public DestinationCard[] getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(DestinationCard[] destinationCards) {
        this.destinationCards = destinationCards;
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}