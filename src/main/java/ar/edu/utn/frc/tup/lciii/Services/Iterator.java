package ar.edu.utn.frc.tup.lciii.Services;
/**
 * Iterator interface that defines the methods for iterating over a collection of elements.
 *
 * @param <T> type of elements returned
 *
 * The Iterator pattern is a design pattern that provides a way to access the elements of an aggregate object
 * sequentially without exposing its underlying representation.
 *
 * @see "Iterator Design Pattern"
 */
public interface Iterator<T> {
    /**
     * @return {@code true} if the iteration has more elements
     */
    boolean hasMore();
    /**
     * @return the next element in the iteration
     */
    T next();
}
