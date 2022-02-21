package org.makershaven.stopminingdrops;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BlockBreakListener implements Listener {

    private final Set<Material> blockedDrops = new HashSet<>();
    private final Set<World> worlds = new HashSet<>();
    private final Set<World> containerWorlds = new HashSet<>();
    private boolean blockingMiningDrops;
    private boolean blockingContainerDrops;

    BlockBreakListener(Plugin plugin) {
        updateEnabled(plugin);
        setBlockedDrops(plugin);
        setWorlds(plugin);
        setContainerWorlds(plugin);

    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event){

        if(blockingContainerDrops && event.getBlock().getState() instanceof Container){
            if (containerWorlds.contains(event.getBlock().getWorld())) {
                Container container = (Container) event.getBlock().getState();
                container.getInventory().setContents(new ItemStack[1]);
            }
        }

        if(!blockingMiningDrops){
            return;
        }

        if(worlds.contains(event.getBlock().getWorld()) && blockedDrops.contains(event.getBlock().getType())){
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

    void setContainerWorlds(Plugin plugin){
        List<String> containerWorlds = plugin.getConfig().getStringList("block-container-drops.worlds");

        this.containerWorlds.clear();
        for (String worldName : containerWorlds) {
            this.containerWorlds.add(plugin.getServer().getWorld(worldName));
        }

    }

    void updateEnabled(Plugin plugin){
        blockingMiningDrops = plugin.getConfig().getBoolean("enabled");
    }

    void updateBlockingContainerDrops(Plugin plugin){
        blockingContainerDrops = plugin.getConfig().getBoolean("block-container-drops.enabled");
    }

}
