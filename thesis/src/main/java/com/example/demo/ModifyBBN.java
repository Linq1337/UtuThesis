package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.*;
import java.sql.*;
import java.util.List;

public class ModifyBBN {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ModifyBBN(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void RunThis() {
        // Parameterized 
        String sql = """
            SELECT id, category, type, threat_level_id, category_type_counter, percentage 
            FROM fair_data 
            WHERE category = ? AND type = ? 
            GROUP BY id, category, type, threat_level_id, category_type_counter, percentage 
            LIMIT 1
            """;

        // Execute query and map results to a list of FairData objects
        List<FairData> results = jdbcTemplate.query(sql, new FairDataRowMapper());

        // Process and display the results
        for (FairData fairData : results) {
            double impactFactor = fairData.getThreatLevelId(); // Example, you need to define how impact factor is set
            int valueCounter = fairData.getCategoryTypeCounter();
            int categoryTypePercentage = fairData.getPercentage();
            double totalValueFixed = calculateTotalValue(valueCounter, categoryTypePercentage);

            System.out.printf(
                "ID: %d, Category: %s, Type: %s, Threat Level ID: %.2f, Counter: %d, Percentage: %d, Total Value: %.2f%n",
                fairData.getId(), fairData.getCategory(), fairData.getType(),
                fairData.getThreatLevelId(), fairData.getCategoryTypeCounter(),
                fairData.getPercentage(), totalValueFixed
            );

            // File Handling
            String oldFileName = "C:/Users/alexa/Downloads/unbbayes-4.22.18-dist/unbbayes-4.22.18/examples/bn/net/BBNModelThesis.net";
            String tmpFileName = "C:/Users/alexa/Downloads/unbbayes-4.22.18-dist/unbbayes-4.22.18/examples/bn/net/BBNModelThesisTemp.net";

            try (BufferedReader reader = new BufferedReader(new FileReader(oldFileName));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFileName))) {
                String row;
                while ((row = reader.readLine()) != null) {
                    // Impact Factor Processing
                    if (impactFactor >= 4) {
                        if (row.contains("potential (Threat_Capability){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )");
                    } else if (impactFactor > 1 && impactFactor <= 3) {
                        if (row.contains("potential (Threat_Capability){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 1.0 0.0 )");
                    } else if (impactFactor == 1) {
                        if (row.contains("potential (Threat_Capability){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 0.0 1.0 )");
                    }

                    // Resistance Capability Processing
                    if (row.contains("potential (Resistance_Capability){ data = ( 1.0 0.0 0.0 );"))
                        row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )");

                    // Probability of Action Processing
                    if (valueCounter < 3) {
                        if (row.contains("potential (Probability_of_action){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 0.0 1.0 )");
                    } else if (valueCounter > 3 && valueCounter < 10) {
                        if (row.contains("potential (Probability_of_action){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 1.0 0.0 )");
                    } else if (valueCounter > 10) {
                        if (row.contains("potential (Probability_of_action){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )");
                    }

                    // Contact Frequency Processing
                    if (categoryTypePercentage < 5) {
                        if (row.contains("potential (Contact_Frequency){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )");
                    } else if (categoryTypePercentage < 0.5 && categoryTypePercentage < 5) {
                        if (row.contains("potential (Contact_Frequency){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 1.0 0.0 )");
                    } else if (categoryTypePercentage < 0 && categoryTypePercentage < 0.5) {
                        if (row.contains("potential (Contact_Frequency){ data = ( 1.0 0.0 0.0 );"))
                            row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 0.0 0.1 )");
                    }

                    writer.write(row + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Delete old file and rename temporary file
            File oldFile = new File(oldFileName);
            if (oldFile.exists()) {
                oldFile.delete();
            }

            File newFile = new File(tmpFileName);
            if (newFile.exists()) {
                newFile.renameTo(oldFile);
            }
        }
    }

    private double calculateTotalValue(int counter, int percentage) {
        // Example computation logic
        return counter * (percentage / 100.0);
    }

    // RowMapper to map ResultSet rows to FairData objects
    private static class FairDataRowMapper implements RowMapper<FairData> {
        @Override
        public FairData mapRow(ResultSet rs, int rowNum) throws SQLException {
            FairData fairData = new FairData();
            fairData.setId(rs.getLong("id"));
            fairData.setCategory(rs.getString("category"));
            fairData.setType(rs.getString("type"));
            fairData.setThreatLevelId(rs.getDouble("threat_level_id"));
            fairData.setCategoryTypeCounter(rs.getInt("category_type_counter"));
            fairData.setPercentage(rs.getInt("percentage"));
            return fairData;
        }
    }
}
