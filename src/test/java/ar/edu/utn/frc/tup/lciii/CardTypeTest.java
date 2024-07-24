package ar.edu.utn.frc.tup.lciii;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTypeTest {

    @Test
    public void test() {
        assertEquals("LUCK", CardType.LUCK.name());
        assertEquals("DESTINY", CardType.DESTINY.name());
    }

}
