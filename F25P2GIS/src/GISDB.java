//-------------------------------------------------------------------------
/**
 * Implementation of the GIS interface. This is what calls the BST and the
 * Bintree to do the work.
 *
 * @author Josh Kwen, James Son
 * @version 10/14/2025
 *
 */
public class GISDB implements GIS {

    /**
     * The maximum allowable value for a coordinate
     */
    public static final int MAXCOORD = 32767;

    /**
     * Dimension of the points stored in the tree
     */
    public static final int DIMENSION = 2;
    
    private BST<City> bst;
    private KDTree kd;

    // ----------------------------------------------------------
    /**
     * Create a new GISDB object.
     */
    GISDB() {
        bst = new BST<City>();
        kd = new KDTree();
    }


    // ----------------------------------------------------------
    /**
     * Reinitialize the database
     * @return True if the database has been cleared
     */
    public boolean clear() {
        bst.clear();
        kd.clear();
        return true;
    }

    // ----------------------------------------------------------
    /**
     * A city at coordinate (x, y) with name name is entered into the database.
     * It is an error to insert two cities with identical coordinates,
     * but not an error to insert two cities with identical names.
     * @param name City name.
     * @param x City x-coordinate. Integer in the range 0 to 2^{15} − 1.
     * @param y City y-coordinate. Integer in the range 0 to 2^{15} − 1.
     * @return True iff the city is successfully entered into the database
     */
    public boolean insert(String name, int x, int y) {
        if (name == null) {
            return false;
        }
        if (x < 0 || y < 0 || x > MAXCOORD || y > MAXCOORD) {
            return false;
        }
        if (kd.findDuplicate(x, y)) {
            return false;
        }
        City newCity = new City(name, x, y);
        bst.insert(newCity);
        kd.insert(newCity);
        return true;
    }


    // ----------------------------------------------------------
    /**
     * The city with these coordinates is deleted from the database
     * (if it exists).
     * Print the name of the city if it exists.
     * If no city at this location exists, print the empty string.
     * @param x City x-coordinate.
     * @param y City y-coordinate.
     * @return A string with the number of nodes visited during the deletion
     *          followed by the name of the city (this is blank if nothing
     *          was deleted).
     */
    public String delete(int x, int y) {
        City deletedCity = kd.delete(x,  y);
        int nodesVisited = kd.getNodesVisited();
        if (deletedCity == null) {
            return "" + nodesVisited;
        }
        bst.delete(deletedCity);
        return nodesVisited + "\n" + deletedCity.getName();
    }


    // ----------------------------------------------------------
    /**
     * The city with this name is deleted from the database (if it exists).
     * If two or more cities have this name, then ALL such cities must be
     * removed.
     * Print the coordinates of each city that is deleted.
     * If no city with this name exists, print the empty string.
     * @param name City name.
     * @return A string with the coordinates of each city that is deleted
     *          (listed in preorder as they are deleted).
     *          Print the empty string if no cites match.
     */
    public String delete(String name) {
        StringBuilder str = new StringBuilder();
        while (true) {
            City city = bst.findFirstCityByName(name);
            if (city == null) {
                break;
            }
            if (str.length() > 0) {
                str.append("\n");
            }
            str.append("(").append(city.getX()).append(", ").append(
                city.getY()).append(")");
            bst.delete(city);
            kd.delete(city.getX(), city.getY());
            
        }
        return str.toString();
    }


    // ----------------------------------------------------------
    /**
     * Display the name of the city at coordinate (x, y) if it exists.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return The city name if there is such a city, empty otherwise
     */
    public String info(int x, int y) {
        City found = kd.findCity(x, y);
        if (found == null) {
            return "";
        }
        return found.getName();
    }


    // ----------------------------------------------------------
    /**
     * Display the coordinates of all cities with this name, if any exist.
     * @param name The city name.
     * @return String representing the list of cities and coordinates,
     *          empty if there are none.
     */
    public String info(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return bst.findByName(name);
    }


    // ----------------------------------------------------------
    /**
     * All cities within radius distance from location (x, y) are listed.
     * A city that is exactly radius distance from the query point should be
     * listed.
     * This operation should be implemented so that as few nodes as possible in
     * the k-d tree are visited.
     * @param x Search circle center: X coordinate. May be negative.
     * @param y Search circle center: X coordinate. May be negative.
     * @param radius Search radius, must be non-negative.
     * @return String listing the cities found (if any) , followed by the count
     *          of the number of k-d tree nodes looked at during the
     *          search process. If the radius is bad, return an empty string.
     *          If k-d tree is empty, the number of nodes visited is zero.
     */
    public String search(int x, int y, int radius) {
        if (radius < 0) {
            return "";
        }
        return kd.regionSearch(x, y, radius);
    }


    // ----------------------------------------------------------
    /**
     * Print a listing of the database as an inorder traversal of the k-d tree.
     * Each city should be printed on a separate line. Each line should start
     * with the level of the current node, then be indented by 2 * level spaces
     * for a node at a given level, counting the root as level 0.
     * @return String listing the cities as specified.
     */
    public String debug() {
        return kd.debug();
    }


    // ----------------------------------------------------------
    /**
    /**
     * Print a listing of the BST in alphabetical order (inorder traversal)
     * on the names.
     * Each city should be printed on a separate line. Each line should start
     * with the level of the current node, then be indented by 2 * level spaces
     * for a node at a given level, counting the root as level 0.
     * @return String listing the cities as specified.
     */
    public String print() {
        return bst.print();
    }
}