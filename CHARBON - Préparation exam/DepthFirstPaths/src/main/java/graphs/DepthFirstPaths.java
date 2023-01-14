package graphs;

import java.util.*;

/**
 * Consider this class, DepthFirstPaths, which computes the paths to any connected node
 * from a source node s in an undirected graph using a DFS path.
 * <p>
 * The Graph class is already implemented and here it is:
 * <p>
 * public class Graph {
 * // @return the number of vertices
 * public int V() { }
 * <p>
 * // @return the number of edges
 * public int E() { }
 * <p>
 * // Add edge v-w to this graph
 * public void addEdge(int v, int w) { }
 * <p>
 * // @return the vertices adjacent to v
 * public Iterable<Integer> adj(int v) { }
 * <p>
 * // @return a string representation
 * public String toString() { }
 * }
 * <p>
 * <p>
 * You are asked to implement all the TODOs of the DepthFirstPaths class.
 */
public class DepthFirstPaths {
    private boolean[] marked; // marked[v] = is there an s-v path?
    private int[] edgeTo;     // edgeTo[v] = last edge on s-v path
    private final int s;

    /**
     * Computes a path between s and every other vertex in graph G
     *
     * @param G the graph
     * @param s the source vertex
     */
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // Depth first search from v
    private void dfs(Graph G, int v) {
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        marked[v] = true;

        while(!stack.isEmpty()){
            int current = stack.pop();
            for(int i : G.adj(current)){
                if(!marked[i]){
                    marked[i] = true;
                    edgeTo[i] = current;
                    stack.push(i);
                }
            }
        }
    }

    /**
     * Is there a path between the source s and vertex v?
     *
     * @param v the vertex
     * @return true if there is a path, false otherwise
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns a path between the source vertex s and vertex v, or
     * null if no such path
     *
     * @param v the vertex
     * @return the sequence of vertices on a path between the source vertex
     * s and vertex v, as an Iterable
     */
    public Iterable<Integer> pathTo(int v) {
        if(!hasPathTo(v)) return null;
        int current = v;

        LinkedList<Integer> path = new LinkedList<>();
        while(current != s){
            path.add(current);
            current = edgeTo[current];
        }
        //if(path.isEmpty()) return null;
        path.add(s);
        Collections.reverse(path);
        return path;
    }

    static class Graph {

        private List<Integer>[] edges;

        public Graph(int nbNodes)
        {
            this.edges = (ArrayList<Integer>[]) new ArrayList[nbNodes];
            for (int i = 0;i < edges.length;i++)
            {
                edges[i] = new ArrayList<>();
            }
        }

        /**
         * @return the number of vertices
         */
        public int V() {
            return edges.length;
        }

        /**
         * @return the number of edges
         */
        public int E() {
            int count = 0;
            for (List<Integer> bag : edges) {
                count += bag.size();
            }

            return count/2;
        }

        /**
         * Add edge v-w to this graph
         */
        public void addEdge(int v, int w) {
            edges[v].add(w);
            edges[w].add(v);
        }

        /**
         * @return the vertices adjacent to v
         */
        public Iterable<Integer> adj(int v) {
            return edges[v];
        }
    }

    public static void main(String[] args) {
        DepthFirstPaths.Graph graph = new DepthFirstPaths.Graph(7);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);
        graph.addEdge(6, 5);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);

        System.out.println(dfs.hasPathTo(6));
        System.out.println(dfs.pathTo(4).toString());


    }

}
