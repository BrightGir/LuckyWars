package me.bright.skyluckywars.game.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Collection;

public class SwordSoulKill extends Event implements Cancellable {

    private Collection<LivingEntity> splashedEntities;
    private static final HandlerList handlers = new HandlerList();
    private Player killer;
    private Player victim;
    private boolean isCancelled;

    public SwordSoulKill(Player killer, Player victim) {
        this.killer = killer;
        this.victim = victim;
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

    public Player getKiller() {
        return killer;
    }

    public Player getVictim() {
        return victim;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
