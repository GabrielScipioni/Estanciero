package ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD;

import ar.edu.utn.frc.tup.lciii.Model.Event.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEventMoney() {
        Event event = eventService.createEvent(EventType.MONEY);
        assertNotNull(event);
        assertEquals(EventType.MONEY, event.getType());
        assertTrue(event.getStrategy() instanceof MoneyStrategy);
    }

    @Test
    void testCreateEventGoToJail() {
        Event event = eventService.createEvent(EventType.GO_TO_JAIL);
        assertNotNull(event);
        assertEquals(EventType.GO_TO_JAIL, event.getType());
        assertTrue(event.getStrategy() instanceof GoToJailStrategy);
    }

    @Test
    void testCreateEventNull() {
        Event event = eventService.createEvent(null);
        assertNull(event);
    }
    
    @Test
    void testCreateEventGetOutOfJail() {
        Event event = eventService.createEvent(EventType.GET_OUT_OF_JAIL);
        assertNotNull(event);
        assertEquals(EventType.GET_OUT_OF_JAIL, event.getType());
        assertTrue(event.getStrategy() instanceof GetOutOfJailStrategy);
    }

    @Test
    void testCreateEventMoveToSquare() {
        Event event = eventService.createEvent(EventType.MOOVE_TO_SQUARE);
        assertNotNull(event);
        assertEquals(EventType.MOOVE_TO_SQUARE, event.getType());
        assertTrue(event.getStrategy() instanceof TeleportStrategy);
    }

    @Test
    void testCreateEventMoveToSquareAndEarnAtExit() {
        Event event = eventService.createEvent(EventType.MOOVE_TO_SQUARE_AND_EARN_AT_EXIT);
        assertNotNull(event);
        assertEquals(EventType.MOOVE_TO_SQUARE_AND_EARN_AT_EXIT, event.getType());
        assertTrue(event.getStrategy() instanceof MoveToSquareStrategy);
    }

    @Test
    void testCreateEventChargeAllPlayers() {
        Event event = eventService.createEvent(EventType.CHARGE_ALL_PLAYERS);
        assertNotNull(event);
        assertEquals(EventType.CHARGE_ALL_PLAYERS, event.getType());
        assertTrue(event.getStrategy() instanceof ChargeAllPlayersStrategy);
    }

    @Test
    void testCreateEventMoveXSteps() {
        Event event = eventService.createEvent(EventType.MOOVE_X_STEPS);
        assertNotNull(event);
        assertEquals(EventType.MOOVE_X_STEPS, event.getType());
        assertTrue(event.getStrategy() instanceof MoveStepsStrategy);
    }

    @Test
    void testCreateEventPayPerUpgrade() {
        Event event = eventService.createEvent(EventType.PAY_PER_UPGRADE);
        assertNotNull(event);
        assertEquals(EventType.PAY_PER_UPGRADE, event.getType());
        assertTrue(event.getStrategy() instanceof PayPerUpgradeStrategy);
    }

    @Test
    void testCreateEventPickUpCardType() {
        Event event = eventService.createEvent(EventType.PICK_UP_CARD_TYPE);
        assertNotNull(event);
        assertEquals(EventType.PICK_UP_CARD_TYPE, event.getType());
        assertTrue(event.getStrategy() instanceof PickUpCardTypeStrategy);
    }

    @Test
    void testCreateEventPayAndPickUpCardType() {
        Event event = eventService.createEvent(EventType.PAY_AND_PICKUP_CARD_TYPE);
        assertNotNull(event);
        assertEquals(EventType.PAY_AND_PICKUP_CARD_TYPE, event.getType());
        assertTrue(event.getStrategy() instanceof PayOrPickupStrategy);
    }

    @Test
    void testCreateEventJail() {
        Event event = eventService.createEvent(EventType.JAIL);
        assertNotNull(event);
        assertEquals(EventType.JAIL, event.getType());
        assertTrue(event.getStrategy() instanceof JailStrategy);
    }
}
