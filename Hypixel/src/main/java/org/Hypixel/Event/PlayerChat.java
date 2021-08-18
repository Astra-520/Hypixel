package org.Hypixel.Event;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.Hypixel.Party.Party;
import org.Hypixel.Party.PartyPlayer;
import org.Hypixel.Player.Channel;
import org.Hypixel.Player.HypixelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class PlayerChat implements Listener{

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        HypixelPlayer hplayer = HypixelPlayer.getPlayer(player.getUniqueId());

        String msg = event.getMessage();

        if (Channel.getChannel(player.getUniqueId()).getA().equalsIgnoreCase("party")) {
            PartyPlayer pp = PartyPlayer.getPartyPlayer(player);

            if (!pp.inParty()) {
                line(player);
                player.sendMessage(ChatColor.RED+"You are not in a party and were moved to the ALL channel.");
                line(player);
                Channel.getChannel(player.getUniqueId()).setA("all");
                return;
            }

            Party party = Party.getParty(pp.getId());

            if (party == null) {
                line(player);
                player.sendMessage(ChatColor.RED+"You are not in a party and were moved to the ALL channel.");
                line(player);
                Channel.getChannel(player.getUniqueId()).setA("all");
                return;
            }

            String arg = "[\"\",{\"text\":\"Party\",\"color\":\"blue\",\"clickEvent\":" +
                    "{\"action\":\"run_command\",\"value\":\"/viewprofile uuid\"}," +
                    "\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click here to view \",\"color\":\"yellow\"},\"player\",{\"text\":\"'s profile\",\"color\":\"yellow\"}]}},{\"text\":\" > \",\"color\":\"dark_gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/viewprofile uuid\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click here to view \",\"color\":\"yellow\"},\"player\",{\"text\":\"'s profile\",\"color\":\"yellow\"}]}},{\"text\":\"player_name\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/viewprofile uuid\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click here to view \",\"color\":\"yellow\"},\"player\",{\"text\":\"'s profile\",\"color\":\"yellow\"}]}},{\"text\":\": \",\"color\":\"white\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/viewprofile uuid\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click here to view \",\"color\":\"yellow\"},\"player\",{\"text\":\"'s profile\",\"color\":\"yellow\"}]}},{\"text\":\"msg\",\"color\":\"white\"}]";

            arg = arg.replaceAll("uuid",player.getUniqueId().toString());
            arg = arg.replaceAll("player_name",hplayer.getName());
            arg = arg.replaceAll("player",hplayer.getRank().b()+player.getName());
            arg = arg.replaceAll("msg",msg);

            party.sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(arg)));
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().equals(player.getWorld())) {
                if (hplayer.getRank().c()>=4) {
                    String a =hplayer.getRank().a();
                    a = a.replace("%player_name%", player.getName());
                    a = a.replace("%+_color%", hplayer.getRankColor().c());
                    p.sendMessage(a+ChatColor.WHITE+": "+msg);
                }else if (hplayer.getRank().c()>=1){
                    p.sendMessage(hplayer.getRank().a().replace("%player_name%", player.getName())+ChatColor.WHITE+": "+msg);
                }else {
                    p.sendMessage(hplayer.getRank().a().replace("%player_name%", player.getName())+ChatColor.GRAY+": "+msg);
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(HypixelPlayer.getPlayer(player.getUniqueId()).getName()+" "+ChatColor.WHITE+msg);
    }

    private void line(Player player) {
        player.sendMessage(ChatColor.BLUE+"---------------------------------");
    }
}
