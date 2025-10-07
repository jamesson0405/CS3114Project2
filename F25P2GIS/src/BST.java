/**
 * Implementation of the Binary Seach Tree
 * @author Josh Kwen, James Son
 * @version 10/06/2025
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
            data = value;
            left = null;
            right = null;
        }
        
        public E value() {
            return data;
        }
        public void setValue(E val) {
            data = val;
        }
        public BSTNode<E> left() {
            return left;
        }
        public void setLeft(BSTNode<E> l) {
            left = l;
        }
        public BSTNode<E> right() {
            return right;
        }
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
     */
    /**public void insert(E value) {
        root = inserthelp(root, value);
        nodeCount++;
        
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
    */
    
    // ----------------------------------------------------------
    /**
     * Print a listing of the BST inorder traversal
     * @return String listing of BST elements
     */
    
    /**public String print() {
        StringBuilder str = new StringBuilder();
        inorder(root, str);
        return str.toString();
        
    }
    
    private void inorder(BSTNode<E> node, StringBuilder str) {
        if (node == null) {
            return;
        }
        inorder(node.left(), str);
        str.append(node.value()).append(" ");
        inorder(node.right(), str);
    }
    
    
    */

}
