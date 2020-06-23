package it.polimi.ingsw.client;

import java.io.Serializable;
import java.util.Objects;

// code taken from StackOverflow
//https://stackoverflow.com/questions/5303539/didnt-java-once-have-a-pair-class

/**
 * Container to ease passing around a tuple of two objects. This object provides a sensible
 * implementation of equals(), returning true if equals() is true on each of the contained
 * objects.
 */
public class Couple<F, S> implements Serializable {

    private final F first;
    private final S second;


    /**
     * Constructor for a Pair.
     *
     * @param first the first object in the Pair
     * @param second the second object in the pair
     */
    public Couple(F first, S second) {
        this.first = first;
        this.second = second;
    }


    public F getFirst() {
        return first;
    }


    public S getSecond() {
        return second;
    }


    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link Couple} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     *         equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Couple)) {
            return false;
        }
        Couple<?, ?> p = (Couple<?, ?>) o;
        return Objects.equals(p.first, first) && Objects.equals(p.second, second);
    }


    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }


    /**
     * Convenience method for creating an appropriately typed pair.
     * @param a the first object in the Pair
     * @param b the second object in the Pair
     * @param <A> a
     * @param <B> b
     * @return a Pair that is templatized with the types of a and b
     */
    public static <A, B> Couple <A, B> create(A a, B b) {
        return new Couple<A, B>(a, b);
    }


}