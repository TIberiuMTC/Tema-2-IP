package ro.mta.se.lab.controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 *
 *@author Mihai Tiberiu
 * */

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
    @FXML
    private Label infoLabel;

    private String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerButtonOnAction(ActionEvent event){
        String username=userField.getText();
        String password=passwordField.getText();

        if (password.length()<14){
            infoLabel.setText("The password must be at least 14 characters long");
            return;
        }

        if (username.contains("/") || username.contains(".") || username.contains("\\")){
            infoLabel.setText("The username must not contain one of the following characters: ./\\");
            return;
        }

        if (username.length()<10){
            infoLabel.setText("The username must be at least 10 characters long");
            return;
        }

        try {
            FileWriter myWriter = new FileWriter("users.txt",true);

            String hashedPassword=getMd5(password);

            myWriter.append(username+ " " + hashedPassword + "\n");
            myWriter.close();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Thank you! You are now registered into our sistem!");
            alert.showAndWait();

            File theDir = new File(username);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
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
