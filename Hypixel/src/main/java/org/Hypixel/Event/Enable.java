package org.Hypixel.Event;

import org.Hypixel.Main.Main;
import org.bukkit.Bukkit;

public class Enable {

    private static Main plugin = Main.getPlugin(Main.class);

    public static void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(),plugin);
    }
}
