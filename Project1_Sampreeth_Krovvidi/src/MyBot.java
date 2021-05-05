/**********************************************************************************************************************
 MyBot Class
 Description: This is the IRC bot class that uses the pirc bot library, that allows for the bot to respond to the user
              that is asking it questions.
 Created by Sampreeth Krovvidi
 Professor: Dr.Khan
 Class: 2336.502
 *********************************************************************************************************************/
import org.jibble.pircbot.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBot extends PircBot{

    //The default location for the Open Weather Map API
    static final String defaultLocation = "75080";

    //The default state for the Covid Tracking API
    static final String defaultState = "TX";

    //Regex to find the zip code in a sentence
    static final Pattern regex = Pattern.compile("(\\d{5})");

    public MyBot() {
        this.setName("WeatherBotSam"); //The name of the bot
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {

        //Converts the message string to lower case letters
        message = message.toLowerCase();

        //If the words hello, hi or hey are mentioned
        if (message.contains("hello") || message.contains("hi") || message.contains("hey")){
            sendMessage(channel, "Hey " + sender + "!");
        }

        //If the word "time" is mentioned
        if (message.contains("time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, " The time now is " + time);
        }

        //If the word "weather" is mentioned
        if (message.contains("weather")) {
            String location = defaultLocation;

            //Splits the sentence by spaces
            String[] sentence = message.split(" ");

            if (sentence.length == 2){
                //If the first word is weather, then the second word is the location
                //Other wise the first word is the location and the second word is weather
                location = (sentence[0].equals("weather")) ? sentence[1] : sentence[0];
            } else {
                //If the sentence is longer than two words, uses regex to find zipcode
                Matcher matcher = regex.matcher(message);
                if (matcher.find()) {
                    location = matcher.group(1);
                } else {
                    sendMessage(channel, "Can not find location");
                }
            }

            //Creating the weather object
            WeatherApi weatherData = new WeatherApi();

            //Gets the Weather Data and store into a string
            String weather = weatherData.getWeather(location);

            //If the Weather API fails to return data
            if (weather == null) {
                sendMessage(channel, "Sorry, unable to get the weather for " + location + ". ");
                sendMessage(channel, "Giving the weather for the default location instead (" + defaultLocation + "). ");
                weather = weatherData.getWeather(defaultLocation);
            }

            //Sends the Open Weather Map Api Data to the user as a message
            sendMessage(channel, weather);
        }

        if (message.contains("covid")) {
            //The Default state is set to texas, in case of input error
            String state = defaultState;

            //Splits the sentence by spaces
            String[] sentence = message.split(" ");

            if (sentence.length == 2){
                //If the first word is covid, then the second word is the state
                //Other wise the first word is the state and the second word is covid
                state = (sentence[0].equals("covid")) ? sentence[1] : sentence[0];
            } else {
                    sendMessage(channel, "Please use specified input format!");
                    sendMessage(channel, "Using default state (TX), instead.");
            }

            //Creating the covidData object
            CovidApi covidData = new CovidApi();

            //Gets the Covid Statistics and store into a string
            String covidInfo = covidData.getCovidStats(state);

            //If the Covid API fails to return data
            if (covidInfo == null) {
                sendMessage(channel, "Sorry, unable to get the weather for " + state + ". ");
                sendMessage(channel, "Giving the weather for the default location instead (" + defaultState + "). ");
                covidInfo = covidData.getCovidStats(defaultState);
            }

            //Sends the Covid Tracking Api Statistics to the user as a message
            sendMessage(channel, covidInfo);
        }
    }

}
