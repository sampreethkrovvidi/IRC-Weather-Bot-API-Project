/**********************************************************************************************************************
 CovidApi Class
 Description: This class utilizes the Covid Tracking API to retrieve and parse the current Coronavirus Pandemic
              statistics from each state in the United States. (https://covidtracking.com/data/api).
              This API does not require a API key as it is
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

public class CovidApi {
    public static String getCovidStats (String state) {

        //The url is the API endpoint to get the Covid Statistics
        String url = "https://api.covidtracking.com/v1/states/" + state + "/current.json?state=" + state;

        URL urlObj;
        HttpURLConnection con;
        try {
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

            //Returning the covid statistics in a string
            return parsingJson(result.toString(), state);

        } catch (Exception e) {
            //If the GET Request Fails
            System.out.print("Fetching for the Covid data failed!");
        }
        //Returns null if there is a failure
        return null;
    }

    //The following method parses the JSON data given by the Covid Tracking API
    public static String parsingJson (String json, String location) {

        //Creating an object and Parsing the JSON string
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();

        //Getting the current number of positive cases from the object
        String positiveCases = obj.get("positive").getAsString();

        //If Json Returns a null, because data is not available
        if (positiveCases == null) {
            positiveCases = " (not available at this moment)";
        }

        //Getting the current number of negative cases from the object
        String negativeCases = obj.get("negative").getAsString();

        //If Json Returns a null, because data is not available
        if (negativeCases == null) {
            positiveCases = " (not available at this moment)";
        }

        //Getting the time of the last update from the object
        String lastChecked = obj.get("lastUpdateEt").getAsString();

        //If Json Returns a null, because data is not available
        if (lastChecked == null) {
            positiveCases = " (unknown at this moment)";
        }

        //Getting the current number of positive cases from the object
        String deaths = obj.get("death").getAsString();

        //If Json Returns a null, because data is not available
        if (deaths == null) {
            deaths = " (not available at this moment)";
        }

        //Getting the current number of people hospitalized from the object
        String currentlyHospitalized = obj.get("hospitalizedCurrently").getAsString();

        //If Json Returns a null, because data is not available
        if (currentlyHospitalized == null) {
            currentlyHospitalized = " (not available at this moment)";
        }

        //The following concatenates the retrieved data into a string
        String covidData = "Last check at " + lastChecked + ". "
                            + "The state " + location.toUpperCase() + " has " + positiveCases + " reported Positive cases. "
                            + "There are " + negativeCases + " reported Negative cases. "
                            + "There are " + currentlyHospitalized + " currently hospitalized. "
                            + "There have been " + deaths + " deaths. ";

        //Returns to the getCovidStats method
        return covidData;
    }
}
