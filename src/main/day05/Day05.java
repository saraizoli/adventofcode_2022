package main.day05;

import main.utils.Day;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day05 extends Day {

    private final List<LinkedList<Character>> initState;
    private final List<int[]> moves;

    public Day05() {
        List<String> rows = getReader().readAsStringList(5);

        int splitInd = rows.indexOf("");
        this.initState = getInitStackState(rows, splitInd);
        this.moves = rows.subList(splitInd + 1, rows.size()).stream()
                .map(s -> s.split(" "))
                .map(sl -> Stream.of(sl[1], sl[3], sl[5]).mapToInt(Integer::valueOf).toArray())
                .collect(Collectors.toList());
    }

    private List<LinkedList<Character>> getInitStackState(List<String> rows, int splitInd) {
        List<String> startStacks = rows.subList(0, splitInd - 1);
        int stackCnt = (rows.get(0).length() + 1) / 4;
        return IntStream.range(0, stackCnt)
                .map(stackNo -> stackNo * 4 + 1)
                .mapToObj(stackInd -> startStacks.stream().map(s -> s.charAt(stackInd)).filter(c -> c != ' ').collect(Collectors.toCollection(LinkedList::new)))
                .collect(Collectors.toList());
    }

    @Override
    public void getSolution1() {
        solve(true);
    }

    @Override
    public void getSolution2() {
        solve(false);
    }

    private void solve(boolean reverse) {
        List<LinkedList<Character>> currState = initState.stream().map(LinkedList::new).collect(Collectors.toList());
        moves.forEach(move -> moveStack(currState, move, reverse));
        currState.stream().map(s -> s.get(0)).forEach(System.out::print);
        System.out.println();
    }

    private void moveStack(List<LinkedList<Character>> stackState, int[] move, boolean reverse) {
        int cnt = move[0];
        int fromInd = move[1] - 1;
        int toInd = move[2] - 1;
        List<Character> fromStack = stackState.get(fromInd);
        List<Character> toMove = fromStack.subList(0, cnt);
        LinkedList<Character> rest = new LinkedList<>(fromStack.subList(cnt, fromStack.size()));
        if (reverse) {
            Collections.reverse(toMove);
        }
        stackState.set(fromInd, rest);
        stackState.get(toInd).addAll(0, toMove);
    }
}
