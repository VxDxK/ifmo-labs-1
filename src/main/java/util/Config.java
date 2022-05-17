package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private String jdbcUrl;
    private String username;
    private String password;

    public Config(Path file) {
        try(FileInputStream stream = new FileInputStream(file.toFile())){
            Properties properties = new Properties();
            properties.load(stream);
            jdbcUrl = properties.getProperty("jdbcUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            System.out.println("Error on config reading: " + e.getMessage());
            System.exit(-1);
        }
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Config{" +
                "jdbcUrl='" + jdbcUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
