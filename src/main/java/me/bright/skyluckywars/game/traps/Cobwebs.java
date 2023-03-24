package me.bright.skyluckywars.game.traps;

import me.bright.skylib.utils.Messenger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cobwebs extends Trap {

    public Cobwebs() {
        setTrapAction((loc,sp) -> {
            Location ploc = sp.getPlayer().getLocation();
            List<Block> blcks = getBlockByRadius(ploc,1,2,1);
            blcks.forEach(b -> {b.setType(Material.COBWEB);});
        // //   Bukkit.getLogger().info("size = " + blcks.size());
        //    Collections.shuffle(blcks);
        //    ploc.getBlock().setType(Material.COBWEB);
        //    for(int i = 0; i < 14; i++) {
        //        blcks.get(i).setType(Material.COBWEB);
        //    }
            sp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20 * Messenger.rnd(4,8),
                    0,false,false));
        });

    }

    private List<Block> getBlockByRadius(Location center, int radiusX, int dy, int radiusZ) {
        List<Block> blocks = new ArrayList<>();
        int i = 0;
        while(i < dy) {
            for (int dx = -radiusX; dx <= radiusX; dx++) {
                for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                    Block b = center.clone().add(dx, i, dz).getBlock();
                    blocks.add(b);
                }
            }
            i++;
        }
        return blocks;
    }
}
