import DBHelper.DBHelper;
import DBHelper.regulars;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BarRegularGUI extends Application {

    private TextArea output = new TextArea();
    private regulars regulars = new regulars("C:\\sqlite\\bar_regulars.db");

    /**
     * This is the starting stage where I set the grid for the main menu of the DMS.
     * @param stage the stage we are going to return
     */
    @Override
    public void start (Stage stage){
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(grid, 550, 500);

        Text scenetitle = new Text("Bar Regular DMS");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        GridPane.setColumnSpan(scenetitle, 2);
        GridPane.setHalignment(scenetitle, HPos.CENTER);

        //this is where the output will be when you want to view all regulars or all vip
        output.setEditable(false);
        output.setPrefHeight(175);
        output.setPromptText("Output will appear here...");

                    //ADD REGULAR BUTTON AND FUNCTIONALITY


        Button addreg = new Button("Add Regular");

        addreg.setOnAction(e -> {
            Stage addStage = new Stage();
            addStage.setTitle("Add Regular");

            GridPane addGrid = new GridPane();
            addGrid.setPadding(new Insets(20));
            addGrid.setHgap(10);
            addGrid.setVgap(10);
            addGrid.setAlignment(Pos.CENTER);

            TextField idField = new TextField();
            TextField nameField = new TextField();
            TextField drinkField = new TextField();
            TextField visitsField = new TextField();
            TextField spendField = new TextField();

            addGrid.add(new Text("Customer ID: "), 0, 0);
            addGrid.add(idField, 1, 0);

            addGrid.add(new Text("Name: "), 0,1);
            addGrid.add(nameField, 1, 1);

            addGrid.add(new Text("Favorite Drink:"), 0, 2);
            addGrid.add(drinkField, 1, 2);

            addGrid.add(new Text("Visit Count:"), 0, 3);
            addGrid.add(visitsField, 1, 3);

            addGrid.add(new Text("Monthly Spend"), 0, 4);
            addGrid.add(spendField, 1, 4);


            Button saveButton = new Button("Save");
            addGrid.add(saveButton, 1, 5);

            //this is what happens when you press save, fills all the barmanager variables
            saveButton.setOnAction(event ->{
                try{
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String drink = drinkField.getText();
                    int visits = Integer.parseInt(visitsField.getText());
                    double spend = Double.parseDouble(spendField.getText());
                    //this is where im adding the part where we figure out if someone is vip or not, store it as 0 or 1, and return those later
                    int vip = (visits > 25 || spend > 1000) ? 1 : 0;

                    try {
                        regulars.insert(id, name, drink, visits, spend, vip);
                        output.setText("Bar Regular added successfully.");
                        addStage.close();
                    } catch (Exception ex) {
                        output.setText("Error adding regular.");
                    }

                } catch (NumberFormatException ex) {
                    output.setText("Please enter valid numbers for ID, visits, and spend.");
                }
            });

            Scene addScene = new Scene(addGrid, 400, 375);
            addStage.setScene(addScene);
            addStage.show();
        });


                        //REMOVE REGULAR BUTTON AND FUNCTIONALITY
        Button remreg = new Button("Remove Regular");

        remreg.setOnAction(e -> {
            Stage removeStage = new Stage();
            removeStage.setTitle("Remove Regular");
            ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
            data = regulars.getExecuteResult("select * from regulars;");


            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));
            layout.setAlignment(Pos.TOP_CENTER);

            for (ArrayList<Object> rowData : data) {

                int id = (int) rowData.get(0);
                String name = (String) rowData.get(1);
                String drink = (String) rowData.get(2);
                int visits = (int) rowData.get(3);
                double spend = (double) rowData.get(4);


                HBox row = new HBox(10);
                row.setAlignment(Pos.CENTER_LEFT);

                Label info = new Label(id + " | " + name + " | " + drink + " | Visits: " + visits + " | $" + spend);

                Button removeBtn = new Button("Remove");

                removeBtn.setOnAction(event -> {
                    regulars.delete(regulars.id, String.valueOf(id));
                    output.setText("Removed: " + name);
                    removeStage.close(); // close after removal
                });

                row.getChildren().addAll(info, removeBtn);
                layout.getChildren().add(row);
            }

            ScrollPane scrollPane = new ScrollPane(layout);
            scrollPane.setFitToWidth(true);

            Scene remscene = new Scene(scrollPane, 800, 600);
            removeStage.setScene(remscene);
            removeStage.show();
        });



                    //EDIT REGULAR BUTTON AND ALL BUTTONS WITHIN

        Button edreg = new Button("Edit Regular");

        edreg.setOnAction(e -> {
            Stage editStage = new Stage();
            editStage.setTitle("Edit Regular");
            ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
            data = regulars.getExecuteResult("select * from regulars;");

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));
            layout.setAlignment(Pos.TOP_CENTER);

            for (ArrayList<Object> rowData : data) {

                int id = (int) rowData.get(0);
                String name = (String) rowData.get(1);
                String drink = (String) rowData.get(2);
                int visits = (int) rowData.get(3);
                double spend = (double) rowData.get(4);


                HBox row = new HBox(10);
                row.setAlignment(Pos.CENTER_LEFT);

                Label info = new Label(id + " | " + name + " | " + drink + " | Visits: " + visits + " | $" + spend);

                Button editBtn = new Button("Edit");

                editBtn.setOnAction(event -> {

                    Stage editPage = new Stage();
                    editPage.setTitle("Choose");

                    GridPane pageGrid = new GridPane();
                    pageGrid.setPadding(new Insets(20));
                    pageGrid.setHgap(10);
                    pageGrid.setVgap(10);
                    pageGrid.setAlignment(Pos.CENTER);

                    Button edName = new Button("Edit Name");
                    Button edDrink = new Button("Edit Favorite Drink");
                    Button edVis = new Button("Edit Number of Visits");
                    Button edSpend = new Button("Edit Average Monthly Spend");


                    VBox editButtonBox = new VBox(10);
                    editButtonBox.setAlignment(Pos.CENTER);
                    editButtonBox.setFillWidth(false);

                    edName.setPrefWidth(220);
                    edDrink.setPrefWidth(220);
                    edVis.setPrefWidth(220);
                    edSpend.setPrefWidth(220);


                    editButtonBox.getChildren().addAll(edName, edDrink, edVis, edSpend);

                    pageGrid.add(editButtonBox, 0, 0);
                    GridPane.setHalignment(editButtonBox, HPos.CENTER);

                    //buttons for editing now

                    edName.setOnAction(ev -> {
                        Stage nameStage = new Stage();
                        nameStage.setTitle("Edit Name");

                        GridPane nameGrid = new GridPane();
                        nameGrid.setPadding(new Insets(20));
                        nameGrid.setHgap(10);
                        nameGrid.setVgap(10);
                        nameGrid.setAlignment(Pos.CENTER);

                        TextField newNameField = new TextField();

                        nameGrid.add(new Text("New Name:"), 0, 0);
                        nameGrid.add(newNameField, 1, 0);

                        Button saveName = new Button("Save");
                        nameGrid.add(saveName, 1, 1);

                        saveName.setOnAction(saveEvent -> {
                            String newName = newNameField.getText();

                            regulars.update(
                                    regulars.name,
                                    newName,
                                    regulars.id,
                                    String.valueOf(id)
                            );
                            output.setText("Name updated successfully.");
                            nameStage.close();
                            editPage.close();
                            editStage.close();
                        });

                        Scene nameScene = new Scene(nameGrid, 300, 150);
                        nameStage.setScene(nameScene);
                        nameStage.show();
                    });

                    edDrink.setOnAction(ev -> {
                        Stage drinkStage = new Stage();
                        drinkStage.setTitle("Edit Favorite Drink");

                        GridPane drinkGrid = new GridPane();
                        drinkGrid.setPadding(new Insets(20));
                        drinkGrid.setHgap(10);
                        drinkGrid.setVgap(10);
                        drinkGrid.setAlignment(Pos.CENTER);

                        TextField newDrinkField = new TextField();

                        drinkGrid.add(new Text("New Favorite Drink:"), 0, 0);
                        drinkGrid.add(newDrinkField, 1, 0);

                        Button saveDrink = new Button("Save");
                        drinkGrid.add(saveDrink, 1, 1);

                        saveDrink.setOnAction(saveEvent -> {
                            String newDrink = newDrinkField.getText();
                            regulars.update(
                                    regulars.favorite_drink,
                                    newDrink,
                                    regulars.id,
                                    String.valueOf(id)
                            );
                            output.setText("Favorite drink updated successfully.");
                            drinkStage.close();
                            editPage.close();
                            editStage.close();
                        });

                        Scene drinkScene = new Scene(drinkGrid, 320, 150);
                        drinkStage.setScene(drinkScene);
                        drinkStage.show();
                    });

                    edVis.setOnAction(ev -> {
                        Stage visitsStage = new Stage();
                        visitsStage.setTitle("Edit Number of Visits");

                        GridPane visitsGrid = new GridPane();
                        visitsGrid.setPadding(new Insets(20));
                        visitsGrid.setHgap(10);
                        visitsGrid.setVgap(10);
                        visitsGrid.setAlignment(Pos.CENTER);

                        TextField newVisitsField = new TextField();

                        visitsGrid.add(new Text("New Monthly Visits:"), 0, 0);
                        visitsGrid.add(newVisitsField, 1, 0);

                        Button saveVisits = new Button("Save");
                        visitsGrid.add(saveVisits, 1, 1);

                        saveVisits.setOnAction(saveEvent -> {
                            try {
                                int newVisits = Integer.parseInt(newVisitsField.getText());
                                // get current spend from rowData
                                double currentSpend = (double) rowData.get(4);

                                int vip = (newVisits > 25 || currentSpend > 1000) ? 1 : 0;

                                // update BOTH fields
                                regulars.update(regulars.visits, String.valueOf(newVisits), regulars.id, String.valueOf(id));
                                regulars.update(regulars.vip_status, String.valueOf(vip), regulars.id, String.valueOf(id));
                                output.setText("Monthly visits updated successfully.");
                                visitsStage.close();
                                editPage.close();
                                editStage.close();
                            } catch (NumberFormatException ex) {
                                output.setText("Please enter a valid number for visits.");
                            }
                        });

                        Scene visitsScene = new Scene(visitsGrid, 320, 150);
                        visitsStage.setScene(visitsScene);
                        visitsStage.show();
                    });

                    edSpend.setOnAction(ev -> {
                        Stage spendStage = new Stage();
                        spendStage.setTitle("Edit Monthly Spend");

                        GridPane spendGrid = new GridPane();
                        spendGrid.setPadding(new Insets(20));
                        spendGrid.setHgap(10);
                        spendGrid.setVgap(10);
                        spendGrid.setAlignment(Pos.CENTER);

                        TextField newSpendField = new TextField();

                        spendGrid.add(new Text("New Monthly Spend:"), 0, 0);
                        spendGrid.add(newSpendField, 1, 0);

                        Button saveSpend = new Button("Save");
                        spendGrid.add(saveSpend, 1, 1);

                        saveSpend.setOnAction(saveEvent -> {
                            try {
                                double newSpend = Double.parseDouble(newSpendField.getText());

                                // get current visits from rowData
                                int currentVisits = (int) rowData.get(3);

                                int vip = (currentVisits > 25 || newSpend > 1000) ? 1 : 0;

                                regulars.update(regulars.total_spent, String.valueOf(newSpend), regulars.id, String.valueOf(id));
                                regulars.update(regulars.vip_status, String.valueOf(vip), regulars.id, String.valueOf(id));

                                output.setText("Monthly spend updated successfully.");
                                spendStage.close();
                                editPage.close();
                                editStage.close();
                            } catch (NumberFormatException ex) {
                                output.setText("Please enter a valid number for spend.");
                            }
                        });

                        Scene spendScene = new Scene(spendGrid, 320, 150);
                        spendStage.setScene(spendScene);
                        spendStage.show();
                    });

                    Scene editPageScene = new Scene(pageGrid, 320, 250);
                    editPage.setScene(editPageScene);
                    editPage.show();
                });

                row.getChildren().addAll(info, editBtn);
                layout.getChildren().add(row);
            }
                ScrollPane scrollPane = new ScrollPane(layout);
                scrollPane.setFitToWidth(true);

                Scene editScene = new Scene(scrollPane, 800, 600);
                editStage.setScene(editScene);
                editStage.show();

            });


                //VIEW REGULARS
        Button viewregs = new Button("View all Bar Regulars");

        viewregs.setOnAction(e ->{
            ArrayList<ArrayList<Object>> data = regulars.getExecuteResult("SELECT * FROM regulars;");

            StringBuilder sb = new StringBuilder();

            for (ArrayList<Object> row : data) {
                int id = (int) row.get(0);
                String name = (String) row.get(1);
                String drink = (String) row.get(2);
                int visits = (int) row.get(3);
                double spend = (double) row.get(4);

                sb.append(id)
                        .append(" | ")
                        .append(name)
                        .append(" | ")
                        .append(drink)
                        .append(" | Visits: ")
                        .append(visits)
                        .append(" | $")
                        .append(spend)
                        .append("\n");
            }
            output.setText(sb.toString());
        });


                // VIEW ONLY VIP REGULARS
        Button viewVIP = new Button("View all VIP Bar Regulars");

        viewVIP.setOnAction(e -> {
            ArrayList<ArrayList<Object>> data =
                    regulars.getExecuteResult("SELECT * FROM regulars WHERE vip_status = 1;");

            StringBuilder sb1 = new StringBuilder();

            for (ArrayList<Object> row : data) {
                sb1.append(row.get(0)).append(" | ")
                        .append(row.get(1)).append(" | ")
                        .append(row.get(2)).append(" | Visits: ")
                        .append(row.get(3)).append(" | $")
                        .append(row.get(4)).append("\n");
            }

            output.setText(sb1.toString());
        });



        Button loadDB = new Button("Load Database");

        loadDB.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select SQLite Database");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("SQLite Database", "*.db")
            );

            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                String path = selectedFile.getAbsolutePath();
                regulars = new regulars(selectedFile.getAbsolutePath()); // recreate object if needed
                output.setText("Database loaded from: " + path);
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setPrefWidth(200);

        exitBtn.setOnAction(e -> {
            stage.close(); // closes main window
        });


        addreg.setPrefWidth(200);
        remreg.setPrefWidth(200);
        edreg.setPrefWidth(200);
        viewregs.setPrefWidth(200);
        viewVIP.setPrefWidth(200);
        loadDB.setPrefWidth(200);
        exitBtn.setPrefWidth(200);

        grid.add(scenetitle,0,0,2,1);
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(addreg,remreg,edreg,viewregs,viewVIP, loadDB, exitBtn);
        grid.add(buttonBox,0,1, 2, 1);
        GridPane.setHalignment(buttonBox, HPos.CENTER);
        grid.setGridLinesVisible(false);
        grid.add(output, 0, 2, 2, 1);

        stage.setTitle("Bar Regular Manager");
        stage.setScene(scene);
        stage.show();



    }
    public static void main(String[] args){
        launch();
    }

}
