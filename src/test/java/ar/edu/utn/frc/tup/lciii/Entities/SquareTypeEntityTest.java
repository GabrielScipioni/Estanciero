package ar.edu.utn.frc.tup.lciii.Entities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SquareTypeEntityTest {

    private SquareTypeEntity squareType;

    @BeforeEach
    void setUp() {
        squareType = new SquareTypeEntity(1L, "Test Description");
    }

    @Test
    void testSquareTypeEntityConstructor() {
        Assertions.assertEquals(1L, squareType.getSquareTypeId());
        Assertions.assertEquals("Test Description", squareType.getDescription());
    }

    @Test
    void testSquareTypeEntitySettersAndGetters() {
        squareType.setSquareTypeId(2L);
        squareType.setDescription("New Description");

        Assertions.assertEquals(2L, squareType.getSquareTypeId());
        Assertions.assertEquals("New Description", squareType.getDescription());
    }
}
