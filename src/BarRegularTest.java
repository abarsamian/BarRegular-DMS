import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class BarRegularTest {
    BarRegular regular;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    regular = new BarRegular(1029384, "Sarah Smith", "Titos soda lime", 27, 1039);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Display customer id")
    void getCustomerId() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Display customer name")
    void getName() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Display customer favorite drink")
    void getFavoriteDrink() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Display customer visit frequency")
    void getVisitFrequencyMonthly() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Display customer average spend")
    void getAverageSpendMonthly() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Display customer VIP Status")
    void getVipStatus() {
        //assert that the vip status is equal to the one in BarRegular
        boolean vip_status = (regular.getVisitFrequencyMonthly() > 25 ||  regular.getAverageSpendMonthly() > 1000);
        assertEquals(vip_status, regular.getVipStatus());

    }
}