package PriorityQueue;
import java.util.ArrayList;

public class PriorityQueue {

    ArrayList<Node> theQueue = new ArrayList<Node>();
    int front = 0;
    int rear = -1;
    int size = 0;
    int maxSize = 0;


//Constructor which initialises the max size for the queue
    public PriorityQueue(int maxSize)
    {
        this.maxSize = maxSize;
    }

    //Checks if the queue is full.
    public boolean isFull()
    {
        boolean isFull = false;
        if (this.size == this.maxSize)
        {
            isFull = true;
        }
        return isFull;
    }

    //Checks if the queue is empty
    public boolean isEmpty()
    {
        boolean isEmpty = false;
        if (this.size == 0)
        {
            isEmpty = true;
        }
        return isEmpty;
    }


    //Checks what the first value in the queue is without removing it.
    public Node peak()
    {
        return theQueue.get(0);
    }

    //Adds a new node to the queue
    public void enQueue(Double a ,int graphNode)
    {
        //Makes sure the queue is not full before adding a new node to the queue. If there is not any space then it will return an error message instead.
        if (!isFull())
        {
            int i = 0;
            //This makes sure that the bigger numbers of a go to the back of the list. This keeps the smaller numbers of a to the front.
            //It checks the double tempDist value of all the nodes from the beginning until it reaches one that is bigger than or equal to a and then
            //sets the new position for the node that will be added right before it.
            while (i < theQueue.size() && theQueue.get(i).getTempDistance() <= a){
                i++;
            }
            //Node created and added in the right spot.
            Node current = new Node(a, graphNode);
            theQueue.add(i, current);
            this.size++;
        }
        else
        {
            System.out.println("Queue Full!");
        }
    }

    //This function is used when the tempDistance of a node needs to be changed.
    public void updateVal(int GraphNode, Double tempDistance)
    {
        //The while function is used to work out where in the queue a particular graph node is, so it can be updated.
        int i = 0;
        while (theQueue.get(i).getGraphNode() != GraphNode)
        {
            if (i==theQueue.size()-1)
            {
                break;
            }
            i++;
        }
        //The old node is removed from the queue and then a new node with the same graph and an updated distance is added. And the size is updated accordingly.
        theQueue.remove(i);
        size--;
        Node current = new Node(tempDistance, GraphNode);
        enQueue(tempDistance, GraphNode);
    }


    //This removes the first node from the queue. Also changes the length var accordingly.
    public void deQueue()
    {
        if(!isEmpty())
        {
            theQueue.remove(0);

            this.size--;

        }
        else
        {
            System.out.println("Queue Empty!!");
        }
    }
    //This removes the first node from the queue and then returns it. Also changes the length var accordingly.
    public Node pop()
    {
        Node x = theQueue.get(0);
        if(!isEmpty())
        {
            theQueue.remove(0);

            this.size--;

        }
        else
        {
            System.out.println("Queue Empty!!");
        }
        return x;
    }
    //This returns the temporary distance of the node which is inputted.
    public Double findTempDist(char x)
    {
        Double tempDist = -1.0;
        for(int i = 0; i<theQueue.size()-1;i++)
        {
            if (theQueue.get(i).getGraphNode() == x)
            {
                tempDist = (Double) theQueue.get(i).getTempDistance();
            }
        }
        if(tempDist>-1)
        {
            return tempDist;
        }
        else
        {
            return tempDist;
        }
    }

    //This returns the current size of the queue.
    public int getSize()
    {
        return this.size;
    }
    public void getQueue()
    {
        System.out.println(theQueue.toString());
    }




}
