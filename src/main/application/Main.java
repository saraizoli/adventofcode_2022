package main.application;

import main.day06.Day06;
import main.utils.Day;

public class Main {
    public static void main(String[] args) {
        Day day = new Day06();
        System.out.println("Task 1:");
        day.getSolution1();
        System.out.println();
        System.out.println("Task 2:");
        day.getSolution2();
    }
}