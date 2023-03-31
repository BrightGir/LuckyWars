package me.bright.skyluckywars.configs;

import me.bright.skylib.configs.SConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GamesConfig extends SConfig {

    public GamesConfig(JavaPlugin plugin) {
        super(plugin.getDataFolder(),"games");
    }

    @Override
    protected void values() {
        dataConfig("blockname","&aЛаки блок");
     //   dataConfig("lobby_world_name","lobby");
        dataConfig("games.luckywars-1.worldname","game1");
        dataConfig("games.luckywars-1.mode","SOLO");
        dataConfig("games.luckywars-1.max_players",8);
        dataConfig("games.luckywars-1.team_size",1);
        dataConfig("games.luckywars-1.min_start",2);
        dataConfig("games.luckywars-1.min_forcestart",5);
        dataConfig("games.luckywars-1.map_name","Спанчбоб");
        dataConfig("games.luckywars-1.lobby_spawn_location","-240, 48, 284, 0, 0");
        dataConfig("games.luckywars-1.spectator_spawn","-240, 48, 284, 0, 0");
        dataConfig("games.luckywars-1.schematic_location","-240, 20, 284");
        dataConfig("games.luckywars-1.schematic_file_path","schemas&game1.schem");
        dataConfig("games.luckywars-1.border_size",25);
        dataConfig("games.luckywars-1.deathmatch_border_size",10);
        dataConfig("games.luckywars-1.border_center_x",-280);
        dataConfig("games.luckywars-1.border_center_z",295);
        dataConfig("games.luckywars-1.gameend_seconds",300);
        dataConfig("games.luckywars-1.deathmatch_seconds",300);
        dataConfig("games.luckywars-1.islands_locations", Arrays.asList(
                "-280, 44, 295, 0, 0",
                "-282, 44, 274, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0"));
        dataConfig("games.luckywars-1.death_match_locations", Arrays.asList(
                "-280, 44, 295, 0, 0",
                "-282, 44, 274, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0",
                "-267, 44, 257, 0, 0"));
    }
}
