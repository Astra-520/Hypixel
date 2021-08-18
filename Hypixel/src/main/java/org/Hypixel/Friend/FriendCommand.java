package org.Hypixel.Friend;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.Hypixel.Main.Main;
import org.Hypixel.Player.HypixelPlayer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class FriendCommand implements CommandExecutor{

    private static Main plugin = Main.getPlugin(Main.class);
    private static Map<UUID,ArrayList<UUID>> map = new HashMap<UUID,ArrayList<UUID>>();

    private static File file = new File(plugin.getDataFolder()+"/Name.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    @Override
    public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("fl")) {
                int index;
                if (args.length == 0) {
                    index = 0;
                }else {
                    try {
                        index = Integer.parseInt(args[0]);
                    }catch(NumberFormatException e) {
                        line(player);
                        player.sendMessage(ChatColor.RED+"Invalid Page Number!");
                        line(player);
                        return true;
                    }
                }

                ArrayList<UUID> friends = HypixelPlayer.getPlayer(player.getUniqueId()).getFriend().getFriends();
                Collections.sort(friends);

                ArrayList<UUID> a = new ArrayList<UUID>();

                for (UUID uuid : friends) {
                    if (HypixelPlayer.getPlayer(uuid).isOnline()) {
                        a.add(uuid);
                    }
                }

                for (UUID uuid : friends) {
                    if (!HypixelPlayer.getPlayer(uuid).isOnline()) {
                        a.add(uuid);
                    }
                }

                if (index>friends.size()/8+1) {
                    index = friends.size()/8;
                }

                line(player);

                String arg;

                if (index==friends.size()/8||index==0) {
                    arg = "[\"\",{\"text\":\"              Friends (Page "+(index+1)+" of "+(friends.size()/8+1)+")\",\"bold\":false,\"color\":\"gold\"}]";
                }else if (index==0) {
                    arg = "[\"\",{\"text\":\"              Friends (Page "+(index+1)+" of "+(friends.size()/8+1)+")\",\"bold\":false,\"color\":\"gold\"},{\"text\":\">>\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/f list "+(index+2)+"\"},\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"Click to view page"+(index+2)+"\"}}]";
                }else if (index==friends.size()/8) {
                    arg = "[\"\",{\"text\":\"           <<\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/f list "+(index)+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click to view page "+(index)+"\",\"color\":\"yellow\"}]}},{\"text\":\" Friends (Page "+(index+1)+" of "+(friends.size()/8+1)+")\",\"bold\":false,\"color\":\"gold\"}]";
                }else {
                    arg = "[\"\",{\"text\":\"           <<\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/f list "+(index)+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click to view page "+(index)+"\",\"color\":\"yellow\"}]}},{\"text\":\" Friends (Page "+(index+1)+" of "+(friends.size()/8+1)+")\",\"bold\":false,\"color\":\"gold\"},{\"text\":\">>\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/f list "+(index+2)+"\"},\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"Click to view page "+(index+2)+"\"}}]";
                }
                PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(arg));
                PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                connection.sendPacket(packet);

                for (int i=index*8;i<index*8+8;i++) {
                    if (friends.size()<=i) {
                        player.sendMessage("");
                        continue;
                    }
                    UUID uuid = a.get(i);
                    HypixelPlayer hplayer = HypixelPlayer.getPlayer(uuid);
                    if (!hplayer.isOnline()) {
                        player.sendMessage(hplayer.getRank().b()+StringUtils.substringAfter(hplayer.getName(), " ")+ChatColor.RED+" is currently offline");
                    }else {
                        player.sendMessage(hplayer.getRank().b()+StringUtils.substringAfter(hplayer.getName(), " "));
                    }
                }

                line(player);
            }else {
                if (args.length == 0) {
                    line(player);
                    player.sendMessage(ChatColor.GREEN+"Friend Commands:");
                    player.sendMessage(ChatColor.YELLOW+"/f accept Player "+ChatColor.GRAY+"- "+ChatColor.AQUA+"Accept a friend request");
                    player.sendMessage(ChatColor.YELLOW+"/f add Player "+ChatColor.GRAY+"- "+ChatColor.AQUA+"Add a player as a friend");
                    player.sendMessage(ChatColor.YELLOW+"/f deny Player "+ChatColor.GRAY+"- "+ChatColor.AQUA+"Decline a friend request");
                    player.sendMessage(ChatColor.YELLOW+"/f help "+ChatColor.GRAY+"- "+ChatColor.AQUA+"Prints all available friend commands");
                    player.sendMessage(ChatColor.YELLOW+"/f list (page) "+ChatColor.GRAY+"- "+ChatColor.AQUA+"List your friends");
                    player.sendMessage(ChatColor.YELLOW+"/f notifications "+ChatColor.GRAY+"- "+ChatColor.AQUA+"Toggle friend join/leave notifications");
                    player.sendMessage(ChatColor.YELLOW+"/f remove Player "+ChatColor.GRAY+"- "+ChatColor.AQUA+"Remove a player from your friends");
                    player.sendMessage(ChatColor.YELLOW+"/f requests (page) "+ChatColor.GRAY+"- "+ChatColor.AQUA+"View friend requests");
                    player.sendMessage(ChatColor.YELLOW+"/f toggle "+ChatColor.GRAY+"- "+ChatColor.AQUA+"Toggle friend requests");
                    line(player);
                    return true;
                }


                if (args[0].equalsIgnoreCase("accept")) {

                    if (args.length == 1) {
                        return true;
                    }

                    OfflinePlayer offTarget = Bukkit.getOfflinePlayer(args[1]);
                    Player target = offTarget.getPlayer();


                }

                if (args[0].equalsIgnoreCase("add")) {

                    if (args.length == 1) {

                        return true;
                    }
                }

            }



            if (args.length == 0) {

            }else {

                if (cmd.getName().equalsIgnoreCase("fl")||cmd.getName().equalsIgnoreCase("flist")) {
                    int	index = Integer.parseInt(args[0])-1;

                    ArrayList<UUID> friends = HypixelPlayer.getPlayer(player.getUniqueId()).getFriend().getFriends();
                    Collections.sort(friends);

                    ArrayList<UUID> a = new ArrayList<UUID>();

                    for (UUID uuid : friends) {
                        if (HypixelPlayer.getPlayer(uuid).isOnline()) {
                            a.add(uuid);
                        }
                    }

                    for (UUID uuid : friends) {
                        if (!HypixelPlayer.getPlayer(uuid).isOnline()) {
                            a.add(uuid);
                        }
                    }

                    if (index>friends.size()/8+1) {
                        index = friends.size()/8;
                    }

                    line(player);

                    String arg = "[\"\",{\"text\":\"           <<\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/f list "+(index)+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click to view page "+(index)+"\",\"color\":\"gold\"}]}},{\"text\":\" Page "+(index+1)+" of "+(friends.size()/8+1)+" \",\"bold\":true,\"color\":\"gold\"},{\"text\":\">>\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"\"},\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"Click to view page\"}}]";
                    PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(arg));
                    PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                    connection.sendPacket(packet);

                    for (int i=index*8;i<index*8+8;i++) {
                        if (friends.size()<=i) {
                            player.sendMessage("");
                            continue;
                        }
                        UUID uuid = a.get(i);
                        HypixelPlayer hplayer = HypixelPlayer.getPlayer(uuid);
                        if (!hplayer.isOnline()) {
                            player.sendMessage(hplayer.getName()+ChatColor.RED+" is currently offline");
                        }else {
                            player.sendMessage(hplayer.getName());
                        }
                    }

                    line(player);

                    return true;
                }

                if (args[0].equalsIgnoreCase("list")) {
                    if (args.length>=3) {
                        line(player);
                        player.sendMessage(ChatColor.RED+"Too many arguments");
                        line(player);
                        return true;
                    }

                    int index;
                    if (args.length==1) {
                        index = 0;
                    }else {
                        index = Integer.parseInt(args[1])-1;
                    }

                    ArrayList<UUID> friends = HypixelPlayer.getPlayer(player.getUniqueId()).getFriend().getFriends();
                    Collections.sort(friends);

                    ArrayList<UUID> a = new ArrayList<UUID>();

                    for (UUID uuid : friends) {
                        if (HypixelPlayer.getPlayer(uuid).isOnline()) {
                            a.add(uuid);
                        }
                    }

                    for (UUID uuid : friends) {
                        if (!HypixelPlayer.getPlayer(uuid).isOnline()) {
                            a.add(uuid);
                        }
                    }

                    if (index>friends.size()/8+1) {
                        index = friends.size()/8;
                    }

                    line(player);

                    String arg = "[\"\",{\"text\":\"           <<\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/f list "+(index)+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[{\"text\":\"Click to view page "+(index)+"\",\"color\":\"gold\"}]}},{\"text\":\" Page "+(index+1)+" of "+(friends.size()/8+1)+" \",\"bold\":true,\"color\":\"gold\"},{\"text\":\">>\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"\"},\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"Click to view page\"}}]";
                    PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(arg));
                    PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                    connection.sendPacket(packet);

                    for (int i=index*8;i<index*8+8;i++) {
                        if (friends.size()<=i) {
                            player.sendMessage("");
                            continue;
                        }
                        UUID uuid = a.get(i);
                        HypixelPlayer hplayer = HypixelPlayer.getPlayer(uuid);
                        if (!hplayer.isOnline()) {
                            player.sendMessage(hplayer.getName()+ChatColor.RED+" is currently offline");
                        }else {
                            player.sendMessage(hplayer.getName());
                        }
                    }

                    line(player);

                }

                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length<=1) {
                        line(player);
                        player.sendMessage(ChatColor.RED+"Not enough arguments");
                        line(player);
                    }

                    Player p = Bukkit.getPlayer(args[1]);

                    if (!map.containsKey(player.getUniqueId())) {
                        map.put(player.getUniqueId(), new ArrayList<UUID>());
                    }

                    ArrayList<UUID> list = map.get(player.getUniqueId());
                    list.add(p.getUniqueId());
                    map.put(player.getUniqueId(), list);

                    line(player);
                    player.sendMessage(ChatColor.YELLOW+"You have sent friend request to "+HypixelPlayer.getPlayer(p.getUniqueId()).getName());
                    line(player);

                    line(p);
                    p.sendMessage(ChatColor.YELLOW+"You have got a friend request from "+HypixelPlayer.getPlayer(player.getUniqueId()).getName());
                    p.sendMessage(ChatColor.YELLOW+"대충 만듬 /f accept 닉넴");
                    line(p);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (map.get(player.getUniqueId()).contains(p.getUniqueId())) {
                                line(p);
                                p.sendMessage(ChatColor.YELLOW+"Friend invite from "+HypixelPlayer.getPlayer(player.getUniqueId()).getName()+ChatColor.YELLOW+" expired.");
                                line(p);
                                line(player);
                                player.sendMessage(ChatColor.YELLOW+"Friend invite to "+HypixelPlayer.getPlayer(p.getUniqueId()).getName()+ChatColor.YELLOW+" expired.");
                                line(player);
                                ArrayList<UUID> list = map.get(player.getUniqueId());
                                list.remove(p.getUniqueId());
                                map.put(player.getUniqueId(), list);
                            }
                        }
                    }.runTaskLater(plugin, 20*60*5);
                }

                if (args[0].equalsIgnoreCase("accept")) {
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED+"Not enough arguments");
                        return true;
                    }

                    Player p = Bukkit.getPlayer(args[1]);

                    if (!map.containsKey(p.getUniqueId())||!map.get(p.getUniqueId()).contains(player.getUniqueId())) {
                        line(player);
                        player.sendMessage(ChatColor.YELLOW+"You didn't get friend request from "+HypixelPlayer.getPlayer(p.getUniqueId()).getName());
                        line(player);
                        return true;
                    }

                    ArrayList<UUID> list = map.get(p.getUniqueId());
                    list.remove(player.getUniqueId());
                    map.put(p.getUniqueId(), list);

                    line(player);
                    player.sendMessage(ChatColor.YELLOW+"You are now friend with "+HypixelPlayer.getPlayer(p.getUniqueId()).getName());
                    line(player);

                    line(p);
                    p.sendMessage(ChatColor.YELLOW+"You are now friend with "+HypixelPlayer.getPlayer(player.getUniqueId()).getName());
                    line(p);

                    Friend friend = HypixelPlayer.getPlayer(p.getUniqueId()).getFriend();
                    Friend friend2 = HypixelPlayer.getPlayer(player.getUniqueId()).getFriend();

                    friend.addFriend(player.getUniqueId());
                    friend2.addFriend(p.getUniqueId());

                    HypixelPlayer.getPlayer(player.getUniqueId()).setFriend(friend2);
                    HypixelPlayer.getPlayer(p.getUniqueId()).setFriend(friend);
                }
            }
        }else {
            sender.sendMessage(ChatColor.RED+"You need to be player to use this");
        }

        return true;
    }

    private void line(Player player) {
        player.sendMessage(ChatColor.BLUE+"---------------------------------");
    }
}
