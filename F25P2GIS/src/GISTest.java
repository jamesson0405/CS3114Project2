import java.io.IOException;
import student.TestCase;

/**
 * @author Josh Kwen, James Son
 * @version 10/06/2025
 */
public class GISTest extends TestCase {

    private GIS it;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        it = new GISDB();
    }

    /**
     * Test clearing on initial
     * @throws IOException
     */
    public void testRefClearInit()
        throws IOException
    {
        assertTrue(it.clear());
    }


    /**
     * Print testing for empty trees
     * @throws IOException
     */
    public void testRefEmptyPrints()
        throws IOException
    {
        assertFuzzyEquals("", it.print());
        assertFuzzyEquals("", it.debug());
        assertFuzzyEquals("", it.info("CityName"));
        assertFuzzyEquals("", it.info(5, 5));
        assertFuzzyEquals("", it.delete("CityName"));
        assertFuzzyEquals("", it.delete(5, 5));
    }


    /**
     * Print bad input checks
     * @throws IOException
     */
    public void testRefBadInput()
        throws IOException
    {
        assertFalse(it.insert("CityName", -1, 5));
        assertFalse(it.insert("CityName", 5, -1));
        assertFalse(it.insert("CityName", 100000, 5));
        assertFalse(it.insert("CityName", 5, 100000));
        assertFuzzyEquals("", it.search(-1, -1, -1));
        
    }


    /**
     * Insert some records and check output requirements for various commands
     * @throws IOException
     */
    /**public void testRefOutput()
        throws IOException
    {
        assertTrue(it.insert("Chicago", 100, 150));
        assertTrue(it.insert("Atlanta", 10, 500));
        assertTrue(it.insert("Tacoma", 1000, 100));
        assertTrue(it.insert("Baltimore", 0, 300));
        assertTrue(it.insert("Washington", 5, 350));
        assertFalse(it.insert("X", 100, 150));
        assertTrue(it.insert("L", 101, 150));
        assertTrue(it.insert("L", 11, 500));
        assertFuzzyEquals("1  Atlanta (10, 500)\n"
            + "2    Baltimore (0, 300)\n"
            + "0Chicago (100, 150)\n"
            + "3      L (11, 500)\n"
            + "2    L (101, 150)\n"
            + "1  Tacoma (1000, 100)\n"
            + "2    Washington (5, 350)\n", it.print());
        assertFuzzyEquals("2    Baltimore (0, 300)\n"
            + "3      Washington (5, 350)\n"
            + "1  Atlanta (10, 500)\n"
            + "2    L (11, 500)\n"
            + "0Chicago (100, 150)\n"
            + "1  Tacoma (1000, 100)\n"
            + "2    L (101, 150)\n", it.debug());
        assertFuzzyEquals("L (101, 150)\nL (11, 500)", it.info("L"));
        assertFuzzyEquals("L", it.info(101, 150));
        assertFuzzyEquals("Tacoma (1000, 100)", it.delete("Tacoma"));
        assertFuzzyEquals("3\nChicago", it.delete(100, 150));
        assertFuzzyEquals("L (101, 150)\n"
                + "Atlanta (10, 500)\n"
                + "Baltimore (0, 300)\n"
                + "Washington (5, 350)\n"
                + "L (11, 500)\n5", it.search(0, 0, 2000));
        assertFuzzyEquals("Baltimore (0, 300)\n4", it.search(0, 300, 0));
    }*/
    
    /**
     * Test coordinate boundaries
     * @throws IOException
     */
    public void testCoordinatesBoundary() 
        throws IOException
    {
        assertTrue(it.insert("City1", 0, 0));
        assertTrue(it.insert("City2", 32767, 32767));
        assertFalse(it.insert("City3", 32768, 200));
        assertFalse(it.insert("City4", 200, 32768));
    }
    
    /**
     * Test same city name with different coordinates
     * @throws IOException
     */
    public void testSameNameDiffCoord() 
        throws IOException
    {
        assertTrue(it.insert("Blacksburg", 100, 100));
        assertTrue(it.insert("Blacksburg", 200, 200));
        assertTrue(it.insert("Blacksburg", 90, 90));
        
        assertFuzzyEquals("2 Blacksburg (90, 90)\n"
            + "1 Blacksburg (200, 200)\n"
            + "0Blacksburg (100, 100)\n", it.print());
    }
    
    /**
     * Test isEmpty in BST
     * @throws IOException
     */
    public void testIsEmpty() 
        throws IOException
    {
        assertFuzzyEquals("", it.print());
        assertFuzzyEquals("", it.debug());
        assertTrue(it.insert("City", 500, 500));
        assertFuzzyEquals("0City (500, 500)\n", it.print());
    }
    
    /**
     * Test insertion and then clear
     * @throws IOException
     */
    public void testInsertThenClear() 
        throws IOException
    {
        assertTrue(it.insert("Christiansburg", 50, 50));
        assertTrue(it.clear());
        assertFuzzyEquals("", it.print());
        
    }
    
    /**
     * Test duplicate coordinate insertion
     * @throws IOException
     */
    public void testDuplicateCoordinates() 
        throws IOException
    {
        assertTrue(it.insert("Radford", 90, 90));
        assertFalse(it.insert("Blacksburg", 90, 90));

    }
    
    /**
     * Test coordinates off by one 
     * @throws IOException
     */
    public void testCoordinatesOffByOne() 
        throws IOException
    {
        assertTrue(it.insert("City", 91, 90));
        assertTrue(it.insert("City", 89, 90));
        assertTrue(it.insert("City", 90, 91));
        assertTrue(it.insert("City", 90, 89));
    }
    
    /**
     * Test X discriminator 
     * @throws IOException
     */
    public void testXDiscriminator() 
        throws IOException
    {
        // For X at root
        assertTrue(it.insert("CityA", 100, 100));
        assertTrue(it.insert("CityB", 99, 101));
        assertTrue(it.insert("CityC", 101, 99));
        assertFuzzyEquals("1 CityB (99, 101)\n"
            + "0CityA (100, 100)\n"
            + "1 CityC (101, 99)\n", it.debug());
    }
    
    /**
     * Test Y discriminator
     * @throws IOException
     */
    public void testYDiscriminator() 
        throws IOException
    {
        assertTrue(it.insert("CityA", 200, 200));
        assertTrue(it.insert("CityB", 100, 200));
        assertTrue(it.insert("CityC", 50, 100));
        assertTrue(it.insert("CityD", 150, 400));
        assertFuzzyEquals("2 CityC (50, 100)\n"
            + "1 CityB (100, 200)\n"
            + "2 CityD (150, 400)\n"
            + "0CityA (200, 200\n)", it.debug());
    }
    
    /**
     * Test X discriminator at level 2 
     * @throws IOException
     */
    public void testXDiscriminatorLevelTwo() 
        throws IOException
    {
        assertTrue(it.insert("CityA", 200, 200));
        assertTrue(it.insert("CityB", 100, 200));
        assertTrue(it.insert("CityC", 100, 100));
        assertTrue(it.insert("CityD", 50, 75));
        assertTrue(it.insert("CityE", 150, 75));
        assertFuzzyEquals("3 CityD (50, 75)\n"
            + "2 CityC (100, 100)\n"
            + "3 CityE (150, 75)\n"
            + "1 CityB (100, 200)\n"
            + "0CityA (200, 200)\n", it.debug());
    }
    
    /**
     * Test alternating discriminator 
     * @throws IOException
     */
    public void testAlternatinDiscriminator() 
        throws IOException
    {
        assertTrue(it.insert("CityA", 200, 200));
        assertTrue(it.insert("CityB", 150, 175));
        assertTrue(it.insert("CityC", 400, 300));
        assertTrue(it.insert("CityD", 100, 150));
        assertFuzzyEquals("2 CityD (100, 150)\n"
            + "1 CityB (150, 175)\n"
            + "0CityA (200, 200)\n"
            + "1 CityC (400, 300)\n", it.debug());
    }
    
    /**
     * Test discriminator for more coverage
     * @throws IOException
     */
    public void testDiscriminatorCoverage() 
        throws IOException
    {
        assertTrue(it.insert("CityA", 200, 200));
        assertTrue(it.insert("CityB", 150, 250));
        assertTrue(it.insert("CityC", 250, 150));
        assertTrue(it.insert("CityD", 125, 275));
        assertTrue(it.insert("CityE", 175, 125));
        assertTrue(it.insert("CityF", 140, 260));
        assertTrue(it.insert("CityG", 260, 140));
        
        assertFuzzyEquals("2 CityE (175, 125)\n"
            + "1 CityB (150, 250)\n"
            + "2 CityD (125, 275)\n"
            + "3 CityF (140, 260)\n"
            + "0CityA (200, 200)\n"
            + "2 CityG (260, 140)\n"
            + "1 CityC (250, 150)\n", it.debug());
    }
    
    
    
    
    
}
