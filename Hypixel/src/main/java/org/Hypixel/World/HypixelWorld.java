package org.Hypixel.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.Hypixel.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.metadata.FixedMetadataValue;

public class HypixelWorld {

    private static Main plugin = Main.getPlugin(Main.class);
    private static File file = new File(plugin.getDataFolder()+"/Hypixel.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    private static int lastId = (int) config.get("Hypixel.World.lastId");

    private World world;
    private File map;
    private int id;
    private Location spawn;
    private ArrayList<String> metadatas;

    public HypixelWorld(ArrayList<String> list,HypixelMap map){
        this.map = map.getFile();
        metadatas = list;
        id = lastId;

        world = Bukkit.createWorld(new WorldCreator("Map"+String.valueOf(lastId)));
        unloadWorld();
        copyWorld(this.map,world.getWorldFolder());
        Bukkit.createWorld(new WorldCreator("Map"+id));

        for (String str : list) {
            world.setMetadata(str, new FixedMetadataValue(plugin,true));
        }

        lastId += 1;
        config.set("Hypixel.World.lastId", lastId);
        saveCustomYml(config,file);
    }

    public ArrayList<String> getMetadata() {
        return metadatas;
    }

    public void addMetadata(String string) {
        metadatas.add(string);
        world.setMetadata(string, new FixedMetadataValue(plugin,true));
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void unloadWorld() {
        if(!world.equals(null)) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }

    public boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    public void copyWorld(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
