import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackGameTest {
    BlackjackGame game = new BlackjackGame();

    @Test
    void testBlackJackGame() {
        assertEquals(0.0,game.currentBet,"Incorrect constructor BlackjackGame behavior");
        assertEquals(0.0,game.totalWinnings,"Incorrect constructor BlackjackGame behavior");
        assertEquals(52,game.theDealer.deckSize(),"Incorrect constructor BlackjackGame behavior");
        assertTrue(game.playerHand.isEmpty(),"Incorrect constructor BlackjackGame behavior");
        assertTrue(game.bankerHand.isEmpty(),"Incorrect constructor BlackjackGame behavior");
    }

    @Test
    void testEmptyNewRound() {
        game.newRound();
        assertEquals(48,game.theDealer.deckSize(), "Incorrect newRound() behavior");
        assertEquals(2,game.playerHand.size(), "Incorrect newRound() behavior");
        assertEquals(2,game.bankerHand.size(), "Incorrect newRound() behavior");

    }

    @Test
    void testSingleNewRound() {
        game.newRound();
        for (int i = 0; i < 47; ++i) {
            game.theDealer.drawOne();
        }
        assertEquals(1,game.theDealer.deckSize(), "Incorrect single newRound() behavior");
        game.newRound();
        assertEquals(48,game.theDealer.deckSize(), "Incorrect single newRound() behavior");
        assertEquals(2,game.playerHand.size(), "Incorrect single newRound() behavior");
        assertEquals(2,game.bankerHand.size(), "Incorrect single newRound() behavior");

    }

    @Test
    void testNewRound() {
        game.newRound();
        for (int i = 0; i < 10; ++i) {
            game.theDealer.drawOne();
        }
        assertEquals(38,game.theDealer.deckSize(), "Incorrect single newRound() behavior");
        game.newRound();
        assertEquals(48,game.theDealer.deckSize(), "Incorrect newRound() behavior");
        assertEquals(2,game.playerHand.size(), "Incorrect newRound() behavior");
        assertEquals(2,game.bankerHand.size(), "Incorrect newRound() behavior");

    }

    @Test
    void testPlaceBet() {
        game.totalWinnings = 500;
        game.placeBet(50);
        assertEquals(450.0,game.totalWinnings,"Incorrect placeBet behavior");
        assertEquals(50.0,game.currentBet, "Incorrect placeBet behavior");
    }

    @Test
    void testLoseEvaluateWinnings() {
        game.totalWinnings = 500;
        game.placeBet(50);
        game.bankerHand.add(new Card("spade", 1));
        game.bankerHand.add(new Card("spade", 13));
        game.playerHand.add(new Card("heart", 12));
        game.playerHand.add(new Card("diamond", 9));
        assertEquals(0.0, game.evaluateWinnings(), "Incorrect lose evaluateWinnings behavior");
    }

    @Test
    void testWinEvaluateWinnings() {
        game.totalWinnings = 500;
        game.placeBet(50);
        game.bankerHand.add(new Card("spade",7));
        game.bankerHand.add(new Card("spade",13));
        game.playerHand.add(new Card("heart",12));
        game.playerHand.add(new Card("diamond",9));
        assertEquals(100,game.evaluateWinnings(), "Incorrect win evaluateWinnings behavior");
    }

    @Test
    void testDrawEvaluateWinnings() {
        game.totalWinnings = 500;
        game.placeBet(50);
        game.bankerHand.add(new Card("spade",9));
        game.bankerHand.add(new Card("club",10));
        game.playerHand.add(new Card("diamond",9));
        game.playerHand.add(new Card("spade",12));
        assertEquals(50.0,game.evaluateWinnings(), "Incorrect draw evaluateWinnings behavior");
    }

    @Test
    void testBlackjackEvaluateWinnings() {
        game.totalWinnings = 500;
        game.placeBet(50);
        game.playerHand.add(new Card("spade", 1));
        game.playerHand.add(new Card("spade", 13));
        game.bankerHand.add(new Card("heart", 12));
        game.bankerHand.add(new Card("diamond", 9));
        assertEquals(125, game.evaluateWinnings(), "Incorrect blackjack evaluateWinnings behavior");
    }

    @Test
    void testBothBlackjackEvaluateWinnings() {
        game.totalWinnings = 500;
        game.placeBet(50);
        game.playerHand.add(new Card("spade", 1));
        game.playerHand.add(new Card("spade", 13));
        game.bankerHand.add(new Card("heart", 12));
        game.bankerHand.add(new Card("diamond", 1));
        assertEquals(50, game.evaluateWinnings(), "Incorrect both blackjack evaluateWinnings behavior");
    }

    @Test
    void testManyEvaluateWinnings() {
        game.totalWinnings = 1000;
        game.placeBet(100);
        game.bankerHand.add(new Card("diamond",2));
        game.bankerHand.add(new Card("diamond",9));
        game.bankerHand.add(new Card("spade",7));
        game.playerHand.add(new Card("heart",1));
        game.playerHand.add(new Card("diamond",13));
        game.playerHand.add(new Card("club",5));
        game.playerHand.add(new Card("diamond",1));
        assertEquals(0.0,game.evaluateWinnings(),"Incorrect many evaluateWinnings");
    }

}
