package main.day19;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

//m(etal) = ore, c(lay), o(bsidian), g(eode)
public record Resources(int m, int c, int o, int g) {

    public static final Resources NONE = new Resources(0, 0, 0, 0);
    public static final Resources M = new Resources(1, 0, 0, 0);
    public static final Resources C = new Resources(0, 1, 0, 0);
    public static final Resources O = new Resources(0, 0, 1, 0);
    public static final Resources G = new Resources(0, 0, 0, 1);

    public static final Resources[] EACH = new Resources[]{NONE, M, C, O, G};

    public Resources add(Resources other) {
        return new Resources(m + other.m, c + other.c, o + other.o, g + other.g);
    }

    public Resources spend(Resources other) {
        return new Resources(m - other.m, c - other.c, o - other.o, g - other.g);
    }

    public boolean canSpend(Resources other) {
        return m >= other.m && c >= other.c && o >= other.o && g >= other.g;
    }

    public static Resources from(String s) {
        Map<String, Integer> read = Arrays.stream(s.split(" and ")).map(t -> t.split(" ")).collect(Collectors.toMap(u -> u[1], u -> Integer.parseInt(u[0])));
        return new Resources(read.getOrDefault("ore", 0), read.getOrDefault("clay", 0), read.getOrDefault("obsidian", 0), 0);
    }
}
