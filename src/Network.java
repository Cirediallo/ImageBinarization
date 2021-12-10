import java.util.ArrayList;

public class Network extends Graph {
    private Integer source, sink;

    /**
     * Réseau à `n` sommets en plus de la source et du puit
     * 
     * @param n Nombre de sommets intermédiaires entre
     */
    public Network(Integer n) {
        super(n + 2);
        source = n;
        sink = n + 1;
    }

    public Network(Network n) {
        super(n);
        source = n.source;
        sink = n.sink;
    }

    public Integer source() {
        return source;
    }

    public Integer sink() {
        return sink;
    }

    /**
     * Deepfirst research to find a path
     * 
     * @param path    A path to complete
     * @param visited A functional array telling the visitedness of each node
     * @param from    The origin of the research
     * @param to      The desired destination
     * @return A path from `from` to `to`, if it exists
     */
    private ArrayList<Edge> findPath(ArrayList<Edge> path, ArrayList<Boolean> visited, Integer from, Integer to) {
        visited.set(from, true);
        for (Edge succession : edges.get(from)) {
            if (succession.flow() == succession.capacity())
                continue;
            Integer successor = succession.destination();
            if (successor.intValue() == to.intValue()) {
                path.add(succession);
                return path;
            }
            if (!visited.get(succession.destination())) {
                ArrayList<Edge> newPath = new ArrayList<>(path);
                newPath.add(succession);
                ArrayList<Edge> attempt = findPath(newPath, visited, successor, to);
                if (attempt != null)
                    return attempt;
            }
        }
        return null;
    }

    /**
     * @return A simple path from source to sink
     */
    public ArrayList<Edge> path() {
        ArrayList<Boolean> visited = new ArrayList<Boolean>(nodes.size());
        for (Integer i : nodes) {
            visited.add(false);
        }
        return findPath(new ArrayList<Edge>(), visited, source, sink);
    }
}
