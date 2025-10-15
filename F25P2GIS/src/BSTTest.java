import student.TestCase;
import java.io.IOException;

/**
 * Test class for BST
 * 
 * @author Josh Kwen, James Son
 * @version 10/15/2025
 */
public class BSTTest extends TestCase {
    
    private BST<City> it;
    private City cityA;
    private City cityB;
    private City cityC;
    private City cityD;
    private City cityE;
    
    /**
     * Set up test fixtures
     */
    public void setUp() {
        it = new BST<City>();
        cityA = new City("CityA", 100, 100);
        cityB = new City("CityB", 200, 200);
        cityC = new City("CityC", 300, 300);
        cityD = new City("CityD", 400, 400);
        cityE = new City("CityE", 500, 500);
    }
    
    /**
     * Test constructor creates empty tree
     * @throws IOException
     */
    public void testConstructor() throws IOException {
        assertEquals("", it.print());
    }
    
    /**
     * Test insert null returns false
     * @throws IOException
     */
    public void testInsertNull() throws IOException {
        assertFalse(it.insert(null));
    }
    
    /**
     * Test insert single element
     * @throws IOException
     */
    public void testInsertSingle() throws IOException {
        assertTrue(it.insert(cityA));
        String result = it.print();
        assertTrue(result.contains("CityA"));
    }
    
    /**
     * Test insert multiple elements
     * @throws IOException
     */
    public void testInsertMultiple() throws IOException {
        assertTrue(it.insert(cityB));
        assertTrue(it.insert(cityA));
        assertTrue(it.insert(cityC));
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
    }
    
    /**
     * Test insert equal value goes left
     * @throws IOException
     */
    public void testInsertEqualGoesLeft() throws IOException {
        City same1 = new City("Same", 100, 100);
        City same2 = new City("Same", 100, 100);
        assertTrue(it.insert(same1));
        assertTrue(it.insert(same2));
        String result = it.print();
        assertTrue(result.contains("Same"));
    }
    
    /**
     * Test insert builds correct tree structure - root at level 0
     * @throws IOException
     */
    public void testInsertTreeStructureRoot() throws IOException {
        it.insert(cityB);
        String result = it.print();
        assertTrue(result.contains("0CityB"));
    }
    
    /**
     * Test insert builds correct tree structure - left child at level 1
     * @throws IOException
     */
    public void testInsertTreeStructureLeft() throws IOException {
        it.insert(cityB);
        it.insert(cityA);
        String result = it.print();
        assertTrue(result.contains("1  CityA"));
        assertTrue(result.contains("0CityB"));
    }
    
    /**
     * Test insert builds correct tree structure - right child at level 1
     * @throws IOException
     */
    public void testInsertTreeStructureRight() throws IOException {
        it.insert(cityB);
        it.insert(cityC);
        String result = it.print();
        assertTrue(result.contains("0CityB"));
        assertTrue(result.contains("1  CityC"));
    }
    
    /**
     * Test clear on empty tree
     * @throws IOException
     */
    public void testClearEmpty() throws IOException {
        it.clear();
        assertEquals("", it.print());
    }
    
    /**
     * Test clear after insertions
     * @throws IOException
     */
    public void testClearAfterInsert() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        it.clear();
        assertEquals("", it.print());
    }
    
    /**
     * Test print empty tree
     * @throws IOException
     */
    public void testPrintEmpty() throws IOException {
        String result = it.print();
        assertEquals("", result);
    }
    
    /**
     * Test print single node - level 0 with no spaces
     * @throws IOException
     */
    public void testPrintSingleNode() throws IOException {
        it.insert(cityA);
        String result = it.print();
        assertTrue(result.contains("0CityA"));
    }
    
    /**
     * Test print inorder traversal
     * @throws IOException
     */
    public void testPrintInorder() throws IOException {
        it.insert(cityB);
        it.insert(cityA);
        it.insert(cityC);
        String result = it.print();
        assertFuzzyEquals("1  CityA (100, 100)\n0CityB (200, 200)\n1  "
            + "CityC (300, 300)\n", result);
    }
    
    /**
     * Test print level 1 spacing
     * @throws IOException
     */
    public void testPrintLevel1Spacing() throws IOException {
        it.insert(cityB);
        it.insert(cityA);
        String result = it.print();
        assertTrue(result.contains("1  "));
    }
    
    /**
     * Test print level 2 spacing
     * @throws IOException
     */
    public void testPrintLevel2Spacing() throws IOException {
        it.insert(cityC);
        it.insert(cityB);
        it.insert(cityA);
        String result = it.print();
        assertTrue(result.contains("2    "));
    }
    
    /**
     * Test print level 3 spacing
     * @throws IOException
     */
    public void testPrintLevel3Spacing() throws IOException {
        it.insert(cityD);
        it.insert(cityC);
        it.insert(cityB);
        it.insert(cityA);
        String result = it.print();
        assertTrue(result.contains("3      "));
    }
    
    /**
     * Test findByName in empty tree
     * @throws IOException
     */
    public void testFindByNameEmpty() throws IOException {
        String result = it.findByName("CityA");
        assertEquals("", result);
    }
    
    /**
     * Test findByName with no matches
     * @throws IOException
     */
    public void testFindByNameNoMatch() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        String result = it.findByName("CityZ");
        assertEquals("", result);
    }
    
    /**
     * Test findByName with single match
     * @throws IOException
     */
    public void testFindByNameSingleMatch() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        String result = it.findByName("CityA");
        assertTrue(result.contains("(100, 100)"));
        assertFalse(result.contains("200"));
    }
    
    /**
     * Test findByName with multiple matches
     * @throws IOException
     */
    public void testFindByNameMultipleMatches() throws IOException {
        City same1 = new City("Same", 100, 100);
        City same2 = new City("Same", 200, 200);
        City same3 = new City("Same", 300, 300);
        it.insert(same1);
        it.insert(same2);
        it.insert(same3);
        String result = it.findByName("Same");
        assertTrue(result.contains("(100, 100)"));
        assertTrue(result.contains("(200, 200)"));
        assertTrue(result.contains("(300, 300)"));
    }
    
    /**
     * Test findByName format with parentheses
     * @throws IOException
     */
    public void testFindByNameFormat() throws IOException {
        it.insert(cityA);
        String result = it.findByName("CityA");
        assertTrue(result.contains("(100, 100)"));
        assertTrue(result.contains("("));
        assertTrue(result.contains(")"));
        assertTrue(result.contains(","));
    }
    
    /**
     * Test delete null returns false
     * @throws IOException
     */
    public void testDeleteNull() throws IOException {
        assertFalse(it.delete(null));
    }
    
    /**
     * Test delete from empty tree
     * @throws IOException
     */
    public void testDeleteEmpty() throws IOException {
        assertFalse(it.delete(cityA));
    }
    
    /**
     * Test delete non-existent element
     * @throws IOException
     */
    public void testDeleteNonExistent() throws IOException {
        it.insert(cityA);
        assertFalse(it.delete(cityB));
    }
    
    /**
     * Test delete leaf node
     * @throws IOException
     */
    public void testDeleteLeaf() throws IOException {
        it.insert(cityB);
        it.insert(cityA);
        assertTrue(it.delete(cityA));
        String result = it.print();
        assertFalse(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
    }
    
    /**
     * Test delete root with no children
     * @throws IOException
     */
    public void testDeleteRootNoChildren() throws IOException {
        it.insert(cityA);
        assertTrue(it.delete(cityA));
        assertEquals("", it.print());
    }
    
    /**
     * Test delete node with only left child
     * @throws IOException
     */
    public void testDeleteNodeOnlyLeftChild() throws IOException {
        it.insert(cityC);
        it.insert(cityB);
        it.insert(cityA);
        assertTrue(it.delete(cityB));
        String result = it.print();
        assertFalse(result.contains("CityB"));
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityC"));
    }
    
    /**
     * Test delete node with only right child
     * @throws IOException
     */
    public void testDeleteNodeOnlyRightChild() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        it.insert(cityC);
        assertTrue(it.delete(cityB));
        String result = it.print();
        assertFalse(result.contains("CityB"));
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityC"));
    }
    
    /**
     * Test delete node with two children
     * @throws IOException
     */
    public void testDeleteNodeTwoChildren() throws IOException {
        it.insert(cityC);
        it.insert(cityA);
        it.insert(cityE);
        it.insert(cityB);
        assertTrue(it.delete(cityC));
        String result = it.print();
        assertFalse(result.contains("CityC"));
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
        assertTrue(result.contains("CityE"));
    }
    
    /**
     * Test delete searches left subtree
     * @throws IOException
     */
    public void testDeleteSearchLeft() throws IOException {
        it.insert(cityC);
        it.insert(cityA);
        assertTrue(it.delete(cityA));
    }
    
    /**
     * Test delete searches right subtree
     * @throws IOException
     */
    public void testDeleteSearchRight() throws IOException {
        it.insert(cityA);
        it.insert(cityC);
        assertTrue(it.delete(cityC));
    }
    
    /**
     * Test delete with equal comparison
     * @throws IOException
     */
    public void testDeleteEqualComparison() throws IOException {
        City same = new City("CityA", 100, 100);
        it.insert(cityA);
        assertTrue(it.delete(same));
    }
    
    /**
     * Test findAndDeleteFirstElement on null tree
     * @throws IOException
     */
    public void testFindAndDeleteFirstNull() throws IOException {
        assertNull(it.findAndDeleteFirstElement(cityA));
    }
    
    /**
     * Test findAndDeleteFirstElement with null element
     * @throws IOException
     */
    public void testFindAndDeleteFirstNullElement() throws IOException {
        it.insert(cityA);
        assertNull(it.findAndDeleteFirstElement(null));
    }
    
    /**
     * Test findAndDeleteFirstElement not found
     * @throws IOException
     */
    public void testFindAndDeleteFirstNotFound() throws IOException {
        it.insert(cityA);
        assertNull(it.findAndDeleteFirstElement(cityB));
    }
    
    /**
     * Test findAndDeleteFirstElement at root
     * @throws IOException
     */
    public void testFindAndDeleteFirstAtRoot() throws IOException {
        it.insert(cityA);
        City result = it.findAndDeleteFirstElement(cityA);
        assertNotNull(result);
        assertEquals("CityA", result.getName());
        assertEquals("", it.print());
    }
    
    /**
     * Test findAndDeleteFirstElement in left subtree
     * @throws IOException
     */
    public void testFindAndDeleteFirstInLeft() throws IOException {
        it.insert(cityB);
        it.insert(cityA);
        City result = it.findAndDeleteFirstElement(cityA);
        assertNotNull(result);
        assertEquals("CityA", result.getName());
        assertFalse(it.print().contains("CityA"));
    }
    
    /**
     * Test findAndDeleteFirstElement in right subtree
     * @throws IOException
     */
    public void testFindAndDeleteFirstInRight() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        City result = it.findAndDeleteFirstElement(cityB);
        assertNotNull(result);
        assertEquals("CityB", result.getName());
        assertFalse(it.print().contains("CityB"));
    }
    
    
    /**
     * Test insert with less than comparison
     * @throws IOException
     */
    public void testInsertLessThan() throws IOException {
        it.insert(cityB);
        it.insert(cityA);
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
    }
    
    /**
     * Test insert with greater than comparison
     * @throws IOException
     */
    public void testInsertGreaterThan() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
    }
    
    /**
     * Test insert with same name different coords
     * @throws IOException
     */
    public void testInsertSameNameDiffCoords() throws IOException {
        City same1 = new City("Same", 200, 200);
        City same2 = new City("Same", 100, 100);
        it.insert(same1);
        it.insert(same2);
        String result = it.print();
        assertTrue(result.contains("(100, 100)"));
        assertTrue(result.contains("(200, 200)"));
    }
    
    /**
     * Test multiple deletes
     * @throws IOException
     */
    public void testMultipleDeletes() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        it.insert(cityC);
        assertTrue(it.delete(cityA));
        assertTrue(it.delete(cityB));
        assertTrue(it.delete(cityC));
        assertEquals("", it.print());
    }
    
    /**
     * Test delete then insert
     * @throws IOException
     */
    public void testDeleteThenInsert() throws IOException {
        it.insert(cityA);
        it.delete(cityA);
        it.insert(cityB);
        String result = it.print();
        assertFalse(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
    }
    
    /**
     * Test deep tree structure - level 4
     * @throws IOException
     */
    public void testDeepTree() throws IOException {
        it.insert(cityE);
        it.insert(cityD);
        it.insert(cityC);
        it.insert(cityB);
        it.insert(cityA);
        String result = it.print();
        assertTrue(result.contains("4        CityA"));
    }
    
    /**
     * Test balanced tree structure
     * @throws IOException
     */
    public void testBalancedTree() throws IOException {
        it.insert(cityC);
        it.insert(cityB);
        it.insert(cityD);
        it.insert(cityA);
        it.insert(cityE);
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
        assertTrue(result.contains("CityD"));
        assertTrue(result.contains("CityE"));
    }
    
    /**
     * Test delete uses findMax from left
     * @throws IOException
     */
    public void testDeleteUsesFindMax() throws IOException {
        it.insert(cityC);
        it.insert(cityA);
        it.insert(cityB);
        it.insert(cityE);
        it.delete(cityC);
        String result = it.print();
        assertFalse(result.contains("CityC"));
        assertTrue(result.contains("CityB"));
    }
    
    /**
     * Test insert after clear
     * @throws IOException
     */
    public void testInsertAfterClear() throws IOException {
        it.insert(cityA);
        it.clear();
        it.insert(cityB);
        String result = it.print();
        assertFalse(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
    }
    
    /**
     * Test findByName traverses inorder
     * @throws IOException
     */
    public void testFindByNameInorder() throws IOException {
        City a = new City("Same", 300, 300);
        City b = new City("Same", 100, 100);
        City c = new City("Same", 200, 200);
        it.insert(a);
        it.insert(b);
        it.insert(c);
        String result = it.findByName("Same");
        assertTrue(result.contains("(100, 100)"));
        assertTrue(result.contains("(200, 200)"));
        assertTrue(result.contains("(300, 300)"));
    }
    
    /**
     * Test delete leaf from left side
     * @throws IOException
     */
    public void testDeleteLeafLeft() throws IOException {
        it.insert(cityC);
        it.insert(cityA);
        it.insert(cityD);
        assertTrue(it.delete(cityA));
        String result = it.print();
        assertFalse(result.contains("CityA"));
    }
    
    /**
     * Test delete leaf from right side
     * @throws IOException
     */
    public void testDeleteLeafRight() throws IOException {
        it.insert(cityC);
        it.insert(cityA);
        it.insert(cityD);
        assertTrue(it.delete(cityD));
        String result = it.print();
        assertFalse(result.contains("CityD"));
    }
    
    /**
     * Test complex delete scenario
     * @throws IOException
     */
    public void testComplexDelete() throws IOException {
        it.insert(cityC);
        it.insert(cityA);
        it.insert(cityE);
        it.insert(cityB);
        it.insert(cityD);
        it.delete(cityA);
        it.delete(cityE);
        String result = it.print();
        assertFalse(result.contains("CityA"));
        assertFalse(result.contains("CityE"));
        assertTrue(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
        assertTrue(result.contains("CityD"));
    }
    
    /**
     * Test findAndDelete removes from tree
     * @throws IOException
     */
    public void testFindAndDeleteRemoves() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        it.insert(cityC);
        it.findAndDeleteFirstElement(cityB);
        String result = it.print();
        assertFalse(result.contains("CityB"));
    }
    
    /**
     * Test empty after all deletes
     * @throws IOException
     */
    public void testEmptyAfterDeletes() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        it.delete(cityA);
        it.delete(cityB);
        assertEquals("", it.print());
    }
    
    /**
     * Test insert comparison at boundaries
     * @throws IOException
     */
    public void testInsertComparisonBoundaries() throws IOException {
        it.insert(cityB);
        assertTrue(it.insert(cityA));
        assertTrue(it.insert(cityC));
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
    }
    
    /**
     * Test delete comparison less than
     * @throws IOException
     */
    public void testDeleteComparisonLess() throws IOException {
        it.insert(cityC);
        it.insert(cityB);
        it.insert(cityA);
        assertTrue(it.delete(cityA));
    }
    
    /**
     * Test delete comparison greater than
     * @throws IOException
     */
    public void testDeleteComparisonGreater() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        it.insert(cityC);
        assertTrue(it.delete(cityC));
    }
    
    /**
     * Test insert increases tree size
     * @throws IOException
     */
    public void testInsertIncreasesSize() throws IOException {
        it.insert(cityA);
        String result1 = it.print();
        assertTrue(result1.contains("CityA"));
        
        it.insert(cityB);
        String result2 = it.print();
        assertTrue(result2.contains("CityA"));
        assertTrue(result2.contains("CityB"));
        
        // If nodeCount++ is mutated, subsequent operations may fail
        it.insert(cityC);
        String result3 = it.print();
        assertTrue(result3.contains("CityC"));
    }

    /**
     * Test multiple inserts all appear in tree
     * @throws IOException
     */
    public void testMultipleInsertsAllPresent() throws IOException {
        assertTrue(it.insert(cityA));
        assertTrue(it.insert(cityB));
        assertTrue(it.insert(cityC));
        assertTrue(it.insert(cityD));
        assertTrue(it.insert(cityE));
        
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
        assertTrue(result.contains("CityD"));
        assertTrue(result.contains("CityE"));
    }

    /**
     * Test insert then delete affects count correctly
     * @throws IOException
     */
    public void testInsertDeleteCount() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        it.insert(cityC);
        
        assertTrue(it.delete(cityB));
        
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertFalse(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
    }


    /**
     * Test insert null doesn't affect count
     * @throws IOException
     */
    public void testInsertNullNoCountChange() throws IOException {
        it.insert(cityA);
        assertFalse(it.insert(null));
        it.insert(cityB);
        
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertTrue(result.contains("CityB"));
    }

    /**
     * Test delete returns false when count would be wrong
     * @throws IOException
     */
    public void testDeleteReturnValue() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        
        assertTrue(it.delete(cityA));
        assertFalse(it.delete(cityA)); // Should return false - already deleted
    }

    /**
     * Test insert after multiple deletes
     * @throws IOException
     */
    public void testInsertAfterMultipleDeletes() throws IOException {
        it.insert(cityA);
        it.insert(cityB);
        it.insert(cityC);
        
        it.delete(cityA);
        it.delete(cityB);
        
        it.insert(cityD);
        it.insert(cityE);
        
        String result = it.print();
        assertFalse(result.contains("CityA"));
        assertFalse(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
        assertTrue(result.contains("CityD"));
        assertTrue(result.contains("CityE"));
    }

    /**
     * Test alternating insert and delete
     * @throws IOException
     */
    public void testAlternatingInsertDelete() throws IOException {
        it.insert(cityA);
        it.delete(cityA);
        it.insert(cityB);
        it.delete(cityB);
        it.insert(cityC);
        
        String result = it.print();
        assertFalse(result.contains("CityA"));
        assertFalse(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
    }

    /**
     * Test delete leaf maintains proper count
     * @throws IOException
     */
    public void testDeleteLeafProperCount() throws IOException {
        it.insert(cityC);
        it.insert(cityA);
        it.insert(cityE);
        it.insert(cityB);
        it.insert(cityD);
        
        assertTrue(it.delete(cityB));
        assertTrue(it.delete(cityD));
        
        String result = it.print();
        assertTrue(result.contains("CityA"));
        assertFalse(result.contains("CityB"));
        assertTrue(result.contains("CityC"));
        assertFalse(result.contains("CityD"));
        assertTrue(result.contains("CityE"));
    }

    /**
     * Test delete with two children maintains count
     * @throws IOException
     */
    public void testDeleteTwoChildrenMaintainsCount() throws IOException {
        it.insert(cityC);
        it.insert(cityA);
        it.insert(cityE);
        it.insert(cityB);
        it.insert(cityD);
        
        assertTrue(it.delete(cityC)); // Has two children
        
        it.insert(new City("CityF", 600, 600));
        
        String result = it.print();
        assertFalse(result.contains("CityC"));
        assertTrue(result.contains("CityF"));
    }

}