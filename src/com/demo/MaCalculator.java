package com.demo;

import com.demo.exceptions.NegativeValueException;
import com.demo.exceptions.UnableToReadLineException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

public class MaCalculator {
    private int columnIndexOfClosingPrice;
    private int numberOfTimePeriods;
    private String outputFilePath;

    private String inputFilePath;

    public MaCalculator(ApplicationConfig applicationConfig) {
        this.columnIndexOfClosingPrice = applicationConfig.getColumnIndexOfClosingPrice();
        this.numberOfTimePeriods = applicationConfig.getNumberOfTimePeriods();
        this.outputFilePath = applicationConfig.getOutputFilePath();
    }

    public MaCalculator inputFile(String inputFilePath) {
        this.inputFilePath = inputFilePath;
        return this;
    }

    public String getResult() {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(this.inputFilePath));
            FileWriter csvWriter = new FileWriter(this.outputFilePath);

            Queue<Float> closePriceList = new LinkedList<>();
            String row;
            double sum = 0;

            while ((row = csvReader.readLine()) != null) {
                float lastClosePrice = getClosePrice(row);
                closePriceList.add(lastClosePrice);

                if (closePriceList.size() >= this.numberOfTimePeriods) {

                    Float firstClosePrice = closePriceList.poll();
                    firstClosePrice = (firstClosePrice != null) ? firstClosePrice : 0;

                    sum = sum + lastClosePrice - firstClosePrice;

                    double movingAverage = (sum/this.numberOfTimePeriods);

                    DecimalFormat df = new DecimalFormat("#.####");
                    csvWriter.append(row).append(",").append(df.format(movingAverage)).append("\n");
                } else {
                    sum = sum + lastClosePrice;
                    csvWriter.append(row).append(",\n");
                }
            }

            csvReader.close();
            csvWriter.flush();
            csvWriter.close();
            return "File written successfully";
        } catch (UnableToReadLineException e) {
            return "Error: Unable to read line " + e.getMessage();
        } catch (NegativeValueException e) {
            return "Error: Negative value found at line " + e.getMessage();
        } catch (NumberFormatException e) {
            return "Error: Number format error at line " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e;
        }
    }
    
    private float getClosePrice(String row) throws NumberFormatException, NegativeValueException, UnableToReadLineException {
        float closePrice;

        String[] data = row.split(",");
        if (data.length < this.columnIndexOfClosingPrice + 1) {
            throw new UnableToReadLineException(row);
        }

        try {
            closePrice = Float.parseFloat(data[this.columnIndexOfClosingPrice]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(row);
        }

        if (closePrice < 0) throw new NegativeValueException(row);

        return closePrice;
    }
}
