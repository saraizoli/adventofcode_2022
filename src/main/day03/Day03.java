package main.day03;

import main.utils.Day;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day03 extends Day<Integer> {

    private final List<String> packs;

    public Day03() {
        this.packs =
//                Arrays.asList(
//                        "vJrwpWtwJgWrhcsFMMfFFhFp",
//                        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
//                        "PmmdzqPrVvPwwTWBwg",
//                        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
//                        "ttgJtRGJQctTZtZT",
//                        "CrZsJsPPZsGzwwsLwLmpwMDw");
                getReader().readAsStringList(3);
    }

    @Override
    public Integer getSolution1() {
        int sum = packs.stream()
                .mapToInt(s -> findIntersectionWithSets(List.of(s.substring(0, s.length() / 2), s.substring(s.length() / 2))))
                .sum();
        return sum;
    }

//    naive direct approach
//    private int findDupeWithSets(String s) {
//        Set<Integer> left = s.substring(0, s.length() / 2).chars().boxed().collect(Collectors.toSet());
//        Set<Integer> right = s.substring(s.length() / 2).chars().boxed().collect(Collectors.toSet());
//        left.retainAll(right);
//        Integer dupeAscii = left.stream().findFirst().orElse(0);
//        return (dupeAscii - 65 + 27) % 58;
//    }

    private int findIntersectionWithSets(List<String> strings) {
        Set<Integer> intersection = strings.stream()
                .map(s -> s.chars().boxed().collect(Collectors.toSet()))
                .reduce((set1, set2) -> { set1.retainAll(set2); return set1; })
                .orElse(Collections.emptySet());
        Integer dupeAscii = intersection.stream().findFirst().orElse(0);
        return (dupeAscii - 65 + 27) % 58;
    }

    @Override
    public Integer getSolution2() {
        int groupSize = 3;
        int sum = IntStream.range(0, packs.size() / groupSize)
                .mapToObj(i -> packs.subList(i * groupSize, (i + 1) * groupSize))
                .mapToInt(this::findIntersectionWithSets)
                .sum();
        return sum;
    }

//        System.out.println(('a'-65+27)%58); //1
//        System.out.println(('z'-65+27)%58); //26
//        System.out.println(('A'-65+27)%58); //27
//        System.out.println(('Z'-65+27)%58); //52
}
