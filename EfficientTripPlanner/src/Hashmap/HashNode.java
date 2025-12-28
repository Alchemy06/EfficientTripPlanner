package Hashmap;

public class HashNode<K, V> {

    //I used generics so that I could put a hashmap inside a hashmap to create the adjacency list data structure without needing to recreate the Hashing code with different data types.
    K key;
    V value;

    //This variable is there to see if there was data in a hashtable space previously.
    boolean isDeleted = false;

    //These are getters and setters.
    public boolean isDeleted() {
        return isDeleted;
    }

    //This is used to signal when a node in the hashmap is deleted. A deleted node is not
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    //Constructor
    HashNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}
