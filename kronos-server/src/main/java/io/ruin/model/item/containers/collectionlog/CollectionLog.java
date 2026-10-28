package io.ruin.model.item.containers.collectionlog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Lists;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * @author Greco
 * @since 09/01/2020
 */
@Slf4j
public class CollectionLog extends ItemContainerG<CollectionLogItem> {

    /**
     * Variables.
     */

    /* collection log interface info */
    public static int COLLECTION_LOG_ITEM_CONTAINER = 620;
    public static int COLLECTION_LOG_ID = 621;

    /* collection log item id */
    public static int COLLECTION_LOG_ITEM_ID = 22711;

    /* collection log client script tab info int params */
    public static int[] bossParams = {40697866, 40697867, 40697868, 40697869};
    public static int[] raidParams = {40697870, 40697871, 40697872, 40697877};
    public static int[] clueParams = {40697878, 40697886, 40697887, 40697879};
    public static int[] minigameParams = {40697880, 40697881, 40697890, 40697882};
    public static int[] otherParams = {40697883, 40697888, 40697889, 40697884};

    /* collection log structures */
    public static int BOSSES_STRUCT = 471;
    public static int RAIDS_STRUCT = 472;
    public static int CLUES_STRUCT = 473;
    public static int MINIGAMES_STRUCT = 474;
    public static int OTHER_STRUCT = 475;

    /* collection log child ids */
    public static int BOSS_CHILD_ID = 11;
    public static int RAIDS_CHILD_ID = 15;
    public static int CLUES_CHILD_ID = 30;
    public static int MINIGAMES_CHILD_ID = 25;
    public static int OTHER_CHILD_ID = 32;

    /* blank container id, blank container filler id */
    public static final int BLANK_ID = -1, FILLER_ID = 20594;

    public static final List<Integer> UNLOCKABLE_ITEM_IDS = Lists.newArrayList();

    private static void loadJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File("./data/collection_log.json"));

            List<Integer> itemIds = new ArrayList<>();

            Iterator<JsonNode> categories = rootNode.elements();
            while (categories.hasNext()) {
                JsonNode category = categories.next();
                Iterator<JsonNode> subCategories = category.elements();
                while (subCategories.hasNext()) {
                    JsonNode subCategory = subCategories.next();
                    JsonNode items = subCategory.get("items");
                    if (items != null && items.isArray()) {
                        for (JsonNode itemNode : items) {
                            int itemId = itemNode.get("itemId").asInt();
                            UNLOCKABLE_ITEM_IDS.add(itemId);
                        }
                    }
                }
            }
            // Print all item IDs
            itemIds.forEach(System.out::println);
            log.info("Loaded "+UNLOCKABLE_ITEM_IDS.size()+" items from collection_log.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens the collection log
     */
    public void openCollectionLog(Player player) {

        /* send collection log item container */
        player.getPacketSender().sendItems(-1, -1, COLLECTION_LOG_ITEM_CONTAINER, player.getCollectionLog().getItems());

        /* collection log interface */
        player.openInterface(InterfaceType.MAIN, COLLECTION_LOG_ID);

        /* bosses */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 10, 0, 34, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 11, 0, 34, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 12, 0, 34, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 29, 0, 34, 2);

        /* raids */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 14, 0, 34, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 15, 0, 34, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 16, 0, 34, 2);

        /* clues */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 22, 0, 6, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 30, 0, 6, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 31, 0, 6, 2);

        /* minigames */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 24, 0, 10, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 25, 0, 10, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 34, 0, 10, 2);

        /* other */
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 27, 0, 18, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 32, 0, 18, 2);
        player.getPacketSender().sendAccessMask(COLLECTION_LOG_ID, 33, 0, 18, 2);

        /* client scripts */
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 0);
        player.getPacketSender().sendClientScript(2730, 40697866, 40697867, 40697868, 40697869, player.collection_log_current_struct = 471, player.collection_log_current_entry = 0);//BOSSES_STRUCT, 0

        sendAll = true;

    }


    public void checkItemReceived(Item item) {
        if (player == null)
            return;
        Optional<Integer> loggedItem = UNLOCKABLE_ITEM_IDS.stream().filter(c -> c == item.getId()).findFirst();
        if (!loggedItem.isPresent())
            return;
        if (!this.contains(item.getId()))
            sendCollectionLogItemScreen(item);
        add(item.getId(), item.getAmount());
        sendAll = true;
        send(player);

    }

    private void sendCollectionLogItemScreen(Item item) {
        player.addEvent(e -> {
            player.openInterface(InterfaceType.UNUSED_OVERLAY1, 660);
            player.getPacketSender().sendClientScript(3346, new Object[]{1, 1, 100});
            player.getPacketSender().sendString(660, 4, "Collection Log");
            player.getPacketSender().sendString(660, 8, "New Item<br><br><col=ffffff>" + item.getDef().name);
            e.delay(6);
            player.closeInterface(InterfaceType.UNUSED_OVERLAY1);
        });
    }

    /**
     * info about the collection log.
     */

    /* client script info - id: 2389, params: category value 0-4.*/
    /* values: 0=bosses, 1=raids, 2=clues, 3=minigames, 4=other */
    /* client script info - id: 2730, params: hashedId int params, structureId 471-475, slotId. */
    /* structureIds: 471=bosses, 472=raids, 473=clues, 474=minigames, 475=other */


    /* struct param 682, string. main category name. bosses, raids, clues, minigames, other. */
    /* struct param 683, enum id. enum id stores struct id info for selected item categories.*/
    /* struct param 683, int id. main category int value 0-4. */

    /* struct param 689, string name. the name for the item category. tob, xeric, easy trails, barrows, etc.*/
    /* struct param 690, id. id = enum id that stores that item categories*/


    /* sending the bosses tab */
    public static void sendBossesTab(Player player) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 0);
        player.getPacketSender().sendClientScript(2730, 40697866, 40697867, 40697868, 40697869, player.collection_log_current_struct = 471, player.collection_log_current_entry = 0);
    }

    /* sending the raids tab */
    public static void sendRaidsTab(Player player) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 1);
        player.getPacketSender().sendClientScript(2730, 40697870, 40697871, 40697872, 40697877, player.collection_log_current_struct = 472, player.collection_log_current_entry = 0);
    }

    /* sending the clues tab */
    public static void sendCluesTab(Player player) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 2);
        player.getPacketSender().sendClientScript(2730, 40697878, 40697886, 40697887, 40697879, player.collection_log_current_struct = 473, player.collection_log_current_entry = 0);
    }

    /* sending the minigames tab */
    public static void sendMinigamesTab(Player player) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 3);
        player.getPacketSender().sendClientScript(2730, 40697880, 40697881, 40697890, 40697882, player.collection_log_current_struct = 474, player.collection_log_current_entry = 0);
    }

    /* sending the other tab */
    public static void sendOtherTab(Player player) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 4);
        player.getPacketSender().sendClientScript(2730, 40697883, 40697888, 40697889, 40697884, player.collection_log_current_struct = 475, player.collection_log_current_entry = 0);
    }

    /* sending specific category tab */
    public static void sendCategoryTab(Player player, int structId, int categoryId, int subCategoryId, int[] params) {
        //player.getPacketSender().sendClientScript(2389, categoryId);
        //player.getPacketSender().sendClientScript(2730, params.length, structId, subCategoryId);
    }

    /* handles the collection log tabs */
    public void handleLogTab(Player player, int childId, int tab) {
        tab = player.collection_log_current_tab;
        switch (childId) {
            case 4://bosses
                player.getPacketSender().sendClientScript(2389, tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 471);
                return;
            case 5://raids
                player.getPacketSender().sendClientScript(2389, tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 472);
                return;
            case 6://clues
                player.getPacketSender().sendClientScript(2389, tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 473);
                return;
            case 7://minigames
                player.getPacketSender().sendClientScript(2389, tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 474);
                return;
            case 8://other
                player.getPacketSender().sendClientScript(2389, tab);
                player.getCollectionLog().handleLogStructure(player, player.collection_log_current_struct = 475);
                return;
        }
    }

    /* handles the collection log client structures */
    public void handleLogStructure(Player player, int struct) {
        struct = player.collection_log_current_struct;
        switch (struct) {
            case 471://bosses
                player.getPacketSender().sendClientScript(2730, 40697866, 40697867, 40697868, 40697869, struct, player.collection_log_current_entry);
                return;
            case 472://raids
                player.getPacketSender().sendClientScript(2730, 40697870, 40697871, 40697872, 40697877, struct, player.collection_log_current_entry);
                return;
            case 473://clues
                player.getPacketSender().sendClientScript(2730, 40697878, 40697886, 40697887, 40697879, struct, player.collection_log_current_entry);
                return;
            case 474://minigames
                player.getPacketSender().sendClientScript(2730, 40697880, 40697881, 40697890, 40697882, struct, player.collection_log_current_entry);
                return;
            case 475://other
                player.getPacketSender().sendClientScript(2730, 40697883, 40697888, 40697889, 40697884, struct, player.collection_log_current_entry);
                return;

        }
    }

    /* handles the collection log kill count slots */
    public void handleKillCountSlot(Player player, CollectionLogInfo info, int slot) {
        player.collection_log_current_entry = slot;
        Config.COLLECTION_LOG_KC_TAB.set(player, player.getCollectionLog().getCollectionLogKillCount(player, info, slot));
    }

    /* gets the kill count for the collection log entries */
    public int getCollectionLogKillCount(Player player, CollectionLogInfo info, int entry) {
        entry = player.getCollection_log_current_entry();//final int
        switch (info) {
            case BOSS:
                switch (entry) {
                    case 0://abyssal sire
                        return player.abyssalSireKills.getKills();
                    case 1://alchemical hydra
                        return player.alchemicalHydraKills.getKills();
                    case 2://barrows
                        return player.barrowsChestsLooted.getKills();
                    case 3://bryophyta
                        return player.bryophytaKills.getKills();
                    case 4://callisto
                        return player.callistoKills.getKills();
                    case 5://cerberus
                        return player.cerberusKills.getKills();
                    case 6://chaos ele
                        return player.chaosElementalKills.getKills();
                    case 7://chaos fanatic
                        return player.chaosFanaticKills.getKills();
                    case 8://commander zilyana
                        return player.commanderZilyanaKills.getKills();
                    case 9://corporeal beast
                        return player.corporealBeastKills.getKills();
                    case 10://crazy archaeologist
                        return player.crazyArchaeologistKills.getKills();
                    case 11://dagannoth kings
                        return CollectionLog.addSumMultipleDagKC(player);
                    case 12://the fight caves
                        return player.theFightCaveKills.getKills();
                    case 13://the gauntlet
                        return player.theGauntletKills.getKills();
                    case 14://general graardor
                        return player.generalGraardorKills.getKills();
                    case 15://giant mole
                        return player.giantMoleKills.getKills();
                    case 16://grotesque guardians
                        return player.grotesqueGuardianKills.getKills();
                    case 17://hespori
                        return player.hesporiKills.getKills();
                    case 18://inferno
                        return player.zukKills.getKills();
                    case 19://kalphite queen
                        return player.kalphiteQueenKills.getKills();
                    case 20://king black dragon
                        return player.kingBlackDragonKills.getKills();
                    case 21://kraken
                        return player.krakenKills.getKills();
                    case 22://kree'ara
                        return player.kreeArraKills.getKills();
                    case 23://k'ril
                        return player.krilTsutsarothKills.getKills();
                    case 24://obor
                        return player.oborKills.getKills();
                    case 25://sarachnis
                        return player.sarachnisKills.getKills();
                    case 26://scorpia
                        return player.scorpiaKills.getKills();
                    case 27://skotizo
                        return player.skotizoKills.getKills();
                    case 28://thermo devil
                        return player.thermonuclearSmokeDevilKills.getKills();
                    case 29://venenatis
                        return player.venenatisKills.getKills();
                    case 30://vet'ion
                        return player.vetionKills.getKills();
                    case 31://vorkath
                        return player.vorkathKills.getKills();
                    case 32://wintertodt
                        return player.wintertodtKills.getKills();
                    case 33://zalcano
                        return player.zalcanoKills.getKills();
                    case 34://zulrah
                        return player.zulrahKills.getKills();
                    case 35://nex
                        return player.nexKills.getKills();
                }
            case RAIDS:
                switch (entry) {
                    case 0://cox
                        return player.chambersofXericKills.getKills();
                    case 1://tob
                        return player.theatreOfBloodKills.getKills();
                }
            case CLUES:
                switch (entry) {
                    case 0://beginner treasure trails
                        return player.beginnerClueCount;
                    case 1://easy treasure trails
                        return player.easyClueCount;
                    case 2://medium treasure trails
                        return player.medClueCount;
                    case 3://hard treasure trails
                        return player.hardClueCount;
                    case 4://elite treasure trails
                        return player.eliteClueCount;
                    case 5://master treasure trails
                        return player.masterClueCount;
                    case 6://shared treasure trail rewards
                        return CollectionLog.addSumMultipleClues(player);
                }
            case MINIGAMES:
                switch (entry) {
                    case 0://barbarian assault high-level gambles
                        return player.barbarianAssultHighLevelGambles;
                }
            case OTHER:
                switch (entry) {
                    case 0:
                        return 0;
                    case 8://glough's experiments kills
                        return player.gloughsExperimentKills.getKills();
                    case 12://revenant kills
                        return player.revenantKills.getKills();
                }
        }
        return entry;
    }

    public static void handleBossesButtonSlots(Player player, int slot) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 0);
        player.getPacketSender().sendClientScript(2730, 40697866, 40697867, 40697868, 40697869, player.collection_log_current_struct = 471, player.collection_log_current_entry = slot);
    }

    public static void handleRaidsButtonSlots(Player player, int slot) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 1);
        player.getPacketSender().sendClientScript(2730, 40697870, 40697871, 40697872, 40697877, player.collection_log_current_struct = 472, player.collection_log_current_entry = slot);
    }

    public static void handleCluesButtonSlots(Player player, int slot) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 2);
        player.getPacketSender().sendClientScript(2730, 40697878, 40697886, 40697887, 40697879, player.collection_log_current_struct = 473, player.collection_log_current_entry = slot);
    }

    public static void handleMinigamesButtonSlots(Player player, int slot) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 3);
        player.getPacketSender().sendClientScript(2730, 40697880, 40697881, 40697890, 40697882, player.collection_log_current_struct = 474, player.collection_log_current_entry = slot);
    }

    public static void handleOtherButtonSlots(Player player, int slot) {
        player.getPacketSender().sendClientScript(2389, player.collection_log_current_tab = 4);
        player.getPacketSender().sendClientScript(2730, 40697883, 40697888, 40697889, 40697884, player.collection_log_current_struct = 475, player.collection_log_current_entry = slot);
    }

    static {

        ItemAction.registerInventory(COLLECTION_LOG_ITEM_ID, 1, (player, item) -> {
            player.getCollectionLog().openCollectionLog(player);
            return;
        });

        InterfaceHandler.register(COLLECTION_LOG_ID, h -> {

            /* handles the bosses button slots */
            h.actions[11] = (DefaultAction) (player, child, option, slot, itemId) -> handleBossesButtonSlots(player, slot);

            /* handles the raids button slots */
            h.actions[15] = (DefaultAction) (player, child, option, slot, itemId) -> handleRaidsButtonSlots(player, slot);

            /* handles the clues button slots */
            h.actions[30] = (DefaultAction) (player, child, option, slot, itemId) -> handleCluesButtonSlots(player, slot);

            /* handles the minigames button slots */
            h.actions[25] = (DefaultAction) (player, child, option, slot, itemId) -> handleMinigamesButtonSlots(player, slot);

            /* handles the other button slots */
            h.actions[32] = (DefaultAction) (player, child, option, slot, itemId) -> handleOtherButtonSlots(player, slot);
            /* handles the switching of tabs */
            h.actions[4] = (SimpleAction) CollectionLog::sendBossesTab;
            h.actions[5] = (SimpleAction) CollectionLog::sendRaidsTab;
            h.actions[6] = (SimpleAction) CollectionLog::sendCluesTab;
            h.actions[7] = (SimpleAction) CollectionLog::sendMinigamesTab;
            h.actions[8] = (SimpleAction) CollectionLog::sendOtherTab;

            h.actions[27] = (SimpleAction) CollectionLog::sendOtherTab;

            /* on interface close */
            h.closedAction = (p, i) -> {
                p.getPacketSender().sendClientScript(101, "i", 11);
            };
        });
        loadJson();
    }

    /* adds up the killcount for dagannoth rex, supreme and prime */
    public static int addSumMultipleDagKC(Player player) {
        int sum = 0;
        int sum1 = player.dagannothRexKills.getKills();
        int sum2 = player.dagannothSupremeKills.getKills();
        int sum3 = player.dagannothPrimeKills.getKills();
        System.out.println("Sum for dag kc = " + sum);
        return sum1 + sum2 + sum3;
    }

    /* adds up all the clue count from beginner -> master */
    public static int addSumMultipleClues(Player player) {
        int sum = 0;
        int sum1 = player.beginnerClueCount;
        int sum2 = player.easyClueCount;
        int sum3 = player.medClueCount;
        int sum4 = player.hardClueCount;
        int sum5 = player.eliteClueCount;
        int sum6 = player.masterClueCount;
        System.out.println("Sum for clue kc = " + sum);
        return sum1 + sum2 + sum3 + sum4 + sum5 + sum6;
    }

    /* adds up all the raids kc */
    public static int addRaidsKC(Player player) {
        int sum = 0;
        int sumCox = player.chambersofXericKills.getKills();
        int sumToB = player.theatreOfBloodKills.getKills();
        return sumCox + sumToB;
    }

    /* adds up all the other tab kc */
    public static int addOtherTabKC(Player player) {
        int sum = 0;
        int sumGlouphs = player.gloughsExperimentKills.getKills();
        int sumRevs = player.revenantKills.getKills();
        return sumRevs + sumGlouphs;
    }


    /**
     * ItemContainerG implementations.
     */

    @Override
    public int add(int id, int amount, Map<String, String> attributes) {
        ItemDef def = ItemDef.get(id);
        if (def == null || def.isPlaceholder())
            return -1;
        int freeSlot = -1;
        CollectionLogItem blankItem = null;

        player.totalCollections++;

        int hash = AttributeExtensions.hashAttributes(attributes);

        if (getAmount(id) + (long) amount > Integer.MAX_VALUE) {
            amount = Integer.MAX_VALUE - getAmount(id);
        }

        for (int slot = 0; slot < items.length; slot++) {
            CollectionLogItem item = items[slot];
            if (item == null) {
                if (freeSlot == -1)
                    freeSlot = slot;
                continue;
            }
            if (item.getId() == BLANK_ID) {
                if (blankItem == null)
                    blankItem = item;
                continue;
            }
            if (item.getId() == id && !item.hasAttributes()) {
                item.incrementAmount(amount);
                return amount;
            }
        }
        if (freeSlot != -1) {
            set(freeSlot, new CollectionLogItem(id, amount, attributes));

            return amount;
        }
        return 0;
    }

    @Override
    protected CollectionLogItem newItem(int id, int amount, Map<String, String> attributes) {
        return new CollectionLogItem(id, amount, attributes);
    }

    @Override
    protected CollectionLogItem[] newArray(int size) {
        return new CollectionLogItem[size];
    }

    /* send collection log item container updates */
    public boolean sendUpdates() {
        return sendUpdates(null);
    }

}
