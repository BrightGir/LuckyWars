package me.bright.skyluckywars.game.items.armor;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.utils.EnchantmentSet;
import org.bukkit.Material;

import java.util.Arrays;

public class GuardianBoots extends LItem implements Enchantable {

    public GuardianBoots() {
        super(Type.GUARDIAN_BOOTS, Arrays.asList(
                "&aДают игроку, надевшему их, постоянный эффект",
                "&a&lЗащита от урона I&a, при этом накладывая эффект",
                "ав"), Arrays.asList(Material.DIAMOND_BOOTS,Material.IRON_BOOTS));
    }

    @Override
    public int getChance() {
        return 50;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.ARMOR;
    }

    @Override
    public EnchantmentSet getEnchSet() {
        return null;
    }

}
