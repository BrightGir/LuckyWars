package me.bright.skyluckywars.game.items.unqiue.eggs;

import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.Cooldown;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.game.Locationable;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class BlazeSpawnEgg extends LItem {

    public BlazeSpawnEgg() {
        super(Type.BLAZE_SPAWN_EGG, Arrays.asList(
                "&6Способность:",
                "&aСпавнит моба, который будет воевать за своего хозяина,",
                "&aдогонять других игроков, при этом будет лоялен к хозяину"), Material.BLAZE_SPAWN_EGG);
        this.setClickAction(event -> {
                // SPAWN BLAZE
            if(event.getClickedBlock() != null) {
                Location location = event.getClickedBlock().getLocation().add(0,2,0);
                JavaPlugin plugin = SPlayer.getPlayer(event.getPlayer()).getGame().getArena().getPlugin();
                Blaze blaze = (Blaze) location.getWorld().spawnEntity(location, EntityType.BLAZE, CreatureSpawnEvent.SpawnReason.CUSTOM);
             //   blaze.setCustomName(Messenger.color(Type.BLAZE.getDpName()));
                blaze.setCustomName(event.getPlayer().getName());
                blaze.setCustomNameVisible(true);
                blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50D);
                blaze.setHealth(50D);
                blaze.setMetadata(LInfo.MOB_OWNER.getKey(),
                        new FixedMetadataValue(plugin,event.getPlayer().getUniqueId().toString()));
                Cooldown cooldown = new Cooldown(plugin,5);;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(blaze == null || blaze.isDead()) this.cancel();
                        if(blaze.getTarget() != null && cooldown.isExpire()) {

                            Vector v = blaze.getLocation().getDirection().multiply(1.8f);
                            Fireball ball = (Fireball)
                                    blaze.getWorld().spawnEntity( blaze.getLocation().clone().add(v.getX(),v.getY(),v.getZ()), EntityType.FIREBALL);
                            ball.setVelocity(v);

                       //     fireball.setVelocity(fireball.getVelocity().multiply(1.5D));
                            cooldown.reset();
                        }
                    }
                }.runTaskTimer(plugin,0, 20L);
//                event.setCancelled(true);
                decreaseAmountItemInHand(event.getPlayer());
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
        return DropSet.DropCategory.EGG;
    }

}
