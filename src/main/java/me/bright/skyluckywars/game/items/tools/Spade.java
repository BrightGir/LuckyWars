package me.bright.skyluckywars.game.items.tools;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;

public class Spade extends LItem implements Enchantable {

    private EnchantmentSet enchSet;

    public Spade() {
        super(new Pair<>(Material.IRON_SHOVEL,100),new Pair<>(Material.DIAMOND_SHOVEL,50),
                new Pair<>(Material.NETHERITE_SHOVEL,15));
        enchSet =  getDefaultEnchSet(DropSet.DropCategory.TOOLS).clone()
                .addEnchantment(Enchantment.VANISHING_CURSE, Arrays.asList(
                        new Pair<>(5,Arrays.asList(1))
                ));
    }

    @Override
    public EnchantmentSet getEnchSet() {
        return enchSet;
    }

    @Override
    public int getChance() {
        return 30;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.TOOLS;
    }
}
