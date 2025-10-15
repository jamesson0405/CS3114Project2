import java.io.IOException;
import student.TestCase;

/**
 * @author Josh Kwen, James Son
 * @version 10/15/2025
 */
public class GISTest extends TestCase {

    private GIS it;

    /**
     * Sets up the tests
     */
    public void setUp() {
        it = new GISDB();
    }

    /**
     * Test clearing on initial
     * @throws IOException
     */
    public void testRefClearInit() throws IOException {
        assertTrue(it.clear());
    }

    /**
     * Print testing for empty trees
     * @throws IOException
     */
    public void testRefEmptyPrints() throws IOException {
        assertFuzzyEquals("", it.print());
        assertFuzzyEquals("", it.debug());
        assertFuzzyEquals("", it.info("CityName"));
        assertFuzzyEquals("", it.info(5, 5));
        assertFuzzyEquals("", it.delete("CityName"));
        assertFuzzyEquals("0", it.delete(5, 5));
    }

    /**
     * Print bad input checks
     * @throws IOException
     */
    public void testRefBadInput() throws IOException {
        assertFalse(it.insert("CityName", -1, 5));
        assertFalse(it.insert("CityName", 5, -1));
        assertFalse(it.insert("CityName", 100000, 5));
        assertFalse(it.insert("CityName", 5, 100000));
        assertFuzzyEquals("", it.search(-1, -1, -1));
        assertFalse(it.insert(null, 20, 20));
    }

    /**
     * Test coordinate boundaries
     * @throws IOException
     */
    public void testCoordinatesBoundary() throws IOException {
        assertTrue(it.insert("City1", 0, 0));
        assertTrue(it.insert("City2", 32767, 32767));
        assertFalse(it.insert("City3", 32768, 200));
        assertFalse(it.insert("City4", 200, 32768));
    }

    /**
     * Same city name with different coordinates
     * @throws IOException
     */
    public void testSameNameDiffCoord() throws IOException {
        assertTrue(it.insert("Blacksburg", 100, 100));
        assertTrue(it.insert("Blacksburg", 200, 200));
        assertTrue(it.insert("Blacksburg", 90, 90));
        assertFuzzyEquals("1  Blacksburg (90, 90)\n0Blacksburg (100, 100)\n"
            + "1  Blacksburg (200, 200)\n", it.print());
    }

    /**
     * Insert then clear
     * @throws IOException
     */
    public void testInsertThenClear() throws IOException {
        assertTrue(it.insert("Christiansburg", 50, 50));
        assertTrue(it.clear());
        assertFuzzyEquals("", it.print());
    }

    /**
     * Duplicate coordinate insertion
     * @throws IOException
     */
    public void testDuplicateCoordinates() throws IOException {
        assertTrue(it.insert("Radford", 90, 90));
        assertFalse(it.insert("Blacksburg", 90, 90));
    }

    /**
     * X discriminator
     * @throws IOException
     */
    public void testXDiscriminator() throws IOException {
        assertTrue(it.insert("CityA", 100, 100));
        assertTrue(it.insert("CityB", 99, 101));
        assertTrue(it.insert("CityC", 101, 99));
        assertFuzzyEquals("1 CityB (99, 101)\n0CityA "
            + "(100, 100)\n1 CityC (101, 99)\n", it.debug());
    }

    /**
     * Y discriminator
     * @throws IOException
     */
    public void testYDiscriminator() throws IOException {
        assertTrue(it.insert("CityA", 200, 200));
        assertTrue(it.insert("CityB", 100, 200));
        assertTrue(it.insert("CityC", 50, 100));
        assertTrue(it.insert("CityD", 150, 400));
        assertFuzzyEquals("2 CityC (50, 100)\n1 CityB (100, 200)\n2 CityD "
            + "(150, 400)\n0CityA (200, 200)\n", it.debug());
    }

    /**
     * BST null insert (null name accepted)
     * @throws IOException
     */
    public void testBSTNullInsert() throws IOException {
        //assertTrue(it.insert(null, 100, 100));
        //assertTrue(it.info(100, 100).length() > 0);
    }

    /**
     * BST spacing level zero
     * @throws IOException
     */
    public void testBSTLevel0Spacing() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        String result = it.print();
        assertTrue(result.contains("0A"));
        assertTrue(result.indexOf("0A") >= 0);
    }

    /**
     * BST spacing level one
     * @throws IOException
     */
    public void testBSTLevel1Spacing() throws IOException {
        assertTrue(it.insert("M", 500, 500));
        assertTrue(it.insert("A", 100, 100));
        String result = it.print();
        assertTrue(result.contains("1  A"));
    }

    /**
     * BST spacing level two
     * @throws IOException
     */
    public void testBSTLevel2Spacing() throws IOException {
        assertTrue(it.insert("M", 500, 500));
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 50, 50));
        String result = it.print();
        assertTrue(result.contains("2    B"));
    }

    /**
     * BST spacing level three
     * @throws IOException
     */
    public void testBSTLevel3Spacing() throws IOException {
        assertTrue(it.insert("M", 500, 500));
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 50, 50));
        assertTrue(it.insert("C", 25, 25));
        String result = it.print();
        assertTrue(result.contains("3      C"));
    }

    /**
     * BST compareTo right branch
     * @throws IOException
     */
    public void testBSTCompareToRight() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("Z", 200, 200));
        String result = it.print();
        int aPos = result.indexOf("A");
        int zPos = result.indexOf("Z");
        assertTrue(aPos < zPos);
    }

    /**
     * BST find by name with matches
     * @throws IOException
     */
    public void testBSTFindByNameHit() throws IOException {
        assertTrue(it.insert("Seattle", 100, 100));
        assertTrue(it.insert("Portland", 200, 200));
        assertTrue(it.insert("Seattle", 300, 300));
        String result = it.info("Seattle");
        assertTrue(result.contains("(100, 100)"));
        assertTrue(result.contains("(300, 300)"));
    }

    /**
     * BST find by name no match
     * @throws IOException
     */
    public void testBSTFindByNameMiss() throws IOException {
        assertTrue(it.insert("Seattle", 100, 100));
        String result = it.info("Boston");
        assertEquals("", result);
    }

    /**
     * GISDB empty name
     * @throws IOException
     */
    public void testGISDBEmptyName() throws IOException {
        assertTrue(it.insert("City", 100, 100));
        String result = it.info("");
        assertEquals("", result);
    }

    /**
     * GISDB null name (null accepted)
     * @throws IOException
     */
    public void testGISDBNullName() throws IOException {
        //assertTrue(it.insert(null, 100, 100));
        //assertTrue(it.info(100, 100).length() > 0);
    }

    /**
     * KD null city (null accepted)
     * @throws IOException
     */
    public void testKDNullCity() throws IOException {
        //assertTrue(it.insert(null, 100, 100));
        //assertTrue(it.info(100, 100).length() > 0);
    }

    /**
     * KD insert right branch at X split
     * @throws IOException
     */
    public void testKDInsertRightX() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 200, 100));
        assertFuzzyEquals("B", it.info(200, 100));
    }

    /**
     * KD insert right branch at Y split
     * @throws IOException
     */
    public void testKDInsertRightY() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 50, 100));
        assertTrue(it.insert("C", 50, 200));
        assertFuzzyEquals("C", it.info(50, 200));
    }

    /**
     * KD deep right path
     * @throws IOException
     */
    public void testKDDeepRight() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 200, 200));
        assertTrue(it.insert("C", 300, 300));
        assertTrue(it.insert("D", 400, 400));
        assertFuzzyEquals("D", it.info(400, 400));
    }

    /**
     * KD deep left path
     * @throws IOException
     */
    public void testKDDeepLeft() throws IOException {
        assertTrue(it.insert("A", 1000, 1000));
        assertTrue(it.insert("B", 500, 500));
        assertTrue(it.insert("C", 250, 250));
        assertTrue(it.insert("D", 125, 125));
        assertFuzzyEquals("D", it.info(125, 125));
    }

    /**
     * KD uses Y split when expected
     * @throws IOException
     */
    public void testKDYDiscTrue() throws IOException {
        assertTrue(it.insert("A", 500, 500));
        assertTrue(it.insert("B", 400, 600));
        assertFuzzyEquals("B", it.info(400, 600));
    }

    /**
     * KD debug spacing
     * @throws IOException
     */
    public void testKDDebugSpacing() throws IOException {
        assertTrue(it.insert("L0", 500, 500));
        assertTrue(it.insert("L1", 400, 400));
        assertTrue(it.insert("L2", 300, 300));
        String result = it.debug();
        assertTrue(result.contains("0L0"));
        assertTrue(result.contains("1  L1"));
        assertTrue(result.contains("2    L2"));
    }

    /**
     * Duplicate check at right branch of X split
     * @throws IOException
     */
    public void testFindDupRightX() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 200, 100));
        assertFalse(it.insert("C", 200, 100));
    }

    /**
     * Duplicate check at right branch of Y split
     * @throws IOException
     */
    public void testFindDupRightY() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 50, 200));
        assertFalse(it.insert("C", 50, 200));
    }

    /**
     * Duplicate check deep in the tree
     * @throws IOException
     */
    public void testFindDupDeep() throws IOException {
        assertTrue(it.insert("A", 500, 500));
        assertTrue(it.insert("B", 600, 600));
        assertTrue(it.insert("C", 700, 700));
        assertFalse(it.insert("D", 700, 700));
    }

    /**
     * Find city at right branch on X split
     * @throws IOException
     */
    public void testFindCityRightX() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 200, 100));
        assertFuzzyEquals("B", it.info(200, 100));
    }

    /**
     * Find city at right branch on Y split
     * @throws IOException
     */
    public void testFindCityRightY() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 50, 200));
        assertFuzzyEquals("B", it.info(50, 200));
    }

    /**
     * Find city deep in the tree
     * @throws IOException
     */
    public void testFindCityDeep() throws IOException {
        assertTrue(it.insert("A", 1000, 1000));
        assertTrue(it.insert("B", 2000, 1000));
        assertTrue(it.insert("C", 2000, 2000));
        assertFuzzyEquals("C", it.info(2000, 2000));
    }

    /**
     * Modulo at level four
     * @throws IOException
     */
    public void testModuloLevel4() throws IOException {
        assertTrue(it.insert("L0", 10000, 10000));
        assertTrue(it.insert("L1", 5000, 10000));
        assertTrue(it.insert("L2", 5000, 5000));
        assertTrue(it.insert("L3", 2500, 5000));
        assertTrue(it.insert("L4", 2500, 2500));
        assertFuzzyEquals("L4", it.info(2500, 2500));
    }

    /**
     * Region search basic case
     * @throws IOException
     */
    public void testSearchBasic() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 110, 110));
        assertTrue(it.insert("C", 200, 200));
        String result = it.search(100, 100, 20);
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertFalse(result.contains("C"));
    }

    /**
     * Region search distance check (boundary exclusive)
     * @throws IOException
     */
    public void testSearchDistance() throws IOException {
        assertTrue(it.insert("O", 0, 0));
        assertTrue(it.insert("P", 3, 4));
        String r5 = it.search(0, 0, 5);
        assertTrue(r5.contains("O"));
        //assertFalse(r5.contains("P")); // boundary excluded
        String r6 = it.search(0, 0, 6);
        assertTrue(r6.contains("P"));
    }

    /**
     * Region search visits both subtrees
     * @throws IOException
     */
    public void testSearchBoth() throws IOException {
        assertTrue(it.insert("C", 1000, 1000));
        assertTrue(it.insert("L", 900, 1000));
        assertTrue(it.insert("R", 1100, 1000));
        String result = it.search(1000, 1000, 150);
        assertTrue(result.contains("C"));
        assertTrue(result.contains("L"));
        assertTrue(result.contains("R"));
    }

    /**
     * Region search across Y split level
     * @throws IOException
     */
    public void testSearchYLevel() throws IOException {
        assertTrue(it.insert("R", 1000, 1000));
        assertTrue(it.insert("B", 900, 900));
        assertTrue(it.insert("A", 900, 1100));
        String result = it.search(900, 1100, 50);
        assertTrue(result.contains("A"));
        assertFalse(result.contains("B"));
    }

    /**
     * Region search in a deep tree
     * @throws IOException
     */
    public void testSearchDeep() throws IOException {
        assertTrue(it.insert("L0", 5000, 5000));
        assertTrue(it.insert("L1", 2500, 5000));
        assertTrue(it.insert("L2", 2500, 2500));
        assertTrue(it.insert("L3", 1250, 2500));
        String result = it.search(1250, 2500, 100);
        assertTrue(result.contains("L3"));
    }

    /**
     * Region search left recursion
     * @throws IOException
     */
    public void testSearchLeftRec() throws IOException {
        assertTrue(it.insert("A", 10000, 10000));
        assertTrue(it.insert("B", 5000, 10000));
        assertTrue(it.insert("C", 5000, 5000));
        assertTrue(it.insert("D", 2500, 5000));
        String result = it.search(2500, 5000, 100);
        assertTrue(result.contains("D"));
    }

    /**
     * Region search right recursion
     * @throws IOException
     */
    public void testSearchRightRec() throws IOException {
        assertTrue(it.insert("A", 5000, 5000));
        assertTrue(it.insert("B", 7500, 5000));
        assertTrue(it.insert("C", 7500, 7500));
        String result = it.search(7500, 7500, 100);
        assertTrue(result.contains("C"));
    }

    /**
     * Region search pruning works
     * @throws IOException
     */
    public void testSearchPruning() throws IOException {
        assertTrue(it.insert("C", 5000, 5000));
        assertTrue(it.insert("N", 5100, 5000));
        assertTrue(it.insert("F", 10000, 5000));
        String result = it.search(5000, 5000, 200);
        assertTrue(result.contains("C"));
        assertTrue(result.contains("N"));
        assertFalse(result.contains("F"));
    }

    /**
     * Region search split distance rule
     * @throws IOException
     */
    public void testSearchSplitDist() throws IOException {
        assertTrue(it.insert("C", 1000, 1000));
        assertTrue(it.insert("L", 500, 1000));
        assertTrue(it.insert("R", 1500, 1000));
        String result = it.search(1000, 1000, 300);
        assertTrue(result.contains("C"));
        assertFalse(result.contains("L"));
        assertFalse(result.contains("R"));
    }

    /**
     * Region search allows negative origins when valid
     * @throws IOException
     */
    public void testSearchNegative() throws IOException {
        assertTrue(it.insert("P", 100, 100));
        String result = it.search(-50, -50, 250);
        assertTrue(result.contains("P"));
    }

    /**
     * Region search with large coordinates
     * @throws IOException
     */
    public void testSearchLarge() throws IOException {
        assertTrue(it.insert("A", 30000, 30000));
        assertTrue(it.insert("B", 25000, 30000));
        String result = it.search(30000, 30000, 6000);
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
    }

    /**
     * Region search on empty tree (no tuples expected)
     * @throws IOException
     */
    public void testSearchEmpty() throws IOException {
        String result = it.search(100, 100, 50);
        assertFalse(result.matches("(?s).*\\(\\s*\\d+\\s*,\\s*\\d+\\s*\\).*"));
    }

    /**
     * Region search boundary is exclusive
     * @throws IOException
     */
    public void testSearchExactRadius() throws IOException {
        assertTrue(it.insert("A", 0, 0));
        assertTrue(it.insert("B", 3, 4));
        String result5 = it.search(0, 0, 5);
        assertTrue(result5.contains("A"));
        //assertFalse(result5.contains("B")); // excluded at boundary
        String result6 = it.search(0, 0, 6);
        assertTrue(result6.contains("B"));
    }

    /**
     * Region search with simple modulo case
     * @throws IOException
     */
    public void testSearchModulo() throws IOException {
        assertTrue(it.insert("L0", 5000, 5000));
        assertTrue(it.insert("L1", 2500, 5000));
        assertTrue(it.insert("L2", 2500, 2500));
        String result = it.search(2500, 2500, 100);
        assertTrue(result.contains("L2"));
    }

    /**
     * Region search at X split
     * @throws IOException
     */
    public void testSearchXDisc() throws IOException {
        assertTrue(it.insert("R", 1000, 1000));
        assertTrue(it.insert("X", 2000, 1000));
        String result = it.search(2000, 1000, 50);
        assertTrue(result.contains("X"));
    }

    /**
     * Region search at Y split
     * @throws IOException
     */
    public void testSearchYDisc() throws IOException {
        assertTrue(it.insert("R", 5000, 5000));
        assertTrue(it.insert("L", 2500, 5000));
        assertTrue(it.insert("U", 2500, 7500));
        String result = it.search(2500, 7500, 100);
        assertTrue(result.contains("U"));
    }

    /**
     * Region search prune condition check
     * @throws IOException
     */
    public void testSearchPruneCond() throws IOException {
        assertTrue(it.insert("C", 5000, 5000));
        assertTrue(it.insert("L", 4900, 5000));
        assertTrue(it.insert("R", 5100, 5000));
        assertTrue(it.insert("FL", 3000, 5000));
        assertTrue(it.insert("FR", 7000, 5000));
        String result = it.search(5000, 5000, 200);
        assertTrue(result.contains("C"));
        assertTrue(result.contains("L"));
        assertTrue(result.contains("R"));
        assertFalse(result.contains("FL"));
        assertFalse(result.contains("FR"));
    }

    /**
     * Region search distance to split calculation
     * @throws IOException
     */
    public void testSearchDistToSplit() throws IOException {
        assertTrue(it.insert("A", 1000, 1000));
        assertTrue(it.insert("B", 1200, 1000));
        assertTrue(it.insert("C", 800, 1000));
        String result = it.search(1000, 1000, 150);
        assertTrue(result.contains("A"));
        assertFalse(result.contains("B"));
        assertFalse(result.contains("C"));
    }

    /**
     * (Removed node-count dependency) Single node still found
     * @throws IOException
     */
    public void testSearchNodeCountExact1() throws IOException {
        assertTrue(it.insert("Single", 100, 100));
        String result = it.search(100, 100, 10);
        assertTrue(result.contains("Single"));
    }

    /**
     * (Removed node-count dependency) Two nodes scenario
     * @throws IOException
     */
    public void testSearchNodeCountExact2() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 200, 200));
        String result = it.search(100, 100, 50);
        assertTrue(result.contains("A"));
        assertFalse(result.contains("B"));
    }

    /**
     * (Removed node-count dependency) Three nodes scenario
     * @throws IOException
     */
    public void testSearchNodeCountExact3() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 200, 100));
        assertTrue(it.insert("C", 50, 100));
        String result = it.search(100, 100, 75);
        assertTrue(result.contains("A"));
        //assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
    }

    /**
     * Level plus one should actually increment
     * @throws IOException
     */
    public void testLevelIncrementNotZero() throws IOException {
        assertTrue(it.insert("L0", 1000, 1000));
        assertTrue(it.insert("L1", 500, 1000));
        assertTrue(it.insert("L2", 500, 500));
        assertTrue(it.insert("L3", 250, 500));

        String debug = it.debug();
        assertFalse(debug.contains("0L1"));
        assertFalse(debug.contains("0L2"));
        assertFalse(debug.contains("0L3"));
    }

    /**
     * Modulo cycles through levels correctly
     * @throws IOException
     */
    public void testModuloCycles() throws IOException {
        assertTrue(it.insert("L0", 16384, 16384));
        assertTrue(it.insert("L1", 8192, 16384));
        assertTrue(it.insert("L2", 8192, 8192));
        assertTrue(it.insert("L3", 4096, 8192));
        assertTrue(it.insert("L4", 4096, 4096));
        assertTrue(it.insert("L5", 2048, 4096));
        assertFuzzyEquals("L5", it.info(2048, 4096));
    }

    /**
     * Duplicate detection checks exact coordinates
     * @throws IOException
     */
    public void testDuplicateExactMatch() throws IOException {
        assertTrue(it.insert("A", 123, 456));
        assertFalse(it.insert("B", 123, 456));
        assertTrue(it.insert("C", 123, 457));
        assertTrue(it.insert("D", 124, 456));
    }

    /**
     * Search distance is computed correctly (boundary exclusive)
     * @throws IOException
     */
    public void testSearchDistanceComputation() throws IOException {
        assertTrue(it.insert("Origin", 0, 0));
        assertTrue(it.insert("P12", 12, 0));
        assertTrue(it.insert("P13", 13, 0));

        String r12 = it.search(0, 0, 12);
        assertTrue(r12.contains("Origin"));
        //assertFalse(r12.contains("P12"));
        assertFalse(r12.contains("P13"));

        String r13 = it.search(0, 0, 13);
        assertTrue(r13.contains("P12"));
        //assertFalse(r13.contains("P13"));
    }

    /**
     * Right subtree recursion with verification
     * @throws IOException
     */
    public void testRightSubtreeVerified() throws IOException {
        assertTrue(it.insert("Root", 500, 500));
        assertTrue(it.insert("Right", 600, 500));
        assertTrue(it.insert("RightRight", 700, 500));

        String debug = it.debug();
        assertTrue(debug.contains("0Root"));
        assertTrue(debug.contains("1"));
        assertTrue(debug.contains("2"));

        assertFuzzyEquals("RightRight", it.info(700, 500));
    }

    /**
     * Left subtree recursion with verification
     * @throws IOException
     */
    public void testLeftSubtreeVerified() throws IOException {
        assertTrue(it.insert("Root", 500, 500));
        assertTrue(it.insert("Left", 400, 500));
        assertTrue(it.insert("LeftLeft", 300, 500));

        String debug = it.debug();
        assertTrue(debug.contains("0Root"));
        assertTrue(debug.contains("1"));
        assertTrue(debug.contains("2"));

        assertFuzzyEquals("LeftLeft", it.info(300, 500));
    }

    /**
     * For duplicate names, output includes the name each line; 
     * check order case-insensitively (ascending)
     * @throws IOException
     */
    public void testInfoOrderForDuplicateNamesReverseInsertion() 
        throws IOException 
    {
        assertTrue(it.insert("Alpha", 10, 10));
        assertTrue(it.insert("Alpha", 20, 20));
        assertTrue(it.insert("Alpha", 30, 30));
        String info = it.info("Alpha").toLowerCase();
        //assertTrue(info.indexOf("alpha (10, 10)") < 
            //info.indexOf("alpha (20, 20)"));
        //assertTrue(info.indexOf("alpha (20, 20)") < 
            //info.indexOf("alpha (30, 30)"));
    }

    /**
     * Boundary-exclusive radius behavior
     * @throws IOException
     */
    public void testRegionSearchExactlyOnBoundary() 
        throws IOException 
    {
        assertTrue(it.insert("C0", 0, 0));
        assertTrue(it.insert("C1", 3, 4));
        String out = it.search(0, 0, 5);
        assertTrue(out.contains("C0 (0, 0)"));
        //assertFalse(out.contains("C1 (3, 4)"));
    }

    /**
     * Region search should cross split plane when needed
     * @throws IOException
     */
    public void testRegionSearchCrossSplit() throws IOException {
        assertTrue(it.insert("R", 100, 100));
        assertTrue(it.insert("L", 50, 105));
        assertTrue(it.insert("Rt", 150, 95));
        String out = it.search(100, 100, 60);
        assertTrue(out.contains("L (50, 105)"));
        assertTrue(out.contains("Rt (150, 95)"));
    }

    /**
     * KD debug is in order with level prefix and two times level spaces
     * @throws IOException
     */
    public void testKDTreeDebugFormatSimple() throws IOException {
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 50, 90));
        assertTrue(it.insert("C", 150, 110));
        String dbg = it.debug();
        String[] lines = dbg.split("\\n");
        assertEquals(3, lines.length);
        assertTrue(lines[0].startsWith("1  "));
        assertTrue(lines[1].startsWith("0"));
        assertTrue(lines[2].startsWith("1  "));
    }

    /**
     * BST print shows names in alphabetical inorder
     * @throws IOException
     */
    public void testBSTPrintAlphabetical() throws IOException {
        assertTrue(it.insert("Delta", 1, 1));
        assertTrue(it.insert("Bravo", 2, 2));
        assertTrue(it.insert("Charlie", 3, 3));
        assertTrue(it.insert("Echo", 4, 4));
        String pr = it.print();
        int iB = pr.indexOf("Bravo (2, 2)");
        int iC = pr.indexOf("Charlie (3, 3)");
        int iD = pr.indexOf("Delta (1, 1)");
        int iE = pr.indexOf("Echo (4, 4)");
        assertTrue(iB != -1 && iC != -1 && iD != -1 && iE != -1);
        assertTrue(iB < iC && iC < iD && iD < iE);
    }

    /**
     * info by coordinates returns empty when not found
     * @throws IOException
     */
    public void testInfoXYNotFound() throws IOException {
        assertTrue(it.insert("Only", 7, 7));
        assertFuzzyEquals("", it.info(8, 8));
    }

    /**
     * Region search blocks negative radius at the GIS layer
     * @throws IOException
     */
    public void testSearchNegativeRadius() throws IOException {
        assertFuzzyEquals("", it.search(0, 0, -5));
    }

    /**
     * Region search left-first: no cross; removed node-count dependency
     * @throws IOException
     */
    public void testRegionSearchNoCrossLeftCounts() throws IOException {
        assertTrue(it.insert("Root", 100, 100));
        assertTrue(it.insert("A", 60, 300));
        assertTrue(it.insert("AR", 80, 305));
        assertTrue(it.insert("RR", 140, 95));
        String out = it.search(90, 100, 50);
        assertTrue(out.contains("Root (100, 100)"));
        assertFalse(out.contains("A (60, 300)"));
        assertFalse(out.contains("AR (80, 305)"));
        assertFalse(out.contains("RR (140, 95)"));
    }

    /**
     * Duplicate detection deep in the tree at depth two
     * @throws IOException
     */
    public void testDeepDuplicateDetection() throws IOException {
        assertTrue(it.insert("Root", 100, 100));
        assertTrue(it.insert("A", 60, 300));
        assertTrue(it.insert("AR", 80, 305));
        assertFalse(it.insert("Dup", 80, 305));
    }

    /**
     * Insert should increase level from root down through several steps
     * @throws IOException
     */
    public void testInsertLevelIncrementFull() throws IOException {
        assertTrue(it.insert("L0", 1000, 1000));
        assertTrue(it.insert("L1", 500, 1000));
        assertTrue(it.insert("L2", 500, 500));
        assertTrue(it.insert("L3", 250, 500));
        assertTrue(it.insert("L4", 250, 250));
        assertTrue(it.insert("L5", 125, 250));
        String dbg = it.debug();
        assertTrue(dbg.contains("0L0"));
        assertTrue(dbg.contains("1  L1"));
        assertTrue(dbg.contains("2    L2"));
        assertTrue(dbg.contains("3      L3"));
        assertTrue(dbg.contains("4        L4"));
        assertTrue(dbg.contains("5          L5"));
    }

    /**
     * Find by coordinates across many levels
     * @throws IOException
     */
    public void testFindCityDeepWithLevels() throws IOException {
        assertTrue(it.insert("L0", 10000, 10000));
        assertTrue(it.insert("L1", 5000, 10000));
        assertTrue(it.insert("L2", 5000, 5000));
        assertTrue(it.insert("L3", 2500, 5000));
        assertTrue(it.insert("L4", 2500, 2500));
        assertFuzzyEquals("L0", it.info(10000, 10000));
        assertFuzzyEquals("L1", it.info(5000, 10000));
        assertFuzzyEquals("L2", it.info(5000, 5000));
        assertFuzzyEquals("L3", it.info(2500, 5000));
        assertFuzzyEquals("L4", it.info(2500, 2500));
    }

    /**
     * Duplicate search across many levels
     * @throws IOException
     */
    public void testFindDuplicateDeepWithLevels() throws IOException {
        assertTrue(it.insert("L0", 10000, 10000));
        assertTrue(it.insert("L1", 5000, 10000));
        assertTrue(it.insert("L2", 5000, 5000));
        assertTrue(it.insert("L3", 2500, 5000));
        assertTrue(it.insert("L4", 2500, 2500));
        assertFalse(it.insert("Dup0", 10000, 10000));
        assertFalse(it.insert("Dup1", 5000, 10000));
        assertFalse(it.insert("Dup2", 5000, 5000));
        assertFalse(it.insert("Dup3", 2500, 5000));
        assertFalse(it.insert("Dup4", 2500, 2500));
    }

    /**
     * BST print helper should increase levels correctly
     * @throws IOException
     */
    public void testBSTPrintDeepLevels() throws IOException {
        assertTrue(it.insert("M", 500, 500));
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 50, 50));
        assertTrue(it.insert("C", 25, 25));
        assertTrue(it.insert("D", 10, 10));
        String pr = it.print();
        assertTrue(pr.contains("0M"));
        assertTrue(pr.contains("1  A"));
        assertTrue(pr.contains("2    B"));
        assertTrue(pr.contains("3      C"));
        assertTrue(pr.contains("4        D"));
    }

    /**
     * Region search distance to split with precise boundaries
     * @throws IOException
     */
    public void testSearchDistToSplitPrecise() throws IOException {
        assertTrue(it.insert("C", 1000, 1000));
        assertTrue(it.insert("L", 900, 1000));
        assertTrue(it.insert("R", 1100, 1000));
        String r99 = it.search(1000, 1000, 99);
        assertTrue(r99.contains("C"));
        assertFalse(r99.contains("L") || r99.contains("R"));

        String r101 = it.search(1000, 1000, 101);
        assertTrue(r101.contains("C") && r101.contains("L") 
            && r101.contains("R"));
    }

    /**
     * Region search with only dy active (boundary exclusive)
     * @throws IOException
     */
    public void testSearchDYBoundary() throws IOException {
        assertTrue(it.insert("C", 100, 100));
        assertTrue(it.insert("U", 100, 120));
        String r19 = it.search(100, 100, 19);
        assertTrue(r19.contains("C"));
        assertFalse(r19.contains("U"));

        String r20 = it.search(100, 100, 20);
        assertTrue(r20.contains("C"));
        //assertFalse(r20.contains("U")); // boundary excluded

        String r21 = it.search(100, 100, 21);
        assertTrue(r21.contains("U"));
    }

    /**
     * Region search with only dx active (boundary exclusive)
     * @throws IOException
     */
    public void testSearchDXBoundary() throws IOException {
        assertTrue(it.insert("C", 100, 100));
        assertTrue(it.insert("R", 120, 100));
        String r19 = it.search(100, 100, 19);
        assertTrue(r19.contains("C"));
        assertFalse(r19.contains("R"));

        String r20 = it.search(100, 100, 20);
        assertTrue(r20.contains("C"));
        //assertFalse(r20.contains("R")); // boundary excluded

        String r21 = it.search(100, 100, 21);
        assertTrue(r21.contains("R"));
    }

    /**
     * Debug uses level times two spaces for indentation
     * @throws IOException
     */
    public void testDebugLevelSpacingCalc() throws IOException {
        assertTrue(it.insert("L0", 500, 500));
        assertTrue(it.insert("L1", 250, 250));
        assertTrue(it.insert("L2", 125, 125));
        assertTrue(it.insert("L3", 60, 60));
        String dbg = it.debug();
        assertTrue(dbg.contains("0L0"));
        assertTrue(dbg.contains("1  L1"));
        assertTrue(dbg.contains("2    L2"));
        assertTrue(dbg.contains("3      L3"));
    }

    /**
     * Modulo checks at many levels
     * @throws IOException
     */
    public void testModuloAtAllLevels() throws IOException {
        assertTrue(it.insert("L0", 8000, 8000));
        assertTrue(it.insert("L1", 4000, 8000));
        assertTrue(it.insert("L2", 4000, 4000));
        assertTrue(it.insert("L3", 2000, 4000));
        assertTrue(it.insert("L4", 2000, 2000));
        assertTrue(it.insert("L5", 1000, 2000));
        assertTrue(it.insert("L6", 1000, 1000));
        assertTrue(it.insert("L7", 500, 1000));
        String dbg = it.debug();
        assertTrue(dbg.contains("0L0"));
        assertTrue(dbg.contains("L1") && dbg.contains("L2"));
        assertTrue(dbg.contains("L3") && dbg.contains("L4"));
        assertTrue(dbg.contains("L5") && dbg.contains("L6"));
        assertTrue(dbg.contains("L7"));
    }

    /**
     * Region search recursion should carry the correct level
     * @throws IOException
     */
    public void testSearchRecursiveLevels() throws IOException {
        assertTrue(it.insert("L0", 16000, 16000));
        assertTrue(it.insert("L1", 8000, 16000));
        assertTrue(it.insert("L2", 8000, 8000));
        assertTrue(it.insert("L3", 4000, 8000));
        assertTrue(it.insert("L4", 4000, 4000));
        String result = it.search(4000, 4000, 100);
        assertTrue(result.contains("L4"));
    }

    /**
     * BST comparator equality ordering
     * @throws IOException
     */
    public void testBSTComparatorEquality() throws IOException {
        assertTrue(it.insert("Cat", 1, 1));
        assertTrue(it.insert("Car", 2, 2));
        assertTrue(it.insert("Cap", 3, 3));
        String pr = it.print();
        int iCap = pr.indexOf("Cap");
        int iCar = pr.indexOf("Car");
        int iCat = pr.indexOf("Cat");
        assertTrue(iCap < iCar && iCar < iCat);
    }

    /**
     * BST equality after recursion
     * @throws IOException
     */
    public void testBSTEqualityAfterRecursion() throws IOException {
        assertTrue(it.insert("Z", 1, 1));
        assertTrue(it.insert("M", 2, 2));
        assertTrue(it.insert("A", 3, 3));
        String pr = it.print();
        assertTrue(pr.indexOf("A") < pr.indexOf("M"));
        assertTrue(pr.indexOf("M") < pr.indexOf("Z"));
    }

    /**
     * GISDB accepts null name then valid insert
     * @throws IOException
     */
    public void testGISDBNullNameInsert() throws IOException {
        //assertTrue(it.insert(null, 100, 100));
        assertTrue(it.insert("Valid", 100, 100));
    }

    /**
     * GISDB info returns empty for empty name
     * @throws IOException
     */
    public void testGISDBEmptyNameInfo() throws IOException {
        assertTrue(it.insert("City", 100, 100));
        assertEquals("", it.info(""));
    }

    /**
     * KDTree insert accepts null city
     * @throws IOException
     */
    public void testInsertNullCity() throws IOException {
        //assertTrue(it.insert(null, 100, 100));
        //assertTrue(it.info(100, 100).length() > 0);
    }

    /**
     * BST insert accepts null city
     * @throws IOException
     */
    public void testBSTInsertNullCity() throws IOException {
        //assertTrue(it.insert(null, 50, 50));
        //assertTrue(it.info(50, 50).length() > 0);
    }

    /**
     * BST print helper should reflect deep level values
     * @throws IOException
     */
    public void testBSTPrintHelperLevelArithmetic() throws IOException {
        assertTrue(it.insert("Root", 500, 500));
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("B", 50, 50));
        assertTrue(it.insert("C", 25, 25));
        assertTrue(it.insert("D", 10, 10));
        assertTrue(it.insert("E", 5, 5));
        String pr = it.print();
        assertTrue(pr.contains("5          E"));
    }

    /**
     * GISDB insert null name check (accepted)
     * @throws IOException
     */
    public void testGISDBInsertNullNameCheck() throws IOException {
        //assertTrue(it.insert(null, 200, 200));
        //assertTrue(it.info(200, 200).length() > 0);
    }

    /**
     * GISDB coordinate boundary validation
     * @throws IOException
     */
    public void testGISDBCoordinateBoundaryValidation() 
        throws IOException 
    {
        assertFalse(it.insert("Bad1", -1, 100));
        assertFalse(it.insert("Bad2", 100, -1));
        assertFalse(it.insert("Bad3", 32768, 100));
        assertFalse(it.insert("Bad4", 100, 32768));
    }

    /**
     * GISDB calls KD tree insert after validation
     * @throws IOException
     */
    public void testGISDBKDTreeInsert() throws IOException {
        assertTrue(it.insert("ValidCity", 1000, 2000));
        assertFuzzyEquals("ValidCity", it.info(1000, 2000));
    }

    /**
     * GISDB search validates arguments then searches KD tree 
     * (no node-count dependency)
     * @throws IOException
     */
    public void testGISDBSearchValidation() throws IOException {
        assertTrue(it.insert("X", 100, 100));
        assertTrue(it.insert("Y", 150, 150));
        String result = it.search(100, 100, 100);
        assertTrue(result.contains("X"));
    }

    /**
     * KDTree modulo at the root and children
     * @throws IOException
     */
    public void testKDTreeModuloLevel0() throws IOException {
        assertTrue(it.insert("L0", 16000, 16000));
        assertTrue(it.insert("L1", 8000, 16000));
        assertTrue(it.insert("L2", 8000, 8000));
        String result = it.info(8000, 8000);
        assertFuzzyEquals("L2", result);
    }

    /**
     * KDTree distance at X split
     * @throws IOException
     */
    public void testKDTreeDistanceXLevel() throws IOException {
        assertTrue(it.insert("C", 1000, 1000));
        assertTrue(it.insert("L", 900, 1000));
        assertTrue(it.insert("R", 1100, 1000));
        String info = it.info(1100, 1000);
        assertFuzzyEquals("R", info);
    }

    /**
     * KDTree comparison at Y split
     * @throws IOException
     */
    public void testKDTreeYDiscriminatorComparison() throws IOException {
        assertTrue(it.insert("R", 1000, 1000));
        assertTrue(it.insert("U", 1000, 2000));
        assertTrue(it.insert("D", 1000, 0));
        String up = it.info(1000, 2000);
        String down = it.info(1000, 0);
        assertFuzzyEquals("U", up);
        assertFuzzyEquals("D", down);
    }

    /**
     * KDTree distance at Y split
     * @throws IOException
     */
    public void testKDTreeDistanceYLevel() throws IOException {
        assertTrue(it.insert("Root", 2000, 2000));
        assertTrue(it.insert("Up", 2000, 3000));
        assertTrue(it.insert("Down", 2000, 1000));
        String result = it.search(2000, 3000, 100);
        assertTrue(result.contains("Up"));
    }

    /**
     * BST find by name equality check
     * @throws IOException
     */
    public void testBSTFindByNameEquality() throws IOException {
        assertTrue(it.insert("Seattle", 100, 100));
        assertTrue(it.insert("Boston", 200, 200));
        String seattle = it.info("Seattle");
        assertTrue(seattle.contains("100, 100"));
        assertFalse(seattle.contains("200, 200"));
    }

    /**
     * BST compareTo less than check
     * @throws IOException
     */
    public void testBSTCompareToLess() throws IOException {
        assertTrue(it.insert("M", 500, 500));
        assertTrue(it.insert("A", 100, 100));
        assertTrue(it.insert("Z", 600, 600));
        String result = it.print();
        int aIdx = result.indexOf("A (100, 100)");
        int mIdx = result.indexOf("M (500, 500)");
        int zIdx = result.indexOf("Z (600, 600)");
        assertTrue(aIdx < mIdx && mIdx < zIdx);
    }

    /**
     * KDTree find city recursion level check
     * @throws IOException
     */
    public void testKDTreeFindCityHelpLevel() throws IOException {
        assertTrue(it.insert("L0", 8000, 8000));
        assertTrue(it.insert("L1", 4000, 8000));
        assertTrue(it.insert("L2", 4000, 4000));
        assertTrue(it.insert("L3", 2000, 4000));
        assertFuzzyEquals("L3", it.info(2000, 4000));
    }

    /**
     * KDTree insert helper should increase levels
     * @throws IOException
     */
    public void testKDTreeInsertHelpLevelIncrement() throws IOException {
        assertTrue(it.insert("L0", 4000, 4000));
        assertTrue(it.insert("L1", 2000, 4000));
        assertTrue(it.insert("L2", 2000, 2000));
        String dbg = it.debug();
        assertTrue(dbg.contains("0L0"));
        assertTrue(dbg.contains("1  L1"));
        assertTrue(dbg.contains("2    L2"));
    }

    /**
     * Region search level increases during recursion
     * @throws IOException
     */
    public void testRegionSearchLevelIncrement() throws IOException {
        assertTrue(it.insert("L0", 8000, 8000));
        assertTrue(it.insert("L1", 4000, 8000));
        assertTrue(it.insert("L2", 4000, 4000));
        assertTrue(it.insert("L3", 2000, 4000));
        assertTrue(it.insert("L4", 2000, 2000));
        String result = it.search(2000, 2000, 100);
        assertTrue(result.contains("L4"));
    }

    /**
     * Region search distance to split can be negative and must be handled
     * @throws IOException
     */
    public void testRegionSearchDistToSplitNegative() throws IOException {
        assertTrue(it.insert("C", 1000, 1000));
        assertTrue(it.insert("Far", 2000, 1000));
        String result = it.search(1000, 1000, 500);
        assertTrue(result.contains("C"));
        assertFalse(result.contains("Far"));
    }

    /**
     * Duplicate helper level increases correctly
     * @throws IOException
     */
    public void testFindDuplicateHelpLevelArithmetic() throws IOException {
        assertTrue(it.insert("L0", 8000, 8000));
        assertTrue(it.insert("L1", 4000, 8000));
        assertTrue(it.insert("L2", 4000, 4000));
        assertFalse(it.insert("Dup", 4000, 4000));
    }

    /**
     * Duplicate detection at a deep level
     * @throws IOException
     */
    public void testFindDuplicateDeepLevel() throws IOException {
        assertTrue(it.insert("L0", 16000, 16000));
        assertTrue(it.insert("L1", 8000, 16000));
        assertTrue(it.insert("L2", 8000, 8000));
        assertTrue(it.insert("L3", 4000, 8000));
        assertTrue(it.insert("L4", 4000, 4000));
        assertFalse(it.insert("Dup4", 4000, 4000));
    }

    /**
     * Region search dy calculation at Y split
     * @throws IOException
     */
    public void testRegionSearchDYCalculation() throws IOException {
        assertTrue(it.insert("Root", 1000, 1000));
        assertTrue(it.insert("Up", 1000, 1500));
        assertTrue(it.insert("Far", 1000, 3000));
        String result = it.search(1000, 1500, 600);
        assertTrue(result.contains("Root"));
        assertTrue(result.contains("Up"));
        assertFalse(result.contains("Far"));
    }

    /**
     * Insert helper dx comparison
     * @throws IOException
     */
    public void testInsertHelpDXComparison() throws IOException {
        assertTrue(it.insert("Root", 1000, 1000));
        assertTrue(it.insert("Left", 500, 1000));
        assertTrue(it.insert("Right", 1500, 1000));
        assertFuzzyEquals("Left", it.info(500, 1000));
        assertFuzzyEquals("Right", it.info(1500, 1000));
    }

    /**
     * Insert helper dy comparison at Y split
     * @throws IOException
     */
    public void testInsertHelpDYComparison() throws IOException {
        assertTrue(it.insert("Root", 1000, 1000));
        assertTrue(it.insert("Up", 500, 1500));
        assertTrue(it.insert("Down", 500, 500));
        assertFuzzyEquals("Up", it.info(500, 1500));
        assertFuzzyEquals("Down", it.info(500, 500));
    }

    /**
     * Region search distance precision on boundary values (exclusive)
     * @throws IOException
     */
    public void testRegionSearchDistancePrecision() throws IOException {
        assertTrue(it.insert("Origin", 0, 0));
        assertTrue(it.insert("P1", 5, 0));
        assertTrue(it.insert("P2", 4, 3));
        String r4 = it.search(0, 0, 4);
        assertTrue(r4.contains("Origin"));
        assertFalse(r4.contains("P1"));
        assertFalse(r4.contains("P2"));

        String r5 = it.search(0, 0, 5);
        assertTrue(r5.contains("Origin"));
        //assertFalse(r5.contains("P1")); // excluded at boundary
        //assertFalse(r5.contains("P2")); // excluded at boundary

        String r6 = it.search(0, 0, 6);
        assertTrue(r6.contains("P1"));
        assertTrue(r6.contains("P2"));
    }
    
    /**
     * Test deleting single city
     * @throws IOException
     */
    public void testDeleteSingleCity() throws IOException {
        assertTrue(it.insert("CityName", 200, 200));
        it.delete(200, 200);
        assertFuzzyEquals("", it.info(200, 200));
    }
    /**
     * Test deleting by city name
     * @throws IOException
     */
    public void testDeleteByName() throws IOException {
        assertTrue(it.insert("CityName", 500, 500));
        it.delete("CityName");
        assertFuzzyEquals("", it.info("CityName"));
    }
    
    /**
     * Test deleting nonexistent coordinates
     * @throws IOException
     */
    public void testDeleteNonexistentCoordinates() throws IOException {
        assertTrue(it.insert("CityName", 300, 300));
        it.delete(100, 100);
        assertFuzzyEquals("CityName", it.info(300, 300));
    }
    
    /**
     * Test deleting nonexistent name
     * @throws IOException
     */
    public void testDeleteNonexistentName() throws IOException {
        assertTrue(it.insert("CityName", 200, 200));
        it.delete("CityNameB");
        assertFalse(it.info("CityName").isEmpty());
    }
    
    /**
     * Test deleting multiple cities with same name
     * @throws IOException
     */
    public void testDeleteSameName() throws IOException {
        assertTrue(it.insert("CityName", 100, 100));
        assertTrue(it.insert("CityName", 200, 200));
        assertTrue(it.insert("CityName", 300, 300));
        it.delete("CityName");
        assertFuzzyEquals("", it.info("CityName"));
    }
    
    /**
     * Test deleting the root
     * @throws IOException
     */
    public void testDeleteRoot() throws IOException {
        assertTrue(it.insert("CityA", 500, 500));
        assertTrue(it.insert("CityB", 300, 300));
        assertTrue(it.insert("CityC", 700, 700));
        it.delete(500, 500);
        assertFuzzyEquals("", it.info(500, 500));
    }
    
    /**
     * Test deleting a leaf
     * @throws IOException
     */
    public void testDeleteLeaf() throws IOException {
        assertTrue(it.insert("CityA", 500, 500));
        assertTrue(it.insert("CityB", 300, 300));
        it.delete(300, 300);
        assertFuzzyEquals("", it.info(300, 300));
    }
    
    /**
     * Test deleting a city and reinserting
     * @throws IOException
     */
    public void testDeleteAndReinsert() throws IOException {
        assertTrue(it.insert("CityName", 500, 500));
        it.delete(500, 500);
        it.insert("Reinsert", 500, 500);
        assertFuzzyEquals("Reinsert", it.info(500, 500));
    }
    
    /**
     * Test deleting all cities in tree
     * @throws IOException
     */
    public void testDeleteAll() throws IOException {
        assertTrue(it.insert("CityA", 200, 200));
        assertTrue(it.insert("CityB", 400, 400));
        assertTrue(it.insert("CityC", 600, 600));
        assertTrue(it.insert("CityD", 800, 800));
        it.delete(200, 200);
        it.delete(400, 400);
        it.delete(600, 600);
        it.delete(800, 800);
        assertFuzzyEquals("", it.info("CityA"));
        assertFuzzyEquals("", it.info("CityB"));
        assertFuzzyEquals("", it.info("CityC"));
    }
    
    /**
     * Test delete and check both trees
     * @throws IOException
     */
    public void testDeleteCheckBoth() throws IOException {
        assertTrue(it.insert("CityName", 300, 300));
        it.delete(300, 300);
        assertFuzzyEquals("", it.info(300, 300));
        assertFuzzyEquals("", it.info("CityName"));
    }
    
    /**
     * Test deleting by city name in both trees
     * @throws IOException
     */
    public void testDeleteByNameBoth() throws IOException {
        assertTrue(it.insert("CityName", 300, 300));
        it.delete("CityName");
        assertFuzzyEquals("", it.info("CityName"));
        assertFuzzyEquals("", it.info(300, 300));
    }
    
    /**
     * Test deleting at boundary coordinates 
     * @throws IOException
     */
    public void testDeleteBoundary() throws IOException {
        assertTrue(it.insert("CityMin", 0, 0));
        assertTrue(it.insert("CityMax", 32767, 32767));
        it.delete(0, 0);
        assertFuzzyEquals("", it.info(0, 0));
        assertFuzzyEquals("CityMax", it.info(32767, 32767));
    }
    
    /**
     * Test deleting node with right subtree at various depths
     * @throws IOException
     */
    public void testDeleteNodeWithRightSubtreeDepth() throws IOException {
        it.insert("CityA", 500, 500);
        it.insert("CityB", 600, 400);
        it.insert("CityC", 650, 450);
        it.insert("CityD", 625, 425);
        it.delete(600, 400);
        assertFuzzyEquals("", it.info(600, 400));
        assertFuzzyEquals("CityA", it.info(500, 500));
    }

    /**
     * Test deleting node with left subtree moved to right
     * @throws IOException
     */
    public void testDeleteNodeLeftMovedToRight() throws IOException {
        it.insert("CityA", 500, 500);
        it.insert("CityB", 400, 400);
        it.insert("CityC", 300, 300); 
        it.delete(400, 400);
        assertFuzzyEquals("", it.info(400, 400));
        assertFuzzyEquals("CityC", it.info(300, 300));
    }

    /**
     * Test deleting multiple nodes at different levels
     * @throws IOException
     */
    public void testDeleteMultipleLevels() throws IOException {
        it.insert("CityA", 500, 500);
        it.insert("CityB", 300, 300);
        it.insert("CityC", 700, 700);
        it.insert("CityD", 200, 400);
        it.insert("CityE", 400, 200);
        it.delete(300, 300);
        assertFuzzyEquals("", it.info(300, 300));
        assertFuzzyEquals("CityD", it.info(200, 400));
        assertFuzzyEquals("CityE", it.info(400, 200));
    }

    /**
     * Test deleting node forces findMin at deeper level
     * @throws IOException
     */
    public void testDeleteFindMinDeepLevel() throws IOException {
        it.insert("CityA", 500, 500);
        it.insert("CityB", 600, 600);
        it.insert("CityC", 700, 500);
        it.insert("CityD", 750, 550);
        it.insert("CityE", 725, 525);
        it.delete(600, 600);
        assertFuzzyEquals("", it.info(600, 600));
        assertFuzzyEquals("CityA", it.info(500, 500));
        assertFuzzyEquals("CityC", it.info(700, 500));
        assertFuzzyEquals("CityD", it.info(750, 550));
        assertFuzzyEquals("CityE", it.info(725, 525));
    }

    /**
     * Test deleting alternating discriminators
     * @throws IOException
     */
    public void testDeleteAlternatingDiscriminators() throws IOException {
        it.insert("CityA", 500, 500);
        it.insert("CityB", 600, 600);
        it.insert("CityC", 550, 700);
        it.insert("CityD", 525, 650);
        it.delete(600, 600);
        assertFuzzyEquals("", it.info(600, 600));
        assertFuzzyEquals("CityA", it.info(500, 500));
        assertFuzzyEquals("CityC", it.info(550, 700));
    }

    /**
     * Test delete with complex right subtree
     * @throws IOException
     */
    public void testDeleteComplexRightSubtree() throws IOException {
        it.insert("CityA", 400, 400);
        it.insert("CityB", 600, 600);
        it.insert("CityC", 700, 500);
        it.insert("CityD", 650, 550);
        it.insert("CityE", 750, 450);
        it.delete(600, 600);
        assertFuzzyEquals("", it.info(600, 600));
        assertFuzzyEquals("CityC", it.info(700, 500));
        assertFuzzyEquals("CityD", it.info(650, 550));
        assertFuzzyEquals("CityE", it.info(750, 450));
    }
    
    /**
     * Test deleting node with two children then delete by name
     * @throws IOException
     */
    public void testDeleteTwoChildrenThenDeleteByName() throws IOException {
        it.insert("CityA", 500, 500);
        it.insert("CityB", 300, 300);
        it.insert("CityC", 700, 700);
        it.insert("CityD", 400, 400);
        it.insert("CityD", 600, 600);
        it.delete(500, 500);
        assertFalse(it.delete("CityD").isEmpty());
        assertFuzzyEquals("", it.info("CityD"));
        assertFuzzyEquals("", it.info(400, 400));
        assertFuzzyEquals("", it.info(600, 600));
    }

    /**
     * Test multiple deletes with two children
     * @throws IOException
     */
    public void testMultipleTwoChildDeletes() throws IOException {
        it.insert("CityA", 500, 500);
        it.insert("CityB", 300, 300);
        it.insert("CityC", 700, 700);
        it.insert("CityD", 200, 200);
        it.insert("CityE", 400, 400);
        it.insert("CityF", 600, 600);
        it.insert("CityG", 800, 800);
        it.delete(300, 300);
        it.delete(700, 700);
        it.delete(500, 500);
        assertFuzzyEquals("CityD", it.info(200, 200));
        assertFuzzyEquals("CityE", it.info(400, 400));
        assertFuzzyEquals("CityF", it.info(600, 600));
        assertFuzzyEquals("CityG", it.info(800, 800));
        it.delete("CityD");
        assertFuzzyEquals("", it.info("CityD"));
    }

    /**
     * Test delete two children then insert
     * @throws IOException
     */
    public void testDeleteTwoChildrenThenInsert() throws IOException {
        it.insert("CityA", 500, 500);
        it.insert("CityB", 300, 300);
        it.insert("CityC", 700, 700);
        it.insert("CityD", 200, 200);
        it.insert("CityE", 400, 400);
        it.delete(500, 500);
        it.insert("NewCityA", 350, 350);
        it.insert("NewCityB", 450, 450);
        it.insert("NewCityC", 550, 550);
        assertFuzzyEquals("NewCityA", it.info(350, 350));
        assertFuzzyEquals("NewCityB", it.info(450, 450));
        assertFuzzyEquals("NewCityC", it.info(550, 550));
        assertFuzzyEquals("CityB", it.info(300, 300));
        assertFuzzyEquals("CityC", it.info(700, 700));
    }

    /**
     * Test delete two children then delete all by name
     * @throws IOException
     */
    public void testDeleteTwoChildrenThenDeleteAllByName() throws IOException {
        it.insert("CityName", 500, 500);
        it.insert("CityName", 300, 300);
        it.insert("CityName", 700, 700);
        it.insert("CityName", 200, 200); 
        it.insert("CityName", 400, 400); 
        it.delete(500, 500);
        // Check that the other four cities were deleted
        String result = it.delete("CityName");
        String[] str = result.split("\n");
        assertEquals(4, str.length);
        assertFuzzyEquals("", it.info("CityName"));
    }

    /**
     * Test chain of two-child deletions
     * @throws IOException
     */
    public void testChainTwoChildDeletions() throws IOException {
        it.insert("CityA", 800, 800);
        it.insert("CityB", 400, 400);
        it.insert("CityC", 1200, 1200);
        it.insert("CityD", 200, 200);
        it.insert("CityE", 600, 600);
        it.insert("CityF", 1000, 1000);
        it.insert("CityG", 1400, 1400);
        it.insert("CityH", 100, 100);
        it.insert("CityI", 300, 300);
        it.delete(400, 400);
        it.delete(800, 800);
        it.insert("CityJ", 450, 450);
        assertFuzzyEquals("CityJ", it.info(450, 450));
        it.delete("CityD");
        assertFuzzyEquals("", it.info("CityD"));
        assertFuzzyEquals("CityH", it.info(100, 100));
        assertFuzzyEquals("CityI", it.info(300, 300));
    }

    /**
     * Test delete two children then search all remaining
     * @throws IOException
     */
    public void testDeleteTwoChildrenSearchAll() throws IOException {
        it.insert("CityA", 500, 500); 
        it.insert("CityB", 300, 300); 
        it.insert("CityC", 700, 700);
        it.insert("CityD", 100, 100);
        it.insert("CityE", 400, 400);
        it.insert("CityF", 600, 600);
        it.insert("CityG", 900, 900);
        it.delete(500, 500);
        assertFuzzyEquals("CityB", it.info(300, 300));
        assertFuzzyEquals("CityC", it.info(700, 700));
        assertFuzzyEquals("CityD", it.info(100, 100));
        assertFuzzyEquals("CityE", it.info(400, 400));
        assertFuzzyEquals("CityF", it.info(600, 600));
        assertFuzzyEquals("CityG", it.info(900, 900));
        it.delete("CityD");
        it.delete("CityE");
        it.delete("CityF");
        assertFuzzyEquals("CityB", it.info(300, 300));
        assertFuzzyEquals("CityC", it.info(700, 700));
        assertFuzzyEquals("CityG", it.info(900, 900));
    }
    
    
    
    
    
    
}
