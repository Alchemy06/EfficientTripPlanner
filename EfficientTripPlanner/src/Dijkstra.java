import Hashmap.HashNode;
import Hashmap.Hashing;
import PriorityQueue.PriorityQueue;
import PriorityQueue.Node;
import com.sun.jdi.IntegerValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

//import java.util.ArrayList;

public class Dijkstra {

//This is the Dijkstra algorithm function which traverses the graph and finds the shortest route between a node and all the other points in a graph.
    public static DijkstraResult dijkstra(AdjacencyList graph, int source, int Destination) {


        //Initialised the priority queue for the algorithm tp used to rank the nodes on distance order.
        PriorityQueue unvisited = new PriorityQueue(graph.maxNodes);
        //This is where the final distances of all the nodes from the source node are stored.
        Hashing distances = new Hashing(graph.maxNodes);
        //This is the hashmap where
        Hashing<Integer, Integer> Predecessors = new Hashing<Integer, Integer>(3*graph.getMaxNodes());

        //This code adds all the nodes in the list into an array. This is done in preparation for the priority queue to be populated.
        int maxNodes = graph.maxNodes;
        int[] nodes = new int[maxNodes];
        nodes = graph.traverseVerticesGraph();
        int s = 0;
        for (int i = 0; i < graph.maxNodes; i++) {
            if (source == nodes[i]) {
                s = i;
            }
        }



        //The source node is first added to the unvisited priority queue with a value of 0 as it is itself.
        unvisited.enQueue((double) 0, nodes[s]);
        //Then a for loop is used to add the rest of the nodes into the unvisited priority and when they are inputted into the queue they are given
        //a temporary distance of infinity.
        for (int k = 0; k < graph.maxNodes; k++) {
            if (nodes[k] != source)
            {
                unvisited.enQueue(Double.POSITIVE_INFINITY, nodes[k]);
            }
        }
        //The source node is added into to the distances hashmap too first with a value of 0.
        distances.add(nodes[s], 0);
        //The rest of the nodes are added into the hashmap with an integers max value as the temporary distance
        for (int k = 0; k < graph.maxNodes; k++) {
            if (nodes[k] != source)
            {
                distances.add(nodes[k], Integer.MAX_VALUE);
            }
        }


        //This is the main part of the algorithm. This while loop runs as long there are still unvisited nodes.
        while (!unvisited.isEmpty()) {
            //This takes out the node with the shortest temporary distance.
            Node current = unvisited.pop();
            //This gets all the edges connected to the current node.
            Hashing edges = graph.getEdge(current.getGraphNode());
            //This stores all the nodes and the traversal cost of the nodes that are connected to the current node.
            ArrayList<Node> neighbouringEdges = new ArrayList<Node>();
            //This creates an array of all the nodes the current node is connected to.
            int[] neighbouringEdgesGraph = edges.traverseVertices();
            //This for loop stores neighbouring nodes and their traversal costs and repeats for the amount of nodes
            //the current node is connected to.
            for (int i = 0; i < graph.getCurrentNodeCountEdges(current.getGraphNode()); i++) {
                Node edge = new Node(edges.itemTraversal(neighbouringEdgesGraph[i]).doubleValue(), neighbouringEdgesGraph[i]);
                neighbouringEdges.add(edge);
            }

            // Updates the distances and predecessors of neighbouring nodes by going through the arraylist and comparing the
            //temporary distances.
            for (Node neighbor : neighbouringEdges) {
                int newDistance;
                Integer currentDistance = distances.itemTraversal(current.getGraphNode());

                if (distances.length() == 0) {
                    newDistance = (int) (0 + neighbor.getTempDistance());

                } else {
                    newDistance = (int) (distances.itemTraversal(current.getGraphNode()) + neighbor.getTempDistance());
                }

                //Distances compared and temporary distances updated accordingly.
                if (newDistance < distances.itemTraversal(neighbor.getGraphNode())) {
                    distances.delete(neighbor.getGraphNode());
                    distances.add(neighbor.getGraphNode(), newDistance);
                    if (Predecessors.contains(neighbor.getGraphNode()))
                    {
                        Predecessors.delete(neighbor.getGraphNode());
                    }
                    Predecessors.add(neighbor.getGraphNode(), current.getGraphNode());
                    unvisited.updateVal(neighbor.getGraphNode(), (double) newDistance);

                }

            }
        }

        // Reconstruct the shortest path.
        ArrayList<Integer> path = new ArrayList<Integer>();
        Integer currentNode = Destination;
        while (currentNode != 0)
        {
            path.add(currentNode);
            Integer I = Predecessors.itemTraversal(currentNode);
            if (I == null)
            {
                currentNode = 0;
            }
            else {
                currentNode = I.intValue();
            }
        }
        Collections.reverse(path);
        //The distance to the destination is stored as a separate variable.
        Integer Distance = distances.itemTraversal(Destination);
        //The path the distances and the distances from all the nodes are returned.
        return new DijkstraResult(Distance.intValue(), path, distances);


    }
}
