package me.bright.skyluckywars;



import com.grinderwolf.swm.api.SlimePlugin;

import me.bright.skylib.Arena;
import me.bright.skylib.game.GameMode;
import me.bright.skylib.utils.ItemBuilder;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.commands.ForceStartCommand;
import me.bright.skyluckywars.commands.GetLuckyBlockCommand;
import me.bright.skyluckywars.configs.GamesConfig;
import me.bright.skyluckywars.configs.SettingsConfig;
import me.bright.skyluckywars.database.DbGameInformationUpdater;
import me.bright.skyluckywars.database.GameConnectorMySQL;
import me.bright.skyluckywars.database.GameInfoMySQL;
import me.bright.skyluckywars.game.LGame;
import me.bright.skyluckywars.listeners.*;
import me.bright.skyluckywars.utils.Constants;
import me.bright.skyluckywars.utils.armor.ArmorListener;
import me.bright.skyluckywars.utils.glowing.Glow;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class LuckyWars extends JavaPlugin {

    private SettingsConfig settingsConfig;
    private GamesConfig gamesConfig;
  //  private MVWorldManager wmanager;
    private GameConnectorMySQL db;
    private GameInfoMySQL dbinfo;
    private List<LGame> games;
    private String serverLobbyName;
    private SlimePlugin slimePlugin;
    private LuckPerms luckperms;

    @Override
    public void onEnable() {
        games = new ArrayList<>();
        load();
        setupLuckPerms();
        loadGames();
        this.getServer().getPluginManager().registerEvents(new TechnicGameListener(this),this);
        this.getServer().getPluginManager().registerEvents(new ActiveGameListener(this),this);
        this.getServer().getPluginManager().registerEvents(new ItemActionListener(this),this);
        this.getServer().getPluginManager().registerEvents(new GameArmorListener(this),this);
        this.getServer().getPluginManager().registerEvents(new GameBowsListener(this),this);
        this.getServer().getPluginManager().registerEvents(new ArmorListener(new ArrayList<>()),this);
        this.getServer().getPluginManager().registerEvents(new GameMobsListener(this),this);
        this.getServer().getPluginManager().registerEvents(new SpectatorModeListener(this),this);
        this.getServer().getPluginManager().registerEvents(new GameChatListener(this),this);
        getCommand("getluckyblock").setExecutor(new GetLuckyBlockCommand(this));
        getCommand("forcestart").setExecutor(new ForceStartCommand());
        Constants.LUCKY_BLOCK_NAME = this.getGamesConfig().getString("blockname");
     //   setupLuckPerms();

        registerGlow();
    }

    public void setupLuckPerms() {
       RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
       if (provider != null) {
           luckperms = provider.getProvider();
       }
    //    luckperms = LuckPermsProvider.get();
    }

    public LuckPerms getLuckpermsApi() {
        return luckperms;
    }

    private void importWorld(String name) {
   //     Bukkit.unloadWorld(name,false);

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String path = Bukkit.getWorldContainer() + File.separator + name;
                    slimePlugin.importWorld(new File(path),name,slimePlugin.getLoader("file"));
                   // Bukkit.getLogger().info("IMPORTED, PATH = " + path);
                } catch(Exception e) {
                    e.printStackTrace();
                 //   Bukkit.getLogger().info("NOT IMPORTED, PATH = ");
                }
            }
        }.runTaskAsynchronously(this);
    }

    public ItemStack getLuckyBlock() {
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setName(Messenger.color(this.getGamesConfig().getString("blockname")))
                .create();
    }

    public String getLuckyblockName() {
        return Messenger.color(this.getGamesConfig().getString("blockname"));
    }

    public FileConfiguration getGamesConfig() {
        return gamesConfig.getConfig();
    }

    private void load() {
        settingsConfig = new SettingsConfig(this);
        gamesConfig = new GamesConfig(this);
        try {
            db = new GameConnectorMySQL(settingsConfig.getConfig());
            dbinfo = new GameInfoMySQL(settingsConfig.getConfig());
            db.loadDb();
            dbinfo.loadDb();
            if (db.getConnection() == null) {
                Bukkit.getLogger().warning("[LuckyWars] Database didn't load");
            } else {
                Bukkit.getLogger().info("[LuckyWars] Database has loaded");
            }
        } catch (Exception e) {
            Bukkit.getLogger().info("[LuckyWars] Database didn't loaded");
        }
        serverLobbyName = settingsConfig.getConfig().getString("settings.lobby_server_name");
      //  slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
     //   wmanager = ((MultiverseCore)Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core")).getMVWorldManager();
    }

    public SlimePlugin getSlimePlugin() {
        return slimePlugin;
    }

    public GameConnectorMySQL getGameMySQL() {
        return db;
    }

    public GameInfoMySQL getGameInfoMysql() {return dbinfo; }

  //  public MVWorldManager getMWManager() {
  //      return wmanager;
  //  }

    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(this);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void redirectToLobby(Player player) {
        Messenger.redirect(this,player,serverLobbyName);
    }

    private void loadGames() {
        Arena arena = new Arena(this);
        arena.setServerLobbyName(serverLobbyName);
        ConfigurationSection gamesSec = gamesConfig.getConfig().getConfigurationSection("games");
        Iterator<String> it = gamesSec.getKeys(false).iterator();
     //   String lobbyWorldName = gamesConfig.getConfig().getString("lobby_world_name");
   //     arena.setLobbyWorldName(lobbyWorldName);
        while(it.hasNext()) {
            ConfigurationSection gameSec = gamesSec.getConfigurationSection(it.next());
            String worldName = gameSec.getString("worldname");
           // new WorldCreator(worldName).environment(World.Environment.NORMAL).createWorld();
            Bukkit.getLogger().info("Load world, worldName = " + ((worldName == null) ? "null" : worldName));
            Bukkit.createWorld(new WorldCreator(worldName));
        //    Bukkit.unloadWorld(worldName,false);
          // Bukkit.createWorld(new WorldCreator(this.getDataFolder() + File.separator + "maps" + File.separator + worldName));
            //Bukkit.createWorld(new WorldCreator(worldName).environment(World.Environment.NORMAL));
            //World world = new WorldCreator(worldName).environment(World.Environment.NORMAL).createWorld();
          //  Bukkit.getWorlds().add(world);
            int maxPlayers = gameSec.getInt("max_players");
            int teamSize = gameSec.getInt("team_size");
            GameMode mode = GameMode.valueOf(gameSec.getString("mode"));
            String mapName = gameSec.getString("map_name");
            LGame game = new LGame(Bukkit.getWorld(worldName),arena,maxPlayers,teamSize,gameSec.getName(),mode,this,mapName);


          //  Bukkit.getLogger().info(String.valueOf("world == null + " + game.getWorld() == null));

            for(String sloc: gameSec.getStringList("islands_locations")) {
                String[] loc = sloc.split(", ");
                game.addIslandsLocation(Double.parseDouble(loc[0]),Double.parseDouble(loc[1]),Double.parseDouble(loc[2]),
                        Float.parseFloat(loc[3]),Float.parseFloat(loc[4]));
            }
            double borderCenterX = gameSec.getDouble("border_center_x");
            double borderCenterZ = gameSec.getDouble("border_center_z");
            double borderSize = gameSec.getDouble("border_size");
            double deathMatchBorderSize = gameSec.getDouble("deathmatch_border_size");
            String[] schematiclLocString = gameSec.getString("schematic_location").split(", ");

           game.setSchematicFile(new File(this.getDataFolder() + File.separator +
                   gameSec.getString("schematic_file_path").replace("&",File.separator)));
            game.setSchematicLoadLocation(Integer.parseInt(schematiclLocString[0]),Integer.parseInt(schematiclLocString[1]),Integer.parseInt(schematiclLocString[2]));
            game.setBorderCenter(borderCenterX,borderCenterZ);
            game.setBorderSize(borderSize);
            game.setDeathMathBorderSize(deathMatchBorderSize);
            game.setGameEndSeconds(gameSec.getInt("gameend_seconds"));
            game.setDeathMatchSeconds(gameSec.getInt("deathmatch_seconds"));
            String[] loc = gameSec.getString("spectator_spawn").split(", ");
            game.setSpectatorSpawn(Double.parseDouble(loc[0]),Double.parseDouble(loc[1]),Double.parseDouble(loc[2]),
                    Float.parseFloat(loc[3]),Float.parseFloat(loc[4]));


          //  String[] dloc = gameSec.getString("death_match_location").split(", ");
            game.setDeathMathLocations(gameSec.getStringList("death_match_locations"));


            int minPlayersToStart = gameSec.getInt("min_start");
            int minPlayersToForceStart = gameSec.getInt("min_forcestart");
            game.setMinPlayersToStart(minPlayersToStart);
            game.setMinPlayersToForcestart(minPlayersToForceStart);
            game.getTeamManager().registerTeams(game.getMaxPlayers()/game.getTeamSize());
            String[] lobbyLoc = gameSec.getString("lobby_spawn_location").split(", ");
            game.setLobbyLocation(Double.parseDouble(lobbyLoc[0]),Double.parseDouble(lobbyLoc[1]),Double.parseDouble(lobbyLoc[2]),
                    Float.parseFloat(lobbyLoc[3]),Float.parseFloat(lobbyLoc[4]));
            // game.backupWorld();
        //    importWorld(worldName);
            game.setMapname(mapName);
            arena.loadGame(game);
            try {
                db.insertGame(game);
                if(db != null && db.getConnection() != null) {
                    new DbGameInformationUpdater(game).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            games.add(game);
        }
    }

    public List<LGame> getGames() {
        return games;
    }

    @Override
    public void onDisable() {

    }
}
