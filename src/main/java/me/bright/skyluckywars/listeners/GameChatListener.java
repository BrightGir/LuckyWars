package me.bright.skyluckywars.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.LuckyWars;
import net.kyori.adventure.text.Component;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
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
        Player player = event.getPlayer();
        SPlayer sp = SPlayer.getPlayer(event.getPlayer());
        UserManager uManager = plugin.getLuckpermsApi().getUserManager();
        User tu =
                (uManager.isLoaded(player.getUniqueId()) ? uManager.getUser(player.getUniqueId())
                        : uManager.loadUser(player.getUniqueId()).join());
        String prefix = (tu.getCachedData().getMetaData().getPrefix() == null) ? "" : tu.getCachedData().getMetaData().getPrefix();
        if(sp.getGame() != null) {
            Component finalMessage = null;
            if(!sp.isSpectator()) {
                finalMessage  = Component.text(prefix +
                        player.getName() + Messenger.color("&7: &f")).append(
                        message);
                for(Player p: sp.getGame().getWorld().getPlayers()) {
                    p.sendMessage(finalMessage);
                }
            } else {
                finalMessage  = Component.text(prefix+
                        player.getName() + Messenger.color(" &7(Spec): &f"))
                        .append(message);
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
