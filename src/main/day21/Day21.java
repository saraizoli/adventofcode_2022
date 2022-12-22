package main.day21;

import main.utils.Day;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day21 extends Day<Long> {

    private final List<String[]> monkeyTokens;

    public Day21() {
//        List<String> text = List.of(
//                "root: pppw + sjmn",
//                "dbpl: 5",
//                "cczh: sllz + lgvd",
//                "zczc: 2",
//                "ptdq: humn - dvpt",
//                "dvpt: 3",
//                "lfqf: 4",
//                "humn: 5",
//                "ljgn: 2",
//                "sjmn: drzm * dbpl",
//                "sllz: 4",
//                "pppw: cczh / lfqf",
//                "lgvd: ljgn * ptdq",
//                "drzm: hmdt - zczc",
//                "hmdt: 32"
//        );
        List<String> text = getReader().readAsStringList(21);
        monkeyTokens = text.stream().map(s -> s.split(": ")).collect(Collectors.toList());

    }

    @Override
    public Long getSolution1() {
        Map<String, Monkey> monkeys = getMonkeys();
        Monkey resolved = monkeys.get("root").resolve();
        return resolved.v();
    }

    @Override
    public Long getSolution2() {
        Map<String, Monkey> monkeys = getMonkeys();
        monkeys.get("humn").setUnknown(true);
        Monkey reduced = monkeys.get("root").resolve();
        while(!"humn".equals(reduced.name())){
            reduced = reduced.reduce();
        }

        return reduced.v();
    }

    private Map<String, Monkey> getMonkeys() {
        Map<String, Monkey> monkeys = monkeyTokens.stream().map(t -> t[0]).collect(Collectors.toMap(s -> s, Monkey::new));
        for (String[] t : monkeyTokens) {
            String[] vals = t[1].split(" ");
            if (vals.length == 1) {
                monkeys.get(t[0]).setAsLeaf(Long.parseLong(vals[0]));
            } else {
                monkeys.get(t[0]).setAsOp(monkeys.get(vals[0]), monkeys.get(vals[2]), vals[1]);
            }
        }
        return monkeys;
    }
}
