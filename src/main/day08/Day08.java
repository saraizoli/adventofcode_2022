package main.day08;

import main.utils.Day;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day08 extends Day<Integer> {

    private final int[][] f;
    private final int H;
    private final int W;

    public Day08() {
//        f = new int[][]{
//                {'3', '0', '3', '7', '3'},
//                {'2', '5', '5', '1', '2'},
//                {'6', '5', '3', '3', '2'},
//                {'3', '3', '5', '4', '9'},
//                {'3', '5', '3', '9', '0'}
//        };

        List<String> text = getReader().readAsStringList(8);
        f = text.stream().map(s -> s.chars().toArray()).toArray(int[][]::new);
        H = f.length;
        W = f[0].length;
    }

    @Override
    public Integer getSolution1() {
        return solve1Performant();
//        return solve1OverEngineered();
    }

    @Override
    public Integer getSolution2() {
        return solve2Direct();
//        return solve2OverEngineered();
    }

    //n^2 complexity only
    private int solve1Performant() {
        boolean[][] p1 = new boolean[f.length][f[0].length];

        int cnt = 0;
        for (int i = 0; i < W; ++i) {
            int maxSoFar = -1;
            for (int j = 0; j < H; ++j) {
                if (f[j][i] > maxSoFar) {
                    maxSoFar = f[j][i];
                    if (!p1[j][i]) {
                        cnt++;
                        p1[j][i] = true;
                    }
                }
            }
        }
        for (int i = 0; i < W; ++i) {
            int maxSoFar = -1;
            for (int j = H - 1; j >= 0; --j) {
                if (f[j][i] > maxSoFar) {
                    maxSoFar = f[j][i];
                    if (!p1[j][i]) {
                        cnt++;
                        p1[j][i] = true;
                    }
                }
            }
        }
        for (int i = 0; i < W; ++i) {
            int maxSoFar = -1;
            for (int j = 0; j < H; ++j) {
                if (f[i][j] > maxSoFar) {
                    maxSoFar = f[i][j];
                    if (!p1[i][j]) {
                        cnt++;
                        p1[i][j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < W; ++i) {
            int maxSoFar = -1;
            for (int j = H - 1; j >= 0; --j) {
                if (f[i][j] > maxSoFar) {
                    maxSoFar = f[i][j];
                    if (!p1[i][j]) {
                        cnt++;
                        p1[i][j] = true;
                    }
                }
            }
        }

        return cnt;
    }

    private int solve2Direct() {
        long currMax = 0;
        //the perimeter can be dropped, the visibility score is always 0 there
        //makes the inner logic simpler
        for (int i = 1; i < H - 1; i++) {
            for (int j = 1; j < W - 1; j++) {
                long v = getVisibility(i, j);
                currMax = Math.max(currMax, v);
            }
        }
        return (int) currMax;
    }

    private long getVisibility(int i, int j) {
        int curr = f[i][j];
        //count(smaller trees) +1 to add the tree that blocks the view. Since we cut the perimeter off, this works even at the side of the forest
        long vis =
                (IntStream.range(1, i).map(c -> i - c).takeWhile(c -> f[c][j] < curr).count() + 1) *
                        (IntStream.range(i + 1, H - 1).takeWhile(c -> f[c][j] < curr).count() + 1) *
                        (IntStream.range(1, j).map(c -> j - c).takeWhile(c -> f[i][c] < curr).count() + 1) *
                        (IntStream.range(j + 1, W - 1).takeWhile(c -> f[i][c] < curr).count() + 1);
        return vis;
    }


    //not part of the final solution

    //playing around with generic solver:
    //solve1 with this doesn't work because of the trick with cutting off the perimeter,
    //    would need to tweak the ranges inside getCombinedValueInAllDirections and the directionReducerTemplate in the solve2 to make it truly generic

    private int getCombinedValueInAllDirections(int i, int j, Function<Integer, Function<IntStream, Integer>> directionReducerTemplate, BinaryOperator<Integer> combiner) {
        int curr = f[i][j];
        Function<IntStream, Integer> directionReducer = directionReducerTemplate.apply(curr);
        int value =
                Stream.of(
                                IntStream.range(1, i).map(c -> i - c).map(c -> f[c][j]),
                                IntStream.range(i + 1, H - 1).map(c -> f[c][j]),
                                IntStream.range(1, j).map(c -> j - c).map(c -> f[i][c]),
                                IntStream.range(j + 1, W - 1).map(c -> f[i][c]))
                        .map(directionReducer)
                        .reduce(combiner)
                        .orElse(0);
        return value;
    }

    private int solve1OverEngineered() {
        return IntStream.range(1, H - 1)
                .flatMap(i -> IntStream.range(1, W - 1)
                        .map(j -> getCombinedValueInAllDirections(i, j, n -> (s -> s.allMatch(c -> c < n) ? 1 : 0), Math::max)))
                .sum();
    }

    private int solve2OverEngineered() {
        return IntStream.range(1, H - 1)
                .flatMap(i -> IntStream.range(1, W - 1)
                        .map(j -> getCombinedValueInAllDirections(i, j, n -> (s -> (int) (s.takeWhile(c -> c < n).count() + 1)), (x, y) -> x * y)))
                .max().orElse(0);
    }
}
