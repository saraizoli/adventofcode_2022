package main.day13;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
    private final int val;
    private final Node parent;
    private final List<Node> children;

    public Node(Node parent, int val, List<Node> children) {
        this.parent = parent;
        this.val = val;
        this.children = children;
    }

    public static Node from(String s) {
        Node curr = list(null);
        s = s.substring(1, s.length() - 1) + ",";
        int readInt = -1; // enable 0 to be an element
        for (char c : s.toCharArray()) {
            switch (c) {
                case '[' -> curr = curr.addList();
                case ']' -> {
                    if (readInt >= 0) curr.addLeaf(readInt);
                    readInt = -1;
                    curr = curr.parent;
                }
                case ',' -> {
                    if (readInt >= 0) curr.addLeaf(readInt);
                    readInt = -1;
                }
                default -> readInt = Math.max(0, readInt) * 10 + c - '0'; //handle multi digit ints
            }
        }
        return curr;
    }


    public static Node list(Node parent) {
        return new Node(parent, -1, new ArrayList<>());
    }

    public static Node leaf(Node parent, int val) {
        return new Node(parent, val, null);
    }

    public Node addList() { //returns the new list
        Node list = list(this);
        this.children.add(list);
        return list;
    }

    public Node addLeaf(int v) { //returns the list we are adding to, NOT the new leaf. Not nice but useful discrepancy
        Node leaf = leaf(this, v);
        this.children.add(leaf);
        return this;
    }

    public Node getRestChildren() {
        return new Node(null, val, children.subList(1, children.size()));
    }

    @Override
    public int compareTo(Node other) {
        if (children == null && other.children == null) { //2 ints
            return val - other.val;
        }
        if (children == null) { //int vs list
            return list(null).addLeaf(val).compareTo(other);
        }
        if (other.children == null) { //list vs int
            return this.compareTo(list(null).addLeaf(other.val));
        }
        if (children.isEmpty() || other.children.isEmpty()) {
            return children.size() - other.children.size();
        }
        int comp = children.get(0).compareTo(other.children.get(0));
        return comp != 0 ? comp : getRestChildren().compareTo(other.getRestChildren());
    }

    @Override
    public String toString() {
        return children == null ? val + "" : children.toString();
    }
}
