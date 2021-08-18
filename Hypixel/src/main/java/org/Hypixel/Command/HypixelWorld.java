package org.Hypixel.Command;

import java.util.ArrayList;

import org.Hypixel.World.HypixelMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HypixelWorld implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender,Command cmd,String label,String args[]) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                //args 0   1~~`
                //     map  metadata

                if (args.length <= 1) {
                    player.sendMessage(ChatColor.RED+"Not enough arguments");
                    return true;
                }

                ArrayList<String> list = new ArrayList<String>();
                for (int i = 2; i<args.length; i++) {
                    list.add(args[i]);
                }

                new org.Hypixel.World.HypixelWorld(list,HypixelMap.getByName(args[0]));
            }else {
                player.sendMessage(ChatColor.RED+"You need to be admin or higher to use this command!");
            }
        }else {

        }
        return true;
    }
}
