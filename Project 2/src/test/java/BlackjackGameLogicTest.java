import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackGameLogicTest {
    BlackjackGameLogic logic = new BlackjackGameLogic();

    @Test
    void testDealerWhoWon() {
        ArrayList<Card> player = new ArrayList<>(), dealer = new ArrayList<>();
        player.add(new Card("spade",8));
        player.add(new Card("heart",1));
        dealer.add(new Card("club",13));
        dealer.add(new Card("club",12));
        assertEquals("dealer",logic.whoWon(player,dealer), "Incorrect dealer whoWon behavior");
    }

    @Test
    void testPlayerWhoWon() {
        ArrayList<Card> player = new ArrayList<>(), dealer = new ArrayList<>();
        player.add(new Card("spade",12));
        player.add(new Card("heart",9));
        dealer.add(new Card("club",10));
        dealer.add(new Card("club",7));
        assertEquals("player",logic.whoWon(player,dealer), "Incorrect player whoWon behavior");
    }

    @Test
    void testDrawWhoWon() {
        ArrayList<Card> player = new ArrayList<>(), dealer = new ArrayList<>();
        player.add(new Card("spade",10));
        player.add(new Card("heart",10));
        dealer.add(new Card("club",1));
        dealer.add(new Card("club",9));
        assertEquals("push",logic.whoWon(player,dealer), "Incorrect draw whoWon behavior");
    }

    @Test
    void testBustWhoWon() {
        ArrayList<Card> player = new ArrayList<>(), dealer = new ArrayList<>();
        player.add(new Card("spade",10));
        player.add(new Card("heart",10));
        player.add(new Card("club",10));
        dealer.add(new Card("club",1));
        dealer.add(new Card("club",9));
        assertEquals("dealer",logic.whoWon(player,dealer), "Incorrect one person busts whoWon behavior");
    }
    
    @Test
    void testManyWhoWon() {
        ArrayList<Card> player = new ArrayList<>(), dealer = new ArrayList<>();
        dealer.add(new Card("diamond",2));
        dealer.add(new Card("diamond",9));
        dealer.add(new Card("spade",7));
        player.add(new Card("heart",1));
        player.add(new Card("diamond",13));
        player.add(new Card("club",5));
        player.add(new Card("diamond",1));
        assertEquals(18,logic.handTotal(dealer), "incorrect many whoWon behavior");
        assertEquals(17,logic.handTotal(player), "Incorrect many whoWon behavior");
    }

    @Test
    void testNoAceHandTotal() {
        ArrayList<Card> player = new ArrayList<>(), dealer = new ArrayList<>();
        player.add(new Card("spade",8));
        player.add(new Card("heart",10));
        dealer.add(new Card("club",13));
        dealer.add(new Card("club",12));
        assertEquals(18,logic.handTotal(player),"Incorrect no ace handTotal behavior");
        assertEquals(20,logic.handTotal(dealer), "Incorrect no ace handTotal behavior");
    }

    @Test
    void testAceIsElevenHandTotal() {
        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card("spade",9));
        player.add(new Card("heart",1));
        assertEquals(20,logic.handTotal(player),"Incorrect one ace handTotal behavior");
    }

    @Test
    void testAceIsOneHandTotal() {
        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card("spade",9));
        player.add(new Card("heart",1));
        player.add(new Card("heart",1));
        player.add(new Card("heart",1));
        assertEquals(12,logic.handTotal(player),"Incorrect one ace handTotal behavior");
    }

    @Test
    void testEvaluateBankerDraw() {
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card("spade",10));
        dealer.add(new Card("heart",2));
        assertTrue(logic.evaluateBankerDraw(dealer),"Incorrect evaluateBankerDraw behavior");
        dealer.add(new Card("spade",13));
        assertFalse(logic.evaluateBankerDraw(dealer),"Incorrect evaluateBankerDraw behavior");
    }
}
