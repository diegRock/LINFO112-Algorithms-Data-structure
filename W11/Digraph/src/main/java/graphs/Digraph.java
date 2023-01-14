package graphs;


/**
 *Implement the Digraph.java interface in the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Adjacency list in Java
 * class Graph{
 *     private int numVertices;
 *     private LinkedList<integer> adjLists[];
 * }
 * The type of LinkedList is determined by what data you want to store in it.
 * For a labeled graph, you could store a dictionary instead of an Integer
 *  * On peut stocker un dictionnaire dans notre liste d'adjacence si on à un graphe "labeled"
 * It is faster to use adjacency lists for graphs having less numbers of edges.
 */

public class Digraph {

    int V;
    int E;
    //ArrayList<ArrayList<Integer>> adjacency;
    HashMap<Integer, HashSet<Integer>> adja;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        //adjacency = new ArrayList<ArrayList<Integer>>(V);
        adja = new HashMap<Integer, HashSet<Integer>>(V);
        for (int i = 0; i < V; i++) {
            //adjacency.add(new ArrayList<>());
            adja.put(i, new HashSet<Integer>());
        }

    }

    /**
     * The number of vertices
     */
    public int V() {
         return V;
    }

    /**
     * The number of edges
     */
    public int E() {
         return E;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        adja.get(v).add(w);

        //adja.get(w).add(v);
        //adjacency.get(v).add(w);
        //adjacency.get(w).add(v);
        E++;
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
         return adja.get(v);
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        Digraph reversed = new Digraph(V);
        for (int i = 0; i < V; i++) {
            for(int w : adj(i)){
                reversed.addEdge(w, i);
            }
        }
        return reversed;
    }

}

/*
    HashSet: A HashSet is a Set that is implemented using a hash table. It offers constant-time performance for the basic operations (add, remove, contains, and size), assuming the hash function disperses the elements properly among the buckets. HashSets are not synchronized, so they are not thread-safe. If multiple threads access a hash set concurrently, and at least one of the threads modifies the set, it should be synchronized externally.

    TreeSet: A TreeSet is a Set that is implemented using a tree data structure. It provides an ordered Set, meaning that the elements in the Set are sorted according to their natural ordering, or according to a Comparator provided at the time the set is created. TreeSets are also not synchronized, so they are not thread-safe. If multiple threads access a tree set concurrently, and at least one of the threads modifies the set, it should be synchronized externally.

    LinkedHashSet: A LinkedHashSet is a Set that is implemented using a hash table with a linked list running through it. It maintains a doubly-linked list running through all of its entries, in the order in which they were added. This allows the LinkedHashSet to maintain an order for its elements, which is useful if you need to maintain the insertion order of the elements. Like HashSet and TreeSet, LinkedHashSet is not synchronized, so it is not thread-safe. If multiple threads access a linked hash set concurrently, and at least one of the threads modifies the set, it should be synchronized externally.
   */
