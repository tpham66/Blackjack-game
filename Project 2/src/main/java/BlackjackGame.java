import java.util.ArrayList;

public class BlackjackGame {
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BlackjackDealer theDealer;
    BlackjackGameLogic gameLogic;
    double currentBet; // amount currently bet from the user
    double totalWinnings; // total amount of value that the user has

    public BlackjackGame() {
        playerHand = new ArrayList<>();
        bankerHand = new ArrayList<>();
        theDealer = new BlackjackDealer();
        theDealer.generateDeck();
        gameLogic = new BlackjackGameLogic();
        currentBet = 0.0;
        totalWinnings = 0.0;
    }

    // initiate new round
    public void newRound() {
        theDealer.generateDeck();
        theDealer.shuffleDeck();
        playerHand.clear();
        bankerHand.clear();
        playerHand.addAll(theDealer.dealHand());
        bankerHand.addAll(theDealer.dealHand());
    }

    // place new bet for this round
    public void placeBet(double newBet) {
        currentBet = newBet;
        totalWinnings -= currentBet;
    }

    // determine if the user
    // won or lost their bet and
    // return the amount won or lost based on the value in
    // currentBet
    public double evaluateWinnings() {
        String winner = gameLogic.whoWon(playerHand, bankerHand);
        switch (winner) {
            case "player":
                return (gameLogic.handTotal(playerHand) == 21 && playerHand.size() == 2) ? 2.5*currentBet : 2*currentBet;// win or blackjack
            case "dealer":
                return 0.0;// lose so the bet is not returned
        }
        return currentBet;// a draw so return the bet
    }

}