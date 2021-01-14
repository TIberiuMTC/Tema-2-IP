package ro.mta.se.lab.model;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import static junit.framework.Assert.assertEquals;

/*
 *
 *@author Mihai Tiberiu
 * */

public class CountryTest {
    private Country instance;
    private City mock;

    @Before
    public void setup() throws Exception{
        mock=org.mockito.Mockito.mock(City.class);
        when(mock.getCityName()).thenReturn("Bucharest");
        when(mock.getCode()).thenReturn("683506");
        when(mock.getLat()).thenReturn("44.4323");
        when(mock.getLong()).thenReturn("26.1063");
        instance=new Country(mock,"RO");
    }

    @Test
    public void verify(){
        assertEquals(instance.getCountryName(),"RO");
        assertEquals(instance.getFirstCity(),"Bucharest");
        assertEquals(instance.getLastCity(),"Bucharest");
        assertEquals(instance.getCityNumbers(),1);
    }
}
