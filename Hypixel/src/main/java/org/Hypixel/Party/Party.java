package org.Hypixel.Party;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Party {

    private static Map<Integer,Party> map = new HashMap<Integer,Party>();
    private static int lastId = 0;

    private int id = 0;
    private Player leader;
    private ArrayList<Player> mods = new ArrayList<Player>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private PartySetting partySetting;

    public Party(Player player) {
        leader = player;
        id = lastId;
        lastId += 1;
        players.add(player);
        partySetting = new PartySetting();
        map.put(id, this);
    }

    public static Party getParty(@Nonnull int id) {
        return map.containsKey(id) ? map.get(id) : null;
    }

    public void addPlayer(@Nonnull Player player) {
        players.add(player);
    }

    public void removePlayer(@Nonnull Player player) {
        players.remove(player);
    }

    public void remove() {
        map.remove(id);
    }

    public int getId() {
        return id;
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(@Nonnull Player leader) {
        this.leader = leader;
    }

    public boolean isLeader(@Nullable Player player) {
        return leader.equals(player);
    }

    public ArrayList<Player> getMods() {
        return mods;
    }

    public void setMods(@Nullable ArrayList<Player> mods) {
        this.mods = mods;
    }

    public void addMod(@Nonnull Player player) {
        mods.add(player);
    }

    public void removeMod(@Nonnull Player player) {
        mods.remove(player);
    }

    public boolean isMod(@Nullable Player player) {
        return mods.contains(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public PartySetting getPartySetting() {
        return partySetting;
    }

    public void setPartySetting(PartySetting partySetting) {
        this.partySetting = partySetting;
    }

    public void sendMessage(@Nonnull String msg) {
        for (Player player : players) {
            if (player.isOnline()) {
                player.sendMessage(msg);
            }
        }
    }

    public void sendPacket(@Nonnull Packet packet) {
        for (Player player : players) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
