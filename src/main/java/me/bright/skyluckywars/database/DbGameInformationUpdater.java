package me.bright.skyluckywars.database;

import me.bright.skyluckywars.game.LGame;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class DbGameInformationUpdater {


    private LGame game;
    private BukkitTask updater;
    public DbGameInformationUpdater(LGame game) {
        this.game = game;
    }

    public void start() {
        if(updater == null || (updater != null && updater.isCancelled())) {
            updater = new BukkitRunnable() {
                @Override
                public void run() {
                    game.getPlugin().getGameMySQL().updateInformation(game);
                }
            }.runTaskTimerAsynchronously(game.getPlugin(),0,30L);
        }
    }

    public void stop() {
        if(updater != null && !updater.isCancelled()) {
            updater.cancel();
        }
    }
}
