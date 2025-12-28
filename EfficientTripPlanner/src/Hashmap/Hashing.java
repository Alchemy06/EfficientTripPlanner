package Hashmap;

import java.util.ArrayList;
import java.lang.*;

public class Hashing<K, V> implements IHash<K, V> {
    private int maxSize;
    private int used = 0;

//The array that stores the hashmap
    private HashNode<K, V>[] nodeList;
//This is the constructor which assigns in the max size and redefines the array that holds the hashmap.
    public Hashing(int maxSize)
    {
        if (maxSize <= 0)
        {
            throw new IllegalArgumentException();
        }
        this.maxSize = maxSize;
        nodeList = new HashNode[maxSize];
    }


//This is the hashing algorithm that is used to generate the location in the hashmap.
    public int genHashCode(K key)
    {
        return key.hashCode()%maxSize;
    }

//Checks if hashmap is full.
    public boolean isFull() {
        return used == maxSize;
    }
//Checks if hashtable is empty
    @Override
    public boolean isEmpty() {
        return used == 0;
    }

    @Override
    public void add(K Key, V Value) {
        //Function adds a node to the hashmap.
        //Checks if the hashtable is full before it adds anything to it.
        if (isFull()) {
            throw new UnsupportedOperationException("Hashtable is full");
        }
        //If the hashtable is not full a new hash node is created with the parameters of this function.
        HashNode<K, V> current = new HashNode<>(Key, Value);

        //Uses the hashing algorithm to find out where to store the Hash node.
        int pos = genHashCode(Key);

        //This is the collision handler in case of 2 nodes ending up in the same spot after the hashing algorithm is applied.
        //The program just finds the next available spot in the hashmap and stores the node there.
        while (nodeList[pos] != null) {
            if (nodeList[pos].isDeleted()) {
                nodeList[pos] = current;
                used++;
                break;
            } else {
                pos++;
                pos = pos % maxSize;
            }
        }
        if (nodeList[pos] == null) {
            nodeList[pos] = current;

            used++;
        }

    }

    //This function deletes the node with corresponds to the key given by marking the node as deleted.
    //It does not immediately remove from the hashmap but rather tells the system that the hashcode can be treated as an empty space and overwritten.
    //I have implemented it this way because to avoid problems in with the item function as if the item function.
    //The item function
    @Override
    public void delete(K Key) {
        if (contains(Key)) {
            int IndexPos = genHashCode(Key);
            while (!nodeList[IndexPos].getKey().equals(Key)) {
                if(nodeList[IndexPos].isDeleted)
                {
                    throw new IllegalArgumentException("Key does not exist");
                }
                IndexPos++;
                IndexPos = IndexPos % maxSize;
            }
            nodeList[IndexPos].setDeleted(true);
            used--;


        } else {
            throw new IllegalArgumentException("Key does not exist");
        }
    }

    //This is used to find value using its key in the hashmap. This particular function outputs the value as a string.
    @Override
    public String item(K Key) {
        //Calculates the hashcode and therefore where it is stored.
        int IndexPos = genHashCode(Key);
        int posChecked = 0;
        //In case the key is not in the list. It will throw an exception.
        if (nodeList[IndexPos] ==null)
        {
            throw new IllegalArgumentException("Key not in list");
        }

        //This is used when a collision has occurred when this node was being added to th hashmap. It checks the next slot
        //in the hashmap until it finds the matching key, or it has checked all the slots.
        //If the key in not found an exception is thrown.
        while (!nodeList[IndexPos].getKey().equals(Key)) {

            if (posChecked == maxSize) {
                throw new IllegalArgumentException("Key not in list");
            }
            if (nodeList[IndexPos].isDeleted())
            {
                throw new IllegalArgumentException("Key not in list");
            }
            IndexPos++;
            IndexPos = IndexPos % maxSize;
            posChecked++;
            if (nodeList[IndexPos] == null) {
                throw new IllegalArgumentException("Key not in list");
            }
        }

        HashNode current = nodeList[IndexPos];
        return (String) current.getValue();
    }

    //This is used to find value using its key in the hashmap. This particular function outputs the value as an integer.
    //itemTraversal is used to output the traversal cost of an edge.
    public Integer itemTraversal(K Key) {
        //Calculates the hashcode and therefore where it is stored.
        int IndexPos = genHashCode(Key);
        int posChecked = 0;
        //In case the key is not in the list. It will throw an exception.
        if (nodeList[IndexPos] == null)
        {
            return null;
        }
        //This is used when a collision has occurred when this node was being added to th hashmap. It checks the next slot
        //in the hashmap until it finds the matching key, or it has checked all the slots.
        //If the key in not found an exception is thrown.
        while (!nodeList[IndexPos].getKey().equals(Key)) {

            if (posChecked == maxSize) {
                return null;
            }
            if (nodeList[IndexPos].isDeleted())
            {
                return null;
            }
            IndexPos++;
            IndexPos = IndexPos % maxSize;
            posChecked++;
            if (nodeList[IndexPos] == null) {
                return null;
            }
        }

        HashNode current = nodeList[IndexPos];

        return (int) current.getValue();
    }

    //This is used to find value using its key in the hashmap. This particular function outputs the value as an integer.
    //This is used when a hashmap is the value. The hashmap is outputted. This is used in the adjacency list to store edges.
    //and to also edit the edges and add or remove them.
    public Hashing<K, V> itemHash(K Key) {
        //Calculates the hashcode and therefore where it is stored.
        int IndexPos = genHashCode(Key);
        int posChecked = 0;
        //In case the key is not in the list. It will throw an exception.
        if (nodeList[IndexPos] ==null)
        {
            throw new IllegalArgumentException("Key not in list");
        }

        //This is used when a collision has occurred when this node was being added to th hashmap. It checks the next slot
        //in the hashmap until it finds the matching key, or it has checked all the slots.
        //If the key in not found an exception is thrown.

        while (!nodeList[IndexPos].getKey().equals(Key)) {

            if (posChecked == maxSize) {
                throw new IllegalArgumentException("Key not in list");
            }
            if (nodeList[IndexPos].isDeleted())
            {
                throw new IllegalArgumentException("Key not in list");
            }
            IndexPos++;
            IndexPos = IndexPos % maxSize;
            posChecked++;
            if (nodeList[IndexPos] == null) {
                throw new IllegalArgumentException("Key not in list");
            }
        }

        HashNode current = nodeList[IndexPos];
        return (Hashing<K, V>) current.getValue();
    }


    //This is used to check whether a particular key value pair is in the hashmap by checking whether the key is there.
    @Override
    public boolean contains(K Key) {
        //Calculates the hashcode and therefore where it is stored.
        int IndexPos = genHashCode(Key);
        int posChecked = 0;
        //In case the key is not in the list. It will throw an exception.
        if (nodeList[IndexPos] ==null)
        {
            return false;
        }
        if (nodeList[IndexPos].isDeleted())
        {
            return false;
        }

        //This is used when a collision has occurred when this node was being added to th hashmap. It checks the next slot
        //in the hashmap until it finds the matching key, or it has checked all the slots.
        //If the key in not found an exception is thrown.

        while (!nodeList[IndexPos].getKey().equals(Key)) {
            if (nodeList[IndexPos].isDeleted())
            {
                return false;
            }
            if (posChecked == maxSize) {
                return false;
            }

            IndexPos++;
            IndexPos = IndexPos%maxSize;
            posChecked++;
            if (nodeList[IndexPos] == null) {
                return false;

            }
        }

        return true;
    }

    //This outputs an array of all the keys that are in the hashmap. This is used in the adjacency list
    public int[] traverseVertices()
    {
        ArrayList<Integer> nodes = new ArrayList<Integer>();
        for(int i = 0; i<maxSize; i++)
        {
            if (nodeList[i] != null)
            {
                nodes.add((Integer) nodeList[i].getKey());
            }
        }
        int[] nodeInt = new int[nodes.size()];
        for(int i = 0; i < nodes.size(); i++) {
            nodeInt[i] = (int) nodes.get(i);
        }

        return nodeInt;
    }

//Outputs the number of nodes in the hashmap.
    @Override
    public int length() {
        return used;
    }
}
