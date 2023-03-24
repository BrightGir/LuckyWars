package me.bright.skyluckywars.listeners;

import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.game.events.CatBowHitEvent;
import me.bright.skyluckywars.game.events.CatBowShootEvent;
import me.bright.skyluckywars.game.events.SpiderBowHitEvent;
import me.bright.skyluckywars.game.events.TntBowHitEvent;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GameBowsListener implements Listener {

    private LuckyWars plugin;
    private Random rand;


    public GameBowsListener(LuckyWars plugin) {
        this.plugin = plugin;
        this.rand = new Random();
    }

    @EventHandler
    public void onCatBowShootEvent(CatBowShootEvent event) {
        int chance = getRnd(5,10);
        if(!luck(chance))return;
        Location loc = event.getShootBowEvent().getProjectile().getLocation();
        Entity catEntity = loc.getWorld().spawnEntity(loc, EntityType.CAT);
        event.getShootBowEvent().getProjectile().addPassenger(catEntity);
    }


    @EventHandler
    public void onCatBowHitEvent(CatBowHitEvent event) {
        Location loc = event.getHitBowEvent().getEntity().getLocation();
        Projectile projectile = event.getHitBowEvent().getEntity();
        if(projectile.getPassengers().size() != 0) {
            loc.getWorld().createExplosion(projectile.getPassengers().get(0),2);
        }
    }

    @EventHandler
    public void onTntBowHitEvent(TntBowHitEvent event) {
        int chance = getRnd(5,25);
        if(!luck(chance)) return;
        World world =  event.getHitBowEvent().getEntity().getLocation().getWorld();
        world.spawnEntity(event.getHitBowEvent().getEntity().getLocation(), EntityType.PRIMED_TNT);
    }

    @EventHandler
    public void onSpiderBowHit(SpiderBowHitEvent event) {
        //Location loc = event.getHitBowEvent().getEntity().getLocation();
        if(event.getHitBowEvent().getHitBlock() != null) {
            BlockFace face = event.getHitBowEvent().getHitBlockFace();
            Location blockLoc = null;
            if(face == BlockFace.UP) {
                blockLoc = event.getHitBowEvent().getHitBlock().getLocation();
                blockLoc.add(0,1,0);
            } else if(face == BlockFace.EAST) {
                blockLoc = event.getHitBowEvent().getHitBlock().getLocation();
                blockLoc.add(1,0,0);
            } else if(face == BlockFace.DOWN) {
                blockLoc = event.getHitBowEvent().getHitBlock().getLocation();
                blockLoc.add(0,-1,0);
            } else if(face == BlockFace.WEST) {
                blockLoc = event.getHitBowEvent().getHitBlock().getLocation();
                blockLoc.add(-1,0,0);
            } else if(face == BlockFace.SOUTH) {
                blockLoc = event.getHitBowEvent().getHitBlock().getLocation();
                blockLoc.add(0,0,1);
            } else if(face == BlockFace.NORTH) {
                blockLoc = event.getHitBowEvent().getHitBlock().getLocation();
                blockLoc.add(0,0,-1);
            }
            if(blockLoc != null) {
                blockLoc.getWorld().getBlockAt(blockLoc).setType(Material.COBWEB);
            }

        } else if(event.getHitBowEvent().getHitEntity() != null) {
            Block mid = event.getHitBowEvent().getHitEntity().getLocation().getBlock();
            World world = mid.getWorld();
            for(int dx = -1; dx <= 1; dx++) {
                for(int dy = -1; dy <= 1; dy++) {
                    for(int dz = -1; dz <= 1; dz++) {
                        Block b = world.getBlockAt(mid.getLocation().clone().add(dx,dy,dz));
                        if(b.getType() == Material.AIR) {
                            b.setType(Material.COBWEB);
                        }
                    }
                }
            }

        }
    }


    private boolean luck(int chance) {
        return getRnd(1,100) <= chance;
    }

    private int getRnd(int min, int max) {
        return rand.nextInt(max-min+1)+min;
    }
}
