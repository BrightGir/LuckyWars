package me.bright.skyluckywars.game.traps;

import me.bright.skylib.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LavaTrap extends Trap {


    public LavaTrap() {
        setTrapAction((loc,sp) -> {


            // Квадрат
            BlockData bdEastWest = Bukkit.createBlockData("minecraft:iron_bars[east=true, west=true]"); // What sides are attached to other blocks
            BlockData bdNorthSouth = Bukkit.createBlockData("minecraft:iron_bars[north=true, south=true]"); // What sides are attached to other blocks
            BlockData bdSouthWest = Bukkit.createBlockData("minecraft:iron_bars[south=true, west=true]"); // What sides are attached to other blocks
            BlockData bdEastNorth = Bukkit.createBlockData("minecraft:iron_bars[east=true, north=true]"); // What sides are attached to other blocks
            BlockData bdWestNorth = Bukkit.createBlockData("minecraft:iron_bars[west=true, north=true]"); // What sides are attached to other blocks
            BlockData bdSouthEast = Bukkit.createBlockData("minecraft:iron_bars[south=true, east=true]"); // What sides are attached to other blocks
    //        BlockData bd2 = Bukkit.createBlockData("minecraft:iron_bars[north=true, south=true"); // What sides are attached to other blocks
            Location loc0 = sp.getPlayer().getLocation().clone().add(0,-1,0);
            for (int dx = -1; dx <= 1; dx++) {
                for(int dz = -1; dz <= 1; dz++) {
                    Location curLoc = loc0.clone().add(dx,0,dz);
                    setBlock(curLoc,Material.STONE_BRICKS);
                }
            }
            List<Block> ironBars = new ArrayList<>();
            int dy = 1;
            while(dy <= 3) {
                Location barsLoc = loc0.clone().add(0,dy,0);
                barsLoc.getChunk().setForceLoaded(true);
                barsLoc.clone().add(0,0,-1).getBlock().setType(Material.IRON_BARS);
                barsLoc.clone().add(0,0,-1).getBlock().setBlockData(bdEastWest);
                barsLoc.clone().add(0,0,-1).getBlock().getState().update(true);
              //  Bukkit.getLogger().info("1 set");

                barsLoc.clone().add(1,0,-1).getBlock().setType(Material.IRON_BARS);
                barsLoc.clone().add(1,0,-1).getBlock().setBlockData(bdSouthWest);
                barsLoc.clone().add(0,0,-1).getBlock().getState().update(true);
             //   Bukkit.getLogger().info("2 set");

                barsLoc.clone().add(-1,0,-1).getBlock().setType(Material.IRON_BARS);
                barsLoc.clone().add(-1,0,-1).getBlock().setBlockData(bdSouthEast);
                barsLoc.clone().add(0,0,-1).getBlock().getState().update(true);
              //  Bukkit.getLogger().info("3 set");

                barsLoc.clone().add(0,0,1).getBlock().setType(Material.IRON_BARS);
                barsLoc.clone().add(0,0,1).getBlock().setBlockData(bdEastWest);
                barsLoc.clone().add(0,0,-1).getBlock().getState().update(true);
              //  Bukkit.getLogger().info("4 set");

                barsLoc.clone().add(1,0,1).getBlock().setType(Material.IRON_BARS);
                barsLoc.clone().add(1,0,1).getBlock().setBlockData(bdWestNorth);
                barsLoc.clone().add(0,0,-1).getBlock().getState().update(true);
               // Bukkit.getLogger().info("5 set");

                barsLoc.clone().add(-1,0,1).getBlock().setType(Material.IRON_BARS);
                barsLoc.clone().add(-1,0,1).getBlock().setBlockData(bdEastNorth);
                barsLoc.clone().add(0,0,-1).getBlock().getState().update(true);
              //  Bukkit.getLogger().info("6 set");

                barsLoc.clone().add(-1,0,0).getBlock().setType(Material.IRON_BARS);
                barsLoc.clone().add(-1,0,0).getBlock().setBlockData(bdNorthSouth);
                barsLoc.clone().add(0,0,-1).getBlock().getState().update(true);
             //   Bukkit.getLogger().info("7 set");

                barsLoc.clone().add(1,0,0).getBlock().setType(Material.IRON_BARS);
                barsLoc.clone().add(1,0,0).getBlock().setBlockData(bdNorthSouth);
                barsLoc.clone().add(0,0,-1).getBlock().getState().update(true);
            //    Bukkit.getLogger().info("8 set");
                dy++;
            }
            loc0.clone().add(0,3,0).getBlock().setType(Material.LAVA);
            loc0.clone().add(0,2,0).getBlock().setType(Material.AIR);
            loc0.clone().add(0,1,0).getBlock().setType(Material.AIR);
       //     ironBars.forEach(b -> b.getState().update(true));

        });


    }

    private void setBlock(Location loc, Material mat) {
      //  if(loc.getBlock().getType() == Material.AIR) {
            loc.getBlock().setType(mat);
            loc.getBlock().getState().update(true);
        //}
    }


}
