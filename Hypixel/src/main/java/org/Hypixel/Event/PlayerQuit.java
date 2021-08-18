package org.Hypixel.Event;

import java.util.UUID;

import org.Hypixel.Player.HypixelPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener{

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");

        Player player = event.getPlayer();
        HypixelPlayer hplayer = HypixelPlayer.getPlayer(player.getUniqueId());

        for (UUID uuid : hplayer.getFriend().getFriends()) {
            HypixelPlayer hp = HypixelPlayer.getPlayer(uuid);

            if (hp.isOnline()) {
                hp.sendMessage(ChatColor.GREEN+"Friend > "+hplayer.getName()+ChatColor.YELLOW+" left");
            }
        }
    }
}
