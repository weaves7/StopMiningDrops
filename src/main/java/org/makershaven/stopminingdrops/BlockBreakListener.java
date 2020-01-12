package org.makershaven.stopminingdrops;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BlockBreakListener implements Listener {

    private Set<Material> blockedDrops = new HashSet<>();
    private Set<World> worlds = new HashSet<>();
    private boolean enabled;

    BlockBreakListener(Plugin plugin) {
        updateEnabled(plugin);
        setBlockedDrops(plugin);
        setWorlds(plugin);

    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event){
        Material blockType = event.getBlock().getType();

        if(!enabled){
            return;
        }

        if(worlds.contains(event.getBlock().getWorld()) && blockedDrops.contains(blockType)){
            event.setDropItems(false);
            event.setExpToDrop(0);
        }
    }

    void setBlockedDrops(Plugin plugin){
        List<String> mats = plugin.getConfig().getStringList("blocks");

        this.blockedDrops.clear();
        for (String matName : mats) {
            blockedDrops.add(Material.matchMaterial(matName));
        }
    }

    void setWorlds(Plugin plugin){
        List<String> worlds = plugin.getConfig().getStringList("worlds");

        this.worlds.clear();
        for (String worldName : worlds) {
            this.worlds.add(plugin.getServer().getWorld(worldName));
        }

    }

    void updateEnabled(Plugin plugin){
        enabled = plugin.getConfig().getBoolean("enabled");
    }


}
