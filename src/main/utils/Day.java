package main.utils;

public abstract class Day {
    InputReader reader;

    public Day() {
        this.reader = new InputReader();
    }

    public InputReader getReader() {
        return reader;
    }

    public abstract void getSolution1();
    public abstract void getSolution2();
}
