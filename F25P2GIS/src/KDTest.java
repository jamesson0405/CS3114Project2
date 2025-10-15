import student.TestCase;
import java.io.IOException;

/**
 * Test class for KdTree
 * 
 * @author Josh Kwen, James Son
 * @version 10/15/2025
 * @throws IOException
 */
public class KDTest extends TestCase {

    private KDTree it;
    private City city1;
    private City city2;
    private City city3;

    /**
     * Set up test fixtures
     */
    public void setUp() {
        it = new KDTree();
        city1 = new City("CityA", 100, 200);
        city2 = new City("CityB", 150, 250);
        city3 = new City("CityC", 50, 150);
    }
    
    /**
     * Test null city
     * @throws IOException
     */
    public void testInsertNullCity() throws IOException {
        assertFalse(it.insert(null));
    }
    
    /**
     * Test comparing X discriminator on the left side of tree
     * @throws IOException
     */
    public void testXDiscriminatorLeft() throws IOException {
        it.insert(city1);
        it.insert(city3);
        assertTrue(it.findDuplicate(50, 150));
        
    }
    
    /**
     * Test comparing X discriminator on right side
     * @throws IOException
     */
    public void testXDiscriminatorRight() throws IOException {
        it.insert(city1);
        it.insert(city2);
        assertTrue(it.findDuplicate(150, 250));
    }
    
    /**
     * Test Y discriminator on left
     * @throws IOException
     */
    public void testYDiscriminatorLeft() throws IOException {
        it.insert(city1);
        it.insert(city3);
        it.insert(new City("City4", 20, 130));
        assertTrue(it.findDuplicate(20, 130));
    }
    
    /**
     * Test Y discriminator on right
     * @throws IOException
     */
    public void testYDiscriminatorRight() throws IOException {
        it.insert(city1);
        it.insert(city3);
        it.insert(new City("City5", 20, 180));
        assertTrue(it.findDuplicate(20, 180));
    }
    
    /**
     * Test duplicates for existing coordinates
     * @throws IOException
     */
    public void testFindExistentDup() throws IOException {
        it.insert(city1);
        it.insert(city2);
        assertTrue(it.findDuplicate(100, 200));
        assertTrue(it.findDuplicate(150, 250));
    }
    
    /**
     * Test duplicate for nonexisting coordinates
     * @throws IOException
     */
    public void testFindNonExistentDup() {
        it.insert(city1);
        assertFalse(it.findDuplicate(200, 200));
    }
    
    /**
     * Test finding nonexistent city
     * @throws IOException
     */
    public void testFindNonExistentCity() throws IOException {
        it.insert(city1);
        assertNull(it.findCity(200, 200));
    }
    
    /**
     * Test finding existing city
     * @throws IOException
     */
    public void testFindExistingCity() throws IOException {
        it.insert(city1);
        assertEquals("CityA", it.findCity(100, 200).getName());
    }
    
    /**
     * Test find duplicate in empty tree
     * @throws IOException
     */
    public void testFindDuplicateEmptyTree() throws IOException {
        assertFalse(it.findDuplicate(100, 100));
    }

    /**
     * Test find city in empty tree
     * @throws IOException
     */
    public void testFindCityEmptyTree() throws IOException {
        assertNull(it.findCity(100, 100));
    }

    /**
     * Test find duplicate at root
     * @throws IOException
     */
    public void testFindDuplicateAtRoot() throws IOException {
        it.insert(city1);
        assertTrue(it.findDuplicate(100, 200));
    }

    /**
     * Test find city at root
     * @throws IOException
     */
    public void testFindCityAtRoot() throws IOException {
        it.insert(city1);
        assertEquals(city1, it.findCity(100, 200));
    }

    /**
     * Test find duplicate with equal X values (goes right)
     * @throws IOException
     */
    public void testFindDuplicateEqualX() throws IOException {
        it.insert(new City("A", 100, 100));
        it.insert(new City("B", 100, 200));
        it.insert(new City("C", 100, 50));
        assertTrue(it.findDuplicate(100, 200));
        assertTrue(it.findDuplicate(100, 50));
    }

    /**
     * Test find duplicate with equal Y values (goes right)
     * @throws IOException
     */
    public void testFindDuplicateEqualY() throws IOException {
        it.insert(new City("A", 100, 100));
        it.insert(new City("B", 50, 100));
        it.insert(new City("C", 25, 100));
        assertTrue(it.findDuplicate(25, 100));
    }

    /**
     * Test find city with equal X values
     * @throws IOException
     */
    public void testFindCityEqualX() throws IOException {
        City a = new City("A", 100, 100);
        City b = new City("B", 100, 200);
        it.insert(a);
        it.insert(b);
        assertEquals(b, it.findCity(100, 200));
    }

    /**
     * Test find city with equal Y values
     * @throws IOException
     */
    public void testFindCityEqualY() throws IOException {
        City a = new City("A", 100, 100);
        City b = new City("B", 50, 100);
        City c = new City("C", 25, 100);
        it.insert(a);
        it.insert(b);
        it.insert(c);
        assertEquals(c, it.findCity(25, 100));
    }

    /**
     * Test region search on empty tree
     * @throws IOException
     */
    public void testRegionSearchEmpty() throws IOException {
        String result = it.regionSearch(100, 100, 50);
        assertTrue(result.contains("Nodes Visited: 0"));
        assertFalse(result.contains("City"));
    }

    /**
     * Test region search finds city at exact location
     * @throws IOException
     */
    public void testRegionSearchExactLocation() throws IOException {
        it.insert(city1);
        String result = it.regionSearch(100, 200, 10);
        assertTrue(result.contains("CityA"));
    }

    /**
     * Test region search with radius 0
     * @throws IOException
     */
    public void testRegionSearchRadiusZero() throws IOException {
        it.insert(city1);
        String result = it.regionSearch(100, 200, 0);
        assertTrue(result.contains("CityA"));
    }

    /**
     * Test region search at exact boundary (distance = radius)
     * @throws IOException
     */
    public void testRegionSearchExactBoundary() throws IOException {
        it.insert(new City("Origin", 0, 0));
        it.insert(new City("AtRadius", 3, 4));
        String result = it.regionSearch(0, 0, 5);
        assertTrue(result.contains("Origin"));
        assertTrue(result.contains("AtRadius"));
    }

    /**
     * Test region search excludes cities outside radius
     * @throws IOException
     */
    public void testRegionSearchExcludesOutside() throws IOException {
        it.insert(new City("Near", 100, 100));
        it.insert(new City("Far", 200, 200));
        String result = it.regionSearch(100, 100, 50);
        assertTrue(result.contains("Near"));
        assertFalse(result.contains("Far"));
    }

    /**
     * Test region search crosses split plane when needed
     * @throws IOException
     */
    public void testRegionSearchCrossesSplit() throws IOException {
        it.insert(new City("Center", 100, 100));
        it.insert(new City("Left", 50, 100));
        it.insert(new City("Right", 150, 100));
        String result = it.regionSearch(100, 100, 60);
        assertTrue(result.contains("Center"));
        assertTrue(result.contains("Left"));
        assertTrue(result.contains("Right"));
    }

    /**
     * Test region search doesn't cross when distance to split > radius
     * @throws IOException
     */
    public void testRegionSearchNoCrossing() throws IOException {
        it.insert(new City("Center", 1000, 1000));
        it.insert(new City("Far", 2000, 1000));
        String result = it.regionSearch(1000, 1000, 50);
        assertTrue(result.contains("Center"));
        assertFalse(result.contains("Far"));
    }

    /**
     * Test region search with negative center coordinates
     * @throws IOException
     */
    public void testRegionSearchNegativeCenter() throws IOException {
        it.insert(new City("City", 100, 100));
        String result = it.regionSearch(-100, -100, 300);
        assertTrue(result.contains("City"));
    }

    /**
     * Test region search counts nodes visited
     * @throws IOException
     */
    public void testRegionSearchCountsNodes() throws IOException {
        it.insert(new City("A", 100, 100));
        it.insert(new City("B", 200, 200));
        it.insert(new City("C", 50, 50));
        String result = it.regionSearch(100, 100, 50);
        assertTrue(result.contains("Nodes Visited:"));
        assertTrue(result.contains("3") || result.contains("2") 
            || result.contains("1"));
    }

    /**
     * Test delete on empty tree
     * @throws IOException
     */
    public void testDeleteEmptyTree() throws IOException {
        City result = it.delete(100, 100);
        assertNull(result);
        assertEquals(0, it.getNodesVisited());
    }

    /**
     * Test delete leaf node
     * @throws IOException
     */
    public void testDeleteLeaf() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("Leaf", 50, 50));
        City deleted = it.delete(50, 50);
        assertEquals("Leaf", deleted.getName());
        assertNull(it.findCity(50, 50));
    }

    /**
     * Test delete root with no children
     * @throws IOException
     */
    public void testDeleteRootNoChildren() throws IOException {
        it.insert(city1);
        City deleted = it.delete(100, 200);
        assertEquals("CityA", deleted.getName());
        assertNull(it.getRoot());
    }

    /**
     * Test delete node with only right child
     * @throws IOException
     */
    public void testDeleteNodeOnlyRightChild() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("Parent", 150, 150));
        it.insert(new City("Right", 200, 200));
        City deleted = it.delete(150, 150);
        assertEquals("Parent", deleted.getName());
        assertNull(it.findCity(150, 150));
        assertNotNull(it.findCity(200, 200));
    }

    /**
     * Test delete node with only left child (moves to right)
     * @throws IOException
     */
    public void testDeleteNodeOnlyLeftChild() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("Parent", 50, 150));
        it.insert(new City("Left", 25, 175));
        City deleted = it.delete(50, 150);
        assertEquals("Parent", deleted.getName());
        assertNull(it.findCity(50, 150));
        assertNotNull(it.findCity(25, 175));
    }

    /**
     * Test delete node with two children
     * @throws IOException
     */
    public void testDeleteNodeTwoChildren() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("Parent", 150, 150));
        it.insert(new City("Left", 125, 175));
        it.insert(new City("Right", 175, 125));
        City deleted = it.delete(150, 150);
        assertEquals("Parent", deleted.getName());
        assertNull(it.findCity(150, 150));
    }

    /**
     * Test delete non-existent city
     * @throws IOException
     */
    public void testDeleteNonExistent() throws IOException {
        it.insert(city1);
        City deleted = it.delete(200, 200);
        assertNull(deleted);
    }

    /**
     * Test delete searches left subtree
     * @throws IOException
     */
    public void testDeleteSearchLeft() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("Left", 50, 50));
        City deleted = it.delete(50, 50);
        assertEquals("Left", deleted.getName());
    }

    /**
     * Test delete searches right subtree
     * @throws IOException
     */
    public void testDeleteSearchRight() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("Right", 150, 150));
        City deleted = it.delete(150, 150);
        assertEquals("Right", deleted.getName());
    }

    /**
     * Test delete counts nodes visited
     * @throws IOException
     */
    public void testDeleteCountsNodes() throws IOException {
        it.insert(new City("A", 100, 100));
        it.insert(new City("B", 200, 200));
        it.delete(200, 200);
        assertTrue(it.getNodesVisited() >= 1);
    }

    /**
     * Test delete with equal X values
     * @throws IOException
     */
    public void testDeleteEqualX() throws IOException {
        it.insert(new City("A", 100, 100));
        it.insert(new City("B", 100, 200));
        City deleted = it.delete(100, 200);
        assertEquals("B", deleted.getName());
    }

    /**
     * Test delete with equal Y values
     * @throws IOException
     */
    public void testDeleteEqualY() throws IOException {
        it.insert(new City("A", 100, 100));
        it.insert(new City("B", 50, 100));
        it.insert(new City("C", 25, 100));
        City deleted = it.delete(25, 100);
        assertEquals("C", deleted.getName());
    }

    /**
     * Test findMin when discriminator matches
     * @throws IOException
     */
    public void testFindMinMatchingDiscriminator() throws IOException {
        it.insert(new City("Root", 500, 500));
        it.insert(new City("Left", 400, 600));
        it.insert(new City("LeftLeft", 300, 700));
        it.delete(500, 500);
        assertNull(it.findCity(500, 500));
    }

    /**
     * Test findMin when discriminator doesn't match
     * @throws IOException
     */
    public void testFindMinNonMatchingDiscriminator() throws IOException {
        it.insert(new City("Root", 500, 500));
        it.insert(new City("A", 600, 400));
        it.insert(new City("B", 550, 350));
        it.insert(new City("C", 650, 450));
        it.delete(600, 400);
        assertNull(it.findCity(600, 400));
    }

    /**
     * Test minNode with left having minimum
     * @throws IOException
     */
    public void testMinNodeLeftMin() throws IOException {
        it.insert(new City("Root", 500, 500));
        it.insert(new City("Right", 600, 600));
        it.insert(new City("RightLeft", 550, 650));
        it.insert(new City("RightRight", 700, 550));
        it.delete(600, 600);
        assertNull(it.findCity(600, 600));
        assertNotNull(it.findCity(550, 650));
    }

    /**
     * Test minNode with right having minimum
     * @throws IOException
     */
    public void testMinNodeRightMin() throws IOException {
        it.insert(new City("Root", 500, 500));
        it.insert(new City("Right", 600, 600));
        it.insert(new City("RightLeft", 650, 650));
        it.insert(new City("RightRight", 550, 550));
        it.delete(600, 600);
        assertNull(it.findCity(600, 600));
        assertNotNull(it.findCity(550, 550));
    }

    /**
     * Test minNode with root having minimum
     * @throws IOException
     */
    public void testMinNodeRootMin() throws IOException {
        it.insert(new City("Root", 500, 500));
        it.insert(new City("Right", 600, 600));
        it.insert(new City("RightLeft", 700, 650));
        it.insert(new City("RightRight", 800, 550));
        it.delete(600, 600);
        assertNull(it.findCity(600, 600));
    }

    /**
     * Test debug empty tree
     * @throws IOException
     */
    public void testDebugEmpty() throws IOException {
        String result = it.debug();
        assertEquals("", result);
    }

    /**
     * Test debug single node
     * @throws IOException
     */
    public void testDebugSingleNode() throws IOException {
        it.insert(city1);
        String result = it.debug();
        assertTrue(result.startsWith("0"));
        assertTrue(result.contains("CityA"));
    }

    /**
     * Test debug multiple levels
     * @throws IOException
     */
    public void testDebugMultipleLevels() throws IOException {
        it.insert(new City("L0", 100, 100));
        it.insert(new City("L1", 50, 50));
        it.insert(new City("L2", 25, 25));
        String result = it.debug();
        assertTrue(result.contains("0"));
        assertTrue(result.contains("1  "));
        assertTrue(result.contains("2    "));
    }


    /**
     * Test discriminator cycles correctly
     * @throws IOException
     */
    public void testDiscriminatorCycling() throws IOException {
        it.insert(new City("Level0", 500, 500)); 
        it.insert(new City("Level1", 400, 600)); 
        it.insert(new City("Level2", 450, 700)); 
        it.insert(new City("Level3", 425, 650)); 
        assertNotNull(it.findCity(425, 650));
    }

    /**
     * Test clear resets tree completely
     * @throws IOException
     */
    public void testClearResetsTree() throws IOException {
        it.insert(city1);
        it.insert(city2);
        it.insert(city3);
        it.clear();
        assertNull(it.getRoot());
        assertFalse(it.findDuplicate(100, 200));
    }

    /**
     * Test multiple deletes
     * @throws IOException
     */
    public void testMultipleDeletes() throws IOException {
        it.insert(new City("A", 100, 100));
        it.insert(new City("B", 200, 200));
        it.insert(new City("C", 50, 50));
        it.delete(100, 100);
        it.delete(200, 200);
        it.delete(50, 50);
        assertNull(it.getRoot());
    }

    /**
     * Test region search at Y discriminator level
     * @throws IOException
     */
    public void testRegionSearchYLevel() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("Up", 50, 150));
        it.insert(new City("Down", 50, 50));
        String result = it.regionSearch(50, 150, 10);
        assertTrue(result.contains("Up"));
    }

    /**
     * Test getNodesVisited after operations
     * @throws IOException
     */
    public void testGetNodesVisited() throws IOException {
        it.insert(city1);
        it.insert(city2);
        it.regionSearch(100, 100, 50);
        assertTrue(it.getNodesVisited() > 0);
    }
    
    
    
}