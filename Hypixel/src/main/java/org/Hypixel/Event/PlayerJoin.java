package org.Hypixel.Event;

import org.Hypixel.Main.Main;
import org.Hypixel.Player.HypixelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener{

    private static final Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");

        Player player = event.getPlayer();
        HypixelPlayer hplayer = HypixelPlayer.getPlayer(player.getUniqueId());

        player.setCustomName(hplayer.getName());
        player.setPlayerListName(hplayer.getName());

        for (UUID uuid : hplayer.getFriend().getFriends()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            HypixelPlayer hp = HypixelPlayer.getPlayer(uuid);
            if (offlinePlayer.isOnline()) {
                hp.sendMessage(ChatColor.GREEN+"Friend > "+hplayer.getName()+ChatColor.YELLOW+" joined");
            }
        }
    }
}
