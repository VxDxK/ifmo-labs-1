package core.server.database;

import core.pojos.UserClient;

public interface UserDAO extends DAO<UserClient.UserServer>{
    int getIDByLogin(String login);
    boolean checkAuth(UserClient.UserServer user);
    UserClient.UserServer getByLogin(String login);
}
