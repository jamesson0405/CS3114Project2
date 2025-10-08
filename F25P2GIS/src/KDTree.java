/**
 * kd tree implementation
 * @author Josh Kwen, James Son
 * @version 10/07/2025
 */

public class KDTree {
    private KDNode root; // KD tree root
    private static final int DIMENSION = 2; // dimensions for coordinates
    // discriminator search keys
    private static final int X_DISCRIMINATOR = 0;
    private static final int Y_DISCRIMINATOR = 1;
    
    private static class KDNode {
        City city;
        KDNode left;
        KDNode right;
        
        public KDNode(City city) {
            this.city = city;
            this.left = null;
            this.right = null;
        }
        /**
         * Get city
         * @return the stored city 
         */
        public City getCity() {
            return city;
        }
        
        /**
         * Get left node
         * @return left node
         */
        public KDNode left() {
            return left;
        }
        
        /**
         * Get right node
         * @return right node
         */
        public KDNode right() {
            return right;
        }
        
        /**
         * Set left node
         * @param l New left node
         */
        public void setLeft(KDNode l) {
            this.left = l;
        }
        
        /**
         * Set right node
         * @param r New right node
         */
        public void setRight(KDNode r) {
            this.right = r;
        }
        
        /**
         * Check if leaf is a node
         * @return true if node has no child nodes
         */
        /**public boolean isLeaf() {
            return (left == null) && (right == null);
        }*/
    }
   
    // ----------------------------------------------------------
    
    /**
     * Constructor for kd tree
     */
    public KDTree() {
        root = null;
    }
    
    // ----------------------------------------------------------
    
    /**
     * clear and reinitialize the kd tree
     */
    public void clear() {
        root = null;
    }
    // ----------------------------------------------------------
    
    /**
     * Check if the tree is empty
     * @return true if empty
     */
    /**public boolean isEmpty() {
        return root == null;
    }*/
    // ----------------------------------------------------------
    /**
     * Get root
     * @return root of tree
     */
    public KDNode getRoot() {
        return root;
    }

    // ----------------------------------------------------------
    
    /**
     * Insert city into the kd tree
     * @param city City to be inserted
     * @return true if inserted successfully
     *         false if there are duplicate coordinates
     */
    public boolean insert(City city) {
        if (city == null) {
            return false;
        }
        root = inserthelp(root, city, 0);
        return true;
    }
    
    /**
     * Recursive helper for insertion
     * @param rt Current node in the recursion
     * @param city City to be inserted
     * @param level Current level of tree
     * @return position of node after insertion
     */
    private KDNode inserthelp(KDNode rt, City city, int level) {
        if (rt == null) {
            return new KDNode(city);
        }
        
        int discriminator = level % DIMENSION;
        if (discriminator == X_DISCRIMINATOR) {
            if (city.getX() < rt.getCity().getX()) {
                rt.setLeft(inserthelp(rt.left(), city, level + 1));
            }
            else {
                rt.setRight(inserthelp(rt.right(), city, level + 1));
            }
        }
        else if (discriminator == Y_DISCRIMINATOR) {
            if (city.getY() < rt.getCity().getY()) {
                rt.setLeft(inserthelp(rt.left(), city, level + 1));
            }
            else {
                rt.setRight(inserthelp(rt.right(), city, level + 1));
            }
        }
        return rt;
        
    }
    
    // ----------------------------------------------------------

    /**
     * Debug print method for KD tree using inorder traversal
     * @return returns string of tree
     */
    public String debug() {
        StringBuilder str = new StringBuilder();
        debughelp(root, 0, str);
        return str.toString();
    }
    
    /**
     * Recursive helper for kd tree debug
     * @param rt Current node of tree
     * @param level Current level
     * @param str StringBuilder for output
     */
    private void debughelp(KDNode rt, int level, StringBuilder str) {
        if (rt == null) {
            return;
        }
        debughelp(rt.left(), level + 1, str);
        str.append(level);
        for (int i = 0; i < level * 2; i++) {
            str.append(" ");
        }
        str.append(rt.getCity().toString());
        str.append("\n");
        debughelp(rt.right(), level + 1, str);
        
    }
    
    // ----------------------------------------------------------
    
    /**
     * Find duplicate coordinates
     * @param x X coordinate
     * @param y Y coordinate
     * @return city at those coordinates
     */
    public boolean findDuplicate(int x, int y) {
        return findduplicatehelp(root, x, y, 0);
    }
    
    /**
     * Recursive helper for finding duplicate coordinates
     * @param rt Current node
     * @param x X coordinate 
     * @param y Y coordinate
     * @param level Current level of tree
     * @return city if found, otherwise null
     */
    private boolean findduplicatehelp(KDNode rt, int x, int y, int level) {
        if (rt == null) {
            return false;
        }
        City city = rt.getCity();
        if (city.getX() == x && city.getY() == y) {
            return true;
        }
        int discriminator = level % DIMENSION;
        if (discriminator == X_DISCRIMINATOR) {
            if (x < city.getX()) {
                return findduplicatehelp(rt.left(), x, y, level + 1);
            }
            else {
                return findduplicatehelp(rt.right(), x, y, level + 1);
            }
        }
        else {
            if (y < city.getY()) {
                return findduplicatehelp(rt.left(), x, y, level + 1);
            }
            else {
                return findduplicatehelp(rt.right(), x, y, level + 1);
            }
        }
    }  

}
