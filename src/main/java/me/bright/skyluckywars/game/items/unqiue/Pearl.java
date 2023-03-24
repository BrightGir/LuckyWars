package me.bright.skyluckywars.game.items.unqiue;

import me.bright.skylib.game.Game;
import me.bright.skylib.utils.ItemBuilder;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Pearl extends LItem {
    public Pearl() {
        super(Type.PEARL, Arrays.asList(
                " ",
                "&7Вместо обычной телепортации",
                "&7вы будете перемещаться верхом",
                "&7на жемчужине"), Material.ENDER_PEARL,1,3);
        setGlowing(true);
    }

    @Override
    public int getChance() {
        return 15;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.HELPING;
    }
}
