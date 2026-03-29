import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BarManagerTest {
    BarManager manager;

    @BeforeEach
    void setUp() {
        manager = new BarManager();
    }

    @Test
    void testAddRegular() {
        boolean result = manager.addRegular(1345432, "Sarah Smith", "Titos Soda Lime", 26, 1001);

        assertEquals(true, result, "Added regular passed");
    }

    @Test
    void testAddRegular_duplicateTest() {
        manager.addRegular(1345432, "Sarah Smith", "Titos Soda Lime", 26, 1001);
        boolean result = manager.addRegular(1345432, "John Tozzy", "Yuengling", 34, 1045);

        assertEquals(false, result, "should fail for duplicate id");
        assertEquals(1, manager.getRegulars().size());
//this test should not add the regular because there is a duplicate id, it should stay at 1
    }


    @Test
    void testRemoveRegular() {
        manager.addRegular(1345432, "Sarah Smith", "Titos Soda Lime", 26, 1001);
        boolean removed = manager.removeRegularById(1345432);

        assertEquals(0, manager.getRegulars().size());
    }

    @Test
    void testRemoveIdNotFound(){
        boolean removed = manager.removeRegularById(1234098);

        assertFalse(removed);
        //should be false because this id does not exist
    }

    @Test
    void testEditRegularVisitsAndVipLogic() {
        manager.addRegular(1234567, "Arlo Kettia", "Bud Light", 20, 200);

        int visits = manager.getRegulars().get(0).getVisitFrequencyMonthly();
        boolean vip = manager.getRegulars().get(0).getVipStatus();

        assertEquals(26, visits, "visit number should change");
        assertEquals(true, vip, "should become a vip");
    }


    @Test
    void testLoadFromFile() {
        boolean result = manager.loadFromFile("regular.txt");
        assertEquals(true, result, "File should add successfully");

        int size = manager.getRegulars().size();
        assertEquals(21, size, "should load 20 but could be more because im not sure exactly lol");

    }
}