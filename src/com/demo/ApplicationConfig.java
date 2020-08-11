package com.demo;

import com.demo.exceptions.ConfigFileException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ApplicationConfig {
    private int numberOfTimePeriods;
    private int columnIndexOfClosingPrice;
    private String outputFilePath;

    public ApplicationConfig() {
        try {
            Properties prop = new Properties();
            String propFileName = "application.properties";

            URL url = getClass().getResource("application.properties");
            InputStream inputStream = getClass().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            this.numberOfTimePeriods = Integer.parseInt(prop.getProperty("number_of_time_periods"));
            this.columnIndexOfClosingPrice = Integer.parseInt(prop.getProperty("column_index_of_closing_price"));
            this.outputFilePath = prop.getProperty("output_file_path");

            if (this.numberOfTimePeriods <= 0) throw new ConfigFileException("Number of time periods must be a positive number");
            if (this.columnIndexOfClosingPrice <= 0) throw new ConfigFileException("Column index of Closing Price must be a positive number");

            inputStream.close();
        } catch (Exception e) {
            System.out.println("Config file error: " + e.getMessage());
        }
    }

    public int getNumberOfTimePeriods() {
        return numberOfTimePeriods;
    }

    public void setNumberOfTimePeriods(int numberOfTimePeriods) {
        this.numberOfTimePeriods = numberOfTimePeriods;
    }

    public int getColumnIndexOfClosingPrice() {
        return columnIndexOfClosingPrice;
    }

    public void setColumnIndexOfClosingPrice(int columnIndexOfClosingPrice) {
        this.columnIndexOfClosingPrice = columnIndexOfClosingPrice;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }
}
