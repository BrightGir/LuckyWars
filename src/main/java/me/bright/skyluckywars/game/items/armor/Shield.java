package me.bright.skyluckywars.game.items.armor;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

public class Shield extends LItem {

    public Shield() {
        super(Material.SHIELD);
    }
    @Override
    public int getChance() {
        return 10;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.OTHER;
    }
}