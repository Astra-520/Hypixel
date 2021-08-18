package org.Hypixel.Command;

import org.Hypixel.Player.HypixelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RankColor implements CommandExecutor{


    public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            HypixelPlayer hplayer = HypixelPlayer.getPlayer(player.getUniqueId());
            org.Hypixel.Player.RankColor rankColor = hplayer.getRankColor();
            int level = hplayer.getLevel();

            if (hplayer.getRank().c()>=4) {
                Inventory inv = Bukkit.createInventory(null, 45, "Rank Color");

                ItemStack red = new ItemStack(Material.INK_SACK,1,(short) 1);
                ItemMeta red_meta = red.getItemMeta();
                red_meta.setDisplayName(ChatColor.GREEN+"Red Rank Color");
                if (rankColor.equals(org.Hypixel.Player.RankColor.red)) {
                    red_meta.setLore(Arrays.asList(ChatColor.GRAY+"The default color for "+ChatColor.AQUA+"MVP"+ChatColor.RED+"+"+ChatColor.GRAY+".","",
                            ChatColor.GREEN+"Currently selected!"));
                }else {
                    red_meta.setLore(Arrays.asList(ChatColor.GRAY+"The default color for "+ChatColor.AQUA+"MVP"+ChatColor.RED+"+"+ChatColor.GRAY+".","",
                            ChatColor.YELLOW+"Click to select!"));
                }
                red.setItemMeta(red_meta);
                inv.setItem(10,red);

                ItemStack gold = new ItemStack(Material.INK_SACK,1,(short) 14);
                ItemMeta gold_meta = gold.getItemMeta();
                gold_meta.setDisplayName(ChatColor.GREEN+"Gold Rank Color");

                if (rankColor.equals(org.Hypixel.Player.RankColor.gold)) {

                }

                ItemStack close = new ItemStack(Material.BARRIER,1);
                ItemMeta close_meta = close.getItemMeta();
                close_meta.setDisplayName(ChatColor.RED+"Close");
                close.setItemMeta(close_meta);
                inv.setItem(40, close);

                player.openInventory(inv);
            }else {
                player.sendMessage(ChatColor.GREEN+"You need to be "+ChatColor.AQUA+"MVP"+ChatColor.RED+"+ "+ChatColor.GREEN+"or higher to use this");
            }
        }else {
            sender.sendMessage(ChatColor.RED+"You need to be player to use this");
        }

        return true;
    }
}
