package me.bright.skyluckywars.game.items.unqiue;


import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Berserk extends LItem {

    public Berserk() {
        super(Type.BERSERK, Arrays.asList(
                " ",
                "&7Когда кажется, что шансов",
                "&7уже нет, используйте его"), Material.MAGMA_CREAM);
        setClickAction(event -> {
            Player p = event.getPlayer();
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,20 * 5,4,false
            ,false));

            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20 * 5,4,false
                    ,false));

            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20 * 5,2,false
                    ,false));

            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20 * 5,2,false
                    ,false));
            decreaseAmountItemInHand(p);
        });
        setGlowing(true);
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }

    @Override
    public int getChance() {
        return 5;
    }
}
