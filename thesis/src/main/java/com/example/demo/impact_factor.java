package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.demo.MYSQL.Util;

public class impact_factor {
 
  // Encapsulates List and Content, Use getters to retrieve //
private ArrayList< String> impact_list = new ArrayList<String>();

private String threat_level;
private String category;
private String type;
private String impact_factor;


// Add category and type to query to determine threat_level
public void impact() {

    String sql = "SELECT id, value, category, type, threat_level_id " +
    "FROM fair_data GROUP BY id, value, category, type, threat_level_id";

try (Connection conn = Util.getConnection();
 
// Change to PREPARED STATEMENT because of security reasons //
Statement stmt  = conn.createStatement();
 
ResultSet rs = stmt.executeQuery(sql)) {

// loop through the result set
// Store rs in ArrayList and declare as a private variable
while (rs.next()) {
    String id = rs.getString("id");
    String value = rs.getString("value");
    String category = rs.getString("category");
    String type = rs.getString("type");
    int impact_factor = rs.getInt("threat_level_id");
  
   // Defining low, medium, high and very high risks and adding items to arraylist.
   if(impact_factor > 4) {
    String threat_level = String.valueOf(" Very High");
    impact_list.add(id);  
    impact_list.add(value); 
    impact_list.add(category);  
    impact_list.add(type);  
    impact_list.add(threat_level);
   
}
  if(impact_factor == 4) {
    String threat_level = String.valueOf(" High");
    impact_list.add(id); 
     impact_list.add(value); 
    impact_list.add(category);  
    impact_list.add(type);  
    impact_list.add(threat_level);
  }

if(impact_factor > 1 && impact_factor < 4) {
  String threat_level = String.valueOf(" Medium");
  impact_list.add(id); 
   impact_list.add(value); 
  impact_list.add(category);  
  impact_list.add(type);  
  impact_list.add(threat_level);
}
else {
  String threat_level = String.valueOf(" Low");
  impact_list.add(id); 
   impact_list.add(value); 
  impact_list.add(category);  
  impact_list.add(type);    
  impact_list.add(threat_level);
}

    System.out.println(impact_list);

  // Use this code for debug purposes //
  // System.out.println("Type:" +Type);
  //  System.out.println("Category:" + Category);
  //  System.out.println("Category_counter:" + Category_Type_Counter);
  //  System.out.println("Value_Match_Counter:" + Value_CNT);
}
} catch (SQLException ex) {
System.out.println(ex.getMessage());
}

}


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








// We already achieve encapsulation partly with help of the RS set, however if more control is wanted add the following Getters/Setters
//public ArrayList<String> getImpact_list() {
//    return impact_list; 
//}
//public void setImpact_list(ArrayList<String> impact_list) {
//    this.impact_list = impact_list;
//}

  //  String test3 = rs.getString("value");
   // String Type = rs.getString("type");		  
   // String Category = rs.getString("category");
  //  String Category_Type_Counter = rs.getString("Category_Type_Counter");
   // String Value_CNT = rs.getString("Value_CNT");
