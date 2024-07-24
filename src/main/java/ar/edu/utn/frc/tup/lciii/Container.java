package ar.edu.utn.frc.tup.lciii;
import java.util.List;
/**
 * The Container interface represents an entity that contains a list of items of a specific type.
 *
 * Implementations of this interface should provide methods to retrieve the list of items
 * contained within the container, as well as an identifier to uniquely identify the container.
 *
 * @param <T> The type of items contained within the container.
 */
public interface Container<T> {
    List<T> getList();
    String getIdentifier();
}