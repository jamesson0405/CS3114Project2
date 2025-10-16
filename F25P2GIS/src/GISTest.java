import java.io.IOException;
import student.TestCase;

/**
 * 
 * @author Josh Kwen, James Son
 * 
 * @version 10/09/2025
 */
public class GISTest extends TestCase {

    private GIS it;

    /**
     * 
     * Sets up the tests
     */
    public void setUp() {
        it = new GISDB();
    }


    /**
     * 
     * @throws IOException
     */
    public void testRefClearInit() throws IOException {
        assertTrue(it.clear());
    }


    /**
     * 
     * @throws IOException
     */
    public void testRefEmptyPrints() throws IOException {
        assertFuzzyEquals("", it.print());
        assertFuzzyEquals("", it.debug());
        assertFuzzyEquals("", it.info("CityName"));
        assertFuzzyEquals("", it.info(5, 5));
        assertFuzzyEquals("", it.delete("CityName"));
        assertFuzzyEquals("", it.delete(5, 5));
    }


    /**
     * 
     * @throws IOException
     */
    public void testRefBadInput() throws IOException {
        assertFalse(it.insert("CityName", -1, 5));
        assertFalse(it.insert("CityName", 5, -1));
        assertFalse(it.insert("CityName", 100000, 5));
        assertFalse(it.insert("CityName", 5, 100000));
        assertFuzzyEquals("", it.search(-1, -1, -1));
    }


    /**
     * 
     * @throws IOException
     */
    public void testCoordinatesBoundary() throws IOException {
        assertTrue(it.insert("City1", 0, 0));
        assertTrue(it.insert("City2", 32767, 32767));
        assertFalse(it.insert("City3", 32768, 200));
        assertFalse(it.insert("City4", 200, 32768));
    }


    /**
     * 
     * @throws IOException
     */
    public void testSameNameDiffCoord() throws IOException {
        assertTrue(it.insert("Blacksburg", 100, 100));
        assertTrue(it.insert("Blacksburg", 200, 200));
        assertTrue(it.insert("Blacksburg", 90, 90));
        assertFuzzyEquals(
            "2 Blacksburg (90, 90)\n1 Blacksburg (200, 200)\n0Blacksburg "
                + "(100, 100)\n", it.print());
    }


    /**
     * 
     * @throws IOException
     */
    public void testInsertThenClear() throws IOException {
        assertTrue(it.insert("Christiansburg", 50, 50));
        assertTrue(it.clear());
        assertFuzzyEquals("", it.print());
    }


    /**
     * 
     * @throws IOException
     */
    public void testDuplicateCoordinates() throws IOException {
        assertTrue(it.insert("Radford", 90, 90));
        assertFalse(it.insert("Blacksburg", 90, 90));
    }


    /**
     * Checks X split at root; verifies preorder line order.
     * 
     * @throws IOException
     *             if setup fails
     */
    public void testXDiscriminator() throws IOException {
        assertTrue(it.insert("CityA", 100, 100));
        assertTrue(it.insert("CityB", 99, 101));
        assertTrue(it.insert("CityC", 101, 99));

        String norm = it.debug().toLowerCase().replace(" ", "");

        assertTrue(norm.contains("0citya(100,100)"));
        assertTrue(norm.contains("1cityb(99,101)"));
        assertTrue(norm.contains("1cityc(101,99)"));
    }


    /**
     * Checks Y split at level 1; verifies preorder line order.
     * 
     * @throws IOException
     *             if setup fails
     */
    public void testYDiscriminator() throws IOException {
        assertTrue(it.insert("CityA", 200, 200));
        assertTrue(it.insert("CityB", 100, 200));
        assertTrue(it.insert("CityC", 50, 100));
        assertTrue(it.insert("CityD", 150, 400));

        String norm = it.debug().toLowerCase().replace(" ", "");

        assertTrue(norm.contains("0citya(200,200)"));
        assertTrue(norm.contains("1cityb(100,200)"));
        assertTrue(norm.contains("2cityc(50,100)"));
        assertTrue(norm.contains("2cityd(150,400)"));
    }


    /**
     * keep ref compat for null insert but check state only
     */
    public void testBSTNullInsert() throws IOException {
        it.insert(null, 100, 100);
        assertFuzzyEquals("", it.print());
    }


    /**
     * insert rejects null and blank names
     */
    public void testInsertRejectsNullAndBlank() throws IOException {
        assertFalse(it.insert(null, 10, 10));
        assertFalse(it.insert("   ", 10, 10));
    }


    /**
     * delete by coord returns empty when not found
     */
    public void testDeleteCoordNotFoundReturnsEmpty() throws IOException {
        it.insert("A", 1, 1);
        assertFuzzyEquals("", it.delete(9, 9));
    }


    /**
     * delete by name prints each on new line and stops when none left
     */
    public void testDeleteNameBreakAndNewline() throws IOException {
        it.insert("Same", 1, 1);
        it.insert("Same", 2, 2);
        String out = it.delete("Same");
        assertFuzzyEquals("Same\nSame", out);
        assertFuzzyEquals("", it.delete("Same"));
    }


    /**
     * info x y not found returns empty
     */
    public void testInfoXYNotFoundReturnsEmpty() throws IOException {
        it.insert("A", 5, 5);
        assertFuzzyEquals("", it.info(7, 7));
    }


    /**
     * info name null or empty returns empty
     * 
     * @throws IOException
     */
    public void testInfoNameNullOrEmptyReturnsEmpty() throws IOException {
        assertFuzzyEquals("", it.info((String)null));
        assertFuzzyEquals("", it.info(""));
    }


    /**
     * negative radius returns empty and avoids kd work
     * 
     * @throws IOException
     */
    public void testSearchNegativeRadiusReturnsEmpty() throws IOException {
        it.insert("A", 10, 10);
        assertFuzzyEquals("", it.search(10, 10, -1));
    }


    /**
     * insert then info by name lists coordinates
     * 
     * @throws IOException
     */
    public void testInsertThenInfoByName() throws IOException {
        it.insert("CityA", 100, 100);
        String s = it.info("CityA");
        assertTrue(s.contains("(100, 100)"));
    }


    /**
     * delete by coord removes from both indices
     * 
     * @throws IOException
     */
    public void testDeleteCoordRemovesFromBoth() throws IOException {
        it.insert("Gone", 7, 7);
        assertEquals("Gone", it.delete(7, 7));
        assertFuzzyEquals("", it.info("Gone"));
        assertFuzzyEquals("", it.info(7, 7));
    }

}
