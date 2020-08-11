package com.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
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
