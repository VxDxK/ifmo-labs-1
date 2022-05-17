package util;

/**
 * Interface helps you to store data in SQL
 * @param <T> first type(any type)
 * @param <V> second type(should be sql storable)
 */
public interface SQLAdapter <T, V>{
    V transForm(T val);

    T parse(V val);
}
