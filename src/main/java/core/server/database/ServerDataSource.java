package core.server.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;
import util.Config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public class ServerDataSource implements AutoCloseable{
    private final Logger logger = Logger.getLogger(ServerDataSource.class.getName());

    private final HikariConfig config;
    private final HikariDataSource ds;

    public ServerDataSource(Config stConfig){
        config = new HikariConfig();
        config.setJdbcUrl(stConfig.getJdbcUrl());
        config.setUsername(stConfig.getUsername());
        config.setPassword(stConfig.getPassword());
        config.setDriverClassName(Driver.class.getName());
        config.setMaximumPoolSize(10);
//        config.setDataSourceClassName(PGSimpleDataSource.class.getName());

        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public void close(){
        try {
            ds.close();
        }catch (Exception e){
            logger.severe(e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
        }
    }
}
