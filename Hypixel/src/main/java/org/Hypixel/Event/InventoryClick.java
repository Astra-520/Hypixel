package org.Hypixel.Event;

import org.Hypixel.Player.HypixelPlayer;
import org.Hypixel.Player.RankColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        HypixelPlayer hplayer = HypixelPlayer.getPlayer(player.getUniqueId());

        Inventory inv = event.getClickedInventory();

        if (inv.equals(player.getInventory())) {
            return;
        }else {

            if (inv.getSize() == 45 && inv.getName().equalsIgnoreCase("Rank Color")) {

                RankColor rankColor = hplayer.getRankColor();
                ItemStack item = event.getCurrentItem();

                if (item == null ||!item.hasItemMeta()) {
                    return;
                }

                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("Close")) {
                    player.closeInventory();
                }

                String display = item.getItemMeta().getDisplayName();

                if (display.contains("Red")) {


                    if (rankColor.equals(RankColor.getByString("red"))) {
                        player.sendMessage(ChatColor.GREEN+"Already Selected");
                    }

                    hplayer.setRankColor(RankColor.red);
                    player.setPlayerListName(hplayer.getName());

                }

                event.setCancelled(true);
                player.closeInventory();
            }

        }
    }
}
