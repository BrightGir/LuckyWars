package me.bright.skyluckywars.configs;

import me.bright.skylib.configs.SConfig;
import me.bright.skylib.game.GameMode;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SettingsConfig extends SConfig {

    public SettingsConfig(JavaPlugin plugin) {
        super(plugin.getDataFolder(), "settings");
    }

    @Override
    protected void values() {
        dataConfig("settings.db.dbname","luckywars");
        dataConfig("settings.lobby_server_name","lobby");
        dataConfig("settings.db.host","localhost");
        dataConfig("settings.db.port",3306);
        dataConfig("settings.db.user","root");
        dataConfig("settings.db.password","root");
        dataConfig("settings.db.lwtable","lw_games");
        dataConfig("settings.db.lwinfotable","lw_info");
    }
}
