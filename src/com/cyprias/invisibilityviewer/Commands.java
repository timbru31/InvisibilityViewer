package com.cyprias.invisibilityviewer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	private InvisibilityViewer plugin;
	public Commands(InvisibilityViewer plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		final String message = getFinalArg(args, 0);
		plugin.info(sender.getName() + ": /" + cmd.getName() + " " + message);
		
		if (args.length == 0) {
			plugin.sendMessage(sender, plugin.pluginName + " v" + plugin.getDescription().getVersion());

			if (plugin.hasPermission(sender, "invisibilityviewer.commands.toggle"))
				plugin.sendMessage(sender, "�a/" + commandLabel + " toggle [type] �7- Toggle toggle setting.", true, false);
			
			if (plugin.hasPermission(sender, "invisibilityviewer.commands.reload"))
				plugin.sendMessage(sender, "�a/" + commandLabel + " reload �7- Reload config", true, false);
			
			return true;
		}
		
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (!hasCommandPermission(sender, "invisibilityviewer.commands.reload")) {
				return true;
			}
			plugin.config.reloadOurConfig();
			plugin.sendMessage(sender, "Config reloaded.");
			return true;
		}else if (args[0].equalsIgnoreCase("toggle")) {
			if (!hasCommandPermission(sender, "invisibilityviewer.commands.toggle")) {
				return true;
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to execute toggle.");
				return true;
			}
			
			
			if (args.length < 2) {
				plugin.sendMessage(sender, "Available types: ");
				
				if (plugin.hasPermission(sender, "invisibilityviewer.commands.toggle.player"))
					plugin.sendMessage(sender, "�a/" + commandLabel + " toggle " + plugin.colouredHasMask(plugin.viewInvis.get(sender.getName()), plugin.maskPlayer) + "player");
				
				if (plugin.hasPermission(sender, "invisibilityviewer.commands.toggle.others"))
					plugin.sendMessage(sender, "�a/" + commandLabel + " toggle " + plugin.colouredHasMask(plugin.viewInvis.get(sender.getName()), plugin.maskOther) + "other");

				return true;
			}
			
			String givenType = args[1];
			plugin.info("givenType: " + givenType);
			
			int flags = plugin.viewInvis.get(sender.getName());
			if (givenType.equalsIgnoreCase("player")){
				if (plugin.hasMask(flags, plugin.maskPlayer)){
					flags = plugin.delMask(flags, plugin.maskPlayer);
					plugin.viewInvis.put(sender.getName(), flags);
				}else{
					flags = plugin.addMask(flags, plugin.maskPlayer);
					plugin.viewInvis.put(sender.getName(), flags);
				}
				plugin.sendMessage(sender, "View " + givenType + " = " + plugin.hasMask(flags, plugin.maskPlayer));
				return true;
				
			}else if (givenType.equalsIgnoreCase("other")){
				if (plugin.hasMask(flags, plugin.maskOther)){
					flags = plugin.delMask(flags, plugin.maskOther);
					plugin.viewInvis.put(sender.getName(), flags);
				}else{
					flags = plugin.addMask(flags, plugin.maskOther);
					plugin.viewInvis.put(sender.getName(), flags);
				}
				plugin.sendMessage(sender, "View " + givenType + " = " + plugin.hasMask(flags, plugin.maskOther));
				return true;
				
			}else{
				plugin.sendMessage(sender, "Invalid toggle type: " + givenType);
				return true;
			}
			
			
			
		}
		
		
			

			
		
		
		
		
		return false;
	}
	
	public static String getFinalArg(final String[] args, final int start) {
		final StringBuilder bldr = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			if (i != start) {
				bldr.append(" ");
			}
			bldr.append(args[i]);
		}
		return bldr.toString();
	}
	public boolean hasCommandPermission(CommandSender player, String permission) {
		if (plugin.hasPermission(player, permission)) {
			return true;
		}
		// sendMessage(player, F("stNoPermission", permission));
		plugin.sendMessage(player, ChatColor.GRAY+"You do not have permission: " + ChatColor.YELLOW + permission);
		return false;
	}

}
