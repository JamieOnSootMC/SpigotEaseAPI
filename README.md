# The SpigotEase API

This API basically makes the things in Spigot that are either really repetative or just fucking annoying a lot easier to use and handle. Here is how to use it!


## Adding it as a dependency:
This API supports 2 different building tools ig?

### Maven:
First off you will want to add the JitPack Repository to your pom.xml file. It should look somewhat like this:
```xml 
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Then you will want to add the dependency itself. You can do this with:
```xml
<dependency>
  <groupId>com.github.SootMC</groupId>
  <artifactId>SpigotEaseAPI</artifactId>
  <version>1.0</version>
</dependency>
```

After that, simply reload your maven dependencies with ```mvn clean install -U``` and it will install the API for you to use!

### Gradle
First off, you will want to add the JitPack repository to your build.gradle
```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

Then you will want to add the dependency itself, you can do this by putting ```gradle implementation 'com.github.SootMC:SpigotEaseAPI:1.0'``` in the ```dependencies``` section of your gradle.build!


## How to use this API

### The Command Handler
The command handler is the biggest part of this API. It allows you to not register any of your commands yourself and instead let the API handle all of that. All you have to do is make sure to implement the API right. First off, you will want to make your commands folder, this is where we will be putting all of our command class files.

Next you will want to make your Command java class. Right now you should see something like this:
```java
package com.example.Commands;

public class Example {
}
```

What you now want to do if above the class decloration, type:
```java 
@CommandInfo(name="COMMAND_NAME", permission="COMMAND_PERMISSION", requiresPlayer=true/false)
```
Replacing the COMMAND_NAME, COMMAND_PERMISSION and true/false placeholders to suit your specific needs.

The name declaration is what will be registered and needs to be the same thing that is put in your plugin.yml command declaration. 

The permission declaration is the permission that will be checked for when the command is executed. This eliminates the need for you to check the permissions of the player when writing your command.

The requiresPlayer declaration is whether or not the command requires a player in order to run. When set to false, the command can be run by either the console or by a player. When set to true, the command can only be run by a player.



Once you have done this, you need to make the class extend CommandHandler rather than the normal CommandExecutor. You should now be looking at something like this:
```java
package com.example.testplugin.Commands;

import dev.jamieisgeek.CommandHandler;
import dev.jamieisgeek.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "test", permission = "testplugin.test", requiresPlayer = false)
public class TestCommand extends CommandHandler {}
```

Now, depending on what you have put in your requiresPlayer declaration you will need to do 1 of 2 things for your main method. If you have made a command that REQUIRES a player, you will need to put:
```java
@Override
public void execute(Player player, String[] args){}
```

If you have made a command that DOES NOT REQUIRE a player, you will need to put:
```java
@Override
public void execute(CommandSender ssender, String[] args){}
```

Inside of the `execute` method is where you will write the main bulk of your command, other than:
- Player checks, the API will automatically run a player check when the requiresPlayer annotation is true!
- Permission checks, the API will automatically check if the sender has the required permission and will send an error message when the player does not have the required permission that is selected in the CommandInfo annotation!

Finally, you need to go into your main class inside of your `onEnable` method you need to put:
```java
new CommandRegisterer(this, this.getClass().getPackage().getName(), "Commands").registerCommands();
```
Replacing the LAST argument with the name of the folder that you have put your commands in! This is now all you need to do, keep repeated the above steps other than the one in the main class to create as many commands as you wish. The API will automatically register all commands that are inside of your given commands folder!


### The Events
The events part of this API is very simple. Simply create a folder to store all your EventListeners in, then add
```java
new EventRegisterer(this, this.getClass().getPackage().getName(), "Listeners").registerEvents()
```
to you onEnable method, replacing the LAST argument of the constructor with the name of the folder that you created with your events in. This will now register all of your events automatically, you don't have to have 500 lines of code just registering event listeners.


### The Scoreboards
The scoreboards part of this API is fairly straight forward. First you will want a new ScoreboardInterface object, you can do this with:
```java
ScoreboardInterface interface = new ScoreboardInterface("Scoreboard Title", scoreboardLines);
```
Replacing the "Scoreboard Title" with the title of your scoreboard and the scoreboardLines object with a List of Strings for your lines in the order you want them to appear.

You can then use
```java
interface.setTitle("Title"); #This will set the title of the scorebaord, note you will then have to run the update method to get the title to update for players!
interface.setLines(lines); # This will set the lines of the scoreboard, you will also have to run the update method to get these to update for the players!
interface.update(Player); # This will add the scoreboard to the player and update it if any changes have occured since it was last added.
interface.resetScoreboard(Player); # This will remove any scoreboards from a player
```

to interact with the ScoreboardInterface.


#### And thats all! Now you will be great at using this API. Any issues don't hestitate to contact Jamie!
