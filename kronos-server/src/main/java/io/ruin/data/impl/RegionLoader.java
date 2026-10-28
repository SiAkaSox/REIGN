package io.ruin.data.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ruin.utility.JsonUtils;
import io.ruin.model.map.MultiZone;
import io.ruin.model.map.Region;
import io.ruin.model.map.dynamic.DynamicMap;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RegionLoader {

    public static final int[] NULL_KEYS = new int[4];

    public static void init() {
        List<XTEA> xteas = null;
        try {
            xteas = readJsonFile("./data/region_keys.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Integer, int[]> keys = new HashMap<>(xteas.size());
        for (XTEA xtea : xteas) {
            keys.put(xtea.getMapsquare(), xtea.getKey());
        }
        for (int regionId = 0; regionId < Region.LOADED.length; regionId++) {
            Region region = new Region(regionId);
            if ((region.keys = keys.get(regionId)) != null && !isValid(region.id, region.keys)) {
                System.err.println("Invalid Keys for Region (" + regionId + "): base=(" + region.baseX + ", " + region.baseY + ") keys=" + Arrays.toString(region.keys));
                region.keys = null;
            }
            Region.LOADED[regionId] = region;
        }
        //Load each region
        for (Region region : Region.LOADED) {
            try {
                region.init();
            } catch (Exception ex) {
                System.err.println("Error loading region " + region.id);
                ex.printStackTrace();
            }
        }
        MultiZone.load();
        DynamicMap.load();
    }

    private static List<XTEA> readJsonFile(String filename) throws IOException {
        Gson gson = new GsonBuilder().create();
        FileReader reader = new FileReader(filename);
        Type listType = new TypeToken<List<XTEA>>() {
        }.getType();
        List<XTEA> xteas = gson.fromJson(reader, listType);
        reader.close();
        return xteas;
    }

    private static boolean isValid(int id, int[] keys) {
        Region r = new Region(id);
        r.keys = keys;
        try {
            r.getLandscapeData();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

}