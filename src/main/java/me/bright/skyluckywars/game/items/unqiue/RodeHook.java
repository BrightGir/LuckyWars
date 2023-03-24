package me.bright.skyluckywars.game.items.unqiue;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class RodeHook extends LItem {

    public RodeHook() {
        super(Type.RODE_HOOK, Material.FISHING_ROD);
        setGlowing(true);
    }

    @Override
    public int getChance() {
        return 5;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }
}
