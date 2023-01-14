package strings;

import java.util.Hashtable;

/**
 * Author Pierre Schaus
 *
 * We are interested in the Rabin-Karp algorithm.
 * We would like to modify it a bit to determine if a word among a list (all words are of the same length) is present in the text.
 * To do this, you need to modify the Rabin-Karp algorithm which is shown below (page 777 of the book).
 * More precisely, you are asked to modify this class so that it has a constructor of the form:
 * public RabinKarp(String[] pat)
 *
 * Moreover the search function must return the index of the beginning of the first word (among the pat array) found in the text or
 * the size of the text if no word appears in the text.
 *
 * Example: If txt = "Here find interesting exercise for Rabin Karp" and pat={"have", "find", "Karp"}
 * the search function must return 5 because the word "find" present in the text and in the list starts at index 5.
 *
 */
public class RabinKarp {




    private Hashtable<Long, String> hTable = new Hashtable<>();
    private int M; // pattern length
    private long Q; // a large prime
    private int R = 2048; // alphabet size
    private long RM; // R^(M-1) % Q

    public RabinKarp(String[] pat) {


        this.M = pat[0].length();
        Q = 4463;
        RM = 1;
        long patHash;


        for (int i = 1; i <= M - 1; i++)
            RM = (R * RM) % Q;


        for (int i = 0; i < pat.length; i++) {
            patHash = hash(pat[i], M);
            this.hTable.put(patHash, pat[i]);
        }

    }


    public boolean check(int i, String pattern, String txt)
    {
        return (pattern.equals(txt.substring(i, M + i)));
    }


    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }


    public int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);


        if (hTable.containsKey(txtHash) && (check(0, hTable.get(txtHash), txt))) return 0;
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (hTable.containsKey(txtHash)) {
                if (check(i - M + 1, hTable.get(txtHash), txt)) {
                    return i - M + 1;
                }
            }
        }
        return N;
    }
}

/*
Q4.1.4 Générer ine clé a partir d'un hash

Private int hash(Key x){return (x.hashCode() & 0x7ffffff)%M);
Tous les bits mis à 1 sauf le premier, c'est ça que veut dire le fffffff
0x implique la notation hexadécimale

Q4.1.5 Collisions

Separate chaining: Parcours de liste chaînée, si elle est trop grande, on met un red black tree.


Linear probing: se renseigner

Q4.1.6 Load factor

load factor = nombre d'éléments dans la hashmap

Q4.1.9 Linear probing avec marqueur.

 */
/*
Comment est ce qu'on caclule un hash ?

What is the hash of int 42 ? On peut hasher l'entier directement

Représentation binaire de 42 = 101010

Computing the hash of a string of size M
Theta(M) longueur de la string

pattern de taille k --> calcul du nouveau hash ne dépend pas de la taille du pattern, formule en temps constant.


 */