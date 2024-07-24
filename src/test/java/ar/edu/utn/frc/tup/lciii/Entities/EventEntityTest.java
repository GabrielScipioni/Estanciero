package ar.edu.utn.frc.tup.lciii.Entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventEntityTest {

    private EventEntity event;

    @BeforeEach
    public void setUp() {
        event = new EventEntity();
    }

    @Test
    public void testEventIdGetterSetter() {
        Long eventId = 1L;
        event.setEventId(eventId);
        Assertions.assertEquals(eventId, event.getEventId(), "Getter and setter for EventId should work");
    }

    @Test
    public void testDescriptionGetterSetter() {
        String description = "Test event description";
        event.setDescription(description);
        Assertions.assertEquals(description, event.getDescription(), "Getter and setter for description should work");
    }

    @Test
    public void testNoArgsConstructor() {
        EventEntity newEvent = new EventEntity();
        Assertions.assertNotNull(newEvent, "No-args constructor should initialize the object");
    }

    @Test
    public void testAllArgsConstructor() {
        Long eventId = 1L;
        String description = "Test event description";
        EventEntity newEvent = new EventEntity(eventId, description);
        Assertions.assertEquals(eventId, newEvent.getEventId(), "AllArgsConstructor should set EventId correctly");
        Assertions.assertEquals(description, newEvent.getDescription(), "AllArgsConstructor should set description correctly");
    }
}
