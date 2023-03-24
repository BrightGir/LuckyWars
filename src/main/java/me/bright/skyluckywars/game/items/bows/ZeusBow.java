package me.bright.skyluckywars.game.items.bows;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.utils.EnchantmentSet;
import org.bukkit.Material;

import java.util.Arrays;

public class ZeusBow extends LItem implements Enchantable {

    public ZeusBow() {
        super(Type.ZEUS_BOW, Arrays.asList(
                " ",
                "&7У вас есть шанс создать",
                "&7молнию на месте попадания",
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

    @Override
    public EnchantmentSet getEnchSet() {
        return getDefaultEnchSet(DropSet.DropCategory.BOW);
    }
}
