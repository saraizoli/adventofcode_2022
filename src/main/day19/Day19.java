package main.day19;

import main.utils.Day;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day19 extends Day<Integer> {

    private final List<Resources[]> blueprints;


    public Day19() {
//        List<String> text = List.of(
//                "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.",
//                "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian."
//        );
        List<String> text = getReader().readAsStringList(19);

        blueprints = text.stream()
                .map(s -> "0 ore" + s) //adding nocost none robot at the beginning
                .map(s -> s.split("( costs )|\\."))
                .map(t -> Stream.of(t[0], t[1], t[3], t[5], t[7]).map(Resources::from).toArray(Resources[]::new))
                .toList();
    }

    @Override
    public Integer getSolution1() {
        return IntStream.range(0, blueprints.size())
                .parallel()
                .map(i -> (i + 1) * simulateMain(blueprints.get(i), 24))
                .sum();
    }

    @Override
    public Integer getSolution2() {
        return IntStream.range(0, blueprints.size())
                .limit(3)
                .parallel()
                .map(i -> simulateMain(blueprints.get(i), 32))
                .reduce((x, y) -> x * y).orElse(0);

    }

    private int simulateMain(Resources[] costs, int simLen) {
        return simulate(costs, Resources.NONE, Resources.M, simLen);
    }

    private int simulate(Resources[] costs, Resources inv, Resources robots, int simLen) {
//        System.out.println("Simulate called with inv " + inv + " robots " + robots + " simLen " + simLen );
        if (simLen == 0) {
            return inv.g();
        }
        return getBuildOptions(costs, inv, robots)
                .map(i -> simulate(costs, inv.add(robots).spend(costs[i]), robots.add(Resources.EACH[i]), simLen - 1))
                .max().orElse(0);
    }

    private static IntStream getBuildOptions(Resources[] costs, Resources inv, Resources robots) {
        //geode and obsi always prio
        if (inv.canSpend(costs[4])) {
            return IntStream.of(4);
        }
        if (inv.canSpend(costs[3]) && robots.o() < costs[4].o()) {
            return IntStream.of(3);
        }
        IntStream.Builder builder = IntStream.builder();
        //no point building more clay/ore than the max used by any of the robots
        if (inv.canSpend(costs[2]) && robots.c() < costs[3].c()) {
            builder.add(2);
        }
        if (inv.canSpend(costs[1]) && robots.m() < Arrays.stream(costs).mapToInt(Resources::m).max().orElse(0)) {
            builder.add(1);
        }
        builder.add(0);
        return builder.build();
    }
}