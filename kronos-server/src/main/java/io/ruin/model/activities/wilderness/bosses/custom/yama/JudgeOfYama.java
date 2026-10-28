package io.ruin.model.activities.wilderness.bosses.custom.yama;

import com.google.api.client.util.Lists;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.utility.Misc;
import io.ruin.utility.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.DumbRoute;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.ruin.model.activities.wilderness.bosses.custom.yama.FireStratergy.*;

public class JudgeOfYama extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend).postDefend(this::postDefend);
        npc.deathStartListener = (entity, killer, killHit) -> {
            Pet pet = Pet.YAMA;
            double chance = Math.random();
            double probability = 1.0 / pet.dropAverage;
            if (chance <= probability) {
                pet.unlock(killer.player);
            }
        };
    }

    private void preDefend(Hit hit) {
        if (hit.attackStyle == null)
            return;
        if (hit.attackStyle.isRanged() || hit.attackStyle.isMagic())
            hit.block();
    }

    private void postDefend(Hit hit) {
        if (hit.damage > 100 && !hit.isBlocked())
            hit.damage = 100;
    }

    @Override
    public void updateLastDefend(Entity attacker) {
        super.updateLastDefend(attacker);
        super.updateLastDefend(attacker);
    }

    @Override
    public void startDeath(Hit killHit) {
        super.startDeath(killHit);
    }


    @Override
    public void follow() {
        follow(0);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        else if (Random.rollDie(9, 1)) {
            flameAttack();
            return true;
        }
        if (!withinDistance(1))
            return false;
        basicAttack(info.attack_animation, info.attack_style, info.max_damage);
        return true;
    }

    public List<GameObject> flames = new CopyOnWriteArrayList<>();

    public FireStratergy strat;

    private void flameAttack() {
        npc.addEvent(event -> {
            if (!npc.dead()) {
                strat = FireStratergy.values()[Misc.random(FireStratergy.values().length - 1)];
                npc.lock();
                npc.localPlayers().forEach(plr -> plr.getCombat().reset());
                event.delay(2);
                npc.face(Direction.SOUTH);
                DumbRoute.route(npc, 3167, 4119);
                npc.faceNone(false);
                event.waitForMovement(npc);
                event.delay(1);
                npc.face(strat.getDirection());
                event.delay(1);
                npc.animate(4679);
                npc.forceText("Feel my wrath! Burn!");
                event.delay(1);
                int startX = strat.getStartX();
                int startY = strat.getStartY();
                int maxFlames = strat.getMax();
                for (int i = 0; i < maxFlames; i++) {
                    if (Misc.random(1, 5) == 1)
                        continue;
                    if (strat == NORTH)
                        flames.add(GameObject.spawn(25465, startX + i, startY, 0, 10, 0));
                    else if (strat == SOUTH)
                        flames.add(GameObject.spawn(25465, startX + i, startY, 0, 10, 0));
                    else if (strat == EAST)
                        flames.add(GameObject.spawn(25465, startX, startY + i, 0, 10, 0));
                    else
                        flames.add(GameObject.spawn(25465, startX, startY + i, 0, 10, 0));
                }
                event.delay(1);
                while (moveCount < 10) {
                    event.delay(1);
                    moveFlame();
                }
                event.delay(2);
                moveCount = 0;
                flames.forEach(f -> f.remove());
                flames.clear();
                npc.unlock();
                npc.getCombat().reset();
                npc.stopEvent(true);
            }
        });
    }

    private int moveCount;

    private void moveFlame() {
        List<GameObject> temp = new CopyOnWriteArrayList<>();
        for (GameObject flame : flames) {
            if (strat == NORTH) {
                temp.add(GameObject.spawn(25465, flame.getPosition().getX(), flame.getPosition().getY() - 1, 0, 10, 0));
            } else if (strat == SOUTH) {
                temp.add(GameObject.spawn(25465, flame.getPosition().getX(), flame.getPosition().getY() + 1, 0, 10, 0));
            } else if (strat == EAST) {
                temp.add(GameObject.spawn(25465, flame.getPosition().getX() - 1, flame.getPosition().getY(), 0, 10, 0));
            } else {
                temp.add(GameObject.spawn(25465, flame.getPosition().getX() + 1, flame.getPosition().getY(), 0, 10, 0));
            }
        }
        moveCount++;
        flames.forEach(f -> f.remove());
        flames.clear();
        flames.addAll(temp);
    }

    @Override
    public void tick() {
        for (Player p : npc.localPlayers()) {
            if (p == null || p.dead())
                continue;
            for (GameObject f : flames) {
                if (p.getPosition().equals(f.getPosition())) {
                    p.hit(new Hit(npc).randDamage(30));
                    p.forceText("Ouch!");
                }
            }
        }
    }
}