package dev.jamieisgeek;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class EventRegister {

    private final JavaPlugin plugin;
    private final String packageName;
    private final String folderName;

    public EventRegister(JavaPlugin plugin, String packageName, String folderName) {
        this.plugin = plugin;
        this.packageName = packageName;
        this.folderName = folderName;
    }

    public void registerEvents() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(Class<?> clazz : new Reflections(packageName + "." + folderName)
                .getSubTypesOf(Listener.class)) {
            Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
