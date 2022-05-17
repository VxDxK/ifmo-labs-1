package core.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IDGeneratorSeq implements Sequence, AutoCloseable{
    private final ServerDataSource dataSource;

    public IDGeneratorSeq(ServerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createSchema() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()){
            return statement.execute("CREATE SEQUENCE IF NOT EXISTS s338999_idgen INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int nextValue() {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery("SELECT nextval('s338999_idgen')");
            if(set.next()){
                return set.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void close() {
        dataSource.close();
    }
}
