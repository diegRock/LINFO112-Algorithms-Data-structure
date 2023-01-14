package searching;

/**
 * With the given partial implementation of LinearProbingHashST, we ask you to
 * implement the resize, get and put methods
 * You are not allowed to use already existing classes and methods from Java
 */
public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    // Please do not add/remove variables/modify their visibility.
    protected int size;           // number of key-value pairs in the symbol table
    protected int capacity;           // size of linear probing table
    protected Key[] keys;      // the keys
    protected Value[] vals;    // the values


    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the initial capacity
     */
    public LinearProbingHashST(int capacity) {
        this.capacity = capacity;
        size = 0;
        keys = (Key[])   new Object[this.capacity];
        vals = (Value[]) new Object[this.capacity];
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size;
    }

    /**
     * Returns the current capacity of the table
     *
     * @return the current capacity of the table
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    protected int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    /**
     * TODO
     * Resizes the hash table to the given capacity by re-hashing all of the keys
     *
     * @param capacity the capacity
     */
    protected void resize(int capacity) {
        LinearProbingHashST<Key, Value> tmp;
        tmp = new LinearProbingHashST<>(capacity);
        for (int i = 0; i < this.capacity; i++) {
            if (keys[i] != null) tmp.put(keys[i], vals[i]);
        }
        this.keys = tmp.keys;
        this.vals = tmp.vals;
        this.capacity = tmp.capacity;
    }

    /**
     * TODO
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * The load factor should never exceed 50% so make sure to resize correctly
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (size >= capacity/2) resize(capacity*2);
        int index = hash(key);
        int i = 0;
        for (i = index; keys[i] != null; i = (i+1)%capacity) {
            if (key.equals(keys[i])) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        size++;
    }

    /**
     * TODO
     * Returns the value associated with the specified key.
     * @param key the key
     * @return the value associated with {@code key};
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("key s null");
        int index = hash(key);
        int i = 0;
        for (i = index; keys[i] != null; i = (i+1)%capacity) {
            if (keys[i].equals(key)) return vals[i];
        } return null;
    }

    /**
     * Returns all keys in this symbol table
     */
    public Object[] keys() {
        Object[] exportKeys = new Object[size];
        int j = 0;
        for (int i = 0; i < capacity; i++)
            if (keys[i] != null) exportKeys[j++] = keys[i];
        return exportKeys;
    }

}