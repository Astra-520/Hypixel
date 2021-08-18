package org.Hypixel.Party;

import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

import org.Hypixel.Main.Main;
import org.Hypixel.Player.HypixelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PartyCommand implements CommandExecutor {

    private static final Main plugin = Main.getPlugin(Main.class);

    private static Map<Party, ArrayList<Player>> inviteMap = new HashMap<Party,ArrayList<Player>>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            HypixelPlayer hplayer = HypixelPlayer.getPlayer(player.getUniqueId());
            PartyPlayer pp = PartyPlayer.getPartyPlayer(player);

            if (cmd.getName().equalsIgnoreCase("pl")) {

                if (!pp.inParty()) {
                    line(player);
                    player.sendMessage(ChatColor.RED+"You are not currently in a party.");
                    line(player);
                    return true;
                }

                Party party = Party.getParty(pp.getId());

                line(player);
                player.sendMessage(ChatColor.YELLOW+"Party Leader: "+HypixelPlayer.getPlayer(party.getLeader().getUniqueId()).getName()+" "+((party.getLeader().isOnline()) ? ChatColor.GREEN+"●" : ChatColor.RED+"●"));

                if (!party.getMods().isEmpty()) {
                    String mod = "";

                    for (Player p : party.getMods()) {
                        mod += HypixelPlayer.getPlayer(p.getUniqueId()).getName()+" "+(p.isOnline() ? ChatColor.GREEN+"●" : ChatColor.RED+"●") + " ";
                    }

                    player.sendMessage(ChatColor.YELLOW+"Party Moderators: "+mod);
                }

                ArrayList<Player> members = new ArrayList<Player>();
                for (Player p : party.getPlayers()) {
                    if (!party.isLeader(p)&&!party.isMod(p)) {
                        members.add(p);
                    }
                }

                if (!members.isEmpty()) {
                    String member = "";

                    for (Player p : members) {
                        member += HypixelPlayer.getPlayer(p.getUniqueId()).getName()+" "+(p.isOnline() ? ChatColor.GREEN+"●" : ChatColor.RED+"●") + " ";
                    }

                    player.sendMessage(ChatColor.YELLOW+"Party Members: "+member);
                }

                line(player);
                return true;
            }else {

                if (args.length == 0) {
                    line(player);
                    player.sendMessage(ChatColor.GOLD+"Party Commands");
                    player.sendMessage(ChatColor.YELLOW+"/p accept <player>"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Accept a party invite from a player");
                    player.sendMessage(ChatColor.YELLOW+"/p invite (players...)"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Invite another player to your party");
                    player.sendMessage(ChatColor.YELLOW+"/p list"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Lists the players in your current party");
                    player.sendMessage(ChatColor.YELLOW+"/p leave"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Leaves your current party");
                    player.sendMessage(ChatColor.YELLOW+"/p warp"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Warps the members of a party to your current server");
                    player.sendMessage(ChatColor.YELLOW+"/p disband"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Disbands the party");
                    player.sendMessage(ChatColor.YELLOW+"/p promote <player>"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Promotes another party member to either Party Moderator or Party Leader");
                    player.sendMessage(ChatColor.YELLOW+"/p demote <player>"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Demotes another party member from Moderator to Member");
                    player.sendMessage(ChatColor.YELLOW+"/p transfer <player>"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Transfers the party to another player");
                    player.sendMessage(ChatColor.YELLOW+"/p kick <player>"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Remove a player from your party");
                    player.sendMessage(ChatColor.YELLOW+"/p kickoffline"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Remove all players that are offline in your party");
                    player.sendMessage(ChatColor.YELLOW+"/p settings <setting> <value>"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Toggle party settings");
                    player.sendMessage(ChatColor.YELLOW+"/p poll <question/answer/answer/answer...>"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Creates a poll for party members to vote on");
                    player.sendMessage(ChatColor.YELLOW+"/p chat"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Send a chat message to the entire party");
                    player.sendMessage(ChatColor.YELLOW+"/p mute"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Mutes party chat so only Staff,Party Mods and the Leader can use it");
                    player.sendMessage(ChatColor.YELLOW+"/p private"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+"Enables private games for your party");
                    line(player);

                    return true;
                }

                if (args[0].equalsIgnoreCase("accept")) {
                    if (args.length == 1) {
                        line(player);
                        player.sendMessage(ChatColor.RED+"Usage: /party accept <inviter>");
                        line(player);
                        return true;
                    }

                    for (OfflinePlayer off : Bukkit.getOfflinePlayers()) {
                        if (off.getName().equals(args[1])) {
                            PartyPlayer partyPlayer = PartyPlayer.getPartyPlayer(off.getPlayer());
                            Party party = Party.getParty(partyPlayer.getId());

                            if (party == null) {
                                line(player);
                                player.sendMessage(ChatColor.RED+"That party has been disbanded!");
                                line(player);
                                return true;
                            }

                            if (!inviteMap.containsKey(party)|| !inviteMap.get(party).contains(player)) {
                                line(player);
                                player.sendMessage(ChatColor.RED+"You don't have an invite to that player's party.");
                                line(player);
                                return true;
                            }

                            line(party);
                            party.sendMessage(hplayer.getName()+ChatColor.YELLOW+" joined the party");
                            line(party);

                            line(player);
                            player.sendMessage(ChatColor.YELLOW+"You have joined "+HypixelPlayer.getPlayer(off.getUniqueId()).getName()+ChatColor.YELLOW+"'s party");
                            pp.joinParty(party.getId());
                            party.addPlayer(player);
                            line(player);

                            ArrayList<Player> list = inviteMap.get(party);
                            list.remove(player);
                            inviteMap.put(party,list);

                            return true;
                        }
                    }

                    line(player);
                    player.sendMessage(ChatColor.RED+"That player does not exist.");
                    line(player);
                    return true;
                }

                if (args[0].equalsIgnoreCase("invite")) {
                    if (args.length == 1) {
                        line(player);
                        player.sendMessage(ChatColor.RED+"Usage: /party invite <player>");
                        line(player);
                        return true;
                    }

                    Party party = Party.getParty(pp.getId());

                    if (party == null) {
                        party = new Party(player);
                        pp.joinParty(party.getId());
                    }

                    if (!(party.isLeader(player)||party.isMod(player))&&!party.getPartySetting().isAllinvite()) {
                        line(player);
                        player.sendMessage(ChatColor.RED+"You don't have permission to use this");
                        line(player);
                        return true;
                    }

                    for (OfflinePlayer off : Bukkit.getOfflinePlayers()) {
                        if (off.getName().equals(args[1])) {
                            if (off.isOnline()) {
                                if (off.getPlayer().equals(player)) {
                                    line(player);
                                    player.sendMessage(ChatColor.RED+"You cannot party yourself!");
                                    line(player);
                                    return true;
                                }else if (party.getPlayers().contains(off.getPlayer())) {
                                    line(player);
                                    player.sendMessage(ChatColor.RED+"That player is in your party!");
                                    line(player);
                                    return true;
                                }else if (inviteMap.containsKey(party)&&inviteMap.get(party).contains(off.getPlayer())) {
                                    line(player);
                                    player.sendMessage(HypixelPlayer.getPlayer(off.getUniqueId()).getRank().b()+off.getName()+ChatColor.RED+" has already been invited to party.");
                                    line(player);
                                    return true;
                                }

                                Player target = off.getPlayer();
                                line(target);
                                if (party.isLeader(player)) {
                                    target.sendMessage(hplayer.getName()+ChatColor.YELLOW+" has invited you to join their party!");
                                }else {
                                    target.sendMessage(hplayer.getName()+ChatColor.YELLOW+" has invited you to join "+
                                            HypixelPlayer.getPlayer(party.getLeader().getUniqueId()).getName()+ChatColor.YELLOW+"'s party!");
                                }

                                String arg = "[\"\",{\"text\":\"You have \",\"color\":\"yellow\"},{\"text\":\"60 \",\"color\":\"red\"},{\"text\":\"seconds to accept. \",\"color\":\"yellow\"}," +
                                        "{\"text\":\"Click here to join!\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/party accept "+player.getName()+"\"}," +
                                        "\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click to run\n/party accept "+player.getName()+"\",\"color\":\"white\"}]}}]";
                                PlayerConnection connection = ((CraftPlayer) target).getHandle().playerConnection;
                                PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(arg));
                                connection.sendPacket(packet);
                                line(target);

                                line(party);
                                party.sendMessage(hplayer.getName()+ChatColor.YELLOW+" invited "+
                                        HypixelPlayer.getPlayer(off.getUniqueId()).getName()+ChatColor.YELLOW+" to the party! They have "+
                                        ChatColor.RED+"60 "+ChatColor.YELLOW+"seconds to accept.");
                                line(party);

                                ArrayList<Player> list;

                                if (inviteMap.containsKey(party)) {
                                    list = inviteMap.get(party);
                                }else {
                                    list = new ArrayList<Player>();
                                }

                                list.add(target);

                                inviteMap.put(party,list);

                                Party fparty = party;
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (inviteMap.get(fparty).contains(target)) {
                                            ArrayList<Player> list = inviteMap.get(fparty);
                                            list.remove(target);
                                            inviteMap.put(fparty,list);

                                            line(fparty);
                                            fparty.sendMessage(ChatColor.RED+"Party invite to "+HypixelPlayer.getPlayer(target.getUniqueId()).getName()+ChatColor.RED+" expired");
                                            line(fparty);

                                            line(target);
                                            target.sendMessage(ChatColor.RED+"Party invite from "+hplayer.getName()+ChatColor.RED+" expired");
                                            line(target);
                                        }
                                    }
                                }.runTaskLater(plugin,20*60);

                            }else {
                                line(player);
                                player.sendMessage(ChatColor.RED+"That player is currently offline");
                                line(player);
                            }
                            return true;
                        }
                    }

                    line(player);
                    player.sendMessage(ChatColor.RED+"That player does not exist.");
                    line(player);
                    return true;
                }

                if (args[0].equalsIgnoreCase("list")) {
                    if (!pp.inParty()) {
                        line(player);
                        player.sendMessage(ChatColor.RED+"You are not currently in a party.");
                        line(player);
                        return true;
                    }

                    Party party = Party.getParty(pp.getId());

                    line(player);
                    player.sendMessage(ChatColor.YELLOW+"Party Leader: "+HypixelPlayer.getPlayer(party.getLeader().getUniqueId()).getName()+" "+((party.getLeader().isOnline()) ? ChatColor.GREEN+"●" : ChatColor.RED+"●"));

                    if (!party.getMods().isEmpty()) {
                        String mod = "";

                        for (Player p : party.getMods()) {
                            mod += HypixelPlayer.getPlayer(p.getUniqueId()).getName()+" "+(p.isOnline() ? ChatColor.GREEN+"●" : ChatColor.RED+"●") + " ";
                        }

                        player.sendMessage(ChatColor.YELLOW+"Party Moderators: "+mod);
                    }

                    ArrayList<Player> members = new ArrayList<Player>();
                    for (Player p : party.getPlayers()) {
                        if (!party.isLeader(p)&&!party.isMod(p)) {
                            members.add(p);
                        }
                    }

                    if (!members.isEmpty()) {
                        String member = "";

                        for (Player p : members) {
                            member += HypixelPlayer.getPlayer(p.getUniqueId()).getName()+" "+(p.isOnline() ? ChatColor.GREEN+"●" : ChatColor.RED+"●") + " ";
                        }

                        player.sendMessage(ChatColor.YELLOW+"Party Members: "+member);
                    }

                    line(player);
                    return true;
                }

                if (args[0].equalsIgnoreCase("leave")) {
                    if (!pp.inParty()) {
                        line(player);
                        player.sendMessage(ChatColor.RED+"You are not currently in a party.");
                        line(player);
                        return true;
                    }

                    Party party = Party.getParty(pp.getId());

                    if (party.getPlayers().size()<=2) {
                        party.removePlayer(player);
                        pp.leaveParty();

                        line(player);
                        player.sendMessage(ChatColor.YELLOW+"You left the party");
                        line(player);

                        line(party);
                        party.sendMessage(hplayer.getName()+ChatColor.YELLOW+" left the party");
                        line(party);

                        line(player);
                        player.sendMessage(ChatColor.RED+"The party was disbanded because all invites expired and the party was empty");
                        line(player);

                        line(party);
                        party.sendMessage(ChatColor.RED+"The party was disbanded because all invites expired and the party was empty");
                        line(party);

                        for (Player p : party.getPlayers()) {
                            PartyPlayer.getPartyPlayer(p).leaveParty();
                        }

                        party.remove();
                    }else {
                        if (party.isLeader(player)) {
                            party.removePlayer(player);
                            pp.leaveParty();

                            line(player);
                            player.sendMessage(ChatColor.YELLOW+"");
                            line(player);

                            line(party);
                            party.setLeader(party.getPlayers().get(0));
                            party.sendMessage(hplayer.getName()+ChatColor.YELLOW+" left the party and "+
                                    HypixelPlayer.getPlayer(party.getLeader().getUniqueId()).getName()+ChatColor.YELLOW+" became leader");
                            line(party);

                        }else {
                            party.removePlayer(player);
                            pp.leaveParty();

                            line(player);
                            player.sendMessage(ChatColor.YELLOW+"You left the party");
                            line(player);

                            line(party);
                            party.sendMessage(hplayer.getName()+ChatColor.YELLOW+" left the party");
                            line(party);
                        }
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("warp")) {
                    if (!pp.inParty()) {
                        player.sendMessage(ChatColor.RED+"You are not currently in a party.");
                        return true;
                    }

                    Party party = Party.getParty(pp.getId());

                    if (party == null) {
                        player.sendMessage(ChatColor.RED+"You are not currently in a party.");
                        return true;
                    }

                    if (party.isLeader(player)) {
                        line(player);
                        player.sendMessage(ChatColor.ITALIC+"업데이트 예정");
                        line(player);
                    }else {
                        line(player);
                        player.sendMessage(ChatColor.RED+"You don't have permission to use this");
                        line(player);
                    }
                }

                if (args[0].equalsIgnoreCase("disband")) {
                    if (!pp.inParty()) {
                        player.sendMessage(ChatColor.RED+"You are not currently in a party.");
                        return true;
                    }

                    Party party = Party.getParty(pp.getId());

                    if (party == null) {
                        player.sendMessage(ChatColor.RED+"You are not currently in a party.");
                        return true;
                    }

                    if (party.isLeader(player)) {
                        line(party);
                        player.sendMessage(hplayer.getName()+ChatColor.RED+" has disbanded the party");
                        line(party);

                        for (Player p : party.getPlayers()) {
                            PartyPlayer.getPartyPlayer(p).leaveParty();
                        }
                        party.remove();
                    }else {
                        line(player);
                        player.sendMessage(ChatColor.RED+"You don't have permission to use this");
                        line(player);
                    }
                }

                if (args[0].equalsIgnoreCase("promote")) {

                }
            }
        }

        return true;
    }

    private void line(Player player) {
        player.sendMessage(ChatColor.BLUE+"---------------------------------");
    }

    private void line(Party party) {
        party.sendMessage(ChatColor.BLUE+"---------------------------------");
    }
}
