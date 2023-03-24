package me.bright.skyluckywars.game.events.mobs;


import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class ZombieTargetEvent extends Event implements Cancellable {

    private EntityTargetLivingEntityEvent event;
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled;

    public ZombieTargetEvent(EntityTargetLivingEntityEvent event) {

        this.event = event;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public EntityTargetLivingEntityEvent getBukkitEvent() {
        return event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
