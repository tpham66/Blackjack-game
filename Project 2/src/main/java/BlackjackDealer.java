import java.util.ArrayList;
import java.util.Collections;

public class BlackjackDealer {
    private ArrayList<Card> deck;

    // Generate a deck of cards, one card for each of
    // 13 faces and 4 suits
    public void generateDeck() {
        deck = new ArrayList<>(52);
        String[] suits = {"spade","heart","diamond","club"};
        int[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13};
        for (String suit: suits) {
            for (int value: values) {
                deck.add(new Card(suit, value));
            }
        }
    }

    // Return an Arraylist of two cards
    // and leave the remainder of the deck able to be drawn later
    public ArrayList<Card> dealHand() {
        ArrayList<Card> cards = new ArrayList<>(2);
        if (deck.size() >= 2) {
            cards.add(drawOne());
            cards.add(drawOne());
        }
        return cards;
    }

    // Return the next card on top of the deck
    public Card drawOne() {
        if (deck.isEmpty()) return null;
        return deck.remove(deck.size() - 1);
    }

    // Return all 52 cards to the deck and
    // shuffle their order
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    // Return the number of cards left in the deck.
    // After a call to shuffleDeck() this should be 52
    public int deckSize() {
        return deck.size();
    }
}
