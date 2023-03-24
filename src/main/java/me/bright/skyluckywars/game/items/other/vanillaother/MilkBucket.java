package me.bright.skyluckywars.game.items.other.vanillaother;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

public class MilkBucket extends LItem {

    public MilkBucket() {
        super(Material.MILK_BUCKET,1,1);
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
