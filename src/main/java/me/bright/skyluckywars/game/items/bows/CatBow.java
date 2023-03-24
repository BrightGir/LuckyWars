package me.bright.skyluckywars.game.items.bows;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class CatBow extends LItem {
    public CatBow() {
        super(Type.CAT_BOW, Arrays.asList(
                " ",
                "&6Способность: ",
                "   &aС шансом 5 - 10% спавнит кошку, которая летит",
                "   &aвместе со стрелой и взрывается при попадании"), Material.BOW);
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
