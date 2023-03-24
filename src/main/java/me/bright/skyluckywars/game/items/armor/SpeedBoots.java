package me.bright.skyluckywars.game.items.armor;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.utils.EnchantmentSet;
import org.bukkit.Material;

import java.util.Arrays;

public class SpeedBoots extends LItem implements Enchantable {


    public SpeedBoots() {
        super(Type.SPEED_BOOTS, Arrays.asList(
                "&aДают игроку, надевшему их, постоянный эффект",
                "&a&lСкорость II &aуровня"), Arrays.asList(Material.DIAMOND_BOOTS,Material.IRON_BOOTS));
    }

    @Override
    public int getChance() {
        return 50;
    }

    @Override
    public EnchantmentSet getEnchSet() {
        return null;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.ARMOR;
    }

}
