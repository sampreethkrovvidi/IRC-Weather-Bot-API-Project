/**********************************************************************************************************************
 MyBotMain Class
 Description: This is the IRC bot class that uses the pirc bot library, that allows for the bot to respond to the user
              that is asking it questions.
 Created by Sampreeth Krovvidi
 Professor: Dr.Khan
 Class: 2336.502
 *********************************************************************************************************************/
public class MyBotMain {

    //The server that we are connecting to
    static final String server = "irc.freenode.net";

    //The IRC channel that we are joining
    static final String ircChannel = "#cs2336";

    public static void main(String[] args) throws Exception {

        //The weather bot is instantiated
        MyBot bot = new MyBot();

        bot.setVerbose(true);

        try {
            //Connection to the server is attempted
            bot.connect(server);
        }
        catch (Exception e) {
            //If the connection couldn't happen, error message is shown
            System.out.println("Connection has failed to join the server : " + server);
            return;
        }
            //The bot joins the channel
            bot.joinChannel(ircChannel);
            bot.sendMessage(ircChannel, "Hey! Enter any message and I'll respond! (Created by Sam Krovvidi)");
            bot.sendMessage(ircChannel," The following things are things I can do: ");
            bot.sendMessage(ircChannel, "Greet you: Just say hello, hey or hi!");
            bot.sendMessage(ircChannel, "Give the time: Just ask for the time!");
            bot.sendMessage(ircChannel, "Give Weather data: ex. weather richardson or weather 75035");
            bot.sendMessage(ircChannel, " \"What is the weather at 75080?\"");
            bot.sendMessage(ircChannel, "Give Covid data: ex. covid (abbreviation): covid TX or covid ca");
    }
}
