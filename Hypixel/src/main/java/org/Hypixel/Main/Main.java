package org.Hypixel.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"\n\nPlugin Enabled\n\n");
        org.Hypixel.Command.Enable.onEnable();
        org.Hypixel.Event.Enable.onEnable();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"\n\nPlugin Disabled\n\n");
    }
}