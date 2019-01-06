import javafx.scene.image.Image;

public class Card {
    private String cardType;
    private Image image;
    public Card(String cardType)
    {
        this.cardType = cardType;
        switch (cardType)
        {
            case "BLACK": image = new Image("src/res/black_card.jpg");
                break;
            case "BLUE": image = new Image("src/res/blue_card.jpg");
                break;
            case "GREEN": image = new Image("src/res/green_card.jpg");
                break;
            case "ORANGE": image = new Image("src/res/orange_card.jpg");
                break;
            case "PINK": image = new Image("src/res/pink_card.jpg");
                break;
            case "RED": image = new Image("src/res/red_card.jpg");
                break;
            case "WHITE": image = new Image("src/res/white_card.jpg");
                break;
            case "YELLOW": image = new Image("src/res/yellow_card.jpg");
                break;
            case "RAINBOW": image = new Image("src/res/rainbow_card.jpg");
                break;
        }
    }

    public String getCardType()
    {
        return cardType;
    }

    public Image getImage()
    {
        return image;
    }
}
