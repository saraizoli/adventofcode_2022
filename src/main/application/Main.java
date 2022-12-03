package main.application;

import main.day01.Day01;
import main.day02.Day02;
import main.day03.Day03;
import main.utils.Day;

public class Main {
    public static void main(String[] args) {
        Day day = new Day03();
        System.out.println("Task 1:");
        day.getSolution1();
        System.out.println();
        System.out.println("Task 2:");
        day.getSolution2();
    }
}