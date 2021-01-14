package ro.mta.se.lab.model;

import static junit.framework.Assert.assertEquals;

public class CityTest {
    @org.junit.jupiter.api.Test
    public void getCityName(){
        assertEquals(new City("Bucharest","683506","44.4323","26.1063").getCityName(),"Bucharest");
        assertEquals(new City("Rome","3169070","41.8947","12.4839").getCityName(),"Rome");
        assertEquals(new City("Berlin","2950159","52.5244","13.4105").getCityName(),"Berlin");
    }
    @org.junit.jupiter.api.Test
    public void getCode(){
        assertEquals(new City("Bucharest","683506","44.4323","26.1063").getCode(),"683506");
        assertEquals(new City("Rome","3169070","41.8947","12.4839").getCode(),"3169070");
        assertEquals(new City("Berlin","2950159","52.5244","13.4105").getCode(),"2950159");
    }
    @org.junit.jupiter.api.Test
    public void getLat(){
        assertEquals(new City("Bucharest","683506","44.4323","26.1063").getLat(),"44.4323");
        assertEquals(new City("Rome","3169070","41.8947","12.4839").getLat(),"41.8947");
        assertEquals(new City("Berlin","2950159","52.5244","13.4105").getLat(),"52.5244");
    }
    @org.junit.jupiter.api.Test
    public void getLong(){
        assertEquals(new City("Bucharest","683506","44.4323","26.1063").getLong(),"26.1063");
        assertEquals(new City("Rome","3169070","41.8947","12.4839").getLong(),"12.4839");
        assertEquals(new City("Berlin","2950159","52.5244","13.4105").getLong(),"13.4105");
    }
}
