package me.bright.skyluckywars.game.items.unqiue;

import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class SlimePlatform extends LItem {
    public SlimePlatform() {
        super(Type.SLIME_PLATFORM, Arrays.asList(
                " ",
                "&7Создает платформу, которая",
                "&7поможет вам в экстренной",
                "&7ситуации"),Material.SLIME_BALL);
        this.setClickAction(event -> {
            Player p = event.getPlayer();
            Location bLoc = p.getLocation().clone();
            Location loc = p.getLocation();
            Vector vec = loc.getDirection().clone().add(new Vector(0, 50, 0));
            Location playerLocation = p.getLocation();

            Material m = Material.SLIME_BLOCK;
            boolean existBlocks = false;
            int addY = 0;
            for(int x = -1; x <= 1; x++) {
                for(int z = -1; z <= 1; z++) {
                    for(int dy = -2; dy <= 0; dy++) {
                        if (bLoc.clone().add(x, dy, z).getBlock().getType() != Material.AIR) {
                            existBlocks = true;
                        }
                    }
                }
            }

            if(!existBlocks) {
                addY = -2;
            }
            for(int x = -1; x <= 1; x++) {
                for(int z = -1; z <= 1; z++) {
                    Location bnewLoc = bLoc.clone().add(x,addY,z);
                    bnewLoc.getBlock().setType(Material.SLIME_BLOCK);
                    bnewLoc.getBlock().getState().update(true);
                }
            }
           // p.setVelocity(new Vector(0,50,0));
            p.teleport(p.getLocation().clone().add(0,1,0));
            p.setVelocity(loc.getDirection().clone().add(new Vector(0, 50, 0)));
            decreaseAmountItemInHand(p);

        });
        setGlowing(true);
    }

    @Override
    public int getChance() {
        return 15;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.HELPING;
    }
}
