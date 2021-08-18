package org.Hypixel.Player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

public enum Rank {
    normal(ChatColor.GRAY+"%player_name%",ChatColor.GRAY+"",0,"normal",ChatColor.GRAY+""),
    vip(ChatColor.GREEN+"[VIP] %player_name%",ChatColor.GREEN+"",1,"vip",ChatColor.GREEN+"[VIP]"),
    vip_plus(ChatColor.GREEN+"[VIP"+ChatColor.GOLD+"+"+ChatColor.GREEN+"] %player_name%",ChatColor.GREEN+"",2,"vip+",ChatColor.GREEN+"[VIP"+ChatColor.GOLD+"+"+ChatColor.GREEN+"]"),
    mvp(ChatColor.AQUA+"[MVP] %player_name%",ChatColor.AQUA+"",3,"mvp",ChatColor.AQUA+"[MVP]"),
    mvp_plus(ChatColor.AQUA+"[MVP%+_color%"+ChatColor.AQUA+"] %player_name%",ChatColor.AQUA+"",4,"mvp+",ChatColor.AQUA+"[MVP%+_color%"+ChatColor.AQUA+"]"),
    mvp_plus_plus(ChatColor.GOLD+"[MVP%+_color%%+_color%"+ChatColor.GOLD+"] %player_name%",ChatColor.GOLD+"",5,"mvp++",ChatColor.GOLD+"[MVP%+_color%%+_color%"+ChatColor.GOLD+"]"),
    admin(ChatColor.RED+"[ADMIN] %player_name%",ChatColor.RED+"",6,"admin",ChatColor.RED+"[ADMIN]"),
    owner(ChatColor.DARK_RED+"[OWNER] %player_name%",ChatColor.DARK_RED+"",7,"owner",ChatColor.DARK_RED+"[OWNER]");

    private static final Map<String,Rank> map = new HashMap<String,Rank>();
    static {
        for (Rank rank : values()) {
            map.put(rank.d(), rank);
        }
    }

    private final String a;
    private final String b;
    private final int c;
    private final String d;
    private final String e;

    private Rank(String a,String b,int c,String d,String e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public static Rank getByString(String d) {
        return map.containsKey(d) ? map.get(d) : null;
    }

    public String a() {
        return a;
    }

    public String b() {
        return b;
    }

    public int c() {
        return c;
    }

    public String d() {
        return d;
    }

    public String e() {
        return e;
    }
}
