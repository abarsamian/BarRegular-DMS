import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class BarManager {


//arraylist of regulars in system
    private ArrayList<BarRegular> regulars = new ArrayList<>();
    private Scanner scnr = new Scanner(System.in);

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
        int id = getValidInt();


//validate input of id
        while (id < 1000000 || id > 9999999) {
            System.out.println("ID must be 7 digits:");
            id = getValidInt();
        }
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
                //if wehn we call the vip status for this customer, it displays and is true
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

    //update a regular's monthly visit, average spend, or favorite drink
    public void editRegular() {

        if (regulars.isEmpty()) {
            System.out.println("No customers to edit.\n");
            return;
        }

        System.out.println("Enter Customer ID to edit:");
        int id = getValidInt();

        for (BarRegular r : regulars) {
            if (r.getCustomerId() == id) {

                System.out.println("Enter new monthly visits:");
                int visits = getValidInt();

                System.out.println("Enter new monthly average spend:");
                double spend = getValidDouble();

                System.out.println("Enter new favorite drink");
                String drink = scnr.next();

                r.updateCustomer(visits, spend, drink);

                System.out.println("Bar Regular updated successfully.\n");
                return;
            }
        }

        System.out.println("Customer not found.\n");
    }

    // load from file method
    public void loadFromFile(){
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter file name");
        String filename = scnr.nextLine();
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
                regulars.add(new BarRegular(id, name, drink, visits, spend));
            }

            fileScnr.close();
            System.out.println("Loaded Bar Regulars from file."); //this tells us it was successful

        } catch (Exception e) {
            System.out.println("Error reading file."); //tells us it was unsuccessful
        }

    }
}