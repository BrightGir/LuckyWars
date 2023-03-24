package me.bright.skyluckywars.game.traps;

import me.bright.skylib.utils.ItemBuilder;
import me.bright.skylib.utils.Messenger;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class ZombieTrap extends Trap{

    public ZombieTrap() {
        setTrapAction((loc, sp) -> {
            Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
            zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
            zombie.setHealth(20D);
            ItemStack boots = new ItemStack(Arrays.asList(Material.IRON_BOOTS,
                    Material.DIAMOND_BOOTS).get(Messenger.rnd(0,1)));
            ItemStack leggings = new ItemStack(Arrays.asList(Material.IRON_LEGGINGS,
                    Material.DIAMOND_LEGGINGS).get(Messenger.rnd(0,1)));
            ItemStack chestplate = new ItemStack(Arrays.asList(Material.IRON_CHESTPLATE,
                    Material.DIAMOND_CHESTPLATE).get(Messenger.rnd(0,1)));
            ItemStack helmet = new ItemStack(Arrays.asList(Material.IRON_HELMET,
                    Material.DIAMOND_HELMET).get(Messenger.rnd(0,1)));
            boots.addUnsafeEnchantment(Enchantment.DURABILITY,10);
            leggings.addUnsafeEnchantment(Enchantment.DURABILITY,10);
            chestplate.addUnsafeEnchantment(Enchantment.DURABILITY,10);
            helmet.addUnsafeEnchantment(Enchantment.DURABILITY,10);
            zombie.getEquipment().setBoots(boots);
            zombie.getEquipment().setChestplate(chestplate);
            zombie.getEquipment().setHelmet(helmet);
            zombie.getEquipment().setLeggings(leggings);
            zombie.setAdult();
            ItemStack sword = new ItemStack(Arrays.asList(Material.IRON_SWORD,
                    Material.GOLDEN_SWORD,Material.DIAMOND_SWORD).get(Messenger.rnd(0,2)));
            sword.addEnchantment(Enchantment.DAMAGE_ALL,1);
          //  sword.addEnchantment(Enchantment.FIRE_ASPECT,1);
            sword.addEnchantment(Enchantment.KNOCKBACK,1);
            zombie.getEquipment().setItemInMainHand(sword);
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,1,false,false));
            zombie.setTarget(sp.getPlayer());


        });

    }

}
