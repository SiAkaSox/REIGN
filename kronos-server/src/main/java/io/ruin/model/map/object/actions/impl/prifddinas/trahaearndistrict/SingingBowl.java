package io.ruin.model.map.object.actions.impl.prifddinas.trahaearndistrict;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;


public enum SingingBowl {

    CRYSTAL_HELM(70, 70, 2500, 23971, 23866, 125, 23956, -1),
    CRYSTAL_BODY(74, 74, 7500, 23975, 23866, 200, 23956, -1),
    CRYSTAL_LEGS(72, 72, 5000, 23979, 23866, 200, 23956, -1),
    CRYSTAL_HARPOON(76, 76, 6000, 23762, 23866, 150, 23953, 21028),
    CRYSTAL_BOW(78, 78, 10000, 23983, 23866, 120, 4207, -1),
    CRYSTAL_HALBERD(78, 78, 10000, 23987, 23866, 175, 4207, 23808),
    CRYSTAL_SHIELD(78, 78, 10000, 23991, 23866, 120, 4207, 23808),
    ENHANCED_CKEY(80, 80, 12000, 23951, 23866, 10, 989, -1),
    BOW_OF_FAERDHINEN(82, 82, 12000, 25865, 23866, 250, 25859, -1),
    BLADE_OF_SAELDOR(82, 82, 12000, 23995, 23866, 250, 25859, -1);



    public final int levelRequired;

    public final int levelRequired2;

    public final double experience;

    public final int product;

    public final int shardId;

    public final int shardAmount;

    public final int item1Used;

    public final int item2Used;

    SingingBowl(int levelRequired, int levelRequired2, double experience, int product, int shardId, int shardAmount, int item1Used, int item2Used) {
        this.levelRequired = levelRequired;
        this.levelRequired2 = levelRequired2;
        this.experience = experience;
        this.product = product;
        this.shardId = shardId;
        this.shardAmount = shardAmount;
        this.item1Used = item1Used;
        this.item2Used = item2Used;
        ItemDef.get(product).singingBowl = this;
    }

    private static void open(Player player) {
        SkillDialogue.make(
                player,
                (p, item) -> makeItem(player, item.getDef()),
                new SkillItem(23971),
                new SkillItem(23975),
                new SkillItem(23979),
                new SkillItem(23762),
                new SkillItem(23983),
                new SkillItem(23987),
                new SkillItem(23991),
                new SkillItem(23951),
                new SkillItem(25865),
                new SkillItem(23995)
        );
    }

    private static void makeItem(Player player, ItemDef def /*SingingBowl singingBowl*/) {
        player.closeInterface(InterfaceType.CHATBOX);
        SingingBowl singingBowl = def.singingBowl;

        player.startEvent(event -> {
            if (!player.getStats().check(StatType.Crafting, singingBowl.levelRequired)) {
                player.sendMessage("<col=880000>You need atleast level " + singingBowl.levelRequired + " Crafting to continue");
                return;
            }
            if (!player.getStats().check(StatType.Smithing, singingBowl.levelRequired2)) {
                player.sendMessage("<col=880000>You need atleast level " + singingBowl.levelRequired2 + " Smithing to continue");
                return;
            }
            if (!player.getInventory().contains(singingBowl.shardId, singingBowl.shardAmount)) {
                System.out.println("one");
                player.sendMessage("<col=880000>You don't have the required items to continue.");
                return;
            }
            if (!player.getInventory().contains(singingBowl.item1Used)) {
                System.out.println("two");
                player.sendMessage("<col=880000>You don't have the required items to continue.");
                return;
            }
            if (singingBowl.item2Used != -1 && !player.getInventory().contains(singingBowl.item2Used)) {
                System.out.println("three");
                player.sendMessage("<col=880000>You don't have the required items to continue.");
                return;
            }
            player.animate(899);
            player.getInventory().add(singingBowl.product, 1);
            player.getInventory().remove(singingBowl.shardId, singingBowl.shardAmount);
            player.getInventory().remove(singingBowl.item2Used, 1);
            player.getInventory().remove(singingBowl.item1Used, 1);
            double xp = singingBowl.experience;
            player.getStats().addXp(StatType.Crafting, xp, false);
            player.getStats().addXp(StatType.Smithing, xp, false);
            event.delay(2);
        });
    }

    static {
        ObjectAction.register(36552, "sing-crystal", (player, obj) -> open(player));
    }

}
