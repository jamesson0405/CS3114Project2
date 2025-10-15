/**
 * Implementation of the Binary Search Tree
 * @author Josh Kwen, James Son
 * @version 10/14/2025
 *
 * @param <E> type of elements
 */
public class BST<E extends Comparable<E>> {
    private BSTNode<E> root; // Root of the BST
    private int nodeCount;   // Number of nodes

    private static class BSTNode<E> {
        E data;
        BSTNode<E> left;
        BSTNode<E> right;

        /**
         * Constructor for BSTNode
         * @param value value to store
         */
        BSTNode(E value) {
            this.data = value;
            this.left = null;
            this.right = null;
        }

        /**
         * Get value stored in node
         * @return the data of node
         */
        public E value() {
            return data;
        }

        /**
         * Set value of node
         * @param val new value of node
         */
        public void setValue(E val) {
            data = val;
        }

        /**
         * Get left node
         * @return left node
         */
        public BSTNode<E> left() {
            return left;
        }

        /**
         * Set left node
         * @param l new left node
         */
        public void setLeft(BSTNode<E> l) {
            left = l;
        }

        /**
         * Get right node
         * @return right node
         */
        public BSTNode<E> right() {
            return right;
        }

        /**
         * Set right node
         * @param r new right node
         */
        public void setRight(BSTNode<E> r) {
            right = r;
        }
    }

    // ----------------------------------------------------------
    /**
     * Constructor for empty BST
     */
    public BST() {
        root = null;
        nodeCount = 0;
    }

    // ----------------------------------------------------------
    /**
     * Reinitialize the BST
     */
    public void clear() {
        root = null;
        nodeCount = 0;
    }

    // ----------------------------------------------------------
    /**
     * Insert a new value into the BST
     * @param value Value to be inserted
     * @return true if inserted successfully
     */
    public boolean insert(E value) {
        if (value == null) {
            return false;
        }
        root = inserthelp(root, value);
        nodeCount++;
        return true;
    }

    /**
     * Helper method for insert
     * @param rt current node
     * @param e element to insert
     * @return node after insertion
     */
    private BSTNode<E> inserthelp(BSTNode<E> rt, E e) {
        if (rt == null) {
            return new BSTNode<E>(e);
        }
        if (e.compareTo(rt.value()) <= 0) {
            rt.setLeft(inserthelp(rt.left(), e));
        }
        else {
            rt.setRight(inserthelp(rt.right(), e));
        }
        return rt;
    }

    // ----------------------------------------------------------
    /**
     * Print a listing of the BST inorder traversal
     * @return String listing of BST elements
     */
    public String print() {
        StringBuilder str = new StringBuilder();
        printhelp(root, 0, str);
        return str.toString();
    }

    /**
     * Helper method for print
     * @param rt current node
     * @param level current level
     * @param str string builder for output
     */
    private void printhelp(BSTNode<E> rt, int level, StringBuilder str) {
        if (rt == null) {
            return;
        }
        printhelp(rt.left(), level + 1, str);
        str.append(level);
        for (int i = 0; i < level * 2; i++) {
            str.append(" ");
        }
        str.append(rt.value().toString());
        str.append("\n");
        printhelp(rt.right(), level + 1, str);
    }

    // ----------------------------------------------------------
    /**
     * Get size of BST
     * @return number of nodes
     */
    /**public int size() {
        return nodeCount;
    }*/

    /**
     * Check if BST is empty
     * @return true if tree is empty
     */
    /**public boolean isEmpty() {
        return root == null;
    }*/

    /**
     * Get root of BST
     * @return root of tree
     */
    /**public BSTNode<E> getRoot() {
        return root;
    }*/

    // ----------------------------------------------------------
    /**
     * Find all elements with matching name (for City objects)
     * @param name name to search for
     * @return String with coordinates of all matching cities
     */
    public String findByName(String name) {
        StringBuilder result = new StringBuilder();
        findByNameHelp(root, name, result);
        return result.toString();
    }

    /**
     * Helper method for findByName
     * @param rt current node
     * @param name name to search for
     * @param result StringBuilder for output
     */
    private void findByNameHelp(BSTNode<E> rt, String name, StringBuilder result) {
        if (rt == null) {
            return;
        }

        // Inorder traversal to find all matches
        findByNameHelp(rt.left(), name, result);

        // Check if current node matches
        if (rt.value() instanceof City) {
            City city = (City) rt.value();
            if (city.getName().equals(name)) {
                result.append("(");
                result.append(city.getX());
                result.append(", ");
                result.append(city.getY());
                result.append(")");
                result.append("\n");
            }
        }

        findByNameHelp(rt.right(), name, result);
    }
    
    /**
     * Delete element from the BST
     * @param element Element to be deleted
     * @return true if deleted, false otherwise
     */
    public boolean delete(E element) {
        if (element == null) {
            return false;
        }
        int currSize = nodeCount;
        root = deleteHelper(root, element);
        return nodeCount < currSize;
    }
    
    /**
     * Helper method for deletion
     * @param rt Current node
     * @param element Element to delete
     * @return the modified BST
     */
    private BSTNode<E> deleteHelper(BSTNode<E> rt, E element) {
        if (rt == null) {
            return null;
        }
        int comp = element.compareTo(rt.value());
        if (comp == 0) {
            nodeCount--;
            if (rt.left() == null && rt.right() == null) {
                return null;
            }
            else if (rt.left() == null) {
                return rt.right();
            }
            else if (rt.right() == null) {
                return rt.left();
            }
            else {
                BSTNode<E> max = findMax(rt.left());
                rt.setValue(max.value());
                rt.setLeft(deleteHelper(rt.left(), max.value()));
                nodeCount++;
            }
            return rt;
        }
        else if (comp < 0) {
            rt.setLeft(deleteHelper(rt.left(), element));
        }
        else {
            rt.setRight(deleteHelper(rt.right(), element));
        }
        return rt;

    }
    
    /**
     * Find and delete the first matching element using preorder
     * @param element Element to match for
     * @return deleted element
     */
    public E findAndDeleteFirstElement(E element) {
        if (root == null || element == null) {
            return null;
        }
        E found = findFirstMatchingElement(root, element);
        if (found == null) {
            return null;
        }
        delete(found);
        return found;
    }
    
    /**
     * Helper to find first matching element
     * @param rt Current node
     * @param element Element to match for
     * @return first matching element
     */
    private E findFirstMatchingElement(BSTNode<E> rt, E element) {
        if (rt == null) {
            return null;
        }
        // Check the current node
        if (element.compareTo(rt.value()) == 0) {
            return rt.value();
        }
        // Check left subtree
        E leftTree = findFirstMatchingElement(rt.left(), element);
        if (leftTree != null) {
            return leftTree;
        }
        // right subtree
        return findFirstMatchingElement(rt.right(), element);
    }
    
    /**
     * Find the max node in the subtree
     * @param rt Current node
     * @return node with max value
     */
    private BSTNode<E> findMax(BSTNode<E> rt) {
        if (rt.right() == null) {
            return rt;
        }
        return findMax(rt.right());
    }

}
