package com.demo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MaCalculator calculator = new MaCalculator(new ApplicationConfig());
        System.out.println("Enter your csv file path: ");
        String inputFilePath = input.next();

        String result = calculator.inputFile(inputFilePath).getResult();

        System.out.println(result);
    }
}
