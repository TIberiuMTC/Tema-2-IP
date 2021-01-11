package ro.mta.se.lab.controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import java.util.ArrayList;

public class LogInControler {

    @FXML
    private Button cancelButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField enterUsernameField;
    @FXML
    private TextField enterPasswordField;

    public static int stringCompare(String str1, String str2) {
        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);
        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
        if (l1 != l2) {
            return l1 - l2;
        }
        else {
            return 0;
        }
    }

    public void loginButtonOnAction(ActionEvent event){
        try{
            String user=enterUsernameField.getText();
            String password=enterPasswordField.getText();

            System.out.println(user);
            System.out.println(password);

            File FileReader = new File("users.txt");
            Scanner myReader = new Scanner(FileReader);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                StringTokenizer defaultTokenizer = new StringTokenizer(data);
                ArrayList<String> tok = new ArrayList<String>();
                while (defaultTokenizer.hasMoreTokens()) {
                    tok.add(defaultTokenizer.nextToken());
                }

                for (int i=0;i<tok.size();i++){
                    System.out.println(tok.get(i));
                }

                if (stringCompare(user,tok.get(0))==0 && stringCompare(password,tok.get(1))==0){
                    Stage CurrentStage= (Stage) cancelButton.getScene().getWindow();
                    CurrentStage.close();

                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/WeatherView.fxml"));
                    Parent root1=(Parent) loader.load();
                    Stage stage=new Stage();
                    stage.setTitle("Red Line Weather App");
                    stage.setScene(new Scene(root1));
                    stage.show();

                    break;
                }
            }
        }catch(Exception e){

        }
    }

    public void registerButtonOnAction(ActionEvent event){
        Parent root;
        try {
            Stage CurrentStage= (Stage) cancelButton.getScene().getWindow();
            CurrentStage.close();

            FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/Register.fxml"));
            Parent root1=(Parent) loader.load();
            Stage stage=new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage= (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
