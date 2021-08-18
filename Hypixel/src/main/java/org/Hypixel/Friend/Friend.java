package org.Hypixel.Friend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.Hypixel.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings("unchecked")
public class Friend {
    private static Map<UUID,Friend> map = new HashMap<UUID,Friend>();
    private static Main plugin = Main.getPlugin(Main.class);
    private static File file = new File(plugin.getDataFolder()+"/Hypixel.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private UUID uuid;
    private ArrayList<UUID> friends = new ArrayList<UUID>();

    public Friend(UUID uuid) {
        this.uuid = uuid;

        if (!config.contains("Hypixel.PlayerData."+uuid.toString()+".Friend")) {
            config.set("Hypixel.PlayerData."+uuid.toString()+".Friend", new ArrayList<String>());
            saveCustomYml(config,file);
        }

        ArrayList<String> a = (ArrayList<String>) config.get("Hypixel.PlayerData."+uuid.toString()+".Friend");
        for (String str : a) {
            friends.add(UUID.fromString(str));
        }

        map.put(uuid, this);
    }

    public static Friend getFriend(UUID uuid) {
        return map.containsKey(uuid) ? map.get(uuid) : new Friend(uuid);
    }

    public void addFriend(UUID uuid) {
        friends.add(uuid);
    }

    public void removeFriend(UUID uuid) {
        if (friends.contains(uuid))  {
            friends.remove(uuid);
        }
    }

    public ArrayList<UUID> getFriends() {
        return friends;
    }

    private void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
