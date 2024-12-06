package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.example.demo.MYSQL.Util;

public class Probability_of_action {

    // retrieves the occurrence of Category and Type matches which is used partly to calculate probability //
    
    // Encapsulates List and Content, Use getters to retrieve //
    private ArrayList<String> TEF_list = new ArrayList<String>();
    
    public void GetTEFData(String filterCategory, String filterType) {
        
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

                    // Use the getters values in TEF_Value_Data class for these or modify manually to define low, medium, high
                    // OR allow users to modify the values 
                    double low = 70.8 / Total_Value_Fixed;
                    double medium = 177 / Total_Value_Fixed;
                    double high = 354 / Total_Value_Fixed;
                    double veryhigh = 708 / Total_Value_Fixed;

                    // Calculating Percentage 
                    double TEF_Percentage_Pre = Category_Type_Counter / Total_Value_Fixed;
                    double TEF_Percentage = TEF_Percentage_Pre * 100;

                    // Categorize the results based on percentage thresholds
                    if (TEF_Percentage <= low) {
                        String TEF_Percentage_output = "Low";
                        TEF_list.add(Type);
                        TEF_list.add(Category);
                        TEF_list.add(TEF_Percentage_output);
                    } else if (TEF_Percentage > low && TEF_Percentage >= medium) {
                        String TEF_Percentage_output = "Medium";
                        TEF_list.add(Type);
                        TEF_list.add(Category);
                        TEF_list.add(TEF_Percentage_output);
                    } else if (TEF_Percentage > medium) {
                        String TEF_Percentage_output = "High";
                        TEF_list.add(Type);
                        TEF_list.add(Category);
                        TEF_list.add(TEF_Percentage_output);
                    }

                    // Debugging Output (optional)
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

    // Getter for TEF_list
    public ArrayList<String> getTEF_list() {
        return TEF_list;
    }

    // Setter for TEF_list
    public void setTEF_list(ArrayList<String> tEF_list) {
        TEF_list = tEF_list;
    }
}
