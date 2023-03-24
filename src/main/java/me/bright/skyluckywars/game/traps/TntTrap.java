package me.bright.skyluckywars.game.traps;

import me.bright.skylib.utils.ItemBuilder;
import me.bright.skylib.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TntTrap extends Trap {

    public TntTrap() {
        setTrapAction((loc, sp) -> {
            int r = Messenger.rnd(4,8);
            List<Integer> dxs = Arrays.asList(0,0,1,1,1,-1,-1,-1);
            List<Integer> dzs = Arrays.asList(1,-1,0,1,-1,0,1,-1);
            int i = 0;
            while(i < r) {
                Vector v = loc.getDirection();
                TNTPrimed tnt = (TNTPrimed) loc.getWorld()
                        .spawnEntity(loc.clone().add(v.getX()+dxs.get(i), v.getY() + 1, v.getZ()+dzs.get(i)), EntityType.PRIMED_TNT);
                tnt.setFuseTicks(20*3);
                i++;
            }
        });

    }

    private void spawnTnt(Location loc) {
        Vector v = loc.getDirection();
        loc.getWorld()
                  .spawnEntity(loc.clone().add(v.getX(), v.getY(), v.getZ()), EntityType.PRIMED_TNT);
    }

    private void setBlock(Location loc, Material mat) {
        //  if(loc.getBlock().getType() == Material.AIR) {
        loc.getBlock().setType(mat);
        //}
    }
}
