package me.bright.skyluckywars.game.events;


import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityShootBowEvent;

public class CatBowShootEvent extends Event implements Cancellable {

    private Player player;
    private EntityShootBowEvent event;
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled;

    public CatBowShootEvent(Player player, EntityShootBowEvent shoowBowEvent) {
        this.player = player;
        this.event = shoowBowEvent;
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

    public EntityShootBowEvent getShootBowEvent() {
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
