import student.TestCase;
import java.io.IOException;


/**
 * Test class for City
 * 
 * @author Josh Kwen, James Son
 * @version 10/15/2025
 */
public class CityTest extends TestCase {

    private City city1;
    private City city2;
    private City city3;
    private City city4;

    /**
     * Set up test fixtures
     */
    public void setUp() throws IOException {
        city1 = new City("Blacksburg", 100, 200);
        city2 = new City("Blacksburg", 100, 200);
        city3 = new City("Roanoke", 150, 250);
        city4 = new City("Blacksburg", 100, 300);
    }

    /**
     * Test constructor and getters
     * @throws IOException
     */
    public void testConstructorAndGetters() throws IOException {
        assertEquals("Blacksburg", city1.getName());
        assertEquals(100, city1.getX());
        assertEquals(200, city1.getY());
    }

    /**
     * Test getName method
     * @throws IOException
     */
    public void testGetName() throws IOException {
        assertEquals("Blacksburg", city1.getName());
        assertEquals("Roanoke", city3.getName());
    }

    /**
     * Test getX method
     * @throws IOException
     */
    public void testGetX() throws IOException {
        assertEquals(100, city1.getX());
        assertEquals(150, city3.getX());
    }

    /**
     * Test getY method
     * @throws IOException
     */
    public void testGetY() throws IOException {
        assertEquals(200, city1.getY());
        assertEquals(250, city3.getY());
    }

    /**
     * Test compareTo with equal names
     * @throws IOException
     */
    public void testCompareToEqual() throws IOException {
        assertEquals(0, city1.compareTo(city2));
    }

    /**
     * Test compareTo with first city less than second
     * @throws IOException
     */
    public void testCompareToLessThan() throws IOException {
        assertTrue(city1.compareTo(city3) < 0);
    }

    /**
     * Test compareTo with first city greater than second
     * @throws IOException
     */
    public void testCompareToGreaterThan() throws IOException {
        assertTrue(city3.compareTo(city1) > 0);
    }

    /**
     * Test compareTo with same name but different coordinates
     * @throws IOException
     */
    public void testCompareToSameNameDifferentCoords() throws IOException {
        assertFalse(city1.compareTo(city4) == 0);
    }

    /**
     * Test toString method
     * @throws IOException
     */
    public void testToString() throws IOException {
        assertEquals("Blacksburg (100, 200)", city1.toString());
        assertEquals("Roanoke (150, 250)", city3.toString());
    }

    /**
     * Test toString with negative coordinates
     * @throws IOException
     */
    public void testToStringNegativeCoords() throws IOException {
        City negCity = new City("Test", -50, -100);
        assertEquals("Test (-50, -100)", negCity.toString());
    }

    /**
     * Test toString with zero coordinates
     * @throws IOException
     */
    public void testToStringZeroCoords() throws IOException {
        City zeroCity = new City("Origin", 0, 0);
        assertEquals("Origin (0, 0)", zeroCity.toString());
    }

    /**
     * Test equals with same object
     * @throws IOException
     */
    public void testEqualsSameObject() throws IOException {
        assertTrue(city1.equals(city1));
    }

    /**
     * Test equals with identical cities
     * @throws IOException
     */
    public void testEqualsIdenticalCities() throws IOException {
        assertTrue(city1.equals(city2));
        assertTrue(city2.equals(city1));
    }

    /**
     * Test equals with different names
     * @throws IOException
     */
    public void testEqualsDifferentNames() throws IOException {
        assertFalse(city1.equals(city3));
    }

    /**
     * Test equals with same name but different x coordinate
     * @throws IOException
     */
    public void testEqualsDifferentX() throws IOException {
        City differentX = new City("Blacksburg", 99, 200);
        assertFalse(city1.equals(differentX));
    }

    /**
     * Test equals with same name but different y coordinate
     * @throws IOException
     */
    public void testEqualsDifferentY() throws IOException {
        assertFalse(city1.equals(city4));
    }

    /**
     * Test equals with same name and x but different y
     * @throws IOException
     */
    public void testEqualsSameNameAndX() throws IOException {
        City sameNameX = new City("Blacksburg", 100, 201);
        assertFalse(city1.equals(sameNameX));
    }

    /**
     * Test equals with null object
     * @throws IOException
     */
    public void testEqualsNull() throws IOException {
        assertFalse(city1.equals(null));
    }

    /**
     * Test equals with different class object
     * @throws IOException
     */
    public void testEqualsDifferentClass() throws IOException {
        String notACity = "Not a city";
        assertFalse(city1.equals(notACity));
    }

    /**
     * Test equals with different class (Integer)
     * @throws IOException
     */
    public void testEqualsDifferentClassInteger() throws IOException {
        Integer notACity = 100;
        assertFalse(city1.equals(notACity));
    }

    /**
     * Test with empty string name
     * @throws IOException
     */
    public void testEmptyName() throws IOException {
        City emptyName = new City("", 10, 20);
        assertEquals("", emptyName.getName());
        assertEquals(" (10, 20)", emptyName.toString());
    }

    /**
     * Test compareTo with empty strings
     * @throws IOException
     */
    public void testCompareToEmptyString() throws IOException {
        City empty1 = new City("", 0, 0);
        City empty2 = new City("", 0, 0);
        assertEquals(0, empty1.compareTo(empty2));
    }

    /**
     * Test compareTo with one empty string
     * @throws IOException
     */
    public void testCompareToOneEmpty() throws IOException {
        City empty = new City("", 0, 0);
        assertTrue(empty.compareTo(city1) < 0);
        assertTrue(city1.compareTo(empty) > 0);
    }
}