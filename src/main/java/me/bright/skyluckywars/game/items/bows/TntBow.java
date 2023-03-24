package me.bright.skyluckywars.game.items.bows;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class TntBow extends LItem {
    public TntBow() {
        super(Type.TNT_BOW, Arrays.asList(
                " ",
                "&7У вас есть шанс создать",
                "&7взрыв на месте попадания",
                "&7стрелы"), Material.BOW);
        setGlowing(true);
    }

    @Override
    public int getChance() {
        return 10;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.BOW;
    }
}
