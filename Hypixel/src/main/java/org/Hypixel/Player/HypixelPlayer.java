package org.Hypixel.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.Hypixel.Friend.Friend;
import org.Hypixel.Main.Main;
import org.Hypixel.Party.PartyPlayer;
import org.Hypixel.Setting.Setting;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class HypixelPlayer {
    private static Map<UUID,HypixelPlayer> map = new HashMap<UUID,HypixelPlayer>();
    private static Main plugin = Main.getPlugin(Main.class);
    private static File file = new File(plugin.getDataFolder()+"/Hypixel.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private UUID uuid;
    private Rank rank;
    private int level;
    private RankColor rankcolor;
    private Friend friend;
    private String name;
    private Setting setting;

    public HypixelPlayer(UUID uuid) {
        this.uuid = uuid;

        if (!config.contains("Hypixel.PlayerData."+uuid.toString()+".Rank")) {
            config.set("Hypixel.PlayerData."+uuid.toString()+".Rank", "normal");
            config.set("Hypixel.PlayerData."+uuid.toString()+".Level", 1);
            config.set("Hypixel.PlayerData."+uuid.toString()+".RankColor", "none");
            saveCustomYml(config,file);
        }

        rank = Rank.getByString((String) config.get("Hypixel.PlayerData."+uuid.toString()+".Rank"));
        level = (int) config.get("Hypixel.PlayerData."+uuid.toString()+".Level");
        rankcolor = RankColor.getByString((String) config.get("Hypixel.PlayerData."+uuid.toString()+".RankColor"));
        friend = Friend.getFriend(uuid);
        setting = Setting.getSetting(uuid);
        name = Bukkit.getOfflinePlayer(uuid).getPlayer().getName();

        map.put(uuid, this);
    }

    public static HypixelPlayer getPlayer(UUID uuid) {
        return map.containsKey(uuid) ? map.get(uuid) : new HypixelPlayer(uuid);
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
        config.set("Hypixel.PlayerData."+uuid.toString()+".Rank", rank.d());
        saveCustomYml(config,file);
    }

    public UUID getUUID() {
        return uuid;
    }

    public World getWorld() {
        return Bukkit.getPlayer(uuid).getWorld();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public RankColor getRankColor() {
        return rankcolor;
    }

    public void setRankColor(RankColor rankcolor) {
        this.rankcolor = rankcolor;
        config.set("Hypixel.PlayerData."+uuid.toString()+".RankColor", rankcolor.a());
        saveCustomYml(config,file);
    }

    public void setFriend(Friend friend) {
        this.friend = friend;

        ArrayList<String> a = new ArrayList<String>();
        for (UUID uuid : friend.getFriends()) {
            a.add(uuid.toString());
        }
        config.set("Hypixel.PlayerData."+uuid.toString()+".Friend", a);
        saveCustomYml(config,file);
    }

    public Friend getFriend() {
        return friend;
    }

    public String getName() {
        String name = rank.a();
        name = name.replace("%player_name%", this.name);

        if (rank.c()>=4) {
            name = name.replace("%+_color%", rankcolor.c());
        }

        return name;
    }

    public boolean isOnline() {
        ArrayList<UUID> a = new ArrayList<UUID>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            a.add(player.getUniqueId());
        }

        return a.contains(uuid);
    }

    public void sendMessage(String msg) {
        if (!isOnline()) {
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId().equals(uuid)) {
                player.sendMessage(msg);
            }
        }
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    private void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
