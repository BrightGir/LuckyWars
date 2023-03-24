package me.bright.skyluckywars.game.items.unqiue;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

import java.util.Arrays;

public class SnowballFlake extends LItem {

    public SnowballFlake() {
        super(Type.SNOWBALL_FLAKE, Arrays.asList(" ",
                        "&7Создает чешуйницу, которая",
                        "&7будет атаковать противников"),
                Material.SNOWBALL,3,8);
        setGlowing(true);
    }
    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }

    @Override
    public int getChance() {
        return 15;
    }
}
