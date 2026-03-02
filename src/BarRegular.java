public class BarRegular {

    private int customerId;
    private String name;
    private String favoriteDrink;
    private int visitFrequencyMonthly;
    private double averageSpendMonthly;
    private boolean vipStatus;

    public BarRegular(int customerId, String name, String favoriteDrink,
                      int visitFrequencyMonthly, double averageSpendMonthly) {

        this.customerId = customerId;
        this.name = name;
        this.favoriteDrink = favoriteDrink;
        this.visitFrequencyMonthly = visitFrequencyMonthly;
        this.averageSpendMonthly = averageSpendMonthly;

        calculateVipStatus();
    }
//vip status method is in here because every bar regular has one
    private void calculateVipStatus() {
        vipStatus = (visitFrequencyMonthly > 25 ||
                (visitFrequencyMonthly * averageSpendMonthly) > 1000);
    }
//update customer alters bar regular data, need it in here so we can recalculate vip status
    public void updateCustomer(int visits, double spend, String drink) {
        this.visitFrequencyMonthly = visits;
        this.averageSpendMonthly = spend;
        this.favoriteDrink = drink;
        calculateVipStatus();
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getFavoriteDrink() { return favoriteDrink; }
    public int getVisitFrequencyMonthly() { return visitFrequencyMonthly; }
    public double getAverageSpendMonthly() { return averageSpendMonthly; }
    public boolean getVipStatus() { return vipStatus; }

    public void display() {
        System.out.println("ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Favorite Drink: " + favoriteDrink);
        System.out.println("Monthly Visits: " + visitFrequencyMonthly);
        System.out.println("Monthly Spend: $" + averageSpendMonthly);
        System.out.println("VIP: " + vipStatus);
        System.out.println("---------------------------");
    }
}