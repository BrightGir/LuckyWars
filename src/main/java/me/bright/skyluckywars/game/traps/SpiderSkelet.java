package me.bright.skyluckywars.game.traps;

import me.bright.skylib.utils.ItemBuilder;
import me.bright.skylib.utils.Messenger;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;


public class SpiderSkelet extends Trap {


   public SpiderSkelet() {
       setTrapAction((loc, sp) -> {
           Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
           skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40D);
           skeleton.setHealth(40D);
       //   ItemStack boots = new ItemStack(Arrays.asList(Material.IRON_BOOTS,
       //           Material.GOLDEN_BOOTS,Material.DIAMOND_BOOTS).get(Messenger.rnd(0,2)));
       //   ItemStack leggings = new ItemStack(Arrays.asList(Material.IRON_LEGGINGS,
       //           Material.GOLDEN_LEGGINGS,Material.DIAMOND_LEGGINGS).get(Messenger.rnd(0,2)));
          ItemStack chestplate = new ItemStack(Arrays.asList(Material.IRON_CHESTPLATE,
                  Material.GOLDEN_CHESTPLATE,Material.DIAMOND_CHESTPLATE).get(Messenger.rnd(0,2)));
     //     ItemStack helmet = new ItemStack(Arrays.asList(Material.IRON_HELMET,
     //             Material.GOLDEN_HELMET,Material.DIAMOND_HELMET).get(Messenger.rnd(0,2)));
         //  boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,Messenger.rnd(1,4));
         //  leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,Messenger.rnd(1,4));
           chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,Messenger.rnd(1,3));
        //   helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,Messenger.rnd(1,4));
        //   skeleton.getEquipment().setBoots(boots);
           skeleton.getEquipment().setChestplate(chestplate);
         //  skeleton.getEquipment().setHelmet(helmet);
         //  skeleton.getEquipment().setChestplate(chestplate);
           ItemStack bow = new ItemBuilder(Material.BOW)
                   .addEnchantment(Enchantment.ARROW_INFINITE,1)
                   .addEnchantment(Enchantment.ARROW_KNOCKBACK,2)
                   .addEnchantment(Enchantment.ARROW_FIRE,1).create();
           skeleton.getEquipment().setItemInMainHand(bow);
           skeleton.getEquipment().setItem(EquipmentSlot.OFF_HAND,new ItemStack(Material.ARROW));

           Spider spider = (Spider) loc.getWorld().spawnEntity(loc, EntityType.SPIDER);
           spider.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80D);
           spider.setHealth(80D);
           spider.addPotionEffect(
                   new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,1,false,false));
           spider.addPassenger(skeleton);

         //  spider.setTarget(sp.getPlayer());
         //  skeleton.setTarget(sp.getPlayer());

       });

   }


}
