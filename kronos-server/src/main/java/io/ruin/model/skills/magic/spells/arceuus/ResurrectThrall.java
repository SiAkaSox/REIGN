package io.ruin.model.skills.magic.spells.arceuus;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class ResurrectThrall extends Spell {

    public enum Thrall {

        LESSER_GHOSTLY_THRALL("Lesser ghostly thrall", 38, 10878, 9047, 1903, 8973, 1873, 55.0,  Rune.AIR.toItem(10), Rune.COSMIC.toItem(1), Rune.MIND.toItem(5)),
        LESSER_SKELETAL_THRALL("Lesser skeletal thrall", 38, 10881, 9048, 1874, 8973, 1904, 55.0, Rune.AIR.toItem(10), Rune.COSMIC.toItem(1), Rune.MIND.toItem(5)),
        LESSER_ZOMBIFIED_THRALL("Lesser zombified thrall", 38, 10884, 9046, 1905, 8973, 1875, 55.0, Rune.AIR.toItem(10), Rune.COSMIC.toItem(1), Rune.MIND.toItem(5)),

        SUPERIOR_GHOSTLY_THRALL("Superior ghostly thrall", 57, 10879, 9047, 1903, 8973, 1873, 70.0, Rune.EARTH.toItem(10), Rune.COSMIC.toItem(1), Rune.DEATH.toItem(5)),
        SUPERIOR_SKELETAL_THRALL("Superior skeletal thrall", 57, 10882, 9048, 1904, 8973, 1874, 70.0, Rune.EARTH.toItem(10), Rune.COSMIC.toItem(1), Rune.DEATH.toItem(5)),
        SUPERIOR_ZOMBIFIED_THRALL("Superior zombified thrall", 57, 10885, 9046, 1905, 8973, 1875, 70.0, Rune.EARTH.toItem(10), Rune.COSMIC.toItem(1), Rune.DEATH.toItem(5)),

        GREATER_GHOSTLY_THRALL("Greater ghostly thrall", 76, 10880, 9047, 1903, 8973, 1873, 88.0, Rune.FIRE.toItem(10), Rune.COSMIC.toItem(1), Rune.BLOOD.toItem(5)),
        GREATER_SKELETAL_THRALL("Greater skeletal thrall", 76, 10883, 9048, 1904, 8973, 1874, 88.0, Rune.FIRE.toItem(10), Rune.COSMIC.toItem(1), Rune.BLOOD.toItem(5)),
        GREATER_ZOMBIFIED_THRALL("Greater zombified thrall", 76, 10886, 9046, 1905, 8973, 1875, 88.0, Rune.FIRE.toItem(10), Rune.COSMIC.toItem(1), Rune.BLOOD.toItem(5))
        ;

        public final String thrallName;
        public final int levelReq;
        public final int npcId;
        public final int npcanimateId;
        public final int npcgfxId;
        public final int playeranimateId;
        public final int playergfxId;
        public final double mageExp;
        public final Item[] runes;

        Thrall(String thrallName, int levelReq, int npcId, int npcanimateId, int npcgfxId, int playeranimateId, int playergfxId, double mageExp, Item... runes) {
            this.thrallName = thrallName;
            this.levelReq = levelReq;
            this.npcId = npcId;
            this.npcanimateId = npcanimateId;
            this.npcgfxId = npcgfxId;
            this.playeranimateId = playeranimateId;
            this.playergfxId = playergfxId;
            this.mageExp = mageExp;
            this.runes = runes;
        }

        private int getId() {
            return this.npcId;
        }
    }

    private static void resurrect(Player p, Thrall npc) {
        p.startEvent(e -> {
            p.animate(npc.playeranimateId); //Player summoning animation
            p.graphics(npc.playergfxId); //Player summoning gfx
            NPC thrall = new NPC(npc.getId()).spawn(p.getPosition().getX() + 1, p.getPosition().getY(), p.getPosition().getZ());
            thrall.animate(npc.npcanimateId); //Thrall summoned anim
            thrall.graphics(npc.npcgfxId); //Thrall summoned gfx
            p.thrallActive = true; //Player thrall is summoned
        });
    }

    public ResurrectThrall(Thrall npc) {
       registerClick(npc.levelReq, npc.mageExp, true, npc.runes, (p, i) -> {
           resurrect(p, npc);
           return true;
       });
    }

}