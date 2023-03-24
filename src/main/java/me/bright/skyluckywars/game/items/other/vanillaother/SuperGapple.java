package me.bright.skyluckywars.game.items.other.vanillaother;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

public class SuperGapple extends LItem {

    public SuperGapple() {
        super(Material.ENCHANTED_GOLDEN_APPLE);
    }
    @Override
    public int getChance() {
        return 1;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.OTHER;
    }
}
