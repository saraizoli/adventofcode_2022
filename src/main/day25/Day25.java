package main.day25;

import main.utils.Day;

import java.util.List;

public class Day25 extends Day<String> {
    private final List<String> snafus;

    private final int[] snafuEncode = new int[62];
    private final char[] intEncode = new char[5];

    {
        snafuEncode['0'] = 0;
        snafuEncode['1'] = 1;
        snafuEncode['2'] = 2;
        snafuEncode['-'] = -1;
        snafuEncode['='] = -2;

        intEncode[0] = '0';
        intEncode[1] = '1';
        intEncode[2] = '2';
        intEncode[3] = '=';
        intEncode[4] = '-';
    }


    public Day25() {
        snafus = getReader().readAsStringList(25);
//        snafus = List.of(
//                "1=-0-2",
//                "12111",
//                "2=0=",
//                "21",
//                "2=01",
//                "111",
//                "20012",
//                "112",
//                "1=-1=",
//                "1-12",
//                "12",
//                "1=",
//                "122"
//        );
    }

    @Override
    public String getSolution1() {
        long sum = snafus.stream().mapToLong(this::snafuToLong).sum();
        return longToSnafu(sum);
    }

    private long snafuToLong(String s) {
        return s.chars().mapToLong(c -> snafuEncode[c]).reduce((x, y) -> 5 * x + y).orElse(0);
    }

    private String longToSnafu(long i) {
        StringBuilder sb = new StringBuilder();
        while (i > 0) {
            int dig = (int) (i % 5);
            int carry = dig / 3;
            sb.insert(0, intEncode[dig]);
            i = i / 5 + carry;
        }
        return sb.toString();
    }

    @Override
    public String getSolution2() {
        return "Merry Christmas!";
    }
}
