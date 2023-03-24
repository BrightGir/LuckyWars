package me.bright.skyluckywars.game.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class PoisonFlaskEvent extends Event implements Cancellable {

    private Collection<LivingEntity> splashedEntities;
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled;
    private Player player;

    public PoisonFlaskEvent(Collection<LivingEntity> splashedEntities, Player player) {
        this.splashedEntities = splashedEntities;
        this.isCancelled = false;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public Collection<LivingEntity> getSplashedEntities() {
        return splashedEntities;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
