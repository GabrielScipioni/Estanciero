package ar.edu.utn.frc.tup.lciii.Services.DB.Or;

import ar.edu.utn.frc.tup.lciii.Entities.EventEntity;
import ar.edu.utn.frc.tup.lciii.Entities.ProvinceEntity;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.EventOrService;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.OR.ProvinceORService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventORServiceTest {
    private static EventOrService eventORService;


    @BeforeAll
    static void setUp() {
        eventORService = new EventOrService();
    }

    @Test
    void testGetById() {

        EventEntity retrievedEvent = eventORService.getById(1L);

        assertNotNull(retrievedEvent);
        assertEquals("MONEY", retrievedEvent.getDescription());
    }


    @Test
    void testGetAll() {

        List<EventEntity> eventEntityListEntityList = eventORService.getAll();

        // Verify that the list of games is not empty and contains the created game
        assertNotNull(eventEntityListEntityList);
        assertFalse(eventEntityListEntityList.isEmpty());
        assertEquals(11,eventEntityListEntityList.size());

        assertEquals(1L,eventEntityListEntityList.get(0).getEventId());
        assertEquals("MONEY",eventEntityListEntityList.get(0).getDescription());
        assertEquals(3L,eventEntityListEntityList.get(1).getEventId());
        assertEquals("GO_TO_JAIL",eventEntityListEntityList.get(1).getDescription());
        assertEquals(4L,eventEntityListEntityList.get(2).getEventId());
        assertEquals("GET_OUT_OF_JAIL",eventEntityListEntityList.get(2).getDescription());
        assertEquals(5L,eventEntityListEntityList.get(3).getEventId());
        assertEquals("MOOVE_TO_SQUARE",eventEntityListEntityList.get(3).getDescription());
        assertEquals(6L,eventEntityListEntityList.get(4).getEventId());
        assertEquals("MOOVE_TO_SQUARE_AND_EARN_AT_EXIT",eventEntityListEntityList.get(4).getDescription());
        assertEquals(7L,eventEntityListEntityList.get(5).getEventId());
        assertEquals("CHARGE_ALL_PLAYERS",eventEntityListEntityList.get(5).getDescription());
        assertEquals(8L,eventEntityListEntityList.get(6).getEventId());
        assertEquals("MOOVE_X_STEPS",eventEntityListEntityList.get(6).getDescription());
        assertEquals(9L,eventEntityListEntityList.get(7).getEventId());
        assertEquals("PAY_PER_UPGRADE",eventEntityListEntityList.get(7).getDescription());
        assertEquals(10L,eventEntityListEntityList.get(8).getEventId());
        assertEquals("PAY_AND_PICKUP_CARD_TYPE",eventEntityListEntityList.get(8).getDescription());
        assertEquals(11L,eventEntityListEntityList.get(9).getEventId());
        assertEquals("PICK_UP_CARD_TYPE",eventEntityListEntityList.get(9).getDescription());
        assertEquals(12L,eventEntityListEntityList.get(10).getEventId());
        assertEquals("JAIL",eventEntityListEntityList.get(10).getDescription());
    }
}
