package me.bright.skyluckywars.utils;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Scheduler extends BukkitRunnable {
    Plugin plugin;

    Scheduler(Plugin main) {
        this.plugin = main;
    }

    @Override
    public void run() {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player player : players) {
            for (Player p : players) {
                double d = player.getLocation().distance(p.getLocation());
                if (d < 50.0) {
                    player.showPlayer(this.plugin, p);
                } else {
                    player.hidePlayer(this.plugin, p);
                }
            }
        }
    }
}