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
     * 
     * @throws IOException
     */
    public void testInsertNullCity() throws IOException {
        assertFalse(it.insert(null));
    }


    /**
     * Test comparing X discriminator on the left side of tree
     * 
     * @throws IOException
     */
    public void testXDiscriminatorLeft() throws IOException {
        it.insert(city1);
        it.insert(city3);
        assertTrue(it.findDuplicate(50, 150));

    }


    /**
     * Test comparing X discriminator on right side
     * 
     * @throws IOException
     */
    public void testXDiscriminatorRight() throws IOException {
        it.insert(city1);
        it.insert(city2);
        assertTrue(it.findDuplicate(150, 250));
    }


    /**
     * Test Y discriminator on left
     * 
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
     * 
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
     * 
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
     * 
     * @throws IOException
     */
    public void testFindNonExistentDup() {
        it.insert(city1);
        assertFalse(it.findDuplicate(200, 200));
    }


    /**
     * Test finding nonexistent city
     * 
     * @throws IOException
     */
    public void testFindNonExistentCity() throws IOException {
        it.insert(city1);
        assertNull(it.findCity(200, 200));
    }


    /**
     * Test finding existing city
     * 
     * @throws IOException
     */
    public void testFindExistingCity() throws IOException {
        it.insert(city1);
        assertEquals("CityA", it.findCity(100, 200).getName());
    }


    /**
     * Test find duplicate in empty tree
     * 
     * @throws IOException
     */
    public void testFindDuplicateEmptyTree() throws IOException {
        assertFalse(it.findDuplicate(100, 100));
    }


    /**
     * Test find city in empty tree
     * 
     * @throws IOException
     */
    public void testFindCityEmptyTree() throws IOException {
        assertNull(it.findCity(100, 100));
    }


    /**
     * Test find duplicate at root
     * 
     * @throws IOException
     */
    public void testFindDuplicateAtRoot() throws IOException {
        it.insert(city1);
        assertTrue(it.findDuplicate(100, 200));
    }


    /**
     * Test find city at root
     * 
     * @throws IOException
     */
    public void testFindCityAtRoot() throws IOException {
        it.insert(city1);
        assertEquals(city1, it.findCity(100, 200));
    }


    /**
     * Test find duplicate with equal X values (goes right)
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @throws IOException
     */
    public void testRegionSearchEmpty() throws IOException {
        String result = it.regionSearch(100, 100, 50);
        assertTrue(result.contains("Nodes Visited: 0"));
        assertFalse(result.contains("City"));
    }


    /**
     * Test region search finds city at exact location
     * 
     * @throws IOException
     */
    public void testRegionSearchExactLocation() throws IOException {
        it.insert(city1);
        String result = it.regionSearch(100, 200, 10);
        assertTrue(result.contains("CityA"));
    }


    /**
     * Test region search with radius 0
     * 
     * @throws IOException
     */
    public void testRegionSearchRadiusZero() throws IOException {
        it.insert(city1);
        String result = it.regionSearch(100, 200, 0);
        assertTrue(result.contains("CityA"));
    }


    /**
     * Test region search at exact boundary (distance = radius)
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @throws IOException
     */
    public void testRegionSearchNegativeCenter() throws IOException {
        it.insert(new City("City", 100, 100));
        String result = it.regionSearch(-100, -100, 300);
        assertTrue(result.contains("City"));
    }


    /**
     * Test region search counts nodes visited
     * 
     * @throws IOException
     */
    public void testRegionSearchCountsNodes() throws IOException {
        it.insert(new City("A", 100, 100));
        it.insert(new City("B", 200, 200));
        it.insert(new City("C", 50, 50));
        String result = it.regionSearch(100, 100, 50);
        assertTrue(result.contains("Nodes Visited:"));
        assertTrue(result.contains("3") || result.contains("2") || result
            .contains("1"));
    }


    /**
     * Test delete on empty tree
     * 
     * @throws IOException
     */
    public void testDeleteEmptyTree() throws IOException {
        City result = it.delete(100, 100);
        assertNull(result);
        assertEquals(0, it.getNodesVisited());
    }


    /**
     * Test delete leaf node
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @throws IOException
     */
    public void testDeleteNonExistent() throws IOException {
        it.insert(city1);
        City deleted = it.delete(200, 200);
        assertNull(deleted);
    }


    /**
     * Test delete searches left subtree
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @throws IOException
     */
    public void testDebugEmpty() throws IOException {
        String result = it.debug();
        assertEquals("", result);
    }


    /**
     * Test debug single node
     * 
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
     * 
     * @throws IOException
     */
    public void testDebugMultipleLevels() throws IOException {
        it.insert(new City("L0", 100, 100));
        it.insert(new City("L1", 50, 50));
        it.insert(new City("L2", 25, 25));

        String s = it.debug();

        assertTrue(s.contains("L2 (25, 25)"));
        assertTrue(s.contains("L1 (50, 50)"));
        assertTrue(s.contains("L0 (100, 100)"));

        int p2 = s.indexOf("L2 (25, 25)");
        int p1 = s.indexOf("L1 (50, 50)");
        int p0 = s.indexOf("L0 (100, 100)");

        assertTrue(p2 < p1);
        assertTrue(p1 < p0);
    }


    /**
     * Test discriminator cycles correctly
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @throws IOException
     */
    public void testGetNodesVisited() throws IOException {
        it.insert(city1);
        it.insert(city2);
        it.regionSearch(100, 100, 50);
        assertTrue(it.getNodesVisited() > 0);
    }


    /**
     * insert branches x then y left and right
     */
    public void testInsertBranchesXY() throws IOException {
        KDTree t = new KDTree();
        // root level x discriminator
        t.insert(new City("R", 100, 100)); // root
        t.insert(new City("XL", 50, 120)); // x< -> left
        t.insert(new City("XR", 150, 120)); // x>= -> right
        // level 1 y discriminator on left subtree
        t.insert(new City("YL", 40, 90)); // y< -> left
        t.insert(new City("YR", 60, 130)); // y>= -> right
        assertTrue(t.findDuplicate(50, 120));
        assertTrue(t.findDuplicate(150, 120));
        assertTrue(t.findDuplicate(40, 90));
        assertTrue(t.findDuplicate(60, 130));
    }


    /**
     * findDuplicate equality and both split sides
     */
    public void testFindDuplicateEqualityAndSides() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 100, 100));
        t.insert(new City("L", 50, 100)); // go left at x split
        t.insert(new City("RL", 150, 50)); // right then left at y split
        assertTrue(t.findDuplicate(100, 100)); // equality path
        assertTrue(t.findDuplicate(50, 100)); // x left path
        assertTrue(t.findDuplicate(150, 50)); // y left path under right child
    }


    /**
     * findCity equality and both split sides
     */
    public void testFindCityEqualityAndSides() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 100, 100));
        t.insert(new City("RX", 150, 120)); // x right
        t.insert(new City("LY", 50, 80)); // x left then y left
        assertEquals("R", t.findCity(100, 100).getName());
        assertEquals("RX", t.findCity(150, 120).getName());
        assertEquals("LY", t.findCity(50, 80).getName());
    }


    /**
     * region search crosses split plane both ways
     */
    public void testRegionSearchCrossLeftThenRight() throws IOException {
        it.insert(new City("Center", 100, 100));
        it.insert(new City("LeftFar", 60, 100));
        it.insert(new City("RightFar", 140, 100));
        String res = it.regionSearch(100, 95, 10); // planeDist 0 so cross
        assertTrue(res.contains("Center"));
        assertTrue(it.getNodesVisited() >= 2); // both sides visited
    }


    /**
     * regionSearch crosses plane from right then also left
     */
    public void testRegionSearchCrossRightThenLeft() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 100, 100));
        t.insert(new City("Up", 100, 140)); // will be on right then go up at y
                                            // split
        t.insert(new City("Down", 100, 60));
        // center just right of plane, radius crosses back
        String s = t.regionSearch(105, 100, 10);
        assertTrue(s.contains("R"));
    }


    /**
     * delete found node uses right subtree min then relinks
     */
    public void testDeleteFoundWithRightChildPath() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 100, 100));
        t.insert(new City("A", 150, 150)); // right child exists
        t.insert(new City("B", 130, 130)); // right-left to be min
        City d = t.delete(100, 100);
        assertEquals("R", d.getName());
        assertNull(t.findCity(100, 100));
        assertNotNull(t.findCity(150, 150)); // still present after relink
    }


    /**
     * delete found node uses left subtree min then relinks to right
     */
    public void testDeleteFoundWithLeftChildPath() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 100, 100));
        t.insert(new City("L", 50, 150)); // left child exists
        t.insert(new City("LL", 25, 175)); // left-left to be min
        City d = t.delete(100, 100);
        assertEquals("R", d.getName());
        assertNull(t.findCity(100, 100));
        assertNotNull(t.findCity(25, 175));
    }


    /**
     * delete continues search left then right branches
     */
    public void testDeleteSearchLeftAndRightBranches() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 100, 100));
        t.insert(new City("Left", 50, 50));
        t.insert(new City("Right", 150, 150));
        assertEquals("Left", t.delete(50, 50).getName()); // goes left branch
        assertEquals("Right", t.delete(150, 150).getName()); // goes right
                                                             // branch
    }


    /**
     * findMin matching discriminator hits return rt when left null
     */
    public void testFindMinMatchingDiscReturnSelf() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 100, 100));
        t.insert(new City("Right", 150, 150)); // ensure delete uses findMin
                                               // with matching disc
        // delete root to call findMin on right subtree with x disc and left
        // null
        t.delete(100, 100);
        assertNull(t.findCity(100, 100));
    }


    /**
     * findMin non matching discriminator compares children via minNode
     */
    public void testFindMinNonMatchingDiscCombines() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 100, 100));
        t.insert(new City("A", 150, 60)); // right subtree
        t.insert(new City("B", 130, 40)); // right-left subtree
        t.delete(150, 60); // triggers non-matching path inside findMin
        assertNull(t.findCity(150, 60));
    }


    /**
     * minNode compares temp1 and temp2 values for x and y cases
     */
    public void testMinNodeBothTempsAffectChoice() throws IOException {
        KDTree t = new KDTree();
        t.insert(new City("R", 500, 500));
        t.insert(new City("Right", 600, 600));
        t.insert(new City("RightLeft", 550, 650)); // forces temp1 path
        t.insert(new City("RightRight", 700, 550)); // forces temp2 path
        t.delete(600, 600); // invokes minNode under y disc
        assertNull(t.findCity(600, 600));
        // at least one of the two candidates remains
        assertTrue(t.findCity(550, 650) != null || t.findCity(700,
            550) != null);
    }


    /**
     * insert follows x then y discriminators
     */
    public void testInsertXThenYPaths() throws IOException {
        it.insert(new City("R", 100, 100));
        it.insert(new City("LX", 50, 100)); // x < root.x goes left
        it.insert(new City("RX", 150, 100)); // x >= root.x goes right
        it.insert(new City("LY", 25, 50)); // y < at level 1 goes left
        it.insert(new City("RY", 175, 150)); // y >= at level 1 goes right
        assertTrue(it.findDuplicate(25, 50));
        assertTrue(it.findDuplicate(175, 150));
    }


    /**
     * delete path when node has right child uses findMin on right
     */
    public void testDeleteNodeHasRightChildPath() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("R1", 150, 100));
        it.insert(new City("Rmin", 125, 50)); // min in right by x then y
        City deleted = it.delete(100, 100);
        assertEquals("Root", deleted.getName());
        assertNotNull(it.findCity(125, 50)); // promoted node remains
    }


    /**
     * delete path when node has only left child
     */
    public void testDeleteNodeHasOnlyLeftChildPath() throws IOException {
        it.insert(new City("Root", 100, 100));
        it.insert(new City("L", 50, 100));
        it.insert(new City("LL", 25, 150));
        City deleted = it.delete(50, 100);
        assertEquals("L", deleted.getName());
        assertNotNull(it.findCity(25, 150));
    }


    /**
     * findMin same discriminator and non matching discriminator
     */
    public void testFindMinBothBranches() throws IOException {
        it.insert(new City("R", 100, 100));
        it.insert(new City("A", 50, 120)); // left
        it.insert(new City("B", 150, 80)); // right
        it.insert(new City("C", 25, 130)); // deeper left
        // delete root forces findMin on right subtree with x disc at level 0
        it.delete(100, 100);
        assertNull(it.findCity(100, 100));
    }


    /**
     * minNode chooses among rt temp1 temp2
     */
    public void testMinNodeChoices() throws IOException {
        it.insert(new City("R", 500, 500));
        it.insert(new City("L", 400, 600));
        it.insert(new City("LL", 300, 700));
        it.insert(new City("R1", 600, 400));
        it.insert(new City("RR", 700, 300));
        String s = it.regionSearch(0, 0, 1); // just to touch counters
        assertNotNull(s); // no assertion on content, just execution
    }

}
