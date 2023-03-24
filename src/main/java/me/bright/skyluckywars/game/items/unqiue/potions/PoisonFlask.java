package me.bright.skyluckywars.game.items.unqiue.potions;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Arrays;
import java.util.List;

public class PoisonFlask extends LItem {
    public PoisonFlask() {
        super(Type.POISON_FLASK, Arrays.asList("&fСпособность:",
                "Моментальный урон 5 уровня игрокам,",
                "попавшим под его действие"), Material.SPLASH_POTION);
        setGenerateAction(item -> {
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            meta.setColor(Color.fromRGB(Integer.parseInt("8B0000",16)));
            item.setItemMeta(meta);
        });
        setGlowing(true);

    }

    @Override
    public int getChance() {
        return 5;
       // return 100;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }

}
