package org.Hypixel.World;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public enum HypixelMap {

    main_lobby("Main_Lobby",new File("C:\\Users\\dkim1\\Desktop\\서버\\Hypixel\\Maps\\Main Lobby"));

    private static Map<String,HypixelMap> map = new HashMap<String,HypixelMap>();
    static {
        for (HypixelMap hm : values()) {
            map.put(hm.getName().toLowerCase(), hm);
        }
    }


    private final String name;
    private final File file;

    public static HypixelMap getByName(String name) {
        return map.containsKey(name.toLowerCase()) ? map.get(name.toLowerCase()) : null;
    }

    private HypixelMap(String name,File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
}
