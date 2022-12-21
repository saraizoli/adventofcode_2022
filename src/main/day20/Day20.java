package main.day20;

import main.utils.Day;

import java.util.List;
import java.util.stream.IntStream;

public class Day20 extends Day<Long> {

    private final List<Integer> origList;
    private final int len;
    private final int zeroInd;

    public Day20() {
//        List<String> text = List.of("1", "2", "-3", "3", "-2", "0", "4");
        List<String> text = getReader().readAsStringList(20);

        origList = text.stream().map(Integer::parseInt).toList();
        len = origList.size();
        zeroInd = origList.indexOf(0);
    }

    @Override
    public Long getSolution1() {
        return solve(1, 1);
    }

    @Override
    public Long getSolution2() {
        return solve(811589153, 10);
    }

    private long solve(int mult, int iter) {
        List<Node> list = setup(mult % (len - 1));
        IntStream.range(0, iter).forEach(c -> mix(list));
        Node zero = list.get(zeroInd);
        return ((long) mult) * (zero.rawNext(1000).v() + zero.rawNext(2000).v() + zero.rawNext(3000).v());
    }

    private List<Node> setup(int mult) {
        Node.reset(mult);
        List<Node> list = origList.stream().map(Node::new).toList();
        IntStream.range(0, len).forEach(i -> list.get(i).setNext(list.get((i + 1) % len)));
//        show(list);
        return list;
    }

    private static void mix(List<Node> list) {
        list.forEach(Node::move);
    }

    void show(List<Node> list) {
        Node curr = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(curr + ", ");
            curr = curr.next();
        }
        System.out.println();
    }
}


//works for task 1:
//where ListElem = public record ListElem(int i, boolean v)
//    @Override
//    public Integer getSolution1() {
//        int min = origList.stream().mapToInt(x -> x).min().orElse(0);
//        int offset = min >= 0 ? 0 : ((-1 * min) / (len - 1) + 1) * (len - 1);
//        LinkedList<ListElem> list = origList.stream().map(ListElem::new).collect(Collectors.toCollection(LinkedList::new));
//        int visitedCount = 0;
//        while (visitedCount < len) {
//            ListElem curr = list.removeFirst();
//            if (curr.v()) {
//                list.addLast(curr);
//            } else {
////                System.out.println("moving " + curr.i());
//                visitedCount++;
//                curr = curr.visit();
//                list.add((curr.i() + offset) % (len - 1), curr);
//            }
////            System.out.println(list.stream().map(ListElem::i).toList());
//        }
//        int zeroInd = list.indexOf(new ListElem(0, true));
//        return IntStream.of(1000,2000,3000).map(i -> (zeroInd + i) % len).map(i-> list.get(i).i()).sum();
//    }
