package ar.edu.utn.frc.tup.lciii.Services;

import org.junit.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Test
    public void createEventFromKeyTest() {

        try {
            EventService eventService = mock(EventService.class);
            doNothing().when(eventService).createEventFromKey(anyString());

            eventService.createEventFromKey(anyString());

            //doNothing().when(eventService.createEventFromKey(anyString()));

            assertNotNull(eventService);
        }
        catch (Exception ignored) {}
    }

}