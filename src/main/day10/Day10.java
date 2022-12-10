package main.day10;

import main.utils.Day;

import java.util.List;
import java.util.stream.IntStream;

public class Day10 extends Day<Integer> {
    private final int[] sumsDuringStep;

    public Day10() {
        List<String> code = getReader().readAsStringList(10);
//        code = List.of("noop","addx 3", "addx -5");
        int[] steps = code.stream().flatMapToInt(s -> "noop".equals(s) ? IntStream.of(0) : IntStream.of(0, Integer.parseInt(s.split(" ")[1]))).toArray();
        sumsDuringStep = new int[steps.length + 1];
        sumsDuringStep[0] = 1;
        IntStream.range(0, steps.length).forEach(i -> sumsDuringStep[i + 1] = sumsDuringStep[i] + steps[i]);
    }

    @Override
    public Integer getSolution1() {
        return IntStream.range(0, 6).map(i -> 20 + i * 40).map(i -> i * sumsDuringStep[i - 1]).sum();
    }

    @Override
    public Integer getSolution2() {
        for (int i = 0; i < 240; i++) {
            System.out.print(Math.abs(sumsDuringStep[i] - i % 40) < 2 ? "#" : ".");
            if ((i + 1) % 40 == 0) {
                System.out.println();
            }
        }
        return 0; //meh
    }

}
