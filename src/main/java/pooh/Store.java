package pooh;

/**
 * Interface of Store
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public interface Store<T> {
    /**
     * Method return something fom store
     *
     * @return T
     */
    T get();

    /**
     * Method add something to store
     *
     * @param some
     */
    void add(T some);

    /**
     * Method of copy store
     *
     * @return
     */
    Store copyStore();
}
