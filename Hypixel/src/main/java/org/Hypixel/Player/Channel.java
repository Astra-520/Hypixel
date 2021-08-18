package org.Hypixel.Player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Channel {

    private static Map<UUID,Channel> map = new HashMap<UUID,Channel>();

    private UUID uuid;
    private String a;
    private Player target;

    public Channel(UUID uuid) {
        this.uuid = uuid;
        a = "all";
        map.put(uuid,this);
    }

    public static Channel getChannel(UUID uuid) {
        return map.containsKey(uuid) ? map.get(uuid) : new Channel(uuid);
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

}
