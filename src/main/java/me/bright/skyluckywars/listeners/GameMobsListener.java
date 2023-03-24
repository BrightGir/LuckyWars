package me.bright.skyluckywars.listeners;

import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.game.events.mobs.BlazeTargetEvent;
import me.bright.skyluckywars.game.events.mobs.CreeperTargetEvent;
import me.bright.skyluckywars.game.events.mobs.ZombieTargetEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class GameMobsListener implements Listener {

    private JavaPlugin plugin;

    public GameMobsListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onZombieTarget(ZombieTargetEvent event) {
        Entity entity = event.getBukkitEvent().getEntity();
        if(entity.hasMetadata(LInfo.MOB_OWNER.getKey()) &&event.getBukkitEvent().getTarget() != null &&
                entity.getMetadata(LInfo.MOB_OWNER.getKey()).get(0).asString().equalsIgnoreCase(event.getBukkitEvent().getTarget().getUniqueId().toString())) {
            event.getBukkitEvent().setCancelled(true);
        }
    }

    @EventHandler
    public void onBlazeTarget(BlazeTargetEvent event) {
        Entity entity = event.getBukkitEvent().getEntity();
        if(entity.hasMetadata(LInfo.MOB_OWNER.getKey()) &&event.getBukkitEvent().getTarget() != null &&
                entity.getMetadata(LInfo.MOB_OWNER.getKey()).get(0).asString().equalsIgnoreCase(event.getBukkitEvent().getTarget().getUniqueId().toString())) {
            event.getBukkitEvent().setCancelled(true);
        }
    }
    @EventHandler
    public void onCreeperTarget(CreeperTargetEvent event) {
        Entity entity = event.getBukkitEvent().getEntity();
        if(entity.hasMetadata(LInfo.MOB_OWNER.getKey()) && event.getBukkitEvent().getTarget() != null &&
                entity.getMetadata(LInfo.MOB_OWNER.getKey()).get(0).asString().equalsIgnoreCase(event.getBukkitEvent().getTarget().getUniqueId().toString())) {
            event.getBukkitEvent().setCancelled(true);
        }
    }

}
