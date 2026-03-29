import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class BarManager {


//arraylist of regulars in system
    private ArrayList<BarRegular> regulars = new ArrayList<>();
//getter for getting regulars-- to use for testing the size of the array
    public ArrayList<BarRegular> getRegulars() {
        return regulars;
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

    //add testable edit method
    //changed edit method to edit all fields
    public boolean editRegular(int id, String newName, String newDrink, int newVisits, double newSpend) {
        for (BarRegular r : regulars) {
            if (r.getCustomerId() == id) {
                r.setName(newName);
                r.setFavoriteDrink(newDrink);
                r.setVisitFrequencyMonthly(newVisits);
                r.setAverageSpendMonthly(newSpend);
                return true;
            }
        }
        return false;
    }
//need this method for the gui
    public BarRegular findRegularById(int id) {
        for (BarRegular r : regulars) {
            if (r.getCustomerId() == id) {
                return r;
            }
        }
        return null;
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

    //removed all scanner stuff, here is return all regulars as text for the gui
    public String getAllRegularsText(){
            if (regulars.isEmpty()) {
                return "No bar regulars found.";
            }

            String result = "";

            for (BarRegular r : regulars) {
                result += r.toString() + "\n";
            }

            return result;
        }

        //and vip regulars as text for the gui
    public String getVipRegularsText() {
        String result = "";
        boolean found = false;

        for (BarRegular r : regulars) {
            if (r.getVipStatus()) {
                result += r.toString() + "\n";
                found = true;
            }
        }

        if (!found) {
            return "No VIP customers found.";
        }

        return result;
    }


}