package main.application;

import main.day04.Day04;
import main.utils.Day;

public class Main {
    public static void main(String[] args) {
        Day day = new Day04();
        System.out.println("Task 1:");
        day.getSolution1();
        System.out.println();
        System.out.println("Task 2:");
        day.getSolution2();
    }
}