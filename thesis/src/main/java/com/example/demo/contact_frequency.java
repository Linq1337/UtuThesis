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

    // Encapsulates List and Content, Use getters to retrieve
    public ArrayList<String> TEF_Value_list = new ArrayList<String>();

    // Method to retrieve TEF data and categorize counts based on user-defined thresholds
    public void GetTEFValueData(ThresholdConfig config) {

        // SQL query to retrieve data from the database
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

                    // Loop through the thresholds defined in config to categorize the CNT value
                    String riskLevel = categorizeRiskLevel(CNT, config);

                    // Add the risk level and the corresponding value to the list
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

    // Method to categorize CNT value dynamically based on thresholds
    private String categorizeRiskLevel(int CNT, ThresholdConfig config) {
        // Loop through each risk level defined in the thresholds config
        for (Map.Entry<String, Integer> entry : config.getThresholds().entrySet()) {
            String riskLevel = entry.getKey();
            int threshold = entry.getValue();

            // Categorize the CNT based on the thresholds
            if (CNT <= threshold) {
                return riskLevel;
            }
        }

        // Return a default category if no thresholds match
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
