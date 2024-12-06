package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.demo.MYSQL.Util;

public class ImpactFactor {
 
    // Encapsulates List and Content, Use getters to retrieve //
    private ArrayList<String> impact_list = new ArrayList<String>();

    private String threat_level;
    private String category;
    private String type;
    private String impact_factor;

    // Add category and type to query to determine threat_level
    public void impact() {

        String sql = """
            SELECT id, value, category, type, threat_level_id
            FROM fair_data
            GROUP BY id, value, category, type, threat_level_id
        """;

        try (Connection conn = Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // If needed, you can set parameters dynamically, for example:
            // stmt.setString(1, someValue);  // Use this if you're filtering by user input

            try (ResultSet rs = stmt.executeQuery()) {

                // Loop through the result set
                while (rs.next()) {
                    String id = rs.getString("id");
                    String value = rs.getString("value");
                    String category = rs.getString("category");
                    String type = rs.getString("type");
                    int impact_factor = rs.getInt("threat_level_id");

                    // Defining low, medium, high, and very high risks and adding items to arraylist
                    if (impact_factor > 4) {
                        String threat_level = "Very High";
                        impact_list.add(id);  
                        impact_list.add(value); 
                        impact_list.add(category);  
                        impact_list.add(type);  
                        impact_list.add(threat_level);
                    }
                    if (impact_factor == 4) {
                        String threat_level = "High";
                        impact_list.add(id); 
                        impact_list.add(value); 
                        impact_list.add(category);  
                        impact_list.add(type);  
                        impact_list.add(threat_level);
                    }

                    if (impact_factor > 1 && impact_factor < 4) {
                        String threat_level = "Medium";
                        impact_list.add(id); 
                        impact_list.add(value); 
                        impact_list.add(category);  
                        impact_list.add(type);  
                        impact_list.add(threat_level);
                    } else {
                        String threat_level = "Low";
                        impact_list.add(id); 
                        impact_list.add(value); 
                        impact_list.add(category);  
                        impact_list.add(type);    
                        impact_list.add(threat_level);
                    }
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
