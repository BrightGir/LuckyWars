package me.bright.skyluckywars.game.items.other.blocks;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

public class Stone extends LItem {


    public Stone() {
        super(Material.STONE,12,24);
    }
    @Override
    public int getChance() {
        return 45;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.OTHER;
    }
}
