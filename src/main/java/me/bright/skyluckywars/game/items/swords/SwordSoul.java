package me.bright.skyluckywars.game.items.swords;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Material;

import java.util.Arrays;

public class SwordSoul extends LItem implements Enchantable {

    public SwordSoul() {
        super(Type.SWORD_SOUL, Arrays.asList(
                        " ",
                        "&6Способность: ",
                        "&aПри убийстве противника этим мечом игрок",
                        "&aполучает 2-4 дополнительных сердца"),
                new Pair<>(Material.IRON_SWORD,100),new Pair<>(Material.DIAMOND_SWORD,50)
                ,new Pair<>(Material.NETHERITE_SWORD,15));
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
