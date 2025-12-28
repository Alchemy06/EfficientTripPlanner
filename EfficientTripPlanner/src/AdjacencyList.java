import Hashmap.Hashing;

public class AdjacencyList{


    public int maxNodes = 34;
    public int getCurrentNodeCountEdges(Integer k)
    {
        Hashing edges = graph.itemHash(k);
        return edges.length();
    }


    public int getMaxNodes() {
        return maxNodes;
    }

    //Constructor
    public AdjacencyList(int maxNodes){
        this.maxNodes = maxNodes;
    }

    //This is a piece of code to create the main hashmap that the graph is based on.
    private Hashing graph = new Hashing(maxNodes);

    //This adds the vertices to the graph and a template for where the edges are stored.
    public void addVertex(int Vertex)
    {
        //There the max size of the edges hashmap has 2 times the number of max nodes so that the graph can be bidirectional and won`t run out of space even if the vertex is connected to every other vertex.
        Hashing edges = new Hashing(maxNodes*2);
        graph.add(Vertex, edges);
    }

    public void addEdge(int source, int destination, int traversalCost)
    {
        //This modifies the edge hashmap of said vertex in the graph and adds the edge with the specifications of the parameters to it and then updates the vertex with the edges hashmap.
        Hashing edges = graph.itemHash(source);
        edges.add(destination, traversalCost);
        graph.delete(source);
        graph.add(source, edges);
    }

    public Hashing getEdge(int a)
    {
        Hashing current = graph.itemHash(a);
        return current;
    }

    //This is used to output all the vertices that are in the graph when the vertices are represented by an int variable as they are in this program.
    public int[] traverseVerticesGraph()
    {
        return graph.traverseVertices();
    }

    //This is used to output all the connections that a particular vertices has.
    public int[] traverseVerticesEdges(Hashing edges)
    {
        return edges.traverseVertices();
    }



}
