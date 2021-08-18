package org.Hypixel.Setting;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.Hypixel.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Setting {
    private static Map<UUID,Setting> map = new HashMap<UUID,Setting>();
    private static Main plugin = Main.getPlugin(Main.class);
    private static File file = new File(plugin.getDataFolder()+"/Hypixel.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private ChatSetting chat;

    public Setting(UUID uuid) {
        if (!config.contains("Hypixel.PlayerData."+uuid.toString()+".Setting")) {
            chat = new ChatSetting();
            config.set("Hypixel.PlayerData."+uuid.toString()+".Setting.Chat", chat.toString());
            saveCustomYml(config,file);
        }

        chat = ChatSetting.getFromString((String) config.get("Hypixel.PlayerData."+uuid.toString()+".Setting.Chat"));

        map.put(uuid, this);
    }

    public static Setting getSetting(UUID uuid) {
        return map.containsKey(uuid) ? map.get(uuid) : new Setting(uuid);
    }

    public ChatSetting getChat() {
        return chat;
    }

    public void setChat(ChatSetting chat) {
        this.chat = chat;
    }

    private void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
