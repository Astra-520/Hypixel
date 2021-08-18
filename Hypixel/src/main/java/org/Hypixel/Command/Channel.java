package org.Hypixel.Command;

import org.Hypixel.Party.PartyPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Channel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            org.Hypixel.Player.Channel ch = org.Hypixel.Player.Channel.getChannel(player.getUniqueId());
            PartyPlayer pp = PartyPlayer.getPartyPlayer(player);

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED+"Invalid usage! Correct usage: /chat channel");
                player.sendMessage(ChatColor.RED+"Valid channels: all, party, guild, officer, skyblock-coop");
                return true;
            }

            if (args[0].equalsIgnoreCase("a")||args[0].equalsIgnoreCase("all")) {

                if (ch.getA().equalsIgnoreCase("all")) {
                    player.sendMessage(ChatColor.RED+"You're already in this channel!");
                    return true;
                }

                player.sendMessage(ChatColor.GREEN+"You are now in the "+ChatColor.GOLD+" ALL "+ChatColor.GREEN+"channel");
                ch.setA("all");
            }else if (args[0].equalsIgnoreCase("p")||args[0].equalsIgnoreCase("party")) {

                if (ch.getA().equalsIgnoreCase("party")) {
                    player.sendMessage(ChatColor.RED+"You're already in this channel!");
                    return true;
                }else if (!pp.inParty()) {
                    line(player);
                    player.sendMessage(ChatColor.RED+"You must be in a party to join the party channel!");
                    line(player);
                    return true;
                }

                player.sendMessage(ChatColor.GREEN+"You are now in the "+ChatColor.GOLD+" PARTY "+ChatColor.GREEN+"channel");
                ch.setA("party");
            }else {
                player.sendMessage(ChatColor.RED + "Invalid Channel! Valid channels: all, party, guild, officer, skyblock-coop");
            }
        }else{
            sender.sendMessage(ChatColor.RED+"Only players can use this command");
        }

        return true;
    }

    private void line(Player player) {
        player.sendMessage(ChatColor.BLUE+"---------------------------------");
    }
}
