package Hashmap;

public interface IHash<K, V> {
    void add(K Key, V Value);
    void delete(K Key);
    String item(K Key);
    boolean contains(K Key);
    int length();
    boolean isEmpty();
}
