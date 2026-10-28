package io.ruin.model.item.containers.collectionlog;

import lombok.Getter;

/**
 * @author Greco
 */
@Getter
public enum CollectionLogInfo {

    BOSS, RAIDS, CLUES, MINIGAMES, OTHER
    ;

    CollectionLogInfo() { }


    public static int BOSSES_STRUCT = 471; public static int RAIDS_STRUCT = 472; public static int CLUES_STRUCT = 473;
    public static int MINIGAMES_STRUCT = 474; public static int OTHER_STRUCT = 475;

    public static int BOSS_CHILD_ID = 11; public static int RAIDS_CHILD_ID = 15;
    public static int CLUES_CHILD_ID = 30; public static int MINIGAMES_CHILD_ID = 25;
    public static int OTHER_CHILD_ID = 32;


}
