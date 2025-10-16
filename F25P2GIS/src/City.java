import javax.print.attribute.standard.MediaSize.Other;

/**
 * City class to store the data for a city record
 * 
 * @author Josh Kwen, James Son
 * @version 10/14/2025
 */

public class City implements Comparable<City> {

    private String name;
    private int x;
    private int y;

    /**
     * Constructs new city
     * 
     * @param name
     *            City name
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     */
    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    /**
     * Getter for city name
     * 
     * @return city name
     */
    public String getName() {
        return name;
    }


    /**
     * Getter for x coordinate
     * 
     * @return x coordinate
     */
    public int getX() {
        return x;
    }


    /**
     * Getter for y coordinate
     * 
     * @return y coordinate
     */
    public int getY() {
        return y;
    }


    /**
     * Compares given city to another city
     */
    @Override
    public int compareTo(City other) {
        
        if (other == null) {
           
            return 1;
        }
        if (this.name == null && other.name == null) {
            return 0;
        }
        if (this.name == null) {
            return -1;
        }
        if (other.name == null) {
            return 1;
        }
        return this.name.compareTo(other.name); // no coordinate tie-breakers
    }



    /**
     * return string representation of city and coordinates
     */
    @Override
    public String toString() {
        return name + " (" + x + ", " + y + ")";
    }


    /**
     * Check if two cities are equals
     * 
     * @param obj
     *            The object to compare
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof City)) {
            return false;
        }
        City other = (City)obj;
        return this.name.equals(other.name) && this.x == other.x
            && this.y == other.y;
    }

}
