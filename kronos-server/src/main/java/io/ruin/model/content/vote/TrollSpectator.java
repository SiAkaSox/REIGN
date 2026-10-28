package io.ruin.model.content.vote;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;
import io.ruin.utility.Random;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.ruin.network.incoming.handlers.InterfaceOnGroundItemHandler.withinReach;

public class TrollSpectator extends NPCCombat {

    public Player randomTarget() {
        List<Player> players = npc.localPlayers();
        List<Player> possibles = new CopyOnWriteArrayList<>();
        for (Player p : players) {
            if (p == null)
                continue;
            if (npc.getCentrePosition().isWithinDistance(p.getPosition(), 14)) {
                possibles.add(p);
            }
        }
        return Random.get(possibles);
    }

    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        if (randomTarget() == null)
            return false;
        Player possible = randomTarget();
        npc.animate(1259);
        int delay = new Projectile(32, 60, 36, 30, 51, 5, 15, 96).send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).ignoreDefence();
        npc.addEvent(e -> {
            e.delay((delay * 12) / 600);
            possible.hit(hit);
        });
        return true;
    }
}
