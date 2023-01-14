package strings;


import java.util.PriorityQueue;

/**
 * This class is used to construct a Huffman trie from frequencies of letters (in unicode or ASCII).
 * As a reminder, in an Huffman trie nodes are weighted (see the `HuffmanNode` class) by
 * the frequencies of the character (if lead node) or the sum of the frequencies of its children
 * (if internal node).
 * For example, let us assume that we have the following letters with their associated frequencies:
 *  (t, 1), (m, 2), (z, 3), (a, 4), (g, 5)
 *
 *  The the following Huffman trie can be constructed
 *  La construction en arbre garantit qu'on aura jamais le même préfix
 *Left = 0                                          Right = 1
 *
 *                      (_, 15)
 *                         |
 *         (_, 9) -------------------- (_, 6)
 *           |                           |
 *  (a, 4)------(g, 5)        (z, 3)----------(_, 3)
 *  left = 0                                    |
 *                                     (t, 1)------(m, 2)
 *
 * ici a = 00 g = 01 z = 10 t = 110 m = 111
 * In practice you are given an array of frequencies for each of the 256 ASCII code or 65536 unicode characters.
 * The goal is to construct the Huffman trie from this array of frequencies.
 */
public class Huffman {

    /**
     * Constructs an Huffman trie for the frequencies of the characters given in arguments.
     * The character are implicitely defined by the `freq` array (ranging from 0 to freq.length -1)
     *
     * @param freq the frequencies of the characters
     */
    public static HuffmanNode buildTrie(int [] freq) {
        /*
         fill the pq with the characters (nodes)
         --> weird for loop

         take the 2 smallest
         reinsert them in the pq by adding their freq

         return the smallest element of the pq
         */
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for(char c = 0; c < freq.length; c++){

            // Techniquement, il faut ajouter une condition if(freq[c] > 0) prcq c'est sans intérêt d'ajouter un bail vide
            HuffmanNode node = new HuffmanNode(c, freq[c], null, null);
            queue.add(node);
        }

        while(queue.size() > 1){
            HuffmanNode first_smallest = queue.remove();
            HuffmanNode second_smallest = queue.remove();
            HuffmanNode parent = new HuffmanNode('\0', first_smallest.getFrequency() + second_smallest.getFrequency(), first_smallest, second_smallest);
            queue.add(parent);
        }
        return queue.remove();
    }
}

class HuffmanNode implements Comparable<HuffmanNode> {

    private final int ch;
    private final int freq;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(int ch, int freq, HuffmanNode left, HuffmanNode right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public HuffmanNode getLeft() {
        return this.left;
    }

    @SuppressWarnings("unchecked")
    public void setLeft(HuffmanNode node) {
        this.left = node;
    }

    public HuffmanNode getRight() {
        return this.right;
    }

    @SuppressWarnings("unchecked")
    public void setRight(HuffmanNode node) {
        this.right = node;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.freq - node.freq;
    }

    public int getFrequency() {
        return this.freq;
    }

    public int getChar() {
        return this.ch;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}

/*

Q5.1.4

Une heap est une implementation d'une priority queue.

Q5.1.10  --> Huffman

On prend les éléments de plus petites fréquences et on les introduits.

Complexité de Huffman avec des listes non triées: O(n²)
Complexité liste triées: O(n²)
Complexité de Huffman avec des heap: O(nlog(n)) au total !



 */
