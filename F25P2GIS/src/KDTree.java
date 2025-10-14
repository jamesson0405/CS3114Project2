/**
 * kd tree implementation
 * 
 * @author Josh Kwen, James Son
 * @version 10/09/2025
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

        /**
         * Constructor for KDNode
         * 
         * @param city
         *            city to store
         */
        public KDNode(City city) {
            this.city = city;
            this.left = null;
            this.right = null;
        }


        /**
         * Get city
         * 
         * @return the stored city
         */
        public City getCity() {
            return city;
        }


        /**
         * Get left node
         * 
         * @return left node
         */
        public KDNode left() {
            return left;
        }


        /**
         * Get right node
         * 
         * @return right node
         */
        public KDNode right() {
            return right;
        }


        /**
         * Set left node
         * 
         * @param l
         *            New left node
         */
        public void setLeft(KDNode l) {
            this.left = l;
        }


        /**
         * Set right node
         * 
         * @param r
         *            New right node
         */
        public void setRight(KDNode r) {
            this.right = r;
        }
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
     * Get root
     * 
     * @return root of tree
     */
    public KDNode getRoot() {
        return root;
    }

    // ----------------------------------------------------------


    /**
     * Insert city into the kd tree
     * 
     * @param city
     *            City to be inserted
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
     * 
     * @param rt
     *            Current node in the recursion
     * @param city
     *            City to be inserted
     * @param level
     *            Current level of tree
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
     * 
     * @return returns string of tree
     */
    public String debug() {
        StringBuilder str = new StringBuilder();
        debughelp(root, 0, str);
        return str.toString();
    }


    /**
     * Recursive helper for kd tree debug
     * 
     * @param rt
     *            Current node of tree
     * @param level
     *            Current level
     * @param str
     *            StringBuilder for output
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
     * 
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @return true if duplicate exists, false otherwise
     */
    public boolean findDuplicate(int x, int y) {
        return findduplicatehelp(root, x, y, 0);
    }


    /**
     * Recursive helper for finding duplicate coordinates
     * 
     * @param rt
     *            Current node
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param level
     *            Current level of tree
     * @return true if found, otherwise false
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

    // ----------------------------------------------------------


    /**
     * Find city at specific coordinates
     * 
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @return City if found, null otherwise
     */
    public City findCity(int x, int y) {
        return findCityHelp(root, x, y, 0);
    }


    /**
     * Recursive helper for finding city
     * 
     * @param rt
     *            Current node
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param level
     *            Current level
     * @return City if found, null otherwise
     */
    private City findCityHelp(KDNode rt, int x, int y, int level) {
        if (rt == null) {
            return null;
        }
        City city = rt.getCity();
        if (city.getX() == x && city.getY() == y) {
            return city;
        }
        int discriminator = level % DIMENSION;
        if (discriminator == X_DISCRIMINATOR) {
            if (x < city.getX()) {
                return findCityHelp(rt.left(), x, y, level + 1);
            }
            else {
                return findCityHelp(rt.right(), x, y, level + 1);
            }
        }
        else {
            if (y < city.getY()) {
                return findCityHelp(rt.left(), x, y, level + 1);
            }
            else {
                return findCityHelp(rt.right(), x, y, level + 1);
            }
        }
    }

    // ----------------------------------------------------------


    /**
     * Perform region search for cities within radius
     * 
     * @param x
     *            X coordinate of center
     * @param y
     *            Y coordinate of center
     * @param radius
     *            Search radius
     * @return String with cities found and node count
     */
    public String regionSearch(int x, int y, int radius) {
        StringBuilder result = new StringBuilder();
        int[] nodesVisited = { 0 };
        regionSearchHelp(root, x, y, radius, 0, result, nodesVisited);
        result.append("Nodes Visited: ").append(nodesVisited[0]).append("\n");
        return result.toString();
    }


    /**
     * Recursive helper for region search
     * 
     * @param rt
     *            Current node
     * @param x
     *            X coordinate of center
     * @param y
     *            Y coordinate of center
     * @param radius
     *            Search radius
     * @param level
     *            Current level
     * @param result
     *            StringBuilder for output
     * @param nodesVisited
     *            Array to track nodes visited
     */
    private void regionSearchHelp(
        KDNode rt,
        int x,
        int y,
        int radius,
        int level,
        StringBuilder result,
        int[] nodesVisited) {
        if (rt == null) {
            return;
        }

        nodesVisited[0]++;
        City city = rt.getCity();

        // Calculate distance from search point to current city
        long dx = (long)city.getX() - x;
        long dy = (long)city.getY() - y;
        long distSquared = dx * dx + dy * dy;
        long radiusSquared = (long)radius * radius;

        // If city is within radius, add to results
        if (distSquared <= radiusSquared) {
            result.append(city.toString()).append("\n");
        }

        int discriminator = level % DIMENSION;
        int splitValue;
        int searchValue;

        if (discriminator == X_DISCRIMINATOR) {
            splitValue = city.getX();
            searchValue = x;
        }
        else {
            splitValue = city.getY();
            searchValue = y;
        }

        // Determine which subtree to search first
        if (searchValue < splitValue) {
            regionSearchHelp(rt.left(), x, y, radius, level + 1, result,
                nodesVisited);
            // Check if we need to search the other subtree
            long distToSplit = (long)(searchValue - splitValue);
            if (distToSplit * distToSplit <= radiusSquared) {
                regionSearchHelp(rt.right(), x, y, radius, level + 1, result,
                    nodesVisited);
            }
        }
        else {
            regionSearchHelp(rt.right(), x, y, radius, level + 1, result,
                nodesVisited);
            // Check if we need to search the other subtree
            long distToSplit = (long)(searchValue - splitValue);
            if (distToSplit * distToSplit <= radiusSquared) {
                regionSearchHelp(rt.left(), x, y, radius, level + 1, result,
                    nodesVisited);
            }
        }
    }
}
