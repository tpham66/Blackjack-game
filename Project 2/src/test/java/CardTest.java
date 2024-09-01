import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    @Test
    void testCard() {
        Card ace = new Card("spade",1);
        Card king = new Card("club",13);
        Card jack = new Card("diamond",11);
        Card queen = new Card("heart",12);
        assertEquals("spade",ace.suit, "Incorrect Card constructor behavior");
        assertEquals("club",king.suit, "Incorrect Card constructor behavior");
        assertEquals("diamond",jack.suit, "Incorrect Card constructor behavior");
        assertEquals("heart",queen.suit, "Incorrect Card constructor behavior");
        assertEquals(1,ace.value, "Incorrect Card constructor behavior");
        assertEquals(13,king.value, "Incorrect Card constructor behavior");
        assertEquals(11,jack.value, "Incorrect Card constructor behavior");
        assertEquals(12,queen.value, "Incorrect Card constructor behavior");
    }

    @Test
    void testGetValue() {
        Card ace = new Card("spade",1);
        Card king = new Card("club",13);
        Card jack = new Card("diamond",11);
        Card queen = new Card("heart",12);
        Card four = new Card("club",4);
        assertEquals(11,ace.getValue(), "Incorrect getValue behavior");
        assertEquals(10,king.getValue(), "Incorrect getValue behavior");
        assertEquals(10,jack.getValue(), "Incorrect getValue behavior");
        assertEquals(10,queen.getValue(), "Incorrect getValue behavior");
        assertEquals(4,four.getValue(), "Incorrect getValue behavior");
    }

    @Test
    void testGetImage() {
        Card ace = new Card("spade",1);
        Card king = new Card("club",13);
        Card jack = new Card("diamond",11);
        Card queen = new Card("heart",12);
        Card four = new Card("club",4);
        assertEquals("image/deck card/spade/A.png",ace.getImage(), "Incorrect getImage behavior");
        assertEquals("image/deck card/club/K.png",king.getImage(), "Incorrect getImage behavior");
        assertEquals("image/deck card/diamond/J.png",jack.getImage(), "Incorrect getImage behavior");
        assertEquals("image/deck card/heart/Q.png",queen.getImage(), "Incorrect getImage behavior");
        assertEquals("image/deck card/club/4.png",four.getImage(), "Incorrect getImage behavior");
    }
}
