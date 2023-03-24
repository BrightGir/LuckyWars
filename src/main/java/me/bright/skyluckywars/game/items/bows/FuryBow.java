package me.bright.skyluckywars.game.items.bows;

import me.bright.skyluckywars.game.items.Enchantable;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import org.bukkit.Material;

import java.util.Arrays;

public class FuryBow extends LItem implements Enchantable {

    public FuryBow() {
        super(Type.FURY_BOW, Arrays.asList(
                " ",
                "&6Способность: ",
                "&aС шансом от 20 до 40% при попадании игрок может нанести",
                "&aкритический удар (Наносит урон в 1.5 - 2.5 раза больше)"), Material.BOW);
        setAttackAction(event -> {
            int chance = getRnd(20,40);
            if(luck(chance)) {
                int dmgInt = getRnd(15,25);
                double dmgBoost = (double)dmgInt/10;
                double curDmg = event.getDamage();
                event.setDamage(curDmg*dmgBoost);
            }
        });
        setGlowing(true);
    }


    @Override
    public EnchantmentSet getEnchSet() {
        return getDefaultEnchSet(DropSet.DropCategory.BOW);
    }

    @Override
    public int getChance() {
        return 5;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.BOW;
    }
}
