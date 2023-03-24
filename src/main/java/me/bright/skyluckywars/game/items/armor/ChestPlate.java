package me.bright.skyluckywars.game.items.armor;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Material;

public class ChestPlate extends LItem implements Enchantable {

    private EnchantmentSet enchSet;

    public ChestPlate() {
        super(new Pair<>(Material.IRON_CHESTPLATE,100),new Pair<>(Material.DIAMOND_CHESTPLATE,40),
                new Pair<>(Material.NETHERITE_CHESTPLATE,10));
        enchSet = getDefaultEnchSet(DropSet.DropCategory.ARMOR);
    }

    @Override
    public int getChance() {
        return 50;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.ARMOR;
    }


    @Override
    public EnchantmentSet getEnchSet() {
        return enchSet;
    }
}
