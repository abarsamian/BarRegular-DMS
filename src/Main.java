import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//create bar manager system and scanner
        Scanner scnr = new Scanner(System.in);
        BarManager manager = new BarManager();

        while (true) {

            System.out.println("---- Bar Regular Management System ----");
            System.out.println("1. Add Bar Regular");
            System.out.println("2. Remove Bar Regular");
            System.out.println("3. Edit Existing Bar Regular");
            System.out.println("4. View All Bar Regulars");
            System.out.println("5. View VIP Bar Regulars");
            System.out.println("6. Load Bar Regular from file");
            System.out.println("7. Exit");

//get user choice with validation-- must be a number from the menu
            while (!scnr.hasNextInt()) {
                System.out.println("Enter a number from the menu:");
                scnr.next();
            }
            int choice = scnr.nextInt();


            if (choice == 1) {
                manager.addRegular();
            }
            else if (choice == 2) {
                manager.removeRegular();
            }
            else if (choice == 3) {
                manager.editRegular();
            }
            else if (choice == 4) {
                manager.showAllRegulars();
            }
            else if (choice == 5) {
                manager.showVipRegulars();
            }
            else if (choice == 6) {
                manager.loadFromFile();
            }
            else if (choice == 7) {
                System.out.println("Goodbye!");
                break;
            }
            else {
                System.out.println("Invalid option.\n");
            }
        }
    }
}