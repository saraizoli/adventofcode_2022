package main.day13;

import main.utils.Day;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day13 extends Day<Integer> {

    private final List<Node> signals;

    public Day13() {
        List<String> text =
//                List.of(
//                        "[1,1,3,1,1]",
//                        "[1,1,5,1,1]",
//                        "",
//                        "[[1],[2,3,4]]",
//                        "[[1],4]",
//                        "",
//                        "[9]",
//                        "[[8,7,6]]",
//                        "",
//                        "[[4,4],4,4]",
//                        "[[4,4],4,4,4]",
//                        "",
//                        "[7,7,7,7]",
//                        "[7,7,7]",
//                        "",
//                        "[]",
//                        "[3]",
//                        "",
//                        "[[[]]]",
//                        "[[]]",
//                        "",
//                        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
//                        "[1,[2,[3,[4,[5,6,0]]]],8,9]");
                getReader().readAsStringList(13);
        signals = text.stream().filter(s -> !"".equals(s)).map(Node::from).collect(Collectors.toList());
    }

    @Override
    public Integer getSolution1() {
        return IntStream.range(0, signals.size() / 2)
                .filter(i -> 0 > signals.get(i * 2).compareTo(signals.get(1 + i * 2)))
                .map(i -> i + 1).sum();
    }

    @Override
    public Integer getSolution2() {
        String n2 = "[[2]]";
        String n6 = "[[6]]";
        List<String> sortedStrings = Stream.concat(signals.stream(), Stream.of(Node.from(n2), Node.from(n6))).sorted().map(Node::toString).toList();
        return (1 + sortedStrings.indexOf(n2)) * (1 + sortedStrings.indexOf(n6));
    }
}
