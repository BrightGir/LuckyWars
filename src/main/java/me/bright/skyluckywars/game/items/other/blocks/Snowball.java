package me.bright.skyluckywars.game.items.other.blocks;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;


public class Snowball extends LItem {

    public Snowball() {
        super(Material.SNOWBALL,4,16);
    }
    @Override
    public int getChance() {
        return 20;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.OTHER;
    }
}