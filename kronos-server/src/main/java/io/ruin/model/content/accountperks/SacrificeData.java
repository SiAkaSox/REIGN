package io.ruin.model.content.accountperks;

import com.google.common.collect.Maps;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;

import java.util.Map;
import java.util.Optional;

public enum SacrificeData {

    SCYTHE_OF_VITUR(22486, 7500),
    TWISTED_BOW(20997, 7500),
    KODAI_WAND(21006, 5000),
    GHRAZI_RAPIER(22324, 5000),
    BOWFA(25865, 3500),
    BLADE_OF_SAELDOR(23995, 3500),
    NIHIL_HORN(26372, 2250),
    BLOWPIPE(12924, 550),
    SOL(22296, 300),
    DRAGON_CLAWS(13652, 1500),
    HEAVY_BALLISTA(19481, 350),
    ABYSSAL_WHIP(4151, 150),
    SANGUINESTI_STAFF(22481, 2000),
    DHCB(21012, 2000),
    DH_LANCE(22978, 1850),
    ACB(11785, 750),
    AGS(11802, 550),
    BGS(11804, 500),
    SGS(11806, 500),
    ZGZ(11808, 350),
    DWH(13576, 1250),
    AVERNIC_HILT(22477, 1500),
    NIGHTMARE_STAFF(24422, 500),
    KRAKEN_TENT(12006, 150),
    UNCHARGED_TOXIC_TRIDENT(12900, 750),
    ZAMORAKIAN_HASTA(11889, 250),
    SARADOMIN_SWORD(11838, 150),
    ABYSSAL_DAGGER(13265, 150),
    ABYSSAL_BLUDGEON(13263, 150),
    DRAGON_PICKAXE(11920, 150),
    DRAGON_AXE(6739, 50),
    CRAWS(22547, 350),
    THAMMARONS(22552, 350),
    VIGGORAS(22542, 350),
    DRAGON_CROSSBOW(21902, 300),
    TOXIC_STAFF(12902, 400),
    INQUISITOR_MACE(24417, 1000),
    VOLATILE_ORB(24514, 1250),
    HARM_ORB(24511, 1250),
    ELDRITCH_ORB(24517, 1250),

    BCP(11832, 500),
    TASSETS(11834, 500),
    ARMA_HELM(11826, 350),
    ARMA_CHEST(11828, 500),
    ARMA_LEGS(11830, 500),
    BASILISK_JAW(24268, 350),
    HYDRA_LEATHER(22983, 300),
    ARCANE_SS(12825, 4000),
    SPECTRAL_SS(12821, 3250),
    ELY_SS(12817, 5000),
    D_BOOTS(11840, 150),
    OCCULT(12002, 150),
    PRIMS(13239, 750),
    PEGS(13237, 750),
    ETERNALS(13235, 750),
    INQUISITOR_HELM(24419, 750),
    INQUISITOR_TOP(24420, 750),
    INQUISITOR_BOTTOM(24421, 750),
    TORVA_HELM(26382, 2000),
    TORVA_TOP(26384, 2000),
    TORVA_LEGS(26386, 2000),
    ANCESTRAL_HAT(21018, 2250),
    ANCESTRAL_TOP(21021, 2250),
    ANCESTRAL_BOTTOM(21024, 2250),
    JUSTICIAR_HELM(22326, 1500),
    JUSTICIAR_TOP(22327, 1500),
    JUSTICIAR_LEGS(22328, 1500),
    BERSERKER_RING(6737, 150),
    ARCHERS_RING(6733, 150),
    DH_SET(12877, 125),
    KARILS_SET(12883, 125),
    AHRIMS_SET(12881, 125);


    public final int itemId, cost;

    SacrificeData(int itemId, int cost) {
        this.itemId = itemId;
        this.cost = cost;
    }

    public int getId() {
        return this.itemId;
    }

    public int getCost() {
        return this.cost;
    }

    public static class SacrificeList {

        public static void sendEnumItems(Player p) {
            int[] sendItem = {330, 333, 336, 339, 342, 345, 348, 351, 354, 357, 360, 363, 366, 369, 372, 375, 378, 381, 384, 387, 390, 393, 396, 399, 402, 405, 408, 411, 414, 417, 420, 423, 426, 429, 432, 435, 438, 441, 444, 447, 451, 454, 457, 460, 463, 466, 469, 472, 475, 478, 481, 484, 487, 490, 493, 496, 499, 502, 505, 508, 511, 514, 517, 520, 523, 526, 529, 532, 535, 538, 541, 544};
            int[] sendPrice = {331, 334, 337, 340, 343, 346, 349, 352, 355, 358, 361, 364, 367, 370, 373, 376, 379, 382, 385, 388, 391, 394, 397, 400, 403, 406, 409, 412, 415, 418, 421, 424, 427, 430, 433, 436, 439, 442, 445, 448, 452, 455, 458, 461, 464, 467, 470, 473, 476, 479, 482, 485, 488, 491, 494, 497, 500, 503, 506, 509, 512, 515, 518, 521, 524, 527, 530, 533, 536, 539, 542, 545};
            for (int i = 0; i < SacrificeData.values().length && i < sendItem.length; i++) {
                int childId = sendItem[i];
                int itemId = SacrificeData.values()[i].getId();
                p.getPacketSender().sendItems(998, childId, 0, new Item(itemId));
            }
            for (int i = 0; i < SacrificeData.values().length && i < sendPrice.length; i++) {
                int childId = sendPrice[i];
                int cost = SacrificeData.values()[i].getCost();
                p.getPacketSender().sendString(998, childId, "" + cost);
            }
        }
    }


    public static void tradeItemForPoints(Player player, int itemId) {

        Optional<Map.Entry<Integer, SacrificeData>> exists = CACHED.entrySet().stream().filter(e -> e.getKey() == itemId).findAny();

        if (!exists.isPresent()) {
            player.sendMessage("This item cannot be traded for points.");
            return;
        }

        SacrificeData data = exists.get().getValue();
        int points = data.getCost();
        player.dialogue(
                new ItemDialogue().one(itemId, "Sacrifice this item for " + points + " points?"),
                new OptionsDialogue("Sacrifice item?",
                        new Option("Yes, continue", () -> itemSacrificed(player, itemId, points)),
                        new Option("Nevermind")));
        player.sendMessage("You can trade this item for " + points + " points.");
    }

    public static void itemSacrificed(Player p, int itemId, int points) {
        p.animate(536);
        p.graphics(1811);
        p.getInventory().remove(itemId, 1);
        p.perkPoints += points;
        p.sendMessage("You receive <col=FF0000>" + points + "</col> points for your sacrifice.");
        p.sendMessage("You now have <col=FF0000>" + p.perkPoints + "</col> Perk Points.");
    }

    public static Map<Integer, SacrificeData> CACHED = Maps.newConcurrentMap();

    static {
        for (SacrificeData itemlist : SacrificeData.values()) {
            CACHED.put(itemlist.itemId, itemlist);
            ItemObjectAction.register(itemlist.getId(), 33321, (player, item, object) -> tradeItemForPoints(player, item.getId()));
        }
    }
}