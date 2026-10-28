package io.ruin.model.map.ground;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ruin.cache.ItemDef;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
public class SpawnedGroundItems {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <K, V> Map<K, List<V>> fromJson(String json, TypeReference<Map<K, List<V>>> typeRef) throws IOException {
        return objectMapper.readValue(json, typeRef);
    }

    static {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./data/items/spawns/ground_items.json")));

            TypeReference<Map<String, List<SpawnedGroundItem>>> typeRef = new TypeReference<Map<String, List<SpawnedGroundItem>>>() {
            };

            Map<String, List<SpawnedGroundItem>> spawns = fromJson(json, typeRef);

            spawns.forEach((key, item) ->
                    item.forEach(gi -> {

                        ItemDef defs = ItemDef.get(key.replaceAll("_", " "), true);
                        if (defs != null) {
                            if (defs.value > 1000) {
                                log.warn("GroundItems Spawn Name={} ({}) is valued at: {} GP position={},{},{}", key, defs.id, defs.value, gi.getX(), gi.getY(), gi.getZ());
                            }
                            //System.err.println(key + "," + gi.getQuantity() + " " + gi.getX() + "," + gi.getY() + "," + gi.getZ());
                            new GroundItem(defs.id, gi.getQuantity(), null)
                                    .position(gi.getX(), gi.getY(), gi.getZ())
                                    .spawnWithRespawn(1);
                        } else
                            log.error("Couldn't find itemdef for key= {} {},{},{},{}", key, gi.x, gi.y, gi.z, gi.quantity);
                    })
            );
            log.info("Loaded {} World item spawns.", spawns.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}