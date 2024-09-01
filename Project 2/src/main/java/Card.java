public class Card {
    String suit;
    int value;
    public Card(String theSuit, int theValue) {
        suit = theSuit;
        value = theValue;
    }
    public String getImage() {
        String cardValue;
        if (value == 11) {
            cardValue = "J";
        } else if (value == 12) {
            cardValue = "Q";
        } else if (value == 13) {
            cardValue = "K";
        } else if (value == 1) {
            cardValue = "A";
        } else {
            cardValue = Integer.toString(value);
        }
        return "image/deck card/" + suit + "/" + cardValue + ".png";
    }
    public int getValue() {
        if (value == 1) return 11;
        return Math.min(value, 10);
    }
}
