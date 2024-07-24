package ar.edu.utn.frc.tup.lciii.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DiceTest {
    private Dice dice;

    @BeforeEach
    public void setUp() {
        dice = new Dice();
    }

    @Test
    public void testThrowDice() {

        Integer[] rolls = dice.throwDice();

        for (Integer roll : rolls) {
            assertTrue(roll >= 1 && roll <= 6);
        }
    }

    @Test
    public void testCheckDouble() {
        Integer[] rolls = dice.throwDice();
        if (rolls[0].equals(rolls[1])){
            assertTrue(dice.checkDouble());
        }else {
            assertFalse(dice.checkDouble());
        }


    }

    @Test
    public void testMove() {
        dice.throwDice();

        int result = dice.getTotalValue();

        assertTrue(result >= 2 && result <= 12);
    }
}