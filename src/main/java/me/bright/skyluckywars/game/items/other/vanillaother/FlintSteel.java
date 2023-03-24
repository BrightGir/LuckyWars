package me.bright.skyluckywars.game.items.other.vanillaother;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

public class FlintSteel extends LItem {

    public FlintSteel() {
        super(Material.FLINT_AND_STEEL);

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
