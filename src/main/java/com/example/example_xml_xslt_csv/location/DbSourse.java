package com.example.example_xml_xslt_csv.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbSourse {

    private static final Logger LOGGER  = LoggerFactory.getLogger(DbSourse.class);
    private Connection connection = null;
    public void initializeResources() throws DbException {
        try {
            Class.forName ("org.h2.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:h2:mem:~/test", // путь для подключение
                    "sa", // login
                    ""); // pass
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Успешно подлючились!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException(e);
        }
    }
    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws DbException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException("Не удается закрыть соединение!", e);
            }
        }
    }
}
