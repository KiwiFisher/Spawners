package me.bukkit.kiwifisher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author KiwiFisher
 */
public class SpawnersCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player && command.getLabel().equalsIgnoreCase("spawners")) {

            Player player = (Player) commandSender;

            if (args[0].equalsIgnoreCase("reload") && player.hasPermission("spawners.reload")) {
                player.sendMessage(ChatColor.GREEN + "Reloaded the config.");
                Spawners.plugin.reloadConfig();
                Spawners.config = Spawners.plugin.getConfig();

                return true;
            }

        } else if (commandSender instanceof ConsoleCommandSender) {

            if (args[0].equalsIgnoreCase("reload")) {
                Spawners.logger.info("Reloaded the config.");
                Spawners.plugin.reloadConfig();
                Spawners.config = Spawners.plugin.getConfig();

                return true;
            }
        }

        return false;
    }
}
