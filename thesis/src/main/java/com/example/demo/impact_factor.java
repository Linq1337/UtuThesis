package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.demo.MYSQL.Util;


public class ImpactFactor {

    // Encapsulates List and Content, Use getters to retrieve
    private ArrayList<String> impact_list = new ArrayList<String>();

    private String threat_level;
    private String category;
    private String type;
    private String impact_factor;

    // Add category and type to query to determine threat_level
    public void impact(Map<String, Integer> thresholds) {

        // Secure parameterized SQL query to prevent SQL injection
        String sql = """
    SELECT id, value, category, type, threat_level_id
    FROM fair_data
    WHERE category = ? AND type = ?
    GROUP BY id, value, category, type, threat_level_id
     """;

        try (Connection conn = Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Execute query and retrieve the result set
            try (ResultSet rs = stmt.executeQuery()) {

                // Loop through the result set
                while (rs.next()) {
                    String id = rs.getString("id");
                    String value = rs.getString("value");
                    String category = rs.getString("category");
                    String type = rs.getString("type");
                    int impact_factor = rs.getInt("threat_level_id");

                    // Categorize based on user-defined thresholds
                    String riskLevel = categorizeRiskLevel(impact_factor, thresholds);

                    // Add categorized data to the list
                    impact_list.add(id);  
                    impact_list.add(value); 
                    impact_list.add(category);  
                    impact_list.add(type);  
                    impact_list.add(riskLevel); // Add the determined risk level
                }
                
                // Optionally, print or log the result for debugging
                System.out.println(impact_list);

            }

        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();  // For better debugging
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Method to categorize the impact_factor based on user-defined thresholds
    private String categorizeRiskLevel(int impactFactor, Map<String, Integer> thresholds) {
        for (Map.Entry<String, Integer> entry : thresholds.entrySet()) {
            String riskLevel = entry.getKey();
            int threshold = entry.getValue();

            // Compare impact factor with thresholds to categorize
            if (impactFactor >= threshold) {
                return riskLevel;
            }
        }
        return "Unknown"; // Default category if no threshold is matched
    }

    // Getter and Setter methods
    public String getThreat_level() {
        return threat_level;
    }

    public void setThreat_level(String threat_level) {
        this.threat_level = threat_level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImpact_factor() {
        return impact_factor;
    }

    public void setImpact_factor(String impact_factor) {
        this.impact_factor = impact_factor;
    }

    public ArrayList<String> getImpact_list() {
        return impact_list;
    }

    public void setImpact_list(ArrayList<String> impact_list) {
        this.impact_list = impact_list;
    }
}
