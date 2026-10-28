package io.ruin.utility;

import java.util.Map;

public class XenPost {


    private static final String URL = "http://ospk.net/world/data/index.php";

    private static final String AUTH = "wrWn4Vsf6bQP2WR3f7KnLs9erLPpdJTw";

    public static String post(String file, Map<Object, Object> map) {
        map.put("auth", AUTH);
        map.put("file", file);
        return PostWorker.postArray(URL, map);
    }

}
