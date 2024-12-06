package com.example.demo.MYSQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.mysql.cj.jdbc.MysqlDataSource;

public class Util {

    private static volatile Connection connection = null;
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    // Database credentials should be securely stored
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mastersthesis?allowPublicKeyRetrieval=TRUE&autoReconnect=true&useSSL=true";
    private static final String USER = System.getenv("DB_USER"); 
    private static final String PASSWORD = System.getenv("DB_PASSWORD"); 

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            synchronized (Util.class) {
                if (connection == null) {
                    try {
                        Class.forName(DRIVER);
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    } catch (ClassNotFoundException e) {
                        logger.log(Level.SEVERE, "Database Driver not found.", e);
                        throw new SQLException("Driver not found.", e);
                    } catch (SQLException e) {
                        logger.log(Level.SEVERE, "Failed to create database connection.", e);
                        throw e;
                    }
                }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            synchronized (Util.class) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Failed to close database connection.", e);
                } finally {
                    connection = null;
                }
            }
        }
    }
}





    
   

