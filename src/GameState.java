import java.io.Serializable;

public class GameState implements Serializable {

    private Player[] players;
    private Deck deck;
    private Card[] availableCards;

    public GameState(Player[] players, Card[] availableCards)
    {
        this.players = players;
        this.availableCards = availableCards;
        deck = new Deck();
    }

    public Player getPlayer(int i)
    {
        return players[i];
    }

    public void setPlayer(int i, Player p)
    {
        players[i] = p;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Card[] getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(Card[] availableCards) {
        this.availableCards = availableCards;
    }
}
