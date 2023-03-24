package me.bright.skyluckywars.game.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class MagicPotionEvent extends Event implements Cancellable {
    private Collection<LivingEntity> splashedEntities;
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled;

    public MagicPotionEvent(Collection<LivingEntity> splashedEntities) {
        this.splashedEntities = splashedEntities;
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
