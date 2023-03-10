package strings;

public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    // Please do not add/remove variables/modify their visibility.
    protected int n;           // number of key-value pairs in the symbol table
    protected int m;           // size of linear probing table
    protected Key[] keys;      // the keys
    protected Value[] vals;    // the values


    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
    }

    public int size() {
        return n;
    }

    public int capacity() {
        return m;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    protected int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /**
     * resizes the hash table to the given capacity by re-hashing all of the keys
     */
    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> resizedHT = new LinearProbingHashST<>(capacity);
        for (int i = 0; i < m; i++) {
            if(keys[i] != null){
                resizedHT.put(keys[i], vals[i]);
            }
        }
        keys = resizedHT.keys;
        vals = resizedHT.vals;
        m = resizedHT.m;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * The load factor should never exceed 50% so make sure to resize correctly
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        // Because we don't want to exceed 50%, we need to resize the HT whenever we have more than 50% of the space occupied.
        if(n >= m / 2) resize(capacity()*2);
        if(key == null) throw new IllegalArgumentException();

        // Le code ci dessous peut aussi se r??-??crire:
        //current = hash(key);
        //while(keys[current] != null){
        //    if(keys[current].equals(key)){
        //        vals[current] = val;
        //        return;
        //    }
        //    current = (current + 1) % m
        //}

        int current;
        for (current = hash(key); keys[current] != null; current = (current + 1) % m){
            if(keys[current].equals(key)){
                vals[current] = val;
                return;
            }
        }

        // En fait ce sont les index du tableau qui sont les hash.

        keys[current] = key;
        vals[current] = val;
        n++;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key
     * @return the value associated with {@code key};
     * {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if(key == null) throw new IllegalArgumentException();
        int current = hash(key);
        while(keys[current] != null){
            if(keys[current].equals(key)){
                return vals[current];
            }
            current = (current + 1) % m;
        }
        return null;
    }

    /**
     * Returns all keys in this symbol table as an array
     */
    public Object[] keys() {
        Object[] exportKeys = new Object[n];
        int j = 0;
        for (int i = 0; i < m; i++)
            if (keys[i] != null) exportKeys[j++] = keys[i];
        return exportKeys;
    }

}

/*

Q4.2.1 (Hash of Long and Double)

En java, >>> permet de d??placer de x bits vers la droite avec une certaine particularit??.
Si on utilise >>>, on va recopier le premier bit x fois par exemple : bits >>> 32, si le
premier bit de bits c'est 1 on va avoir 32 fois 1 et inversement si c'est 0.

Ici on va ??tablir un xor entre les 64 bits de bits et les 32 derniers bits de bits.
Les 32 premier bits seront remplac??s par le premier bit et on aura donc un hash plus performant
Puisque le int va tronquer les 64 bits en 32 bits, c'est int??ressant pour avoir une diversit?? de hash
qui ??vitera la collison.

A double in Java is represented in 64 bits as (???1)s??1,m??2(e???1023). The first bit s is the sign,
the next 11 bits represent the exponent in binary form and the last 52 bits represent
the mantissa (decimal part) in binary form. Do a positive decimal number and its opposite get different hash functions?

Q4.2.2 (Hash and casting of integers)

int (32 bits)   long (64 bits)

float (repr??sentation d??cimale 32 bits) double (64 bits)

Le hash d'un int et le hash d'un double, n'auront jamais
le m??me hash car ils sont des repr??sentations diff??rents.

Le hash d'un int et d'un long auront le m??me hash si le nombre est positif
mais puisqu'on utilise la notation ?? compl??ment ?? deux, le hash diff??rera
si le nombre est n??gatif parce que la notation n'a rien ?? voir. On inverse
tous les bits et on fait +1.

Q4.2.3 (Hash of String: the choice of M and R constants)

Si R est un multiple de M, le modulo ?? la fin va toujours nous
donner 0. Ce n'est donc pas int??ressant.
Le hash va ??tre un nombre qui est associ?? ?? un string dans ce cas
ci. Donc si on ?? "Bonjour" et que son hash vaut 589403, on va l'introduire
dans une hastable {589403: "Bonjour"} et ??a va nous permettre de r??cuperer
l'information en O(1).
On veut que R soit un nombre premier pour ??viter le plus possible
les collisions. Il n'y a que le dernier caract??re qui est pris en compte.


Puisque 256 est un nombre pair, il y a + de chances que mon % valent 0.

R = 2*n et M = 2^k.

Le probl??me est que, si R est un nombre pair, on aura plus que les k derniers bits
qui seront significatifs. (Pas sur de ??a du tout)

On voit bien que tous les premiers termes ne seront pas pris en compte.
On a beaucoup plus de chance d'avoir notre % M qui vaut 0.

Et au plus notre nombre pair est grand, au plus on ?? de chances de tomber sur 0.

Plusieurs atouts pour 31:
 - il n'est pas un multiple de 2

Q4.2.4 (Design of Hash function for Vehicules)

11 chiffres (max = 9) et 6 lettres (max = 26)

La meilleure fonction de hasahage, c'est celle qui va ne cr??er aucune collision
entre 0 et le nombre possible de plaque de v??hicule.

X = hash des chiffres, il sufft de le lire comme un nombre
Y = hash des lettres  Somme allant de i = 0 jusque 5 de (26)????????*Yi
Final hash = X*26???+Y

Exo registre national:

Il faut un tableau de 10???? qui est ??quivalent ?? 2????? donc ??a ne va pas c'est trop gros !
Plus que le plus grand int repr??sent??.

Priority Queue --> Se renseigner
Binary Heap --> Se renseigner

Huffman Tree:

Algo qui a un encodage optimal : les bits associ?? ?? chaque lettre sont associ??es ?? un arbre binaire
l'encodage d'une lettre ne pourra jamais ??tre le pr??fixe d'une autre lettre (pr??fixe : 0111 pour A et 011101 pour Z, ils ont le m??me pr??ficxe)

Lorsqu'on va a gauche, on met 0 et lorsqu'on va ?? droite on met 1. Les lettres les plus utilis??es sont au plus pr??t de la racine

La cl?? de cryptage doit ??tre dans le fichier zip !
Dans un fichier zip, on ?? une cl?? qui permet de d??zipper le fichier




 */
