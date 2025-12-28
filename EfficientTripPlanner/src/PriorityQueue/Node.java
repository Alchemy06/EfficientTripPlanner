package PriorityQueue;

public class Node {

    private Double tempDistance;
    private int graphNode;


    public Node(Double tempDistance, int graphNode) {
        this.graphNode = graphNode;
        this.tempDistance = tempDistance;
    }

    public Double getTempDistance() {
        return tempDistance;
    }

    public void setTempDistance(Double tempDistance) {
        this.tempDistance = tempDistance;
    }

    public int getGraphNode()
    {
        return graphNode;
    }

    public void setGraphNode(char graphNode)
    {
        this.graphNode = graphNode;
    }
}
