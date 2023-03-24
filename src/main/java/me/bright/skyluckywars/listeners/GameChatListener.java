package me.bright.skyluckywars.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.LuckyWars;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class GameChatListener implements Listener {

    private LuckyWars plugin;

    public GameChatListener(LuckyWars plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Component message = event.message();
        SPlayer sp = SPlayer.getPlayer(event.getPlayer());
        if(sp.getGame() != null) {
            Component finalMessage = null;
            if(!sp.isSpectator()) {
                finalMessage  = Component.text(
                        event.getPlayer().getName() + Messenger.color("&7: &f")).append(
                        message);
                for(Player p: sp.getGame().getWorld().getPlayers()) {
                    p.sendMessage(finalMessage);
                }
            } else {
                finalMessage  = Component.text(
                        event.getPlayer().getName() + Messenger.color(" &7(Spec): &f")).append(
                        message);
                for(UUID uuid: sp.getGame().getSpectators()) {
                    Player p = Bukkit.getPlayer(uuid);
                    if(p != null) {
                        p.sendMessage(finalMessage);
                    }
                }
            }
        }
        event.setCancelled(true);
    }
}
