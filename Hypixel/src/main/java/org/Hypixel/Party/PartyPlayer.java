package org.Hypixel.Party;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PartyPlayer {

    private static Map<Player, PartyPlayer> map = new HashMap<Player, PartyPlayer>();

    private int partyId;

    public PartyPlayer(Player player) {
        partyId = -1;
        map.put(player, this);
    }

    public static PartyPlayer getPartyPlayer(Player player) {
        return map.containsKey(player) ? map.get(player) : new PartyPlayer(player);
    }

    public int getId() {
       return partyId;
    }

    public void joinParty(int id) {
        partyId = id;
    }

    public void leaveParty() {
        partyId = -1;
    }

    public boolean inParty() {
        return partyId != -1;
    }
}
