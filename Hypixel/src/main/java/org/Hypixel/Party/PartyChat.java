package org.Hypixel.Party;

import org.Hypixel.Player.HypixelPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label,String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            HypixelPlayer hplayer = HypixelPlayer.getPlayer(player.getUniqueId());
            PartyPlayer pp = PartyPlayer.getPartyPlayer(player);

            if (args.length == 0) {
                line(player);
                player.sendMessage(ChatColor.RED+"Usage: /party chat <message>");
                line(player);
                return true;
            }

            if (!pp.inParty()) {
                line(player);
                player.sendMessage(ChatColor.RED+"You are not in a party right now.");
                line(player);
                return true;
            }

            Party party = Party.getParty(pp.getId());

            String msg = "";
            for (String str : args) {
                msg += str + " ";
            }

            party.sendMessage(ChatColor.BLUE+"Party "+ChatColor.DARK_GRAY+"> "+hplayer.getName()+ChatColor.WHITE+": "+msg);
        }else {
            sender.sendMessage(ChatColor.RED+"Only players can use this command");
        }

        return true;
    }

    private void line(Player player) {
        player.sendMessage(ChatColor.BLUE+"---------------------------------");
    }
}
