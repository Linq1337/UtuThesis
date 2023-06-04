package com.example.demo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.demo.MYSQL.Util;

public class TEF_Total_Value_Data {
    
    public void GetTotal_TEFData () {
    String sql = " SELECT COUNT(Value) as CNT " +
    "FROM misp";


try (Connection conn = Util.getConnection();

// Change to PREPARED STATEMENT because of security reasons //
Statement stmt  = conn.createStatement();
ResultSet rs    = stmt.executeQuery(sql)) {

// loop RS
while (rs.next()) {

int Total_Value_CNT = rs.getInt("CNT");

System.out.println("Total:" + Total_Value_CNT);

// Add value to List //
                
   
}
} catch (SQLException ex) {
System.out.println(ex.getMessage());
}

}
}

