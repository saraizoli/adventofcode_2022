package main.day16;


import main.utils.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day16 extends Day<Integer> {

    private final Node reducedRoot;
    private final Set<Node> relevant;
    private final Map<String, Node> reduced;

    public Day16() {
//        List<String> text = getReader().readAsStringList("day16_sample.txt");
        List<String> text = getReader().readAsStringList(16);
        List<String[]> tokens = text.stream().map(s -> s.split("[ =;]")).toList();
        //create map of nodeName -> Node for further handling
        Map<String, Node> nodes = tokens.stream().collect(Collectors.toMap(t -> t[1], t -> new Node(t[1], Integer.parseInt(t[5]))));
        //add edges to each node
        text.forEach(t ->
                Arrays.stream(t.split(" valves? ")[1].split(", "))
                        .forEach(n -> nodes.get(t.substring(6, 8)).addEdge(nodes.get(n), 1)));
        Node root = nodes.get("AA");
        //get Nodes with positive pressure
        relevant = nodes.values().stream().filter(n -> n.rate() > 0).collect(Collectors.toSet());

        //create a reduced graph: points are the relevant nodes and root, edges with weight come from BFS from each relevant Node
        reducedRoot = new Node("AA", root.rate());
        reduced = relevant.stream().collect(Collectors.toMap(Node::name, n -> new Node(n.name(), n.rate()))); //cloning into nodes, edge list will differ
        reduced.put("AA", reducedRoot);
        setReducedDistancesFromNodeWithBFS(root);
        relevant.forEach(this::setReducedDistancesFromNodeWithBFS);
    }

    @Override
    public Integer getSolution1() {
        return wander(reducedRoot, 31, relevant);
    }

    private int wander(Node n, int energy, Set<Node> rest) {
        Set<Node> newRest = new HashSet<>(rest);
        newRest.remove(n);
        int allPressure = n.rate() * (energy - 1);
        if (!newRest.isEmpty()) {
            allPressure += n.edges().stream()
                    .filter(e -> newRest.contains(e.n()))
                    .filter(e -> e.dist() < energy - 1)
                    .mapToInt(e -> wander(e.n(), energy - 1 - e.dist(), newRest)).max().orElse(0);
        }
        return allPressure;
    }

    @Override
    public Integer getSolution2() {
        Set<Set<Node>> subsets = getSubsets(relevant);
        int halfLength = relevant.size() / 2;
        //partition into 2 every possible way, run the algo on the partitions
        return subsets.stream()
                .filter(ss -> ss.size() <= halfLength) //this avoids double running each subset - complement pair
                .mapToInt(ss -> {
                    HashSet<Node> compl = new HashSet<>(relevant);
                    compl.removeAll(ss);
                    return wander(reducedRoot, 27, ss) + wander(reducedRoot, 27, compl);
                }).max().orElse(0);
    }


    public void setReducedDistancesFromNodeWithBFS(Node start) {
        //setup
        Node startReduced = reduced.get(start.name());
        LinkedList<Node> que = new LinkedList<>();
        Map<Node, Integer> visited = new HashMap<>();
        visited.put(start, 0);
        que.addLast(start);

        while (!que.isEmpty()) {
            Node curr = que.removeFirst();
            int dist = visited.get(curr);
            curr.edges().stream()
                    .filter(e -> !visited.containsKey(e.n()))
                    .forEach(e -> {
                        Node n = e.n();
                        visited.put(n, dist + e.dist());
                        que.addLast(n);
                        if (relevant.contains(n)) { //for relevant nodes save distance as edge in reduced graph both ways
                            reduced.get(n.name()).addEdge(startReduced, dist + e.dist());
                            startReduced.addEdge(reduced.get(n.name()), dist + e.dist());
                        }
                    });
        }
    }

    private Set<Set<Node>> getSubsets(Set<Node> set) {
        ArrayList<Node> setAsList = new ArrayList<>(set);
        Set<Set<Node>> allSubsets = new HashSet<>();
        int max = 1 << set.size();

        for (int i = 0; i < max; i++) { // i in binary indexes which elements we want to take out of the orig set in the current iteration
            Set<Node> subset = new HashSet<>();
            for (int j = 0; j < set.size(); j++) {
                if (((i >> j) & 1) == 1) { //byteshifts to check jth byte in i if we want to add the jth element
                    subset.add(setAsList.get(j));
                }
            }
            allSubsets.add(subset);
        }
        return allSubsets;
    }


    //tried to optimize but gave worse results:
    private int wander2(Node n, int energy, Set<Node> rest, int executors, int maxEnergy) {
        Set<Node> newRest = new HashSet<>(rest);
        newRest.remove(n);
        int allPressure = n.rate() * (energy - 1);
        if (!newRest.isEmpty()) {
            allPressure += n.edges().stream()
                    .filter(e -> newRest.contains(e.n()))
                    .mapToInt(e -> {
                        if (e.dist() < energy - 1) {
                            return wander2(e.n(), energy - 1 - e.dist(), newRest, executors, maxEnergy);
                        } else if (executors > 0) {
                            return wander2(reducedRoot, maxEnergy, newRest, executors - 1, maxEnergy);
                        } else {
                            return 0;
                        }
                    }).max().orElse(0);
        }
        return allPressure;
    }
}
