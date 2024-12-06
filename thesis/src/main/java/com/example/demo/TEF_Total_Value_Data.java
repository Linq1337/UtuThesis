package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.demo.MYSQL.Util;

public class TEF_Total_Value_Data {
    
    public void GetTotal_TEFData() {
        String sql = "SELECT COUNT(Value) as CNT FROM misp";  // No dynamic parameters, but using PreparedStatement for consistency

        try (Connection conn = Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // If you plan to add dynamic conditions, you could use stmt.setXxx methods to safely add parameters here
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the result set
                while (rs.next()) {
                    int totalValueCNT = rs.getInt("CNT");
                    System.out.println("Total: " + totalValueCNT);
                    // Optionally, add the value to a list or process it further
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();  // For better debugging
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
