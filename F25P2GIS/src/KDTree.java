/**
 * kd tree implementation
 * @author Josh Kwen, James Son
 * @version 10/06/2025
 */

public class KDTree {
    private KDNode root;
    private int nodeCount;
    
    private static class KDNode {
        City city;
        KDNode left;
        KDNode right;
        
        KDNode(City city) {
            this.city = city;
            left = null;
            right = null;
        }
        
        int[] key() {
            return new int[] {city.getX(), city.getY()};
        }
    }
    
    // ----------------------------------------------------------
    
    /**
     * Constructor for kd tree
     */
    public KDTree() {
        root = null;
        nodeCount = 0;
    }
    
    // ----------------------------------------------------------
    
    /**
     * clear and reinitialize the kd tree
     */
    public void clear() {
        root = null;
        nodeCount = 0;
    }
    // ----------------------------------------------------------
    
    /**
     * Insert city into the kd tree
     * @param city City to be inserted
     * @return true if inserted successfully
     *         false if there are duplicate coordinates
     */
    // NEED TO FIX
    /**public boolean insert(City city) {
        if (city == null) {
            return false;
        }
        KDNode inserted = inserthelp(root, city, 0);
        if (inserted == null) {
            return false;
        }
        
        if (root == null) {
            root = inserted;
        }
        nodeCount++;
        return true;
    }
    
    private KDNode inserthelp(KDNode rt, City city, int level) {
        if (rt == null) {
            return new KDNode(city);
        }
        
        int[] citykey = {city.getX(), city.getY()};
        int[] itkey = rt.key();
        
        if (citykey[0] == itkey[0] && citykey[1] == itkey[1]) { // check for duplicate
            return null ;
        }
        if (citykey[level] < itkey[level]) {
            rt.left = inserthelp(rt.left, city, (level + 1) % 2);
        }
        else {
            rt.right = inserthelp(rt.right, city, (level + 1) % 2);
        }
        return rt;
    }
    */
}
