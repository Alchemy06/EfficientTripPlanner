import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseConnect {
    private Connection conn = null;

    public DatabaseConnect(String Url)
    {
        //This makes a connection with the database which in is location and has the name that is specified by the URL parameter.
        try
        {
            Class.forName("org.sqlite.JDBC");//SQLite Java driver that is used for this program.
            conn = DriverManager.getConnection(Url);//This is URL which has the name and location for the database to be opened.
            System.out.println("Opened database successfully");
        } catch (Exception e)
        {
            //Handles the exception if the database is not found.
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public int numberEntries()
    {
        //This function finds out how many attractions there are.
        //This SQL command requests the number of entries in the attractions table.
        String sql = "SELECT COUNT(*) FROM Attractions";
        int numberEntries = 0;
        //SQL execution
        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                numberEntries = (rs.getInt("COUNT(*)"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return numberEntries;
    }


    public void ReadExplain(String table, String column1, String column2, String column3, boolean AgeCate)
    {
        //This function is used in the explain mode to out put the contents of either the Age based interest rating table and the category table depending on the parameters.
        String sql = "SELECT " + column1 +", " + column2 + ", " + column3 + " FROM " + table;
        //Checks whether the date being collected is an age based interest rating or a category and outputs the format accordingly.
        if (AgeCate)
        {
            System.out.println("Rating - Description - Age rating ID");
        }
        else {
            System.out.println("CategoryID, Category name, Category Description");
        }

        try (
                //Executes the SQL statement.
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                //Reads the output of the SQL statement and outputs it.
                System.out.println(rs.getString(column1) + "-" + "\t" +
                        rs.getString(column2) + "-" + "\t" +
                        rs.getString(column3));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    public void outputAllAttractions()
    {
        //This function outputs the attraction names with their respective ID, Age based interest rating and category.
        String sql = "SELECT AttractionName, AttractionId, AgeRatingID, CategoryID FROM Attractions ORDER BY AttractionName";
        System.out.println("This is the format.");
        System.out.println("AttractionId - Attraction Name - Age based interest rating - Category");
        System.out.println("Here is the list");
        try (
                //Creates and executes statement.
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                //This while loop reads the output of the database and prints it onto the screen.
                System.out.println(rs.getString("AttractionName") + "-" + "\t" +
                        rs.getInt("AttractionId") + "-" + "\t" +
                        rs.getString("AgeRatingID") + "-" + "\t" +
                        rs.getString("CategoryID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public String IdToName(int id)
    {
        //This function takes in the attraction ID and matches the name of the attraction to the ID by looking it up in the database.
        String sql = "SELECT AttractionName FROM Attractions WHERE AttractionId ==" + id +";";
        String AttractionName = null;
        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                AttractionName = (rs.getString("AttractionName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return AttractionName;
    }

    public String idToAge(int id)
    {
        //This function takes in the attraction id and matches it to the age based interest rating by looking it up in the table.
        String sql = "SELECT AgeRatingID FROM Attractions WHERE AttractionId ==" + id +";";
        String AgeRatingID = null;
        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                AgeRatingID = (rs.getString("AgeRatingID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return AgeRatingID;
    }

    public String idToCate(int id)
    {
        //This function takes in the attraction id and changes it to the category.
        String sql = "SELECT CategoryID FROM Attractions WHERE AttractionId ==" + id +";";
        String CategoryID = null;
        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                CategoryID = (rs.getString("CategoryID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return CategoryID;
    }



    public AdjacencyList ReadCreateGraph(AdjacencyList graph)
    {
        //This function reads the attraction id and the routes and their respective traversal costs from the database and creates a graph using with the data using my adjacency list implementation.
        String sql = "SELECT AttractionId FROM Attractions";
        String sql2 = "SELECT StartPoint, EndPoint, WalkingTime FROM Routes";
        try (
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                //Adds the attractionId as a vertex to the graph.
                graph.addVertex(rs.getInt("AttractionId"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (
                Statement stmt2  = conn.createStatement();
                ResultSet rs    = stmt2.executeQuery(sql2)){

            while (rs.next()) {
                int StartPoint = rs.getInt("StartPoint");
                int EndPoint = rs.getInt("EndPoint");
                int WalkingTime = rs.getInt("WalkingTime");

                //Adds the route as an edge to the graph then adds the same edge in the opposite direction in order to make the graph bidirectional.
                graph.addEdge(StartPoint, EndPoint, WalkingTime);
                graph.addEdge(EndPoint, StartPoint, WalkingTime);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //Returns the final graph with all the vertexes(attractions) and edges(routes).
        return graph;
    }


    public void close()
    {
        //Closes the database.
        try
        {
            conn.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




}
