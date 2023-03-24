package me.bright.skyluckywars.game.items.unqiue;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class LFireball extends LItem {
    public LFireball() {
        super(Type.FIREBALL, null, Material.FIRE_CHARGE,2,4);
        setClickAction(event -> {
            Player p = event.getPlayer();
            Vector v = p.getLocation().getDirection().multiply(1.25f);
            Fireball ball = (Fireball) p.getWorld().spawnEntity(p.getLocation().clone().add(v.getX(),v.getY(),v.getZ()), EntityType.FIREBALL);
            ball.setVelocity(v);
            ball.setYield(4f);
            decreaseAmountItemInHand(event.getPlayer());
        });
        setGlowing(true);
    }

    @Override
    public int getChance() {
        return 20;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }

}
