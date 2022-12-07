package main.day07;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Node {
    private int size;
    private final Node parent;
    private final Map<String, Node> subDirs;

    public Node(Node parent) {
        this.parent = parent;
        this.size = 0;
        this.subDirs = new HashMap<>();
    }

    public int getSize() {
        return size;
    }

    //makes adding files less performant, but no calculation needed after parsing
    private void addSize(int plus){
        size += plus;
        if(parent != null){
            parent.addSize(plus);
        }
    }

    public Node getParent() {
        return parent;
    }

    public Node getChild(String name) {
        return subDirs.get(name);
    }

    public void addFile(int size) {
        addSize(size);
    }

    public void addDir(String name) {
        subDirs.put(name, new Node(this));
    }

    public Stream<Node> getDirs() {
        return subDirs.values().stream();
    }
}
