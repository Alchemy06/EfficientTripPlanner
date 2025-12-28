import java.util.*;
import java.util.ArrayList;

import Hashmap.Hashing;
import Hashmap.IHash;
import PriorityQueue.Node;

public class DijkstraResult {
    //THis class is here so that I would not have to create copies of the Dijkstra's algorithm with different return outputs. All the outputs of the algorithm can be returned at once with this class.

    //This is the final value of the distance between the two nodes that are inputted.
    Integer distance;
    //This is a hashmap with stores all the distances from the source node and all the other nodes in the graph.
    Hashing<Integer, Integer> distances;

    //Getters and setters.
    public Hashing<Integer, Integer> getDistances() {
        return distances;
    }

    public int getDistance() {
        return distance;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }


    //This arraylist has the of all the nodes that a particular route passes through in order of how they pass.
    ArrayList<Integer> path = new ArrayList<Integer>();

    //Constructor.
    public DijkstraResult(Integer distance, ArrayList<Integer> path, Hashing<Integer, Integer> distances)
    {
        this.distance = distance;
        this.path = path;
        this.distances = distances;
    }
}
