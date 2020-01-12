package org.makershaven.stopminingdrops;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class StopMiningDrops extends JavaPlugin {

    private BlockBreakListener blockBreakListener;


    @Override
    public void onEnable(){
        //TODO make this configurable
        Metrics metrics = new Metrics(this);

        saveDefaultConfig();
        blockBreakListener = new BlockBreakListener(this);
        getServer().getPluginManager().registerEvents(blockBreakListener, this);
        getCommand("StopMiningDrops").setExecutor(new Commands(this,blockBreakListener));


    }

    public BlockBreakListener getBlockBreakListener(){
        return this.blockBreakListener;
    }


}

