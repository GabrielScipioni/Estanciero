package ar.edu.utn.frc.tup.lciii.Entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardEntityTest {

    private BoardEntity boardEntity;

    @BeforeEach
    public void setUp() {
        boardEntity = new BoardEntity(1L, 2L, 3L, 4L, true, 2);
    }

    @Test
    public void testGetters() {
        assertEquals(1L, boardEntity.getBoardId());
        assertEquals(2L, boardEntity.getGamesId());
        assertEquals(3L, boardEntity.getSquareId());
        assertEquals(4L, boardEntity.getSquareOwner());
        assertTrue(boardEntity.getMortgage());
        assertEquals(2, boardEntity.getUpgradeLevel());
    }

    @Test
    public void testSetters() {
        boardEntity.setBoardId(10L);
        boardEntity.setGamesId(20L);
        boardEntity.setSquareId(30L);
        boardEntity.setSquareOwner(40L);
        boardEntity.setMortgage(false);
        boardEntity.setUpgradeLevel(3);

        assertEquals(10L, boardEntity.getBoardId());
        assertEquals(20L, boardEntity.getGamesId());
        assertEquals(30L, boardEntity.getSquareId());
        assertEquals(40L, boardEntity.getSquareOwner());
        assertFalse(boardEntity.getMortgage());
        assertEquals(3, boardEntity.getUpgradeLevel());
    }

    @Test
    public void testNoArgsConstructor() {
        BoardEntity newBoardEntity = new BoardEntity();
        assertNotNull(newBoardEntity);
        assertNull(newBoardEntity.getBoardId());
        assertNull(newBoardEntity.getGamesId());
        assertNull(newBoardEntity.getSquareId());
        assertNull(newBoardEntity.getSquareOwner());
        assertNull(newBoardEntity.getMortgage());
        assertNull(newBoardEntity.getUpgradeLevel());
    }

    @Test
    public void testAllArgsConstructor() {
        BoardEntity allArgsBoardEntity = new BoardEntity(5L, 6L, 7L, 8L, false, 1);
        assertEquals(5L, allArgsBoardEntity.getBoardId());
        assertEquals(6L, allArgsBoardEntity.getGamesId());
        assertEquals(7L, allArgsBoardEntity.getSquareId());
        assertEquals(8L, allArgsBoardEntity.getSquareOwner());
        assertFalse(allArgsBoardEntity.getMortgage());
        assertEquals(1, allArgsBoardEntity.getUpgradeLevel());
    }




}