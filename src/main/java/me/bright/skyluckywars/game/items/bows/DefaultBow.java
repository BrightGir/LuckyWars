package me.bright.skyluckywars.game.items.bows;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import org.bukkit.Material;

public class DefaultBow extends LItem implements Enchantable {
    public DefaultBow() {
        super(Type.DEFAULT_BOW, null, Material.BOW);
    }

    @Override
    public int getChance() {
        return 20;
    }

    @Override
    public EnchantmentSet getEnchSet() {
        return getDefaultEnchSet(DropSet.DropCategory.BOW);
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.BOW;
    }
}
