package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.example.demo.MYSQL.Util;
import java.util.Map;
import java.util.HashMap;


public class ContactFrequency {

    // List to store categorized data
    public ArrayList<String> TEF_Value_list = new ArrayList<String>();

    // Method to retrieve TEF data and categorize based on user-defined thresholds
    public void GetTEFValueData(Map<String, Integer> thresholds, String filterValue) {

        // Secure parameterized SQL query to prevent SQL injection
        String sql = """
            SELECT id, value, COUNT(value) AS CNT
            FROM fair_data
            WHERE value IS NOT NULL
            AND value LIKE ?  
            GROUP BY id, value
        """;

        try (Connection conn = Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter for the filter (user input)
            stmt.setString(1, "%" + filterValue + "%");  // Safe handling of user input using LIKE

            try (ResultSet rs = stmt.executeQuery()) {

                // Loop through the ResultSet
                while (rs.next()) {
                    String value = rs.getString("value");
                    int CNT = rs.getInt("CNT");

                    // Dynamically categorize based on user-defined thresholds
                    String riskLevel = categorizeRiskLevel(CNT, thresholds);

                    // Add categorized data to the list
                    TEF_Value_list.add(riskLevel);
                    TEF_Value_list.add(value);
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

    // Method to categorize the CNT based on thresholds provided by the user
    private String categorizeRiskLevel(int CNT, Map<String, Integer> thresholds) {
        for (Map.Entry<String, Integer> entry : thresholds.entrySet()) {
            String riskLevel = entry.getKey();
            int threshold = entry.getValue();

            // If CNT is less than or equal to a threshold, return the corresponding risk level
            if (CNT <= threshold) {
                return riskLevel;
            }
        }

        // If no thresholds match, return a default category
        return "Unknown";
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
