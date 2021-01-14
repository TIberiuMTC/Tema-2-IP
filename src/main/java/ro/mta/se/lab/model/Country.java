package ro.mta.se.lab.model;

import java.util.ArrayList;

/*
 *
 *@author Mihai Tiberiu
 * */

public class Country {
    private String _mCountryName;
    private ArrayList<City> _mCities=new ArrayList<City>();

    public Country(City _iCity, String _iCountryName){
        this._mCountryName=_iCountryName;
        this._mCities.add(_iCity);
    }

    public void addCity(City _iCity){
        this._mCities.add(_iCity);
    }

    public String getCountryName(){return this._mCountryName;}
    public ArrayList<City> getCities(){return this._mCities;}
    public String getFirstCity(){return this._mCities.get(0).getCityName();}
    public String getLastCity(){return this._mCities.get(this._mCities.size()-1).getCityName();}
    public int getCityNumbers(){return this._mCities.size();}
}
