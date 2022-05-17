package core.readers;

import core.pojos.UserClient;
import core.server.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class UserReader extends ElementReader<UserClient>{
    @Override
    public UserClient read(BufferedReader reader, OutputStreamWriter writer) throws IOException {
        UserClient.UserBuilder builder = new UserClient.UserBuilder();

        boolean login_stat = false;
        while (!login_stat){
            writer.write("Enter login: ");
            writer.flush();
            String loginStr = reader.readLine();
            try {
                builder.validateLogin(loginStr);
                login_stat = builder.setLogin(loginStr);
            }catch (ValidationException e){
                writer.write(e.getMessage());
                writer.flush();
            }
        }

        boolean pass_stat = false;
        while (!pass_stat){
            writer.write("Enter password: ");
            writer.flush();
            String passwordStr = reader.readLine();
            try {
                builder.validatePassword(passwordStr);
                pass_stat = builder.setPassword(passwordStr);
            }catch (ValidationException e){
                writer.write(e.getMessage());
                writer.flush();
            }
        }


        UserClient user;
        try {
            user = builder.build();
        } catch (ValidationException e) {
            throw new IOException("Error in IO: " + e.getMessage());
        }

        assert user != null;

        return user;
    }

    @Override
    public UserClient read(BufferedReader reader) throws IOException {
        UserClient.UserBuilder builder = new UserClient.UserBuilder();

        String loginStr = reader.readLine();
        try {
            builder.validateLogin(loginStr);
            builder.setLogin(loginStr);
        }catch (ValidationException e){
            throw new IOException("Error in script file: " + e.getMessage());
        }

        String passwordStr = reader.readLine();
        try {
            builder.validatePassword(passwordStr);
            builder.setPassword(passwordStr);
        } catch (ValidationException e) {
            throw new IOException("Error in script file: " + e.getMessage());
        }

        UserClient user;
        try {
            user = builder.build();
        } catch (ValidationException e) {
            throw new IOException("Error in script file: " + e.getMessage());
        }

        assert user != null;

        return user;
    }
}
