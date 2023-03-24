package me.bright.skyluckywars.game.items.other.vanillaother;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

import java.util.Arrays;

public class Arrow extends LItem {

    public Arrow() {
        super(Material.ARROW,12,24);
    }

    @Override
    public int getChance() {
        return 20;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.BLOCKS;
    }
}
