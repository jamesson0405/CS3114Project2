/**
 * Implementation of the Binary Seach Tree
 * @author Josh Kwen, James Son
 * @version 10/07/2025
 * 
 * @param <E> type of elements
 */

public class BST<E extends Comparable<E>> {
    private BSTNode<E> root; // Root of the BST
    private int nodeCount; // Number of nodes
    
    private static class BSTNode<E> {
        E data;
        BSTNode<E> left;
        BSTNode<E> right;
        
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
     * reinitialize the BST
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
     * Check is BST is empty
     * @return true if tree is empty
     */
    /**public boolean isEmpty() {
        return root == null;
    }
    */
    /**
     * Get root of BST
     * @return root of tree
     */
    /**public BSTNode<E> getRoot() {
        return root;
    }
    */
    
    
    

}
