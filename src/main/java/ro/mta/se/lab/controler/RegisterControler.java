package ro.mta.se.lab.controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.FileWriter;

public class RegisterControler {

    @FXML
    private Button cancelButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField userField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatField;

    public void registerButtonOnAction(ActionEvent event){
        String username=userField.getText();
        String password=passwordField.getText();

        try {
            FileWriter myWriter = new FileWriter("users.txt");
            myWriter.write(username+ " " + password + "\n");
            myWriter.close();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Thank you! You are now registered into our sistem!");
            alert.showAndWait();

            try {
                Stage CurrentStage= (Stage) cancelButton.getScene().getWindow();
                CurrentStage.close();

                FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
                Parent root1=(Parent) loader.load();
                Stage stage=new Stage();
                stage.setTitle("Register");
                stage.setScene(new Scene(root1));
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage= (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
