package me.bright.skyluckywars.game.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CustomPearlEvent  extends Event implements Cancellable {

    private Player player;
    private Projectile pearlProjectile;
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled;

    public CustomPearlEvent(Player player, Projectile pearlProjectile) {
        this.player = player;
        this.pearlProjectile = pearlProjectile;
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

    public Projectile getPearl() {
        return pearlProjectile;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
