package com.itemsdrop.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsDropCommand implements CommandExecutor, TabCompleter {

    private final ItemsDropPlugin plugin;
    private static final List<String> SUBCOMMANDS = Arrays.asList("start", "stop", "reload");

    public ItemsDropCommand(ItemsDropPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /itemsdrop <start|stop|reload>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                if (plugin.startTask()) {
                    int interval = plugin.getConfig().getInt("interval-seconds", 10);
                    sender.sendMessage(ChatColor.GREEN + "Distribution d'items demarree (toutes les " + interval + " secondes).");
                } else {
                    sender.sendMessage(ChatColor.RED + "La distribution est deja en cours.");
                }
                return true;

            case "stop":
                if (plugin.stopTask()) {
                    sender.sendMessage(ChatColor.GREEN + "Distribution d'items arretee.");
                } else {
                    sender.sendMessage(ChatColor.RED + "La distribution n'est pas en cours.");
                }
                return true;

            case "reload":
                boolean wasRunning = plugin.isRunning();
                plugin.stopTask();
                plugin.reloadConfig();
                if (wasRunning) {
                    plugin.startTask();
                }
                sender.sendMessage(ChatColor.GREEN + "Configuration rechargee.");
                return true;

            default:
                sender.sendMessage(ChatColor.YELLOW + "Usage: /itemsdrop <start|stop|reload>");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> matches = new ArrayList<>();
            for (String sub : SUBCOMMANDS) {
                if (sub.startsWith(args[0].toLowerCase())) {
                    matches.add(sub);
                }
            }
            return matches;
        }
        return new ArrayList<>();
    }
}
