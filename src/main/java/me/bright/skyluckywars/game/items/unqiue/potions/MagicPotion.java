package me.bright.skyluckywars.game.items.unqiue.potions;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Arrays;
import java.util.List;

public class MagicPotion extends LItem {
    public MagicPotion() {
        super(Type.MAGIC_POTION, Arrays.asList(
                " ",
                "&7Может дать как хорошие,",
                "&7так и плохие эффекты"), Material.SPLASH_POTION);
        setGenerateAction(item -> {
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            meta.setColor(Color.fromRGB(Integer.parseInt("EE82EE",16)));
            item.setItemMeta(meta);
        });
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
