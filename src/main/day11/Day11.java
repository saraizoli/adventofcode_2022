package main.day11;

import main.utils.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Day11 extends Day<Long> {
    private final List<String> text;

    public Day11() {
        text = getReader().readAsStringList(11);
//        text = getReader().readAsStringList("day11_sample.txt");
    }

    @Override
    public Long getSolution1() {
        List<Monkey> monkeys = getMonkeys();
        IntStream.range(0, 20).forEach(i -> monkeys.forEach(m -> m.throwAll(false)));
        return getMonkeyBusiness(monkeys);
    }

    @Override
    public Long getSolution2() {
        List<Monkey> monkeys = getMonkeys();
        IntStream.range(0, 10000).forEach(i -> monkeys.forEach(m -> m.throwAll(true)));
        return getMonkeyBusiness(monkeys);
    }

    public List<Monkey> getMonkeys() {
        Monkey.reset();
        List<Monkey> monkeys = new ArrayList<>();
        IntStream.range(0, 1 + text.size() / 7).map(i -> i * 7)
                .mapToObj(i -> new Monkey(text.subList(i + 1, i + 6), monkeys)).forEach(monkeys::add);
        return monkeys;
    }


    private static long getMonkeyBusiness(List<Monkey> monkeys) {
        return monkeys.stream().map(Monkey::getInspectionCount).sorted(Comparator.reverseOrder()).limit(2).mapToLong(x -> x).reduce((x, y) -> x * y).orElse(0);
    }
}
