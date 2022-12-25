package main.application;

import main.day01.Day01;
import main.day02.Day02;
import main.day03.Day03;
import main.day04.Day04;
import main.day05.Day05;
import main.day06.Day06;
import main.day07.Day07;
import main.day08.Day08;
import main.day09.Day09;
import main.day10.Day10;
import main.day11.Day11;
import main.day12.Day12;
import main.day13.Day13;
import main.day14.Day14;
import main.day15.Day15;
import main.day16.Day16;
import main.day17.Day17;
import main.day18.Day18;
import main.day19.Day19;
import main.day20.Day20;
import main.day21.Day21;
import main.day22.Day22;
import main.day23.Day23;
import main.day24.Day24;
import main.day25.Day25;
import main.utils.Day;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        solveAll(1);

//        Day<?> day = new Day25();
//        day.printSolution1WithTime();
//        day.printSolution2WithTime();
//
//        Day.printConstructionTime(Day25::new, 1000);
//        day.printSolution1WithTime(1000);
//        day.printSolution2WithTime(1000);
    }

    private static void solveAll(int repetition){
            long start = System.nanoTime();
            IntStream.range(0, repetition ).forEach(i -> {
                Day<?> day;
                System.out.println("Day01");
                day = new Day01();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day02");
                day = new Day02();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day03");
                day = new Day03();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day04");
                day = new Day04();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day05");
                day = new Day05();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day06");
                day = new Day06();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day07");
                day = new Day07();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day08");
                day = new Day08();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day09");
                day = new Day09();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day10");
                day = new Day10();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day11");
                day = new Day11();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day12");
                day = new Day12();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day13");
                day = new Day13();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day14");
                day = new Day14();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day15");
                day = new Day15();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day16");
                day = new Day16();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day17");
                day = new Day17();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day18");
                day = new Day18();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day19");
                day = new Day19();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day20");
                day = new Day20();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day21");
                day = new Day21();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day22");
                day = new Day22();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day23");
                day = new Day23();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day24");
                day = new Day24();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
                System.out.println("Day25");
                day = new Day25();
                System.out.println(day.getSolution1());
                System.out.println(day.getSolution2());
            });
            long end = System.nanoTime();

            double elapsedMillis = (end - start) / 1e6;
            System.out.printf("All tasks time total: %fms, avg run: %fms%n", elapsedMillis, elapsedMillis / repetition);
            System.out.println();
    }
}