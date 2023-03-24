package me.bright.skyluckywars.game.items.tools;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Material;

public class Pickaxe extends LItem implements Enchantable {

    public Pickaxe() {
        super(new Pair<>(Material.IRON_PICKAXE,100),new Pair<>(Material.DIAMOND_PICKAXE,50),
                new Pair<>(Material.NETHERITE_PICKAXE,15));
    }

    @Override
    public EnchantmentSet getEnchSet() {
        return getDefaultEnchSet(DropSet.DropCategory.TOOLS);
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
