package org.Hypixel.Command;

import org.Hypixel.Friend.FriendCommand;
import org.Hypixel.Main.Main;
import org.Hypixel.Party.PartyChat;
import org.Hypixel.Party.PartyCommand;

public class Enable {

    private static Main plugin = Main.getPlugin(Main.class);

    public static void onEnable() {
        plugin.getCommand("friend").setExecutor(new FriendCommand());
        plugin.getCommand("fl").setExecutor(new FriendCommand());
        plugin.getCommand("rankcolor").setExecutor(new RankColor());
        plugin.getCommand("hypixelworld").setExecutor(new HypixelWorld());
        plugin.getCommand("message").setExecutor(new Message());
        plugin.getCommand("pl").setExecutor(new PartyCommand());
        plugin.getCommand("party").setExecutor(new PartyCommand());
        plugin.getCommand("pchat").setExecutor(new PartyChat());
        plugin.getCommand("chat").setExecutor(new Channel());
    }
}
