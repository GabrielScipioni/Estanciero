package ar.edu.utn.frc.tup.lciii.Entities;

import ar.edu.utn.frc.tup.lciii.Entities.GameplayerCardsEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameplayerCardsEntityTest {

    @Test
    public void testConstructorAndGettersSetters() {
        // Given
        Long gamePlayerCardsId = 1L;
        Long playerEntityId = 10L;
        Long cardEntityId = 20L;


        // When
        GameplayerCardsEntity gmentity = new GameplayerCardsEntity(gamePlayerCardsId, playerEntityId, cardEntityId);

        // Then
        assertNotNull(gmentity);
        assertEquals(gamePlayerCardsId, gmentity.getGamePlayerCardsId());
        assertEquals(playerEntityId, gmentity.getPlayerEntity());
        assertEquals(cardEntityId, gmentity.getCardEntity());
    }

    @Test
    public void testSetters() {
        // Given
        GameplayerCardsEntity gmentity = new GameplayerCardsEntity();

        // When
        Long gamePlayerCardsId = 1L;
        Long playerEntityId = 10L;
        Long cardEntityId = 20L;

        gmentity.setGamePlayerCardsId(gamePlayerCardsId);
        gmentity.setPlayerEntity(playerEntityId);
        gmentity.setCardEntity(cardEntityId);

        // Then
        assertEquals(gamePlayerCardsId, gmentity.getGamePlayerCardsId());
        assertEquals(playerEntityId, gmentity.getPlayerEntity());
        assertEquals(cardEntityId, gmentity.getCardEntity());
    }

    @Test
    public void testNoArgsConstructor() {
        // Given
        GameplayerCardsEntity gmentity = new GameplayerCardsEntity();

        // Then
        assertNotNull(gmentity);
        assertNull(gmentity.getGamePlayerCardsId());
        assertNull(gmentity.getPlayerEntity());
        assertNull(gmentity.getCardEntity());
    }
}
