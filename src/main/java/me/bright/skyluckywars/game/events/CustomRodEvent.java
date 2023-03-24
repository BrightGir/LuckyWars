package me.bright.skyluckywars.game.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Collection;

public class CustomRodEvent extends Event implements Cancellable {
    private PlayerFishEvent event;
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled;

    public CustomRodEvent(PlayerFishEvent event) {
        this.isCancelled = false;
        this.event = event;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public PlayerFishEvent getFishEvent() {
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
