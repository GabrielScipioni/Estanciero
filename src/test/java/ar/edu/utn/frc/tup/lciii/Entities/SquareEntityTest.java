package ar.edu.utn.frc.tup.lciii.Entities;

import ar.edu.utn.frc.tup.lciii.Entities.SquareEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareEntityTest {

    @Test
    public void testSquareEntityInitialization() {
        // Arrange
        Long squareId = 1L;
        Long squareTypeId = 2L;
        String nameSquare = "Example Square";
        Long provinceId = 3L;
        Long zoneId = 4L;
        Integer upgradePrice = 100;
        Integer amount = 5;
        Long eventId = 6L;
        Long eventWhenPassingId = 7L;
        Integer price = 200;
        Long cardToPickId = 8L;

        // Act
        SquareEntity square = new SquareEntity(squareId, squareTypeId, nameSquare, provinceId, zoneId,
                upgradePrice, amount, eventId, eventWhenPassingId,
                price, cardToPickId);

        // Assert
        Assertions.assertEquals(squareId, square.getSquareId());
        Assertions.assertEquals(squareTypeId, square.getSquareTypeId());
        Assertions.assertEquals(nameSquare, square.getNameSquare());
        Assertions.assertEquals(provinceId, square.getProvinceId());
        Assertions.assertEquals(zoneId, square.getZoneId());
        Assertions.assertEquals(upgradePrice, square.getUpgradePrice());
        Assertions.assertEquals(amount, square.getAmount());
        Assertions.assertEquals(eventId, square.getEventId());
        Assertions.assertEquals(eventWhenPassingId, square.getEventWhenPassingId());
        Assertions.assertEquals(price, square.getPrice());
        Assertions.assertEquals(cardToPickId, square.getCardToPickId());
    }

}
