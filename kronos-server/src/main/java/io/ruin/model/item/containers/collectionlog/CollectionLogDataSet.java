package io.ruin.model.item.containers.collectionlog;

import io.ruin.cache.EnumMap;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;

import java.util.ArrayList;

/**
 * @author Greco
 */
public class CollectionLogDataSet {

    private final int id;

    private final int[] itemIds;

    private final String contentsMessage;

    private CollectionLogDataSet(EnumMap map) {
        this.id = map.intValues[0];
        this.itemIds = new int[map.length - 1];
        System.arraycopy(map.intValues, 1, this.itemIds, 0, map.length - 1);
        StringBuilder message = new StringBuilder();
        for(int i = 0; i < itemIds.length; i++) {
            message.append(ItemDef.get(itemIds[i]).name);//.append("<col=ef1020>")
            if(i == itemIds.length - 1)
                message.append("</col>.");
            else
                message.append("</col>, ");
        }
        this.contentsMessage = message.toString();
        ItemDef.get(id).collectionLogDataSet = this;
    }

    private CollectionLogDataSet(int id, int... itemIds) {
        this.id = id;
        this.itemIds = itemIds;
        this.contentsMessage = null;
    }

    /* sends the collection log enum ids */
    public static void sendCollectionLogEnumId(Player player, int enumId) {
        EnumMap map = EnumMap.get(enumId);
        ArrayList<CollectionLogDataSet> collectionLogEnumList = new ArrayList<>();
        for(int i = 0; i < map.length; i++) {
            EnumMap enumMap = EnumMap.get(map.intValues[i]);
            collectionLogEnumList.add(new CollectionLogDataSet(enumMap));
            System.out.println("Map: "+enumMap);
        }
        CollectionLogDataSet[] sets = collectionLogEnumList.toArray(new CollectionLogDataSet[collectionLogEnumList.size()]);
        System.out.println(sets);
    }

/*    static {
        EnumMap map = EnumMap.get(2142);
        StringBuilder sb = new StringBuilder();
        for(int key : map.keys) {
            int itemId = map.intValues[key];
            ItemDef def = ItemDef.get(itemId);
            sb.append(def.name).append("|");
        }
    }*/

}
