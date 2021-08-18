package org.Hypixel.Player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

public enum RankColor {

    none("none",0,ChatColor.RED+"+"),
    red("red",0,ChatColor.RED+"+"),
    gold("gold",35,ChatColor.GOLD+"+"),
    green("green",45,ChatColor.GREEN+"+"),
    yellow("yellow",55,ChatColor.YELLOW+"+"),
    light_purple("light_purple",65,ChatColor.LIGHT_PURPLE+"+"),
    white("white",75,ChatColor.WHITE+"+"),
    blue("blue",85,ChatColor.BLUE+"+"),
    dark_green("dark_green",95,ChatColor.DARK_GREEN+"+"),
    dark_red("dark_red",150,ChatColor.DARK_RED+"+"),
    dark_aqua("dark_aqua",150,ChatColor.DARK_AQUA+"+"),
    dark_purple("dark_purple",200,ChatColor.DARK_PURPLE+"+"),
    grey("grey",200,ChatColor.DARK_GRAY+"+"),
    black("black",250,ChatColor.BLACK+"+");

    private static Map<String,RankColor> map = new HashMap<String,RankColor>();
    static {
        for (RankColor rankcolor : values()) {
            map.put(rankcolor.a(), rankcolor);
        }
    }

    private final String a;
    private final int b;
    private final String c;

    private RankColor(String a,int b,String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String a() {
        return a;
    }

    public int b() {
        return b;
    }

    public String c() {
        return c;
    }

    public static RankColor getByString(String name) {
        return map.containsKey(name) ? map.get(name) : null;
    }
}
