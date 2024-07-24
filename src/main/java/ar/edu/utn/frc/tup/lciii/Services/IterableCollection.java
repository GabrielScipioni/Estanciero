package ar.edu.utn.frc.tup.lciii.Services;
/**
 * Defines a method for creating an iterator.
 *
 * @param <T> the type of elements in this collection
 *
 * This interface is part of the Iterator design pattern, providing a way to create an
 * iterator for a collection.
 *
 * @see "Iterator Design Pattern"
 */
public interface IterableCollection<T> {
    /**
     * Creates an iterator for the collection.
     *
     * @return an iterator for the collection
     */
    Iterator<T> createIterator();
}
