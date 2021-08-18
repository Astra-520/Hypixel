package org.Hypixel.Command;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.Hypixel.Player.HypixelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Message implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender,Command cmd,String label,String args[]) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            HypixelPlayer hplayer = HypixelPlayer.getPlayer(player.getUniqueId());

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED+"Invalid Usage! Use: /msg (player) (message)");
                return true;
            }

            for (OfflinePlayer off : Bukkit.getOfflinePlayers()) {
                if (off.getName().equals(args[0])) {
                    if (off.isOnline()) {

                        if (off.getPlayer().equals(player)) {

                        }

                        if (args.length == 1) {
                            player.sendMessage(ChatColor.GREEN+"Sending message to asd");
                        }else {
                            String msg="";
                            for (int i=1;i<args.length;i++) {
                                msg+=args[i]+" ";
                            }

                            String arg1 = "[\"\",{\"text\":\"To \",\"color\":\"light_purple\"}," +
                                    "{\"text\":\""+HypixelPlayer.getPlayer(off.getUniqueId()).getName()+"\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg "+off.getPlayer().getName()+" \"}}," +
                                    "{\"text\":\": "+msg+"\",\"color\":\"gray\"}]";

                            String arg2 = "[\"\",{\"text\":\"From \",\"color\":\"light_purple\"}," +
                                    "{\"text\":\""+HypixelPlayer.getPlayer(player.getUniqueId()).getName()+"\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/msg "+player.getName()+" \"}}," +
                                    "{\"text\":\": "+msg+"\",\"color\":\"gray\"}]";

                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(arg1)));
                            ((CraftPlayer) off.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(arg2)));
                        }
                    }else {
                        player.sendMessage(ChatColor.RED+"That player is currently offline");
                    }
                    return true;
                }
            }

            player.sendMessage(ChatColor.RED+"Can't find a player by the name of '"+args[0]+"'");
        }

        return true;
    }
}
