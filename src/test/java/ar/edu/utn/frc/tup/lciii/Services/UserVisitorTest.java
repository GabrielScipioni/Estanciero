package ar.edu.utn.frc.tup.lciii.Services;

import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Event.EventType;
import ar.edu.utn.frc.tup.lciii.Model.Event.GoToJailStrategy;
import ar.edu.utn.frc.tup.lciii.Model.Property.TerrainProperty;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;
import ar.edu.utn.frc.tup.lciii.Model.Property.Province;
import ar.edu.utn.frc.tup.lciii.Model.Property.Zone;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class UserVisitorTest {

    @Test
    void visitTest() {
        UserVisitor userVisitor = new UserVisitor();
        Event event = new Event(EventType.GO_TO_JAIL, new GoToJailStrategy());
        assertDoesNotThrow(() -> userVisitor.visit(event));
    }

    @Test
    void testVisitTest() {
        UserVisitor userVisitor = new UserVisitor();
        Integer[] rents = {1,2,3,4,5};
        Property property = new TerrainProperty(false,"test",1, Zone.NORTE, Province.CORDOBA,rents,10,0);
        assertDoesNotThrow(() -> userVisitor.visit(property));
    }
}