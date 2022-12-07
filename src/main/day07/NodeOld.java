package main.day07;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

//first solution
public class NodeOld {
    private final boolean isDir;
    private int size;
    private final NodeOld parent;
    private final Map<String, NodeOld> contents;

    public NodeOld(boolean isDir, int size, NodeOld parent, Map<String, NodeOld> contents) {
        this.isDir = isDir;
        this.size = size;
        this.parent = parent;
        this.contents = contents;
    }

    public boolean isDir() {
        return isDir;
    }

    public int getSize() {
        return size;
    }

    private void addSize(int plus){
        size += plus;
        if(parent != null){
            parent.addSize(plus);
        }
    }

    public NodeOld getParent() {
        return parent;
    }

    public NodeOld getChild(String name) {
        return contents.get(name);
    }

    public void addFile(String name, int size) {
        contents.put(name, new NodeOld(false, size, this, null));
        addSize(size);
    }

    public void addDir(String name) {
        contents.put(name, new NodeOld(true, 0, this, new HashMap<>()));
    }

    public Stream<NodeOld> getDirs() {
        return isDir ? contents.values().stream().filter(NodeOld::isDir) : Stream.empty();
    }
}
