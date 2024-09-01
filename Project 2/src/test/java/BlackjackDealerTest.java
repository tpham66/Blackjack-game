import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BlackjackDealerTest {
    BlackjackDealer dealer = new BlackjackDealer();
    @Test
    void testGenerateDeck() {
        dealer.generateDeck();
        assertEquals(52,dealer.deckSize(), "Incorrect generateDeck behavior");
        dealer.generateDeck();
        assertEquals(52,dealer.deckSize(), "Incorrect generateDeck behavior");
        dealer.generateDeck();
        assertEquals(52,dealer.deckSize(), "Incorrect generateDeck behavior");
        dealer.generateDeck();
        assertEquals(52,dealer.deckSize(), "Incorrect generateDeck behavior");
        dealer.generateDeck();
        assertEquals(52,dealer.deckSize(), "Incorrect generateDeck behavior");
        dealer.generateDeck();
        assertEquals(52,dealer.deckSize(), "Incorrect generateDeck behavior");
    }

    @Test
    void testDealHand() {
        dealer.generateDeck();
        assertEquals(2,dealer.dealHand().size(),"Incorrect dealHand behavior");
    }

    @Test
    void testEmptiedDrawOne() {
        dealer.generateDeck();
        for (int i = 0; i < 52; ++i) {
            dealer.drawOne();
        }
        assertNull(dealer.drawOne(), "Incorrect empty drawOne behavior");
    }

    @Test
    void testNonEmptyDrawOne() {
        dealer.generateDeck();
        dealer.drawOne();
        assertEquals(51,dealer.deckSize(), "Incorrect non empty drawOne behavior");
    }

    @Test
    void testShuffleDeck() {
        dealer.generateDeck();
        dealer.shuffleDeck();
        assertEquals(52,dealer.deckSize(),"Incorrect shuffleDeck behavior");
    }
}
