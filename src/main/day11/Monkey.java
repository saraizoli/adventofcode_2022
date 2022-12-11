package main.day11;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

public class Monkey {
    private final LinkedList<Long> items;

    private final LongFunction<Long> operation;
    private final int testMod;
    private final int ifTrue;
    private final int ifFalse;
    private final List<Monkey> others; //bit iffy ooo but easier method signatures

    private int inspectionCount;

    private static int modProd = 1;

    public Monkey(LinkedList<Long> items, LongFunction<Long> operation, int testMod, int ifTrue, int ifFalse, List<Monkey> others) {
        this.items = items;
        this.operation = operation;
        this.testMod = testMod;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
        this.others = others;
        modProd *= this.testMod;
    }

    public Monkey(List<String> text, List<Monkey> others) {
        this(
                getItems(text.get(0)),
                getOperation(text.get(1)),
                getInt(text.get(2)),
                getInt(text.get(3)),
                getInt(text.get(4)),
                others
        );
    }

    private static LinkedList<Long> getItems(String s) {
        return Arrays.stream(s.replaceAll("\\D", " ").split(" +")).filter(t -> !"".equals(t)).map(Long::parseLong).collect(Collectors.toCollection(LinkedList::new));
    }

    private static LongFunction<Long> getOperation(String s) {
        String[] tokens = s.split(" ");
        String operandToken = tokens[7];
        if ("old".equals(operandToken)) {
            return i -> i * i;
        }
        int operand = Integer.parseInt(operandToken);
        return "*".equals(tokens[6]) ? i -> i * operand : i -> i + operand;
    }

    private static int getInt(String s) {
        return Integer.parseInt(s.replaceAll("\\D", ""));
    }


    public void throwAll(boolean aggressive) {
        int size = items.size();
        inspectionCount += size;
        for (int i = 0; i < size; i++) {
            thro(aggressive);
        }
    }

    private void thro(boolean aggressive) {
        long item = inspect(aggressive);
        int to = item % testMod == 0 ? ifTrue : ifFalse;
        others.get(to).catc(item);
    }

    private long inspect(boolean aggressive) {
        long item = operation.apply(items.removeFirst());
        return aggressive ? item % modProd : item / 3;
    }

    private void catc(long item) {
        items.addLast(item);
    }

    public int getInspectionCount() {
        return inspectionCount;
    }

    public static void reset() {
        modProd = 1; //meeh
    }
}
