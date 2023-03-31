package me.bright.skyluckywars.game.items.bows;

import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class FrozenBow extends LItem implements Enchantable {


    public FrozenBow() {
        super(Type.FROZEN_BOW, Arrays.asList(
                " ",
                "&7У вас есть шанс замедлить",
                "&7противника при попадании",
                "&7в него"), Material.BOW);
        setAttackAction(event -> {
            if(event.getEntity().isDead()) return;
            LivingEntity en = (LivingEntity) event.getEntity();
            ItemStack hand = ((Player)event.getDamager()).getInventory().getItemInMainHand();
            int chance = Messenger.rnd(10,30);
           // int chance = Integer.parseInt(hand.getItemMeta().getLore().get(2).split(" ")[6].replace("%",""));
            if(luck(5)) {
                en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20 * getRnd(3,6),
                        9,false,false));
                World world = en.getWorld();
                world.spawnParticle(Particle.BLOCK_CRACK, en.getLocation(), 1, 1, 0.1, 0.1, 0.1,
                        Material.PACKED_ICE.createBlockData());
                world.playEffect(en.getLocation().clone().add(0,0.5,0),Effect.STEP_SOUND,Material.BLUE_ICE);
            } else if(luck(chance)) {
                en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20 * getRnd(3,6),
                        1,false,false));
                World world = en.getWorld();
                world.spawnParticle(Particle.BLOCK_CRACK, en.getLocation(), 1, 1, 0.1, 0.1, 0.1,
                        Material.PACKED_ICE.createBlockData());
                world.playEffect(en.getLocation().clone().add(0,0.5,0),Effect.STEP_SOUND,Material.BLUE_ICE);
            }
        });
    //  setGenerateAction(item -> {
    //    //  item
    //      ItemMeta meta = item.getItemMeta();
    //      meta.setLore(Arrays.asList(
    //              " ",
    //              "&6Способность: ",
    //              "&aПри попадании в противника с шансом " + Messenger.rnd(10,30) + "%",
    //              "&aна него накладывается эффект медлительности II уровня",
    //              "&aна 3-6 секунд, c шансом 5% при попадании в игрока лук",
    //              "&aможет его заморозить на 3 секунды"));
    //      item.setItemMeta(meta);
    //  });
        setGlowing(true);
    }


    @Override
    public EnchantmentSet getEnchSet() {
        return getDefaultEnchSet(DropSet.DropCategory.BOW);
    }

    @Override
    public int getChance() {
        return 5;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.BOW;
    }
}
