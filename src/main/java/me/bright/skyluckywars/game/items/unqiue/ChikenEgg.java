package me.bright.skyluckywars.game.items.unqiue;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

import java.util.Arrays;

public class ChikenEgg extends LItem {


    public ChikenEgg() {
        super(Type.CHIKEN_EGG, Arrays.asList(" ",
                        "&7Создает цыпленка, который",
                        "&7взрывается через несколько",
                        "&7секунд"),
                Material.EGG,3,8);
        setGlowing(true);
    }
    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }

    @Override
    public int getChance() {
        return 10;
    }
}
