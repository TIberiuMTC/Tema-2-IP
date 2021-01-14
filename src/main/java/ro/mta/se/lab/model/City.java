package ro.mta.se.lab.model;

/*
 *
 *@author Mihai Tiberiu
 * */

public class City {
    private String _mCityName;
    private String _mCode;
    private String _mLat;
    private String _mLong;

    public City(String _iCityName, String _iCode, String _iLat, String _iLong){
        this._mCityName=_iCityName;
        this._mCode=_iCode;
        this._mLat=_iLat;
        this._mLong=_iLong;
    }

    public String getCityName(){return this._mCityName;}
    public String getCode(){return this._mCode;}
    public String getLat(){return this._mLat;}
    public String getLong(){return this._mLong;}

    public void setCityName(String _iCityName){this._mCityName=_iCityName;}
    public void setCode(String _iCode){this._mCode=_iCode;}
    public void setLat(String _iLat){this._mLat=_iLat;}
    public void setLong(String _iLong){this._mLong=_iLong;}
}
