package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.example.demo.MYSQL.Util;

public class ContactFrequency {

    // Allow users to define risk levels //
    
    // Encapsulates List and Content, Use getters to retrieve //
    public ArrayList<String> TEF_Value_list = new ArrayList<String>();

    public void GetTEFValueData() {

      // Filter if user input is used, currently it is not 
        String sql = """
            SELECT id, value, COUNT(value) AS CNT
            FROM fair_data
            WHERE value IS NOT NULL
            GROUP BY id, value
        """;

        try (Connection conn = Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                // Loop through the ResultSet
                while (rs.next()) {
                    String value = rs.getString("value");
                    int CNT = rs.getInt("CNT");

                    // Categorize CNT values into High, Medium, and Low risk levels
                    if (CNT > 100) {
                        String CNT_String = "High";
                        TEF_Value_list.add(CNT_String);
                        TEF_Value_list.add(value);
                    }
                    if (CNT > 50 && CNT <= 100) {
                        String CNT_String = "Medium";
                        TEF_Value_list.add(CNT_String);
                        TEF_Value_list.add(value);
                    }
                    // Optionally print or log the result for debugging
                    // System.out.println("Value: " + value + " Count: " + CNT);
                }

            }

        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();  // Better trace for debugging
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Getter for TEF_Value_list
    public ArrayList<String> getTEF_Value_list() {
        return TEF_Value_list;
    }

    // Setter for TEF_Value_list
    public void setTEF_Value_list(ArrayList<String> tEF_Value_list) {
        TEF_Value_list = tEF_Value_list;
    }
}
