import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable
{
    private int points, number;
    private String name;
    private transient Color  color;
    private DestinationCard[] destinationCards = new DestinationCard[2];
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(String name, int number, DestinationCard destCard1, DestinationCard destCard2){
        this.name = name;
        points = 0;
        this.number = number;
        destinationCards[0] = destCard1;
        destinationCards[1] = destCard2;

        //color based on number
        switch (number)
        {
            case 1: color = new Color(0,0,1,1); break; //blue
            case 2: color = new Color(0, 1, 0, 1); break; //green
            case 3: color = new Color(1, 1, 0, 1); break; //yellow
            default: color = new Color( 1, 0, 0, 1); break; //red
        }
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

    public String getName(){return name;}

    public Color getColor() {
        return color;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }
}