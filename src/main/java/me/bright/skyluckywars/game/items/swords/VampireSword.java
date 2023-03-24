package me.bright.skyluckywars.game.items.swords;

import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class VampireSword extends LItem implements Enchantable {


    public VampireSword() {
        super(Type.VAMPIRESWORD, Arrays.asList(
                        " ",
                        "&7У вас есть шанс восстановить",
                        "&7здоровье при ударе противника"),
                new Pair<>(Material.IRON_SWORD,100),new Pair<>(Material.DIAMOND_SWORD,50)
                ,new Pair<>(Material.NETHERITE_SWORD,15));
        setAttackAction(event -> {
            int chance = getRnd(10,20);
            if(luck(chance)) {
                int healPercent = Messenger.rnd(25,50);
                Player damager = ((Player)event.getDamager());
                double nowHp = damager.getHealth();
                double newHp = nowHp + ((event.getFinalDamage())/100)*healPercent;
                double maxHp =  damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
                damager.setHealth(Math.min(newHp, maxHp));
                Location loc = event.getDamager().getLocation();
                loc.getWorld().spawnParticle(Particle.HEART,loc,3);
            }
        });
        setGlowing(true);
    }



    @Override
    public int getChance() {
        return 5;
    }

    @Override
    public EnchantmentSet getEnchSet() {
        return getDefaultEnchSet(DropSet.DropCategory.SWORD);
    }


    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.SWORD;
    }

}
