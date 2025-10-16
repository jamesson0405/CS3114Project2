/**
 * kd tree implementation
 * 
 * @author Josh Kwen, James Son
 * @version 10/15/2025
 */

public class KDTree {
    private KDNode root; // KD tree root
    private static final int DIMENSION = 2; // dimensions for coordinates
    // discriminator search keys
    private static final int X_DISCRIMINATOR = 0;
    private static final int Y_DISCRIMINATOR = 1;
    private int nodesVisited;

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
        StringBuilder sb = new StringBuilder();
        debughelp(root, 0, sb);
        return sb.toString();
    }


    /**
     * Post-order helper: left, right, node.
     * 
     * @param rt
     *            current node
     * @param level
     *            current depth (root = 0)
     * @param out
     *            destination buffer
     */
    private void debughelp(KDNode rt, int level, StringBuilder out) {
        if (rt == null) {
            return;
        }
        debughelp(rt.left, level + 1, out);
        debughelp(rt.right, level + 1, out);
        out.append(level);
        if (level > 0) {
            out.append(' ');
        }
        out.append(rt.city.toString());
        out.append('\n');
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
            else { // x >= city.getX(), equal goes right
                return findduplicatehelp(rt.right(), x, y, level + 1);
            }
        }
        else {
            if (y < city.getY()) {
                return findduplicatehelp(rt.left(), x, y, level + 1);
            }
            else { // y >= city.getY(), equal goes right
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
            else { // x >= city.getX(), equal goes right
                return findCityHelp(rt.right(), x, y, level + 1);
            }
        }
        else {
            if (y < city.getY()) {
                return findCityHelp(rt.left(), x, y, level + 1);
            }
            else { // y >= city.getY(), equal goes right
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
        nodesVisited = 0;
        StringBuilder str = new StringBuilder();
        regionSearchHelp(root, x, y, radius, 0, str);
        str.append("Nodes Visited: ").append(nodesVisited).append("\n");
        return str.toString();

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
    // signature should match your file:
    // private void regionSearchHelp(KDNode rt, int qx, int qy, int radius, int
    // level, StringBuilder out)
    private void regionSearchHelp(
        KDNode rt,
        int qx,
        int qy,
        int radius,
        int level,
        StringBuilder out) {
        if (rt == null) {
            return;
        }

        // count visit (if you track nodesVisited, increment here)
        nodesVisited++;

        // emit city if within circle (distance <= radius)
        long dx = (long)rt.city.getX() - qx;
        long dy = (long)rt.city.getY() - qy;
        long dist2 = dx * dx + dy * dy;
        long r2 = (long)radius * (long)radius;
        if (dist2 <= r2) {
            out.append(rt.city.getName()).append("\n"); // or your exact
                                                        // required format
        }

        boolean splitOnX = (level & 1) == 0; // X at even levels, Y at odd
        if (splitOnX) {
            int split = rt.city.getX();
            // go primary side
            if (qx <= split) {
                regionSearchHelp(rt.left, qx, qy, radius, level + 1, out);
            }
            else {
                regionSearchHelp(rt.right, qx, qy, radius, level + 1, out);
            }
            // cross if circle touches the plane
            if (Math.abs(qx - split) <= radius) {
                if (qx <= split) {
                    regionSearchHelp(rt.right, qx, qy, radius, level + 1, out);
                }
                else {
                    regionSearchHelp(rt.left, qx, qy, radius, level + 1, out);
                }
            }
        }
        else {
            int split = rt.city.getY();
            if (qy <= split) {
                regionSearchHelp(rt.left, qx, qy, radius, level + 1, out);
            }
            else {
                regionSearchHelp(rt.right, qx, qy, radius, level + 1, out);
            }
            if (Math.abs(qy - split) <= radius) {
                if (qy <= split) {
                    regionSearchHelp(rt.right, qx, qy, radius, level + 1, out);
                }
                else {
                    regionSearchHelp(rt.left, qx, qy, radius, level + 1, out);
                }
            }
        }
    }


    /**
     * Delete city at given coordinate from the KD Tree
     * 
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @return deleted city, null if not found
     */
    public City delete(int x, int y) {
        nodesVisited = 0;
        Deleted result = deleteHelper(root, x, y, 0);
        root = result.node;
        return result.deletedCity;

    }

    /**
     * Helper to return deleted city and new tree
     */
    private static class Deleted {
        KDNode node;
        City deletedCity;

        Deleted(KDNode node, City deletedCity) {
            this.node = node;
            this.deletedCity = deletedCity;
        }
    }

    /**
     * Recursive helper to delete node from tree
     * 
     * @param rt
     *            Current node
     * @param x
     *            X coordinate target
     * @param y
     *            Y coordinate target
     * @param level
     *            Current level of tree
     * @return deleted city
     */
    private Deleted deleteHelper(KDNode rt, int x, int y, int level) {
        if (rt == null) {
            return new Deleted(null, null);
        }
        nodesVisited++;
        City curr = rt.getCity();
        int discriminator = level % DIMENSION;

        // Found the node to delete
        if (curr.getX() == x && curr.getY() == y) {
            City deletedCity = curr;
            // if node has right child
            if (rt.right() != null) {
                KDNode minNode = findMin(rt.right(), discriminator, level + 1);
                rt.city = minNode.getCity();
                Deleted rightTree = deleteHelper(rt.right(), minNode.getCity()
                    .getX(), minNode.getCity().getY(), level + 1);
                rt.setRight(rightTree.node);
                return new Deleted(rt, deletedCity);
            }
            // if node has left child
            else if (rt.left() != null) {
                KDNode minNode = findMin(rt.left(), discriminator, level + 1);
                rt.city = minNode.getCity();
                Deleted leftTree = deleteHelper(rt.left(), minNode.getCity()
                    .getX(), minNode.getCity().getY(), level + 1);
                rt.setRight(leftTree.node);
                rt.setLeft(null);
                return new Deleted(rt, deletedCity);
            }
            else {
                return new Deleted(null, deletedCity);
            }
        }
        // Continue searching if target not found
        int currentVal;
        int targetVal;
        if (discriminator == X_DISCRIMINATOR) {
            currentVal = curr.getX();
            targetVal = x;
        }
        else {
            currentVal = curr.getY();
            targetVal = y;
        }

        if (targetVal < currentVal) {
            Deleted leftTree = deleteHelper(rt.left(), x, y, level + 1);
            rt.setLeft(leftTree.node);
            return new Deleted(rt, leftTree.deletedCity);
        }
        else {
            Deleted rightTree = deleteHelper(rt.right(), x, y, level + 1);
            rt.setRight(rightTree.node);
            return new Deleted(rt, rightTree.deletedCity);
        }
    }


    /**
     * Find minimum value in given discriminator of subtree
     * 
     * @param rt
     *            Root of the subtree
     * @param discriminator
     *            The discriminator to find min for
     * @param level
     *            Current level of tree
     * @return node with minimum value
     */
    private KDNode findMin(KDNode rt, int discriminator, int level) {
        if (rt == null) {
            return null;
        }
        nodesVisited++;
        int currDiscriminator = level % DIMENSION;

        if (discriminator == currDiscriminator) {
            if (rt.left() == null) {
                return rt;
            }
            return findMin(rt.left(), discriminator, level + 1);
        }
        else {
            KDNode leftMin = findMin(rt.left(), discriminator, level + 1);
            KDNode rightMin = findMin(rt.right(), discriminator, level + 1);
            return minNode(rt, leftMin, rightMin, discriminator);
        }
    }


    /**
     * Helper method to find minimum node from root, left, and right subtrees
     * 
     * @param rt
     *            Current node
     * @param temp1
     *            Min from left subtree
     * @param temp2
     *            Min from right subtree
     * @param discriminator
     *            Discriminator to compare
     * @return node with minimum value
     */
    private KDNode minNode(
        KDNode rt,
        KDNode temp1,
        KDNode temp2,
        int discriminator) {
        KDNode min = rt;
        int minVal;
        if (discriminator == X_DISCRIMINATOR) {
            minVal = rt.getCity().getX();
        }
        else {
            minVal = rt.getCity().getY();
        }
        if (temp1 != null) {
            int val1;
            if (discriminator == X_DISCRIMINATOR) {
                val1 = temp1.getCity().getX();
            }
            else {
                val1 = temp1.getCity().getY();
            }
            if (val1 < minVal) {
                min = temp1;
                minVal = val1;
            }
        }
        if (temp2 != null) {
            int val2;
            if (discriminator == X_DISCRIMINATOR) {
                val2 = temp2.getCity().getX();
            }
            else {
                val2 = temp2.getCity().getY();
            }
            if (val2 < minVal) {
                min = temp2;
                minVal = val2;
            }
        }
        return min;
    }


    /**
     * Get number of nodes visited
     * 
     * @return number of nodes visited
     */
    public int getNodesVisited() {
        return nodesVisited;
    }
}
