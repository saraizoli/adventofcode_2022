package main.day16;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record Node(String name, int rate, Set<WEdge> edges) {
    public Node(String name, int rate) {
        this(name, rate, new HashSet<>());
    }

    public void addEdge(Node n, int dist) {
        edges.add(new WEdge(n, dist));
    }

    //bit hacky to only use name, but convenient
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }
}
