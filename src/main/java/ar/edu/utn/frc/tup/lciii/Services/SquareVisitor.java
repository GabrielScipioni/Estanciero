package ar.edu.utn.frc.tup.lciii.Services;


import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;

/**
 * Defines the visit methods for different types of squares in the game.
 * It follows the Visitor design pattern, which allows performing operations on elements of an object structure
 * without changing the classes of the elements on which it operates.
 *
 * @see "Visitor Design Pattern"
 */
public interface SquareVisitor {
    /**
     * Visit a square of type EventEntity.
     *
     * @param event the EventEntity square being visited
     */
    void visit(Event event);

    /**
     * Visit a square of type Property.
     *
     * @param property the Property square being visited
     */
    void visit(Property property);
}
