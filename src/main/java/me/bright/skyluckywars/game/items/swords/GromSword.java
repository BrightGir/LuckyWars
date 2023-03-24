package me.bright.skyluckywars.game.items.swords;

import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.nio.Buffer;
import java.util.Arrays;
import java.util.Locale;

public class GromSword  extends LItem implements Enchantable
{

    public GromSword() {
        super(Type.GROM_SWORD, Arrays.asList(
                        " ",
                        "&7У вас есть шанс создать молнию",
                        "&7при ударе мечом"),
                new Pair<>(Material.IRON_SWORD,100),new Pair<>(Material.DIAMOND_SWORD,50)
                ,new Pair<>(Material.NETHERITE_SWORD,15));
        setAttackAction(event -> {
            int chance = getRnd(5,10);
            if(luck(chance)) {
                if(event.getEntity() instanceof LivingEntity) {
                    LivingEntity ent = (LivingEntity) event.getEntity();
                    //     Bukkit.getLogger().info("podl");
                    ent.setVelocity(new Vector(0,Messenger.rnd(4,7),0));
                    Location loc = ent.getLocation();
                    loc.getWorld().strikeLightning(loc);
                }
            }
        });
        setGlowing(true);
    }



    @Override
    public int getChance() {
        return 5;
       // return 100;
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
