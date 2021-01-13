package ro.mta.se.lab.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;

import java.net.URL;
import java.net.URLConnection;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class WeatherControler {
    @FXML
    private ChoiceBox countryDrop;
    @FXML
    private ChoiceBox cityDrop;
    @FXML
    private Button actionButton;

    private ArrayList<String> fullCities=new ArrayList<String>();
    private ArrayList<String> fullCountries=new ArrayList<String>();

    public static Map<String, Object> jsonToMap(String str){
        Map<String, Object> map=new Gson().fromJson(str, new TypeToken<HashMap<String,Object>>() {}.getType());
        return map;
    }

    private List<String> removeDuplicateElements(List<String> wordList){
        List<String>returner=new ArrayList<String>();
        int i;
        for (i=0;i<wordList.size();i++){
            if (!returner.contains(wordList.get(i))){
                returner.add(wordList.get(i));
            }
        }
        return returner;
    }

    public void actionButtonOnAction(ActionEvent event){
        String API_KEY = "473e0ed23729748c0335b9ef3d41e5e8";
        String LOCATION = cityDrop.getSelectionModel().getSelectedItem().toString();
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q="+LOCATION+"&appid="+API_KEY;
        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader (conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null){
                result.append(line);
            }

            rd.close();
            System.out.println(result);

            Map<String, Object > respMap = jsonToMap (result.toString());
            Map<String, Object > mainMap = jsonToMap (respMap.get("main").toString());
            Map<String, Object > windMap = jsonToMap (respMap.get("wind").toString());

            System.out.println("Current Temperature: " + mainMap.get("temp")  );
            System.out.println("Current Humidity: " + mainMap.get("humidity")  );
            System.out.println("Wind Speed: " + windMap.get("speed")  );
            System.out.println("Wind Angle: " + windMap.get("deg")  );


        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void cityDropOnAction(ActionEvent event) {
        String target=countryDrop.getSelectionModel().getSelectedItem().toString();
        List<String>prompter=new ArrayList<String>();
        for (int i=0;i<fullCountries.size();i++){
            if (fullCountries.get(i).equals(target)){
                prompter.add(fullCities.get(i));
            }
        }
        List<String> beginList=new ArrayList<String>();
        beginList=this.removeDuplicateElements(prompter);

        ObservableList cityList = FXCollections.observableList(beginList);
        cityDrop.getItems().clear();
        cityDrop.setItems(cityList);
    }

    public void initialize() {
        try {
            ArrayList<String> city=new ArrayList<String>();
            File FileReader = new File("input.txt");
            Scanner myReader = new Scanner(FileReader);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                StringTokenizer defaultTokenizer = new StringTokenizer(data);
                ArrayList<String> tok = new ArrayList<String>();
                while (defaultTokenizer.hasMoreTokens()) {
                    tok.add(defaultTokenizer.nextToken());
                }
                city.add(tok.get(1));
                city.add(tok.get(4));
            }

            for (int i=0;i<city.size();i++){
                if (i%2==0){
                    fullCities.add(city.get(i));
                }else{
                    fullCountries.add(city.get(i));
                }
            }

            List<String> beginList=new ArrayList<String>();
            beginList=this.removeDuplicateElements(fullCountries);

            ObservableList countryList = FXCollections.observableList(beginList);
            countryDrop.getItems().clear();
            countryDrop.setItems(countryList);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
