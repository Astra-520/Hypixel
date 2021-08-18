package org.Hypixel.Setting;

import org.apache.commons.lang3.StringUtils;

public class ChatSetting {


    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;

    public ChatSetting() {
        a = true;
        b = true;
        c = true;
        d = true;
        e = true;
    }

    public ChatSetting(boolean a,boolean b,boolean c,boolean d,boolean e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public static ChatSetting getFromString(String str) {
        String a = StringUtils.substringBefore(str," ");
        StringUtils.remove(str, a+" ");
        String b = StringUtils.substringBefore(str," ");
        StringUtils.remove(str, b+" ");
        String c = StringUtils.substringBefore(str," ");
        StringUtils.remove(str, c+" ");
        String d = StringUtils.substringBefore(str," ");
        StringUtils.remove(str, d+" ");
        String e = str;
        StringUtils.substringBetween(str," ", " ");
        return new ChatSetting(Boolean.parseBoolean(a),Boolean.parseBoolean(b),Boolean.parseBoolean(c),Boolean.parseBoolean(d),Boolean.parseBoolean(e));
    }

    public String toString() {
        return String.valueOf(a)+" "+String.valueOf(b)+" "+String.valueOf(c)+" "+String.valueOf(d)+" "+String.valueOf(e);
    }

    public boolean a() {
        return a;
    }

    public boolean b() {
        return b;
    }

    public boolean c() {
        return c;
    }

    public boolean d() {
        return d;
    }

    public boolean e() {
        return e;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    public void setE(boolean e) {
        this.e = e;
    }
}
