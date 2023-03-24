package me.bright.skyluckywars.game.items.swords;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Material;

public class Sword extends LItem implements Enchantable {

    public Sword() {
        super(new Pair<>(Material.IRON_SWORD,100),new Pair<>(Material.DIAMOND_SWORD,40)
                ,new Pair<>(Material.NETHERITE_SWORD,10));
    }


    @Override
    public int getChance() {
        return 40;
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
