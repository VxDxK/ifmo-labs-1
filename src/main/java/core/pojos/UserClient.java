package core.pojos;

import core.server.ValidationException;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.Objects;

public final class UserClient implements Serializable {
    private final String login;
    private final String password;

    public UserClient(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserServer trans(){
        return new UserServer(login, DigestUtils.md5Hex(password));
    }


    @Override
    public String toString() {
        return "UserClient{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserClient)) return false;
        UserClient that = (UserClient) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    public static class UserBuilder implements Serializable{
        private String login;
        private String password;

        public void validateLogin(String login) throws ValidationException{
            if(login == null){
                throw new ValidationException("login cannot be null");
            }else if(login.equals("")){
                throw new ValidationException("login cannot be empty string");
            }
        }

        public boolean setLogin(String login) {
            try {
                validateLogin(login);
            }catch (ValidationException e){
                return false;
            }
            this.login = login;
            return true;
        }

        public void validatePassword(String password) throws ValidationException{
            if(password == null){
                throw new ValidationException("password cannot be null");
            }
        }

        public boolean setPassword(String password) {
            try {
                validatePassword(password);
            }catch (ValidationException e){
                return false;
            }
            this.password = password;
            return true;
        }

        public UserClient build() throws ValidationException{
            validateLogin(login);
            validatePassword(password);
            return new UserClient(login, password);
        }

        @Override
        public String toString() {
            return "UserBuilder{" +
                    "login='" + login + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    public static class UserServer implements Serializable{
        private final String login;
        private final String hash;

        public UserServer(String login, String hash) {
            this.login = login;
            this.hash = hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserServer)) return false;
            UserServer that = (UserServer) o;
            return Objects.equals(login, that.login) && Objects.equals(hash, that.hash);
        }

        @Override
        public int hashCode() {
            return Objects.hash(login, hash);
        }

        @Override
        public String toString() {
            return "UserServer{" +
                    "login='" + login + '\'' +
                    ", hash='" + hash + '\'' +
                    '}';
        }

        public String getLogin() {
            return login;
        }

        public String getHash() {
            return hash;
        }
    }

}
