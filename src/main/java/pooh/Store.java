package pooh;

/**
 * Интерфейс ...
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public interface Store<T> {
    T get();
    void add(T some);
    Store copyStore();
}
