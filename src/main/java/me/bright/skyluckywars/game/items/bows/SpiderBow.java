package me.bright.skyluckywars.game.items.bows;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

import java.util.Arrays;

public class SpiderBow extends LItem {

    public SpiderBow() {
        super(Type.SPIDER_BOW, Arrays.asList(
                " ",
                "&6Способность: ",
                "   &aС шансом 10 - 35% создает паутину на месте, куда попала стрела",
                "   &aкуда попала стрела. Однако, если стрела попадает в игрока,",
                "   &aто создает куб 3x3 из паутины"), Material.BOW);
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
