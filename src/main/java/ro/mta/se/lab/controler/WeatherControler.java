package ro.mta.se.lab.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;

import java.net.URL;
import java.net.URLConnection;

import com.google.gson.*;
import com.google.gson.reflect.*;
import javafx.stage.Stage;

import ro.mta.se.lab.model.Country;
import ro.mta.se.lab.model.City;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
*
*@author Mihai Tiberiu
* */

public class WeatherControler {
    private ArrayList<Country> _mCountries=new ArrayList<Country>();
    @FXML
    private ChoiceBox countryDrop;
    @FXML
    private ChoiceBox cityDrop;
    @FXML
    private Button actionButton;
    @FXML
    private Label temperatureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label WindSpeedLabel;
    @FXML
    private Label WindAngleLabel;
    @FXML
    private ImageView weatherIco;
    @FXML
    private Label weatherLabel;
    @FXML
    private Label weatherDescriptionLabel;
    @FXML
    private Label clothingInfo;
    @FXML
    private Label shouldInfo;
    @FXML
    private Label suggestionInfo;

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
            Map<String, Object > respMap = jsonToMap (result.toString());
            Map<String, Object > mainMap = jsonToMap (respMap.get("main").toString());
            Map<String, Object > windMap = jsonToMap (respMap.get("wind").toString());

            int start= respMap.toString().indexOf("weather");
            int stop=0;
            for (int i=start;i<respMap.toString().length();i++){
                if (respMap.toString().charAt(i)==']'){
                    stop=i;
                    break;
                }
            }
            String tracker="";
            for (int i=start;i<stop;i++){
                tracker+=respMap.toString().charAt(i);
            }


            int mainStart= tracker.indexOf("main");
            int mainStop=0;
            mainStart+=5;
            for (int i=mainStart;i<tracker.length();i++){
                if (tracker.charAt(i)==','){
                    mainStop=i;
                    break;
                }
            }

            int descriptionStart= tracker.indexOf("description");
            descriptionStart+=12;
            int descriptionStop=0;
            for (int i=descriptionStart;i<tracker.length();i++){
                if (tracker.charAt(i)==','){
                    descriptionStop=i;
                    break;
                }
            }

            String temperatureString;
            String humidityString;
            String windSpeedString;
            String windAngleString;

            String weatherString="";
            for (int i=mainStart;i<mainStop;i++){
                weatherString+=tracker.charAt(i);
            }
            String weatherDescriptionString="";
            for (int i=descriptionStart;i<descriptionStop;i++){
                weatherDescriptionString+=tracker.charAt(i);
            }

            temperatureString="Current Temperature: "+mainMap.get("temp");
            humidityString="Current Humidity: "+mainMap.get("humidity");

            windSpeedString="Wind Speed: "+windMap.get("speed");
            windAngleString="Wind Angle: "+windMap.get("deg");

            weatherString="Main Weather: "+weatherString;
            weatherDescriptionString="Weather details : "+weatherDescriptionString;

            temperatureLabel.setText(temperatureString);
            humidityLabel.setText(humidityString);
            WindSpeedLabel.setText(windSpeedString);
            WindAngleLabel.setText(windAngleString);
            weatherLabel.setText(weatherString);
            weatherDescriptionLabel.setText(weatherDescriptionString);

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            String u = (String) stage.getUserData();

            clothingInfo.setText("Hello "+u);
            shouldInfo.setText("Don't forget to take your");
            if (weatherString.contains("Clear")){
                Image image = new Image("view/Sunny.jpg");
                weatherIco.setImage(image);
                suggestionInfo.setText("Sun glasses");
            }
            if (weatherString.contains("Clouds")){
                Image image = new Image("view/cloudy.jpg");
                weatherIco.setImage(image);
                suggestionInfo.setText("Umbrella");
            }
            if (weatherString.contains("Mist")){
                Image image = new Image("view/partialSunny.jpg");
                weatherIco.setImage(image);
                suggestionInfo.setText("Scarf");
            }
            if (weatherString.contains("Rain")){
                Image image = new Image("view/rainy.jpg");
                weatherIco.setImage(image);
                suggestionInfo.setText("Umbrella");
            }
            if (weatherString.contains("Snow")){
                Image image = new Image("view/Snowy.jpg");
                weatherIco.setImage(image);
                suggestionInfo.setText("Gloves");
            }

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);

            String path=u+"/history.txt";
            FileWriter myWriter = new FileWriter(path,true);
            myWriter.append(formattedDate+"  "+u+"  "+LOCATION+"  "+temperatureString+"  "+humidityString+"  "+windSpeedString+"  "+windAngleString+"  "+weatherString
                    +"   "+weatherDescriptionString+"\n");
            myWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void cityDropOnAction(ActionEvent event) {
        String target=countryDrop.getSelectionModel().getSelectedItem().toString();
        List<String>prompter=new ArrayList<String>();
        for (int i=0;i<this._mCountries.size();i++){
            if (this._mCountries.get(i).getCountryName().equals(target)){
                for (int j=0;j<this._mCountries.get(i).getCities().size();j++){
                    prompter.add(this._mCountries.get(i).getCities().get(j).getCityName());
                }
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
                City temp=new City(tok.get(1),tok.get(0),tok.get(2),tok.get(3));
                String countryTracker= tok.get(4);
                int reckon=0;
                if (this._mCountries.size()==0){
                    Country tempCountry=new Country(temp,countryTracker);
                    this._mCountries.add(tempCountry);
                }else {
                    for (int i = 0; i < this._mCountries.size(); i++) {
                        if (this._mCountries.get(i).getCountryName().equals(countryTracker)) {
                            this._mCountries.get(i).addCity(temp);
                            reckon++;
                            break;
                        }
                    }
                    if (reckon == 0) {
                        Country tempCountry = new Country(temp, countryTracker);
                        this._mCountries.add(tempCountry);
                    }
                    reckon = 0;
                }
            }

            ArrayList<String>beginList=new ArrayList<String>();
            for (int i=0;i<this._mCountries.size();i++){
                beginList.add(this._mCountries.get(i).getCountryName());
            }
            ObservableList countryList = FXCollections.observableList(beginList);
            countryDrop.getItems().clear();
            countryDrop.setItems(countryList);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
