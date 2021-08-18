package org.Hypixel.Party;

public class PartySetting {

    private boolean allinvite;
    private boolean privategame;

    public PartySetting() {
        allinvite = false;
        privategame = false;
    }

    public void setPrivategame(boolean privategame) {
        this.privategame = privategame;
    }

    public boolean isPrivategame() {
        return privategame;
    }

    public void setAllinvite(boolean allinvite) {
        this.allinvite = allinvite;
    }

    public boolean isAllinvite() {
        return allinvite;
    }
}
