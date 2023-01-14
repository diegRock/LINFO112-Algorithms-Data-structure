package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Consider this class, BreadthFirstShortestPaths, which computes the shortest path between
 * multiple node sources and any node in an undirected graph using a BFS path.
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
 * You are asked to implement all the TODOs of the BreadthFirstShortestPaths class.
 */
public class BreadthFirstShortestPaths {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // marked[v] = is there an s-v path
    private int[] distTo;     // distTo[v] = number of edges shortest s-v path

    /**
     * Computes the shortest path between any/home/jabier/Desktop/Algo Dur/W12/Bubbles/pom.xml
     * one of the sources and very other vertex
     *
     * @param G       the graph
     * @param sources the source vertices
     */
    public BreadthFirstShortestPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        bfs(G, sources);
    }

    // Breadth-first search from multiple sources
    private void bfs(Graph G, Iterable<Integer> sources) {
        // Keeps track of the vertices to process
        LinkedList<Integer> queue = new LinkedList<>();
        for(int source: sources){
            marked[source] = true;
            queue.add(source);
            distTo[source] = 0;
        }

        while (!queue.isEmpty()){
            int v = queue.remove();
            for (int i : G.adj(v)){
                if(!marked[i]){
                    // Je vais dire au deux noeud (par ex) adjacents à v qu'ils précèdent v.
                    // J'associe à leurs index, la valeur v, ce qui veut dire que v est le noeud qui les précèdent
                    //    A
                    //   / \
                    //  B   C (le vertex qui le précède est bien A)
                    // Ici A est égal à v, et B et C seront les i.

                    // Attention, ici je comprend pas trop ce qu'il fait du coup mais j'imagine que c'est la même idée que mes commentaires

                    distTo[i] = distTo[v] + 1; // + 1 prcq on l'initialise à 0.
                    marked[i] = true;
                    queue.add(i); // Je l'ajoute à la queue pour qu'il puisse se faire process plus tard.
                }
            }
        }



    }

    /**
     * Is there a path between (at least one of) the sources and vertex v?
     *
     * @param v the vertex
     * @return true if there is a path, and false otherwise
     */
    public boolean hasPathTo(int v) {
         return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path
     * between one of the sources and vertex v?
     *
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        return distTo[v];
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
}
