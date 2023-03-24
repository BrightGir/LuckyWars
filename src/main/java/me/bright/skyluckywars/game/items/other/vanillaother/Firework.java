package me.bright.skyluckywars.game.items.other.vanillaother;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Material;

import java.util.Arrays;

public class Firework extends LItem {

    public Firework() {
        super(Type.FIREWORK, Arrays.asList(
                " ",
                "&7Если вы владелец элитр,",
                "&7то этот предмет поможет",
                "&7вам покорять небеса"), Material.FIREWORK_ROCKET,6,12);
        setGlowing(true);
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
