package me.bright.skyluckywars.game.items.unqiue.potions;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Arrays;

public class NegativePotion extends LItem {

    public NegativePotion() {
        super(Type.NEGATIVE_POTION,Arrays.asList(
                " ",
                "&7Может дать только",
                "&7плохие эффекты"), Material.SPLASH_POTION);
        setGenerateAction(item -> {
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            meta.setColor(Color.fromRGB(Integer.parseInt("696969",16)));
            item.setItemMeta(meta);
        });
        setGlowing(true);
    }

    @Override
    public int getChance() {
        return 10;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }

}
