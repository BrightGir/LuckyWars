package me.bright.skyluckywars.game.items.unqiue.eggs;

import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.game.Locationable;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class CreeperSpawnEgg extends LItem  {


    public CreeperSpawnEgg() {
        super(Type.CREEPER_SPAWN_EGG, Arrays.asList(
                "&6Способность:",
                "&aСпавнит моба, который будет воевать за своего хозяина,",
                "&aдогонять других игроков, при этом будет лоялен к хозяину"), Material.CREEPER_SPAWN_EGG);
        this.setClickAction(event -> {
            // SPAWN CREEPER
            if(event.getClickedBlock() != null) {
                Location location = event.getClickedBlock().getLocation().add(0,2,0);
                JavaPlugin plugin = SPlayer.getPlayer(event.getPlayer()).getGame().getArena().getPlugin();
                Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER, CreatureSpawnEvent.SpawnReason.CUSTOM);
              //  creeper.setCustomName(Messenger.color(Type.CREEPER.getDpName()));
                creeper.setPowered(true);
                creeper.setCustomName(event.getPlayer().getName());
                creeper.setCustomNameVisible(true);
                creeper.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50D);
                creeper.setHealth(50D);
                creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
                creeper.setMetadata(LInfo.MOB_OWNER.getKey(),
                        new FixedMetadataValue(plugin,event.getPlayer().getUniqueId().toString()));
                decreaseAmountItemInHand(event.getPlayer());
            }
         //   event.setCancelled(true);
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
