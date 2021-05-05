/**********************************************************************************************************************
WeatherApi Class
 Description: This class utilizes the Open Weather Map API to retrieve and parse the current weather statistics with
              given zip code or city name. (https://openweathermap.org/api)
 Created by Sampreeth Krovvidi
 Professor: Dr.Khan
 Class: 2336.502
 *********************************************************************************************************************/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherApi {

    //This my generated api key from Open Weather Map
    static final String apiKey = "1e9f9828ef91b901a8a52d4410f25786";

    public static String getWeather(String location) {

        //Declaring the endpoint for
        String url;

        if (isNumeric(location)) { //If the location is a zip code
            url = "http://api.openweathermap.org/data/2.5/weather?zip=" + location + ",us&units=imperial&appid=" + apiKey;
        } else { //If the location is a city name
            url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&units=imperial&appid=" + apiKey;
        }

        URL urlObj;
        HttpURLConnection con;
        try{
            //Making a GET Request
            urlObj = new URL(url);
            con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");

            //Reading the response by parsing the InputStream of the HttpUrlConnection
            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            //Returns the weather data
            return parsingJson(result.toString(), location);

            } catch (Exception e) {
                //If the GET Request Fails
                System.out.print("Fetching for the weather data failed!");
        }

        //Returns null if there is a failure
        return null;
    }

    //The following method checks to see if a string is numeric
    public static boolean isNumeric (String str) {
        try {
            //Returns true if it is a numeric
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            //Returns false if it is not a numeric
            return false;
        }
    }

    //The following method parses the JSON data given by the Open Weather API
    public static String parsingJson (String json, String location) {

        //Creating an object and Parsing the JSON string
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();

        //Getting the current temperature from the object
        String temperature = obj.getAsJsonObject("main").get("temp").getAsString();

        //Getting the current minimum temperature from the object
        String minTemperature = obj.getAsJsonObject("main").get("temp_min").getAsString();

        //Getting the current max temperature from the object
        String maxTemperature = obj.getAsJsonObject("main").get("temp_max").getAsString();

        //Getting the current pressure from the object
        String pressure = obj.getAsJsonObject("main").get("pressure").getAsString();

        //Getting the current humidity from the object
        String humidity = obj.getAsJsonObject("main").get("humidity").getAsString();

        //Getting the current wind speed from the object
        String windSpeed = obj.getAsJsonObject("wind").get("speed").getAsString();

        //The following concatenates the retrieved data into a string
        String weatherData = "The current weather at " + location + " is " + temperature + " F. "
                              + "The min Temperature is: " + minTemperature + " F. "
                              + "The max Temperature is: " + maxTemperature + " F. "
                              + "The Pressure is: " + pressure + " hPa. "
                              + "The Humidity is: " + humidity + " %. "
                              + "The Wind Speed is: " + windSpeed + " miles/hour.";

        //Returns to the getWeather method
        return weatherData;
    }

}
