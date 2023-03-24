package me.bright.skyluckywars.game.items.swords;

import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.*;

import java.util.Arrays;

public class SwordFury extends LItem implements Enchantable {
    public SwordFury() {
        super(Type.FURY_SWORD, Arrays.asList(
                        " ",
                        "&7У вас есть шанс нанести",
                        "&7врагу критический удар"),
                new Pair<>(Material.IRON_SWORD,100),new Pair<>(Material.DIAMOND_SWORD,50)
                ,new Pair<>(Material.NETHERITE_SWORD,15));
        setAttackAction(event -> {
            int chance = getRnd(10,20);
            if(luck(chance)) {
                int dmgInt = Messenger.rnd(125,150);
                double dmgBoost = (double)dmgInt/100;
                double curDmg = event.getDamage();
                event.setDamage(curDmg*dmgBoost);
                Location loc = event.getEntity().getLocation();
                World world = loc.getWorld();
                world.playSound(loc, Sound.ENTITY_SPLASH_POTION_BREAK,5,5);
                world.playEffect(loc, Effect.STEP_SOUND,Material.REDSTONE_BLOCK);
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
