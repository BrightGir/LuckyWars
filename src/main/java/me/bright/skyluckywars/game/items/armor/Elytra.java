package me.bright.skyluckywars.game.items.armor;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import org.bukkit.Material;

public class Elytra extends LItem implements Enchantable {

    public Elytra() {
        super(Material.ELYTRA);
    }

    @Override
    public int getChance() {
        return 5;
    }

    @Override
    public EnchantmentSet getEnchSet() {
        return getDefaultEnchSet(DropSet.DropCategory.ARMOR);
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.ARMOR;
    }
}
