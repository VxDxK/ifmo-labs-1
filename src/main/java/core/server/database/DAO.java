package core.server.database;

/**
 * Spring CrudRepository analog
 * @param <T> the domain type the dao manages
 * @author vadim
 */
public interface DAO <T>{

    boolean add(T element);

    T getByID(int id);

    void update(T element);

    void deleteByID(int id);

    void delete(T element);

    void deleteAll();

    boolean createSchema();

}
