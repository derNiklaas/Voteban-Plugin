# Voteban Plugin

This is a Voteban Plugin for [Code Overflow](https://github.com/codeoverflow-org/chatoverflow)

# How to install the Plugin?
1. Clone this Repository into the public-plugins (or any other valid plugin folder)
2. Run the Plugins fetch task or run ```sbt build``` in a console
3. Load Code Overflow. You're finished!

# How to use this Plugin?
1. You have to create two Connectors. You can create Connectors using the GUI and add them via the "Manage Connectors" section.
The first connector has to be a FileConnector (```org.codeoverflow.chatoverflow.requirement.service.file.FileConnector```) and the second connector has to be a TwitchChatConnector (```org.codeoverflow.chatoverflow.requirement.service.twitch.chat.TwitchChatConnector```)
2. Add an oauth key to the TwtichChatConnector. I used [this](https://twitchapps.com/tmi/) Website to get an oauth key, but you can choose whatever method you like.

![](http://derniklaas.de/oauth_key.jpg)

3. Create a Plugin instance using the GUI via the "Create / Delete instance" section. The plugin name is ```Voteban``` and the plugin author is ```derNiklaas```.
4. Add the connectors to the instance using the "Instance requirements" and "Change requirement" section. TIPP: Use the blue pencil thing to copy most of the data to the "Change requirement" section!

![](http://derniklaas.de/requirements.jpg)

5. Run the Plugin and replace ```derNiklaas``` with the name of the Twitch channel you want to be able to !voteban people in the ```channel.txt``` 
There is also a file where you can add or remove ban messages. Note: ```$user``` will be replaced with the name of the person after the !voteban command.

6. Rerun the Plugin and enjoy your voteban experience!

# Commands
```!voteban <User>``` - Votebans a user

```!voteban <User> <Reason>``` - Votebans a user with a specific reason

# Note
This plugin will **NOT** ban or timeout any of your twitch users. This plugin is only ment to have a little bit of fun! 
