import javafx.scene.image.Image;

import java.io.Serializable;

public class Card implements Serializable {
    private String cardType;
    private String imageURL;
    public Card(String cardType)
    {
        this.cardType = cardType;
        switch (cardType)
        {
            case "BLACK": imageURL = "res/black_card.jpg";
                break;
            case "BLUE": imageURL = "res/blue_card.jpg";
                break;
            case "GREEN": imageURL = "res/green_card.jpg";
                break;
            case "ORANGE": imageURL = "res/orange_card.jpg";
                break;
            case "PINK": imageURL ="res/pink_card.jpg";
                break;
            case "RED": imageURL = "res/red_card.jpg";
                break;
            case "WHITE": imageURL = "res/white_card.jpg";
                break;
            case "YELLOW": imageURL = "res/yellow_card.jpg";
                break;
            case "RAINBOW": imageURL = "res/rainbow_card.jpg";
                break;
        }
    }

    public String getCardType()
    {
        return cardType;
    }

    public Image getImage()
    {
        return new Image(imageURL);
    }
}
