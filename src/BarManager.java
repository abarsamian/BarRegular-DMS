import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class BarManager {


//arraylist of regulars in system
    private ArrayList<BarRegular> regulars = new ArrayList<>();
    private Scanner scnr = new Scanner(System.in);

//getter for getting regulars-- to use for testing the size of the array
    public ArrayList<BarRegular> getRegulars() {
        return regulars;
    }
//NEED HELPER METHODS FOR VALIDATING DATA
    private int getValidInt() {
        while (!scnr.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number:");
            scnr.next();
        }
        return scnr.nextInt();
    }

    private double getValidDouble() {
        while (!scnr.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number:");
            scnr.next();
        }
        return scnr.nextDouble();
    }

    //add regular method
    public void addRegular() {

        System.out.println("Customer ID:");

        //validate input of id
        while (!scnr.hasNextInt()) {
            System.out.println("Invalid input. Please enter a 7-digit number:");
            scnr.next();
        }
       int id = scnr.nextInt();


        //need to prevent duplicate id
        for (BarRegular r : regulars) {
            if (r.getCustomerId() == id) {
                System.out.println("Customer ID already exists.\n");
                return;
            }
        }

        scnr.nextLine();

        System.out.println("Customer Name:");
        String name = scnr.nextLine();

        System.out.println("Favorite Drink:");
        String drink = scnr.nextLine();

        System.out.println("Monthly Visit Frequency:");
        int visits = getValidInt();

        System.out.println("Monthly Average Spend:");
        double spend = getValidDouble();

//created new regular - added to the list
        BarRegular r = new BarRegular(id, name, drink, visits, spend);
        regulars.add(r);

        System.out.println("Bar Regular added successfully.\n");
    }

    //add a boolean addregular method to help with testing since the other one is void
    public boolean addRegular(int id, String name, String drink, int visits, double spend) {

        // duplicate check
        for (BarRegular r : regulars) {
            if (r.getCustomerId() == id) {
                return false;
            }
        }

        regulars.add(new BarRegular(id, name, drink, visits, spend));
        return true;
    }


    // display all regulars
    public void showAllRegulars() {
        if (regulars.isEmpty()) {
            System.out.println("No bar regulars found.\n");
        } else {
            for (BarRegular r : regulars) {
                r.display();
            }
        }
    }
//show just the vip regulars, we have a boolean variable that calls the display for a customer only if they are VIPs
    public void showVipRegulars() {

        boolean found = false;

        for (BarRegular r : regulars) {
            if (r.getVipStatus()) {
                //if when we call the vip status for this customer, it displays and is true
                r.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No VIP customers found.\n");
        }
    }

//delete a regular method
    public void removeRegular() {

        if (regulars.isEmpty()) {
            System.out.println("No customers to remove.\n");
            return;
        }

        System.out.println("Enter Customer ID to remove:");
        int id = getValidInt();

        BarRegular found = null;

        for (BarRegular r : regulars) {
            if (r.getCustomerId() == id) {
                found = r;
                break;
            }
        }

        if (found != null) {
            regulars.remove(found);
            System.out.println("Bar Regular removed successfully.\n");
        } else {
            System.out.println("Customer not found.\n");
        }
    }

    //add a testable remove method as well
    public boolean removeRegularById(int id) {

        for (BarRegular r : regulars) {
            if (r.getCustomerId() == id) {
                regulars.remove(r);
                return true;
            }
        }

        return false;
    }

    //update a regular's monthly visit, average spend, or favorite drink
    public void editRegular() {

        System.out.println("Enter Customer ID you wish to edit:");
        int idEntry = getValidInt();
        boolean found = false;

        for (BarRegular r : regulars) {

            if (r.getCustomerId() == idEntry) {

                found = true;

                System.out.println("1. Edit favorite drink");
                System.out.println("2. Edit monthly visits");
                System.out.println("3. Edit monthly average spend");
                System.out.println("4. Edit name");


                while (!scnr.hasNextInt()) {
                    System.out.println("Enter 1–4:");
                    scnr.next();
                }
                int editChoice = getValidInt();

                scnr.nextLine(); // clear buffer

                if (editChoice == 1) {

                    System.out.println("Enter new favorite drink:");
                    String newDrink = scnr.nextLine();

                    r.setFavoriteDrink(newDrink);
                    System.out.println("Favorite drink updated.\n");
                }

                else if (editChoice == 2) {

                    System.out.println("Enter new monthly visits:");
                    int newVisits = getValidInt();

                    r.setVisitFrequencyMonthly(newVisits);
                    System.out.println("Monthly visits updated.\n");
                }

                else if (editChoice == 3) {

                    System.out.println("Enter new monthly average spend:");
                    double newSpend = getValidDouble();

                    r.setAverageSpendMonthly(newSpend);
                    System.out.println("Monthly spend updated.\n");
                }

                else if (editChoice == 4){
                    System.out.println("Enter new name");
                    String newName = scnr.nextLine();

                    r.setName(newName);
                    System.out.println("Name updated.\n");
                }

                else {
                    System.out.println("Invalid menu option.\n");
                }

                break;
            }
        }

        if (!found) {
            System.out.println("Customer ID not found.\n");
        }
    }

    //add testable edit method
    public boolean editVisitsById(int id, int visits) {
        for (BarRegular r : regulars) {
            if (r.getCustomerId() == id) {
                r.setVisitFrequencyMonthly(visits);
                return true;
            }
        }
        return false;
    }

    // load from file method boolean so we can test it-- logic goes here
    public boolean loadFromFile(String filename){
        //need a try catch for the file scanner
        try{
            Scanner fileScnr = new Scanner(new File(filename));

            while(fileScnr.hasNextLine()){
                String line = fileScnr.nextLine();
                //reads dash formatting
                String[] parts = line.split("-");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String drink = parts[2];
                int visits = Integer.parseInt(parts[3]);
                double spend = Double.parseDouble(parts[4]);
                //then adds a new patron based on what it reads from the file
                addRegular(id, name, drink, visits, spend);
            }

            fileScnr.close();
            return true;

        } catch (Exception e) {
          return false;
        }

    }

    //this is where we keep the scanner/ui for the load from file
    public void loadFromFile(){
        System.out.println("Enter file name");
        String filename = scnr.nextLine();

        boolean success = loadFromFile(filename);

        if(success){
            System.out.println("Loaded Bar Regulars from file."); //this tells us it was successful
        } else{
            System.out.println("Error reading file."); //tells us it was unsuccessful
        }
    }

}