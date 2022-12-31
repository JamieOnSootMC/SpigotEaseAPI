package dev.jamieisgeek;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public abstract class CommandHandler implements CommandExecutor {
    private final CommandInfo commandInfo;
    private final Arguments argumentInfo;

    public CommandHandler() {
        this.commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(commandInfo, "CommandInfo annotation is required for CommandHandler");
        this.argumentInfo = getClass().getDeclaredAnnotation(Arguments.class);
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!commandInfo.permission().isEmpty() && !sender.hasPermission(commandInfo.permission())) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (commandInfo.requiresPlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cYou must be a player to use this command.");
                return true;
            }
            execute((Player) sender, args);
            return true;
        }

        execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (argumentInfo == null) {
            return null;
        }
        if(args.length == 0) {
            return null;
        }
        if (args.length > argumentInfo.value().length) {
            return null;
        }
        Argument argument = argumentInfo.value()[args.length - 1];
        List<String> options = new ArrayList<>();
        switch(argument.Type().toLowerCase()) {
            case "string":
            case "int":
            case "double":
                options = new ArrayList<>();
                options.add("");
                return options;
            case "option":
                options = new ArrayList<>();
                for (String option : argument.Options()) {
                    if (option.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                        options.add(option);
                    }
                }
                return options;
            case "boolean":
                options = new ArrayList<>();
                options.add("true");
                options.add("false");
                return options;
            case "material":
                options = new ArrayList<>();
                for (Material material : Material.values()) {
                    if (material.name().toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                        options.add(material.name());
                    }
                }
                return options;
            case "entity":
                options = new ArrayList<>();
                for (EntityType entityType : EntityType.values()) {
                    if (entityType.name().toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                        options.add(entityType.name());
                    }
                }
                return options;
            case "player":
            default:
                return null;
        }

    }

    public void execute(Player player, String[] args) {
    }

    public void execute(CommandSender sender, String[] args) {
    }
}
