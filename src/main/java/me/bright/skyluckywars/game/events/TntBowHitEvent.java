package me.bright.skyluckywars.game.events;


import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.ProjectileHitEvent;

public class TntBowHitEvent extends Event implements Cancellable {

    private Player player;
    private ProjectileHitEvent event;
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled;

    public TntBowHitEvent(Player player, ProjectileHitEvent bowHitEvent) {
        this.player = player;
        this.event = bowHitEvent;
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

    public Player getPlayer() {
        return player;
    }

    public ProjectileHitEvent getHitBowEvent() {
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
