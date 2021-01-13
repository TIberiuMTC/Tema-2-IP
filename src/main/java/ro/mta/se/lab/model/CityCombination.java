package ro.mta.se.lab.model;
import java.util.ArrayList;

public class CityCombination {
    private String _mCountry;
    private ArrayList<String> _mCity=new ArrayList<String>();

    public CityCombination(String _inCountry){
        this._mCountry=_inCountry;
        return;
    }

    public CityCombination(String _inCountry,String _inCity){
        this._mCountry=_inCountry;
        this._mCity.add(_inCity);
        return;
    }

    public void addCity(String _inCity){
        this._mCity.add(_inCity);
        return;
    }

    public String getCountry(){return this._mCountry;}
    public ArrayList<String> getCity(){return this._mCity;}
}
