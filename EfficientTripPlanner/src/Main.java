import Hashmap.Hashing;
import PriorityQueue.PriorityQueue;

import java.util.ArrayList;
import java.util.Scanner;

import Hashmap.HashNode;

import PriorityQueue.Node;
//import com.sun.jdi.IntegerValue;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;


public class Main {

    // This is a validation function that checks an input is within the right range of values.
    public static boolean inRange(int num, int min, int max) {
        return num >= min && num <= max;
    }



    public static void main(String[] args)
    {
        System.out.println("This is an efficient trip planner - created by Adarsh Ram Sivakumar ");
        //The code below is there for the user to choose the city/database they want to open.
        Scanner cityObj = new Scanner(System.in);
        System.out.println("Please enter the name of the city you are interested in.");
        String city = cityObj.nextLine();
        city = city.toUpperCase();
        //This creates the variables that the program will use beforehand. This variables are general and do not change depending on the city.
        //This is an example of soft coding.
        DatabaseConnect Conn = null;
        AdjacencyList graph = null;
        boolean validCity = false;


        if (city.equals("LONDON"))
        {
            //Load London database
            Conn = new DatabaseConnect("jdbc:sqlite:London map database.db");
            //This code below calls the function that creates the graph and assigns to the graph variable.
            graph = new AdjacencyList(Conn.numberEntries());
            graph = Conn.ReadCreateGraph(graph);
            //valid city is used to soft code.
            validCity = true;
        }
        else
        {
            System.out.println("This city is not supported. ");
        }
        if (validCity)
        {
            //All the code in this if statement is an example of soft coding. This part of the program is general and can apply to multiple cities. This is useful as the code here does not need to repeated for other cities.
            System.out.println("What would you like to do?");
            System.out.println("This program has multiple functions. They are listed below.");
            System.out.println("1) Route Finder - This function lets you find out the shortest route between two places in " + city + ".");
            System.out.println("This will tell you which attractions are along the way and the time needed to walk between them and the time needed to walk the whole route.");
            System.out.println("2) Explore - This function lists all the other attractions in the map based on walking time from the chosen attraction");
            System.out.println("It also displays the age based interest rating and the category for each attraction.");
            System.out.println("You can also create filters based on time, age based interest rating and category.");
            System.out.println("3) Explain - This function shows the definitions of the age based interest rating and the categories.");
            //Takes in the input required to work out which function
            Scanner functionObj = new Scanner(System.in);
            System.out.println("Please press the respective number needed for the task.");
            int function = functionObj.nextInt();
            if (function == 1)
            {
                int StartNode = 0;
                int Destination = 0;
                Conn.outputAllAttractions();
                System.out.println("Here are all the attraction in this program listed above.");
                Scanner SPObj = new Scanner(System.in);
                System.out.println("Enter the number corresponding to your start point");
                StartNode = SPObj.nextInt();
                if(inRange(StartNode, 1, Conn.numberEntries()))
                {
                    Scanner EPObj = new Scanner(System.in);
                    System.out.println("Now, enter the number corresponding to your end point");
                    Destination = EPObj.nextInt();
                    if (inRange(Destination, 1, Conn.numberEntries()) && Destination != StartNode)
                    {
                        DijkstraResult result = Dijkstra.dijkstra(graph, StartNode, Destination);
                        System.out.println("The walking time between these 2 attractions is " + result.getDistance() + " minutes.");
                        ArrayList<Integer> path = result.getPath();
                        if (path.size() > 2)
                        {
                            System.out.print("This route goes through the attractions: ");
                            for(int i = 0; i<path.size(); i++)
                            {
                                if (i == path.size() - 1 )
                                {
                                    System.out.print(" and " +Conn.IdToName(path.get(i)) + ".");
                                }
                                else if (i == path.size() -2)
                                {
                                    System.out.print(Conn.IdToName(path.get(i)));
                                }
                                else
                                {
                                    System.out.print(Conn.IdToName(path.get(i)) + ", ");
                                }
                            }
                        }
                        else
                        {
                            System.out.println("This route is direct.");
                        }



                    }
                    else
                    {
                        System.out.println("Invalid input for destination.");
                    }
                }
                else
                {
                    System.out.println("Invalid input for Start point.");
                }

            }
            else if (function == 2) {
                int source = 0;
                boolean filter = false;
                String input = null;
                int walkingLim = 0;
                enum AgeBasedRating{
                    E,
                    PG,
                    FifteenA,
                    OA,
                    NA
                }
                enum Category
                {
                    M,
                    PoW,
                    P,
                    OW,
                    S,
                    T,
                    B,
                    La,
                    Li,
                    R,
                    NA
                }
                AgeBasedRating ABR = AgeBasedRating.NA;
                Category cat = Category.NA;

                Conn.outputAllAttractions();
                System.out.println("Here are all the attraction in this program listed above.");
                Scanner SPObj = new Scanner(System.in);
                System.out.println("Enter the number corresponding to your source point. This is where your would like to measure distances from.");
                source = SPObj.nextInt();
                Scanner inputOBJ = new Scanner(System.in);
                System.out.println("Would you like to have any filters. If yes then press 1 if not then click any other button.");
                input = inputOBJ.nextLine();
                if (input.equals("1"))
                {
                    filter = true;
                    input = null;
                }
                if(filter)
                {
                    Scanner walkingLimOBJ = new Scanner(System.in);
                    System.out.println("Input the maximum walking time. If you do not wish to limit the walking time then enter 0");
                    walkingLim = walkingLimOBJ.nextInt();

                    System.out.println("This is the menu for age based interest rating filter.");
                    System.out.println("Press 1 to choose - Everyone (E) rating.");
                    System.out.println("Press 2 to choose - Parental Guidance (PG) rating.");
                    System.out.println("Press 3 to choose - 15A rating.");
                    System.out.println("Press 4 to choose - Older Aim (OA) rating.");
                    System.out.println("Press any other character if you do not want an age rating filter");
                    input = inputOBJ.nextLine();
                    switch (input) {
                        case "1" -> ABR = AgeBasedRating.E;
                        case "2" -> ABR = AgeBasedRating.PG;
                        case "3" -> ABR = AgeBasedRating.FifteenA;
                        case "4" -> ABR = AgeBasedRating.OA;
                        default -> {
                            ABR = AgeBasedRating.NA;
                        }
                    }
                    System.out.println("This is the menu for the the Category filter.");
                    System.out.println("Press 1 to choose - Museum (M) Category");
                    System.out.println("Press 2 to choose - Place of worship (PoW) Category");
                    System.out.println("Press 3 to choose - Plaza (P) Category");
                    System.out.println("Press 4 to choose - Observation Wheel (OW) Category");
                    System.out.println("Press 5 to choose - Train Station (S) Category");
                    System.out.println("Press 6 to choose - Theatre (T) Category");
                    System.out.println("Press 7 to choose - Bridge (B) Category");
                    System.out.println("Press 8 to choose - Landmark (La) Category");
                    System.out.println("Press 9 to choose - Library (Li) Category");
                    System.out.println("Press 10 to choose - Royal Palace or Castle (R) Category");
                    System.out.println("Press any other character if you do not want to set a Category filter");
                    input = inputOBJ.nextLine();
                    switch (input) {
                        case "1" -> cat = Category.M;
                        case "2" -> cat = Category.PoW;
                        case "3" -> cat = Category.P;
                        case "4" -> cat = Category.OW;
                        case "5" -> cat = Category.S;
                        case "6" -> cat = Category.T;
                        case "7" -> cat = Category.B;
                        case "8" -> cat = Category.La;
                        case "9" -> cat = Category.Li;
                        case "10" -> cat = Category.R;
                        default ->  {
                            cat = Category.NA;
                        }

                    }

                }
                if (inRange(source, 1, Conn.numberEntries()))
                {
                    DijkstraResult result = Dijkstra.dijkstra(graph, source, 33);
                    Hashing distances = result.getDistances();
                    PriorityQueue distanceOrder = new PriorityQueue(Conn.numberEntries());
                    for(int i = 1; i<=Conn.numberEntries(); i++)
                    {
                        int a = distances.itemTraversal(i);
                        distanceOrder.enQueue((double)a, i);
                    }
                    for(int i = 0; i<Conn.numberEntries(); i++)
                    {
                        Node current = distanceOrder.pop();
                        String AgeRateConn = Conn.idToAge(current.getGraphNode());
                        if (AgeRateConn.equals("15A"))
                        {
                            AgeRateConn = "FifteenA";
                        }
                        if (current.getTempDistance() > walkingLim && walkingLim != 0)
                        {
                            continue;
                        }
                        if (!AgeRateConn.equals(ABR.toString()) && ABR != AgeBasedRating.NA)
                        {
                            continue;
                        }
                        if (!Conn.idToCate(current.getGraphNode()).equals(cat.toString()) && cat != Category.NA)
                        {
                            continue;
                        }

                        if(i != 0)
                        {
                            System.out.println(Conn.IdToName(current.getGraphNode()) + "(" + "Category: " + Conn.idToCate(current.getGraphNode()) + " Age based interest rating id: " + Conn.idToAge(current.getGraphNode()) + ")" + " is " + current.getTempDistance() + " minutes away from " + Conn.IdToName(source) + ".");
                        }

                    }
                    System.out.println("That it all.");

                }
                else
                {
                    System.out.println("Invalid Input");
                }
            }
            else if (function == 3)
            {
                System.out.println("1) Explanation of the Age based interest rating.");
                System.out.println("2) Explanation of the Categories.");
                System.out.println("3) Both.");
                Scanner ExplainObj = new Scanner(System.in);
                System.out.println("Please press the respective number needed for the task.");
                int Explain = ExplainObj.nextInt();
                if (Explain == 1)
                {
                    Conn.ReadExplain("AgeBasedInterestRating", "Rating", "RatingDescription", "AgeRatingID", true);
                }
                else if (Explain == 2)
                {
                    Conn.ReadExplain("Category", "CategoryID", "CategoryName", "CategoryDescription" , false);
                }
                else if (Explain == 3)
                {
                    Conn.ReadExplain("AgeBasedInterestRating", "Rating", "RatingDescription", "AgeRatingID" ,true);
                    System.out.println(" ");
                    System.out.println(" ");
                    Conn.ReadExplain("Category", "CategoryID", "CategoryName", "CategoryDescription", false);
                }
                else
                {
                    System.out.println("Invalid command");
                }

            }
            else
            {
                System.out.println("Invalid command");
            }

        }
    }
}