package main.day07;

import main.utils.Day;

import java.util.List;

public class Day07 extends Day<Integer> {

    private final Node root;

    public Day07() {
        List<String> text =
                //                List.of(
                //                        "$ cd /",
                //                        "$ ls",
                //                        "dir a",
                //                        "14848514 b.txt",
                //                        "8504156 c.dat",
                //                        "dir d",
                //                        "$ cd a",
                //                        "$ ls",
                //                        "dir e",
                //                        "29116 f",
                //                        "2557 g",
                //                        "62596 h.lst",
                //                        "$ cd e",
                //                        "$ ls",
                //                        "584 i",
                //                        "$ cd ..",
                //                        "$ cd ..",
                //                        "$ cd d",
                //                        "$ ls",
                //                        "4060174 j",
                //                        "8033020 d.log",
                //                        "5626152 d.ext",
                //                        "7214296 k"
                //                );
                getReader().readAsStringList(7);

        root = new Node(null);
        Node currNode = root;
        for (String s : text) {
            String[] tokens = s.split(" ");
            switch (tokens[0]) {
                case "$":
                    if ("cd".equals(tokens[1])) {
                        currNode = switch (tokens[2]) {
                            case "/" -> root;
                            case ".." -> currNode.getParent();
                            default -> currNode.getChild(tokens[2]);
                        };
                    }
                    break;
                case "dir":
                    currNode.addDir(tokens[1]);
                    break;
                default:
                    currNode.addFile(Integer.parseInt(tokens[0]));
            }
        }
    }

    @Override
    public Integer getSolution1() {
        return solve1(root);
    }

    public int solve1(Node curr) {
        int sum = curr.getDirs().mapToInt(this::solve1).sum();
        if (curr.getSize() < 100000) {
            sum += curr.getSize();
        }
        return sum;
    }

    @Override
    public Integer getSolution2() {
        int needed = 30000000 - (70000000 - root.getSize());
        return solve2(root, needed);
    }

    public int solve2(Node curr, int thresh) {
        int childrenMin = curr.getDirs().mapToInt(n -> solve2(n, thresh)).filter(i -> i > thresh).min().orElse(0);
        return childrenMin > thresh ? childrenMin : curr.getSize();
    }
}
