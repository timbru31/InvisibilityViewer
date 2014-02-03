package com.cyprias.invisibilityviewer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Commands implements CommandExecutor {
    private InvisibilityViewer plugin;
    private String chatPrefix = "[" + ChatColor.GREEN + "InvisibilityViewer" + ChatColor.WHITE+ "] ";

    public Commands(InvisibilityViewer plugin) {
	this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	if (sender.hasPermission("iv.toggle")) {
	if (sender instanceof ConsoleCommandSender) {
	    sender.sendMessage(ChatColor.DARK_RED + "Sorry, console is not supported!");
	} else if (args.length == 0) {
	    sender.sendMessage(chatPrefix);
	    sender.sendMessage(ChatColor.GREEN + "/iv " + ChatColor.GRAY + " - Toggles your status");
	} else {
	    sender.sendMessage(chatPrefix);
	    if (!plugin.toggleOptOutOfList(sender.getName())) {
		sender.sendMessage(ChatColor.GREEN + "You will now see other players and mobs!");
	    } else {
		sender.sendMessage(ChatColor.RED + "You won't see other players and mobs!");
	    }
	}
	} else {
	    sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission for this!");
	}
	return true;
    }
}