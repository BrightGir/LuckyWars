package me.bright.skyluckywars.game.traps;

import me.bright.skylib.utils.Messenger;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class FoxTrap extends Trap{

    public FoxTrap() {
        setTrapAction((loc, sp) -> {
            int count = Messenger.rnd(5,7);
            for (int i = 0; i < count; i++) {
                Wolf fox = (Wolf) loc.getWorld().spawnEntity(loc.clone().add(0,1,0), EntityType.WOLF);
                fox.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30D);
                fox.setHealth(30D);
                ItemStack sword = new ItemStack(Material.STONE_SWORD);
                fox.getEquipment().setItemInMainHand(sword);
                fox.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                fox.setTarget(sp.getPlayer());
            }

        });

    }
}
