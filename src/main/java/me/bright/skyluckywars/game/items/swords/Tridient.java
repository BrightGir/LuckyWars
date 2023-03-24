package me.bright.skyluckywars.game.items.swords;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;

public class Tridient extends LItem implements Enchantable {

    private EnchantmentSet enchSet;

    public Tridient() {
        super(Material.TRIDENT);
        enchSet = getDefaultEnchSet(DropSet.DropCategory.SWORD).clone();
               // .addEnchantment(Enchantment.LOYALTY,Arrays.asList(new Pair<>(60,Arrays.asList(1)),
               //         new Pair<>(35,Arrays.asList(2,3)),
               //         new Pair<>(10,Arrays.asList(4,5))));

    }

    @Override
    public EnchantmentSet getEnchSet() {
        return enchSet;
    }

    @Override
    public int getChance() {
        return 10;
    }


    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.SWORD;
    }
}
