/**
 * City class to store the data for a city record
 * @author Josh Kwen, James Son
 * @version 10/06/2025
 */

public class City implements Comparable<City> {
    
    private String name;
    private int x;
    private int y;
    
    
    /**
     * Constructs new city
     * @param name City name
     * @param x X coordinate
     * @param y Y coordinate
     */
    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Getter for city name
     * @return city name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter for x coordinate
     * @return x coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * Getter for y coordinate
     * @return y coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Compares given city to another city
     */
    @Override
    public int compareTo(City o) {
        return this.name.compareTo(o.name);
    }
    
    /**
     * return string representation of city and coordinates
     */
    @Override
    public String toString() { // For milestone 1
        return name + " (" + x + ", " + y + ")";    }
    
    

}
