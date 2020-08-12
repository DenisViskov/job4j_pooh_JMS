package pooh;

/**
 * Интерфейс ...
 *
 * @author Денис Висков
 * @version 1.0
 * @since 12.08.2020
 */
public interface Service<T, V> {

    T getRequest(V server);

    void execute(Decryption decryption, T from);
}