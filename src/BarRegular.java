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
                (averageSpendMonthly) > 1000);
    }

    //setters for updating customers
    public void setFavoriteDrink(String drink) {
        this.favoriteDrink = drink;
    }

    public void setVisitFrequencyMonthly(int visits) {
        this.visitFrequencyMonthly = visits;
        calculateVipStatus();
    }

    public void setAverageSpendMonthly(double spend) {
        this.averageSpendMonthly = spend;
        calculateVipStatus();
    }

    //getters
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getFavoriteDrink() { return favoriteDrink; }
    public int getVisitFrequencyMonthly() { return visitFrequencyMonthly; }
    public double getAverageSpendMonthly() { return averageSpendMonthly; }
    public boolean getVipStatus() { return vipStatus; }


    //display menu so we can call it whenever
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