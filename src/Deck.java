import java.util.ArrayList;
import java.util.Collections;

public class Deck{

    private ArrayList<Card> deck;

    public Deck()
    {
        deck = new ArrayList<Card>();

        String[] cardTypes = {"BLACK", "BLUE", "GREEN", "ORANGE", "PINK", "RED", "WHITE", "YELLOW", "RAINBOW"};
        int cardTypesIndex = 0;
        for(int i = 1; i <= 110; i++)
        {
            deck.add(new Card(cardTypes[cardTypesIndex]));
            if(i % 12 == 0 && cardTypesIndex != 8)
                cardTypesIndex++;
        }
        Collections.shuffle(deck);
    }

    public Card getTop()
    {
        return deck.remove(0);
    }
}
