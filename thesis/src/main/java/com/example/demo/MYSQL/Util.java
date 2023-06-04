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

    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        if(connection != null) {
        return connection;

    } else {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mastersthesis?allowPublicKeyRetrieval=TRUE&autoReconnect=true";
    
        // Store your Password seperately and encrypted if possible.
        String user = "root";
        String password = "321acdc321";

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            
            e.printStackTrace();
        }

    }
    return connection;
}
    
}

    








    
   

