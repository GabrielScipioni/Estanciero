package ar.edu.utn.frc.tup.lciii.Services;

import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Property.Property;

/**
 * UsuarioVisitor implements the SquareVisitor interface to define
 * specific actions for a human player when visiting different types of squares.
 *
 * This class provides the logic for handling the actions that a human player
 * can perform when landing on different types of squares on the board, such as
 * EventEntity or Property squares.
 *
 * @see SquareVisitor
 * @see Event
 * @see Property
 */
public class UserVisitor implements SquareVisitor{
    /**
     * Handles the action when a human player visits an EventEntity square.
     *
     * @param event the EventEntity square being visited
     */
    @Override
    public void visit(Event event) {
        // TODO: Logic to handle visiting an EventEntity square
        // TODO: Additional logic for events
    }
    /**
     * Handles the action when a human player visits a Property square.
     *
     * @param property the Property square being visited
     */
    @Override
    public void visit(Property property) {
        // TODO: Logic to handle visiting a Property square
        // TODO: Additional logic for properties
    }
}
