package me.bright.skyluckywars.game.items.armor;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;

public class Boots extends LItem implements Enchantable {

    private EnchantmentSet enchSet;

    public Boots() {
        super(new Pair<>(Material.IRON_BOOTS,100),new Pair<>(Material.DIAMOND_BOOTS,40),
                new Pair<>(Material.NETHERITE_BOOTS,10));
        enchSet = getDefaultEnchSet(DropSet.DropCategory.ARMOR);
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.ARMOR;
    }

    @Override
    public int getChance() {
        return 50;
    }


    @Override
    public EnchantmentSet getEnchSet() {
        return enchSet;
    }
}
