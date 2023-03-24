package me.bright.skyluckywars.game.items.other.vanillaother;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

public class Totem extends LItem {

    public Totem() {
        super(Material.TOTEM_OF_UNDYING);
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
