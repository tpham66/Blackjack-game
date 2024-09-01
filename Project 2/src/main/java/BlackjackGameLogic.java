import java.util.ArrayList;
public class BlackjackGameLogic {

    // Return either player or dealer or push depending on who wins
    public String whoWon(ArrayList <Card> playerHand1, ArrayList<Card> dealerHand) {
        int userScore = handTotal(playerHand1);
        int dealerScore = handTotal(dealerHand);

        if (dealerScore > 21) {
            return "player"; // dealer busts
        }
        else if (userScore > 21) {
            return "dealer"; // player busts
        }
        else if (dealerScore == userScore) {
            return "push"; // draw
        }
        else if (dealerScore > userScore) {
            return "dealer"; // dealer wins
        }
        else {
            return "player"; // player wins
        }
    }

    // Return the total value of all cards in the hand
    public int handTotal(ArrayList<Card> hand) {
        int totalValue = 0;
        int ace = 0;

        for (Card card : hand) {
            totalValue += card.getValue();

            // Count the aces
            if (card.getValue() == 11) { // Ace is initiated 11
                ace++;
            }
        }

        // Adjust aces' value if total value exceeds 21
        while (totalValue > 21 && ace > 0) {
            totalValue -= 10; // Ace is set to 1
            ace--;
        }

        return totalValue;
    }

    // Return true if the dealer should draw another card
    public boolean evaluateBankerDraw(ArrayList<Card> hand) {
        return handTotal(hand) <= 16;
    }
}
