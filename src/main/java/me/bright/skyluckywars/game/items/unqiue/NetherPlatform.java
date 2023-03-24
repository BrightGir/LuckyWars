package me.bright.skyluckywars.game.items.unqiue;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NetherPlatform extends LItem {
    public NetherPlatform() {
        super(Type.NETHER_PLATFORM, Arrays.asList("&bУникальный предмет"),Material.NETHER_STAR);
        setClickAction(event -> {
            Player p = event.getPlayer();
            Location bLoc = p.getLocation().clone().add(0,-1,0);
            Material m = getRandomGlassMaterial();
            for(int x = -2; x <= 2; x++) {
                for(int z = -2; z <= 2; z++) {
                    bLoc.clone().add(x,0,z).getBlock().setType(m);
                }
            }
            decreaseAmountItemInHand(p);
        });
        setGlowing(true);
    }

    private Material getRandomGlassMaterial() {
        List<Material> list = new ArrayList<>();
        list.add(Material.GREEN_STAINED_GLASS);
        list.add(Material.LIGHT_GRAY_STAINED_GLASS);
        list.add(Material.GREEN_STAINED_GLASS);
        list.add(Material.PURPLE_STAINED_GLASS);
        list.add(Material.BLACK_STAINED_GLASS);
        list.add(Material.CYAN_STAINED_GLASS);
        list.add(Material.YELLOW_STAINED_GLASS);
        list.add(Material.LIME_STAINED_GLASS);
        list.add(Material.MAGENTA_STAINED_GLASS);
        list.add(Material.LIGHT_BLUE_STAINED_GLASS);
        list.add(Material.BLUE_STAINED_GLASS);
        list.add(Material.BROWN_STAINED_GLASS);
        list.add(Material.BLACK_STAINED_GLASS);
        list.add(Material.ORANGE_STAINED_GLASS);
        return list.get(new Random().nextInt(list.size()));
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
