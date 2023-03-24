package me.bright.skyluckywars.game.items.swords;

import me.bright.skylib.utils.FormatChatColor;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class GlowDeadSword extends LItem implements Enchantable {

    public GlowDeadSword() {
        super(Type.GLOW_DEAD_SWORD, Arrays.asList(
                        " ",
                        "&7У вас есть шанс ослепить",
                        "&7противника на несколько",
                        "&7секунд"),
                new Pair<>(Material.IRON_SWORD,100),new Pair<>(Material.DIAMOND_SWORD,50)
                ,new Pair<>(Material.NETHERITE_SWORD,15));
        setAttackAction(event -> {
            int chance = getRnd(10,20);
            if(luck(chance)) {
                if(event.getEntity() instanceof LivingEntity) {
                    LivingEntity ent = (LivingEntity) event.getEntity();
                    ent.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20*Messenger.rnd(1,3),0,
                            false,false));
                }
            }
        });
        setGenerateAction(item -> {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(FormatChatColor.stylish("{#133BFC}Гибель света"));
            item.setItemMeta(meta);
        });
        setGlowing(true);
    }



    @Override
    public int getChance() {
        return 5;
    //    return 100;
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
