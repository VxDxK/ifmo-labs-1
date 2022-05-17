package core.server.database;

import apps.Server;
import core.pojos.UserClient;

import java.sql.*;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO, AutoCloseable {
    private final static Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
    private final ServerDataSource dataSource;

    public UserDAOImpl(ServerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean add(UserClient.UserServer element) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO s338999_users (login, passmd5) values(?, ?)")) {
            statement.setString(1, element.getLogin());
            statement.setString(2, element.getHash());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserClient.UserServer getByID(int id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM s338999_users WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                return new UserClient.
                        UserServer(set.getString("login"), set.getString("passmd5"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public UserClient.UserServer getByLogin(String login) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM s338999_users WHERE login = ?")) {
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                return new UserClient.
                        UserServer(set.getString("login"), set.getString("passmd5"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(UserClient.UserServer element) {
        throw new UnsupportedOperationException("I will do it later");
    }

    @Override
    public void deleteByID(int id) {
        try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM s338999_users WHERE id = ?")) {
            statement.setInt(1, id);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UserClient.UserServer element) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM s338999_users WHERE login = ? and passmd5 = ?")) {
            statement.setString(1, element.getLogin());
            statement.setString(2, element.getHash());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute("DROP table s338999_users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createSchema();
    }

    @Override
    public boolean createSchema() {

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.execute("CREATE TABLE IF NOT EXISTS s338999_users(\n" +
                    "    id  SERIAL NOT NULL PRIMARY KEY,\n" +
                    "    login text NOT NULL,\n" +
                    "    passMD5 text\n" +
                    ");");
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public int getIDByLogin(String login){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM s338999_users WHERE login = ?")) {
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                return set.getInt("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean checkAuth(UserClient.UserServer user) {
        if(user == null){
            return false;
        }
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM s338999_users WHERE login = ? and passmd5 = ?")){
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getHash());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
            logger.severe(e.getMessage());
        }
        return false;
    }

    @Override
    public void close() {
        dataSource.close();
    }
}
