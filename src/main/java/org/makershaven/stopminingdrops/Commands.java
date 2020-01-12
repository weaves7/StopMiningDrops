package org.makershaven.stopminingdrops;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {

    private Plugin plugin;
    private BlockBreakListener blockBreakListener;

    Commands (Plugin plugin, BlockBreakListener blockBreakListener){
        this.plugin = plugin;
        this.blockBreakListener = blockBreakListener;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender,@NotNull Command command,@NotNull String label,@NotNull String[] args) {
        if (args.length >= 1) {

            switch (args[0].toLowerCase()) {

                case "reload":
                    plugin.reloadConfig();
                    blockBreakListener.setBlockedDrops(plugin);
                    blockBreakListener.setWorlds(plugin);
                    blockBreakListener.updateEnabled(plugin);
                    commandSender.sendMessage(ChatColor.GREEN+ "StopMiningDrops has reloaded the config.");
                    break;

                case "version":
                    commandSender.sendMessage(ChatColor.GREEN+ "StopMiningDrops v"+ plugin.getDescription().getVersion());
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender,@NotNull Command command,@NotNull String label,@NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if(args.length==1){
            suggestions.add("reload");
            suggestions.add("version");
        }

        return suggestions;
    }
}
