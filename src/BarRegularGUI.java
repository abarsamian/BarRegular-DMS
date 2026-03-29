import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class BarRegularGUI extends Application {

    private BarManager manager = new BarManager();
    private TextArea output = new TextArea();

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

                    boolean added = manager.addRegular(id, name, drink, visits, spend);

                    if (added) {
                        output.setText("Bar Regular added successfully.");
                        addStage.close();
                    } else {
                        output.setText("Customer ID already exists.");
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

            GridPane addGrid = new GridPane();
            addGrid.setPadding(new Insets(20));
            addGrid.setHgap(10);
            addGrid.setVgap(10);
            addGrid.setAlignment(Pos.CENTER);

            TextField idField = new TextField();

            addGrid.add(new Text("Enter ID to remove: "), 0, 0);
            addGrid.add(idField, 1, 0);

            Button remove = new Button("Remove");
            addGrid.add(remove,0,2);

            remove.setOnAction(event -> {
                try {
                    int id = Integer.parseInt(idField.getText());
                    boolean removed = manager.removeRegularById(id);

                    if (removed) {
                        output.setText("Bar Regular removed successfully.");
                        removeStage.close();
                    } else {
                        output.setText("Customer not found.");
                    }
                } catch (NumberFormatException ex) {
                    output.setText("Please enter a valid numeric ID.");
                }

            });

            Scene removeScene = new Scene(addGrid, 300, 200);
            removeStage.setScene(removeScene);
            removeStage.show();
        });



                    //EDIT REGULAR BUTTON AND ALL BUTTONS WITHIN

        Button edreg = new Button("Edit Regular");

        edreg.setOnAction(e -> {
            Stage editStage = new Stage();
            editStage.setTitle("Edit Regular");

            GridPane editGrid = new GridPane();
            editGrid.setPadding(new Insets(20));
            editGrid.setHgap(10);
            editGrid.setVgap(10);
            editGrid.setAlignment(Pos.CENTER);

            TextField idField = new TextField();

            editGrid.add(new Text("Enter ID to edit: "), 0, 0);
            editGrid.add(idField, 1, 0);

            Button edit = new Button("Edit");
            editGrid.add(edit, 0, 2);

            edit.setOnAction(event -> {

                int id;

                try {
                    id = Integer.parseInt(idField.getText());
                } catch (NumberFormatException ex) {
                    output.setText("Please enter a valid numeric ID.");
                    return;
                }

                BarRegular regular = manager.findRegularById(id);

                if (regular == null) {
                    output.setText("Customer ID not found.");
                    return;
                }

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
                        regular.setName(newName);
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
                        regular.setFavoriteDrink(newDrink);
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
                            regular.setVisitFrequencyMonthly(newVisits);
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
                            regular.setAverageSpendMonthly(newSpend);
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

            Scene editScene = new Scene(editGrid, 350, 180);
            editStage.setScene(editScene);
            editStage.show();

            });


                //VIEW REGULARS
        Button viewregs = new Button("View all Bar Regulars");

        viewregs.setOnAction(e ->{
            output.setText(manager.getAllRegularsText());
        });



                //VIEW VIP REGULARS
        Button viewVIP = new Button("View all VIP Bar Regulars");
        viewVIP.setOnAction(e -> {
            output.setText(manager.getVipRegularsText());
        });


        Button loadfile = new Button("Load Regulars from file");
        loadfile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Bar Regular File");

            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                boolean success = manager.loadFromFile(selectedFile.getAbsolutePath());

                if (success) {
                    output.setText("Loaded bar regulars from file successfully.");
                } else {
                    output.setText("Error reading file.");
                }
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
        loadfile.setPrefWidth(200);
        exitBtn.setPrefWidth(200);

        grid.add(scenetitle,0,0,2,1);
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(addreg,remreg,edreg,viewregs,viewVIP,loadfile, exitBtn);
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
