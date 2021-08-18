package org.Hypixel.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener{

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player1 = (Player) event.getEntity();								//데미지 받는 플레이어
            Player player2 = (Player) event.getDamager();								//데미지 주는 플레이어

            if (player1.getWorld().hasMetadata("lobby")) {
                event.setCancelled(true);
            }else if (player1.getWorld().hasMetadata("game")) {

            }
        }
    }
}
