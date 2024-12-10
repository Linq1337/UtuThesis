package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.example.demo.MYSQL.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Probability_of_action {

    // Encapsulates List and Content, Use getters to retrieve //
    private ArrayList<String> TEF_list = new ArrayList<String>();

    // Enum for TEF percentage categories
    public enum TEFCategory {
        LOW, MEDIUM, HIGH
    }

    // Method to retrieve TEF data with user-configurable thresholds
    public void GetTEFData(String filterCategory, String filterType, ThresholdConfig config) {

        // Parameterized SQL Query
        String sql = """
            SELECT DISTINCT value, category, type, COUNT(category) AS Category_Type_Counter, value_cnt 
            FROM misp 
            WHERE value AND category IS NOT NULL
            AND category = ? AND type = ?
            GROUP BY value, category, type, value_cnt
        """;

        try (Connection conn = Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for category and type
            stmt.setString(1, filterCategory);  // Set category parameter
            stmt.setString(2, filterType);      // Set type parameter

            try (ResultSet rs = stmt.executeQuery()) {

                // Loop through ResultSet
                while (rs.next()) {
                    String Type = rs.getString("type");
                    String Category = rs.getString("category");
                    double Category_Type_Counter = rs.getDouble("Category_Type_Counter");
                    double Total_Value_Fixed = rs.getDouble("value_cnt");

                    // Calculate percentage
                    double TEF_Percentage_Pre = Category_Type_Counter / Total_Value_Fixed;
                    double TEF_Percentage = TEF_Percentage_Pre * 100;

                    // Get thresholds from user config
                    double low = config.getLowThreshold();
                    double medium = config.getMediumThreshold();
                    double high = config.getHighThreshold();

                    // Determine category using helper method
                    TEFCategory category = getTEFCategory(TEF_Percentage, low, medium, high);

                    // Add result to the list
                    addToTEFList(Type, Category, category);

                    // Optional Debugging Output
                    // System.out.println("Type:" + Type);
                    // System.out.println("Category:" + Category);
                    // System.out.println("Category_counter:" + Category_Type_Counter);
                    // System.out.println("TEF_Percentage:" + TEF_Percentage);  
                    // System.out.println("Value_Match_Counter:" + Value_CNT);
                }

            }

        } catch (SQLException ex) {
            // Log the exception with more details
            System.err.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();  // Better trace for debugging
        } catch (Exception ex) {
            // Catch any other unforeseen exceptions
            System.err.println("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Helper method to determine the TEF category based on percentage
    private TEFCategory getTEFCategory(double percentage, double low, double medium, double high) {
        // Map to store thresholds and their corresponding categories
        Map<Double, TEFCategory> thresholds = new HashMap<>();
        thresholds.put(low, TEFCategory.LOW);
        thresholds.put(medium, TEFCategory.MEDIUM);
        thresholds.put(high, TEFCategory.HIGH);

        // Loop through thresholds to find the correct category
        for (Map.Entry<Double, TEFCategory> entry : thresholds.entrySet()) {
            if (percentage <= entry.getKey()) {
                return entry.getValue();
            }
        }

        // Default to HIGH if no category matched
        return TEFCategory.HIGH;
    }

    // Helper method to add data to TEF_list
    private void addToTEFList(String type, String category, TEFCategory categoryLevel) {
        TEF_list.add(type);
        TEF_list.add(category);
        TEF_list.add(categoryLevel.name());
    }

    // Getter for TEF_list
    public ArrayList<String> getTEF_list() {
        return TEF_list;
    }

    // Setter for TEF_list
    public void setTEF_list(ArrayList<String> tEF_list) {
        TEF_list = tEF_list;
    }
}
