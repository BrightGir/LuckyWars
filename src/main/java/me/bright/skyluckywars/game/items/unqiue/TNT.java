package me.bright.skyluckywars.game.items.unqiue;

import me.bright.skylib.game.Game;
import me.bright.skylib.utils.ItemBuilder;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class TNT extends LItem {
    public TNT() {
        super(Type.TNT, null,Material.TNT,2,4);
        setClickAction(event -> {
            Player p = event.getPlayer();
           // p.getInventory().removeItem(new ItemStack(Material.TNT, 1));
        //    Bukkit.getLogger().info("click");
            if(event.getClickedBlock() != null) {
                Vector v = event.getClickedBlock().getLocation().getDirection();
                TNTPrimed tnt = (TNTPrimed) p.getWorld()
                        .spawnEntity(event.getClickedBlock().getLocation().clone().add(v.getX(), v.getY()+1, v.getZ()), EntityType.PRIMED_TNT);
                //   tnt.setVelocity(v);
                //   TNTPrimed tnt = (TNTPrimed) p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
                //   tnt.setVelocity(p.getLocation().toVector().normalize().multiply(2));

                decreaseAmountItemInHand(p);
                event.setCancelled(true);
            }
        });
        setGlowing(true);
    }

    @Override
    public int getChance() {
        return 20;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }



}
