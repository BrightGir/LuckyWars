package me.bright.skyluckywars.game.items.other.blocks;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

public class Cobweb extends LItem {

    public Cobweb() {
        super(Material.COBWEB,3,8);
    }


    @Override
    public int getChance() {
        return 15;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.OTHER;
    }
}
