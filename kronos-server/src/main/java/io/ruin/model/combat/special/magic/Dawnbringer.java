package io.ruin.model.combat.special.magic;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class Dawnbringer extends TargetSpell {

    public static final Dawnbringer INSTANCE = new Dawnbringer();

    public Dawnbringer() {
        setMaxDamage(25);
        setAnimationId(1167);
        setCastGfx(1540, 0, 0);
        setCastSound(122, 1, 0);
        setHitGfx(1541, 0);
        setHitSound(124);
        setProjectile(new Projectile(1539, 31, 31, 46, 36, 10, 16, 64));
    }
}
