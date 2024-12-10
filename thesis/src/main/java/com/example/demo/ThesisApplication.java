package com.example.demo;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.demo.MYSQL.Util;
import com.example.demo.impact_factor;
import com.example.demo.Probability_of_action;
import java.io.File;
import java.io.FileNotFoundException;
import com.thoughtworks.xstream.*;
import java.io.FileWriter;
import java.io.IOException;

public class ThesisApplication extends Util  {

    public static void main(String[] args) throws FileNotFoundException {

        // Use Scanner to get user input for thresholds
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for threshold values
        System.out.print("Enter the High threshold: ");
        int highThreshold = scanner.nextInt();

        System.out.print("Enter the Medium threshold: ");
        int mediumThreshold = scanner.nextInt();

        System.out.print("Enter the Low threshold: ");
        int lowThreshold = scanner.nextInt();

        // Create an instance of the ContactFrequency class or similar to use the thresholds
        ContactFrequency contactFrequencyObj = new ContactFrequency();
        contactFrequencyObj.setThresholds(highThreshold, mediumThreshold, lowThreshold);

        // Now, retrieve the data using the user-defined thresholds
        contactFrequencyObj.GetTEFValueData();

        // Using XStream to serialize the objects to XML
        XStream xstream = new XStream();

        // Export to XML
        String xml = xstream.toXML(contactFrequencyObj.getTEF_Value_list());

        // Write the XML to a file
        try {
            FileWriter xmlfile = new FileWriter("Value.xml");
            xmlfile.write(xml);
            xmlfile.close();
            System.out.println("Data has been exported to Value.xml.");
        } catch (IOException e) {
            System.out.println("Error occurred while exporting data.");
            e.printStackTrace();
        }

        // To test output, you can print the XML content
        // System.out.println(xml);

        // Close the scanner
        scanner.close();
    }



