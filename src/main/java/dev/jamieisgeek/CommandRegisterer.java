package dev.jamieisgeek;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class CommandRegisterer {
    private final JavaPlugin plugin;
    private final String packageName;
    private final String folderName;
    public CommandRegisterer(JavaPlugin plugin, String packageName, String folderName) {
        this.plugin = plugin;
        this.packageName = packageName;
        this.folderName = folderName;
    }

    public void registerEvents() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(Class<? extends CommandHandler> clazz: new Reflections(packageName + "." + folderName)
                .getSubTypesOf(CommandHandler.class)) {
            CommandHandler commandHandler = clazz.getDeclaredConstructor().newInstance();
            plugin.getCommand(commandHandler.getCommandInfo().name()).setExecutor(commandHandler);
        }
    }
}
