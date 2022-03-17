package com.example.lifestyle.dashboardfragments.weather;

import junit.framework.TestCase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtilityTest extends TestCase {

    public void testBuildURLFromString() throws MalformedURLException {
        String location = "Salt&Lake&City,us";
        NetworkUtility network = new NetworkUtility();

        URL result = network.buildURLFromString(location);
        URL expected = new URL("http://api.openweathermap.org/data/2.5/weather?q=Salt&Lake&City,us&appid=46ee370156407173a24ef981fa093f72");
        assertEquals(expected, result);
    }

    public void testGetDataFromURL() throws IOException {
        URL testURL = new URL("http://api.openweathermap.org/data/2.5/weather?q=Salt&Lake&City,us&appid=46ee370156407173a24ef981fa093f72");
        String expected = "{\"coord\":{\"lon\":35.7272,\"lat\":32.0392},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":279.75,\"feels_like\":279.75,\"temp_min\":277.46,\"temp_max\":279.75,\"pressure\":1023,\"humidity\":77,\"sea_level\":1023,\"grnd_level\":925},\"visibility\":10000,\"wind\":{\"speed\":0.75,\"deg\":344,\"gust\":0.93},\"clouds\":{\"all\":0},\"dt\":1647548258,\"sys\":{\"type\":1,\"id\":7520,\"country\":\"JO\",\"sunrise\":1647488709,\"sunset\":1647531957},\"timezone\":10800,\"id\":250258,\"name\":\"Salt\",\"cod\":200}";
        NetworkUtility networkTest = new NetworkUtility();
        String result = networkTest.getDataFromURL(testURL);
        assertEquals(expected, result);
    }
}