package org.makershaven.stopminingdrops;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class StopMiningDrops extends JavaPlugin {

    private BlockBreakListener blockBreakListener;
    private final Logger log = this.getLogger();


    @Override
    public void onEnable(){

        if (getConfig().getBoolean("enable-metrics", true)){
            int pluginId = 4135;
            Metrics metrics = new Metrics(this,pluginId);
            log.info("Thank you for enabling metrics!");
        }


        saveDefaultConfig();
        blockBreakListener = new BlockBreakListener(this);
        getServer().getPluginManager().registerEvents(blockBreakListener, this);
        getCommand("StopMiningDrops").setExecutor(new Commands(this,blockBreakListener));


    }

    public BlockBreakListener getBlockBreakListener(){
        return this.blockBreakListener;
    }

}

