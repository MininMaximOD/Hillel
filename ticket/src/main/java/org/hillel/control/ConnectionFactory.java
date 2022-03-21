package org.hillel.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory implements AutoCloseable{
        private Connection connection = null;
        private Properties properties = new Properties();
        private static Logger logger = Logger.getLogger(ConnectionFactory.class.getName());

    public ConnectionFactory(String pathFileConnection) {
        try {
            properties.load(new FileInputStream((pathFileConnection)));
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Exception found file: ", e);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Exception file: ", e);
        }
    }

    public Connection getConnection() throws SQLException{
        try {
            return connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));
        } catch (Exception e){
            logger.log(Level.WARNING, "Exception connection: ", e);
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        try {
        connection.close();
        } catch (Exception e){
            logger.log(Level.WARNING, "Exception close connection: ", e);
        }
    }
}
