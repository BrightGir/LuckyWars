package me.bright.skyluckywars.game;

import com.fastasyncworldedit.core.regions.filter.CuboidRegionFilter;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.CorruptedWorldException;
import com.grinderwolf.swm.api.exceptions.NewerFormatException;
import com.grinderwolf.swm.api.exceptions.UnknownWorldException;
import com.grinderwolf.swm.api.exceptions.WorldInUseException;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.DataException;
import me.bright.skylib.Arena;
import me.bright.skylib.game.Game;
import me.bright.skylib.game.GameMode;
import me.bright.skylib.game.GameState;
import me.bright.skylib.game.states.ActiveState;
import me.bright.skylib.game.states.EndState;
import me.bright.skylib.game.states.WaitingState;
import me.bright.skylib.scoreboard.game.ActiveGameSkelet;
import me.bright.skylib.scoreboard.game.EndGameSkelet;
import me.bright.skylib.scoreboard.game.WaitingSkelet;
import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.game.states.LActiveState;
import me.bright.skyluckywars.game.states.LEndState;
import me.bright.skyluckywars.game.states.LWaitingState;
import me.bright.skyluckywars.listeners.scoreboards.LActiveGameScoreboard;
import me.bright.skyluckywars.listeners.scoreboards.LEndGameScoreboard;
import me.bright.skyluckywars.listeners.scoreboards.LWaitingScoreboard;
import me.bright.skyluckywars.utils.Constants;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LGame extends Game {

    private String name;
    private GameMode mode;
    private LuckyWars plugin;
    private Location lobbyLoc;
    private String mapName;
    private int minPlayersToStart;
    private int minPlayersToForcestart;
    private double borderCenterX;
    private double borderCenterZ;
    private double borderSize;
    private double deathMathBorderSize;
    private Location spectatorSpawn;
    private Location schematicLoadLocation;
    private List<Location> deathMathLocations;
    private Map<String,Integer> kills;
    private Map<String,Integer> lblocks;
    private int gameEndSeconds;
    private int deathMatchSeconds;
    private File schematicFile;
    private SlimeWorld slimeWorld = null;
    //private World lobbyWorld;


    public LGame(World world, Arena arena, int maxPlayers, int teamSize, String name, GameMode mode, LuckyWars plugin, String mapName) {
        super(arena, maxPlayers, teamSize,world);
        this.name = name;
        this.mapName = mapName;
        this.mode = mode;
        this.plugin = plugin;
        this.kills = new HashMap<>();
        this.lblocks = new HashMap<>();
        deathMathLocations = new ArrayList<>();
        getTeamManager().registerTeams(getMaxPlayers());
        setBroadCastPrefix(Constants.PREFIX);
        //Bukkit.getLogger().info("plugin == null " + String.valueOf(plugin == null));
  //      this.lobbyWorld = WorldUtils.copyWorld(new File(Bukkit.getWorldContainer(),
  //              arena.getLobbyWorldName()),arena.getLobbyWorldName() + "_" + getName());
      //  startGame();

    }

    public void setBorderCenter(double x, double z) {
        this.borderCenterX = x;
        this.borderCenterZ = z;

    }

    public void setSchematicFile(File file) {
        this.schematicFile = file;
    }

    public File getSchematicFile() {
        return schematicFile;
    }

    public int getGameEndSeconds() {
        return gameEndSeconds;
    }

    public void setGameEndSeconds(int gameEndSeconds) {
        this.gameEndSeconds = gameEndSeconds;
    }

    public int getDeathMatchSeconds() {
        return deathMatchSeconds;
    }

    public void setDeathMatchSeconds(int deathMatchSeconds) {
        this.deathMatchSeconds = deathMatchSeconds;
    }

    public Map<String,Integer> getMapKills() {
        return kills;
    }

    public void addKill(Player p) {
        int oldKills = kills.getOrDefault(p.getName(),0);
        kills.put(p.getName(),oldKills+1);
    }

    public void setKills(Player p, int killsCount) {
        kills.put(p.getName(),killsCount);
    }

    public int getKills(Player p) {
        return kills.getOrDefault(p.getName(),0);
    }

    public void addLBlock(Player p) {
        int oldKills = lblocks.getOrDefault(p.getName(),0);
        lblocks.put(p.getName(),oldKills+1);
    }

    public void setLBlocks(Player p, int blocks) {
        lblocks.put(p.getName(),0);
    }

    public int getLBlocks(Player p) {
        return lblocks.getOrDefault(p.getName(),0);
    }


    public List<Location> getDeathMathLocations() {
        deathMathLocations.forEach(loc -> {loc.setWorld(getWorld());});
        return deathMathLocations;
    }

    public void setDeathMathLocations(List<String> locations) {
        locations.forEach(stringLoc -> {
            String[] locs = stringLoc.split(", ");
            deathMathLocations.add(new Location(getWorld(), Double.parseDouble(locs[0]),Double.parseDouble(locs[1]),
                    Double.parseDouble(locs[2]),Float.parseFloat(locs[3]),Float.parseFloat(locs[4])));
        });
    }


    public void setBorderSize(double size) {
        this.borderSize= size;
    }

    public double getBorderSize() {
        return borderSize;
    }

    public double getDeathMatchBorderSize() {
        return deathMathBorderSize;
    }

    public void setDeathMathBorderSize(double deathMathBorderSize) {
        this.deathMathBorderSize = deathMathBorderSize;
    }

    public double getBorderCenterX() {
        return borderCenterX;
    }

    public double getBorderCenterZ() {
        return borderCenterZ;
    }
    public void setSpectatorSpawn(double x,double y,double z, float yaw, float pitch) {
        this.spectatorSpawn = new Location(getWorld(), x,y,z,yaw,pitch);
    }

    public Location getSpectatorSpawn() {
        spectatorSpawn.setWorld(getWorld());
        return spectatorSpawn;
    }

    public String getMapName() {
        return mapName;
    }

    public String getHostport() {
        String res = ((Bukkit.getIp() == null || Bukkit.getIp().isEmpty())) ? "localhost:" + Bukkit.getPort() : Bukkit.getIp() + ":" + Bukkit.getPort();
        return res;
    }

    public void setSchematicLoadLocation(int x, int y, int z) {
        this.schematicLoadLocation = new Location(getWorld(),x,y,z);
    }

    public Location getSchematicLocation() {
        return schematicLoadLocation;
    }

    public Location getLobbyLocation() {
        lobbyLoc.setWorld(getWorld());
        return lobbyLoc;
    }

    public void setLobbyLocation(double x, double y, double z, float yaw, float pitch) {
        lobbyLoc = new Location(getWorld(),x,y,z,yaw,pitch);
    }

    public LuckyWars getPlugin() {
        return plugin;
    }


    public GameMode getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public void setMinPlayersToStart(int minPlayersToStart) {
        this.minPlayersToStart = minPlayersToStart;
    }

    public void setMinPlayersToForcestart(int minPlayersToForcestart) {
        this.minPlayersToForcestart = minPlayersToForcestart;
    }

    @Override
    public int getMinPlayersToStartCounting() {
        return minPlayersToStart;
    }

    @Override
    public int getMinPlayersToSpeedStartCounting() {
        return minPlayersToForcestart;
    }

    @Override
    public WaitingState getWaitingState() {
        return new LWaitingState(this);
    }

    @Override
    public ActiveState getActiveState() {
        return new LActiveState(this);
    }

    @Override
    public EndState getEndState() {
        return new LEndState(this);
    }


    @Override
    public Class<? extends WaitingSkelet> getWaitingScoreboardSkeletClass() {
        return LWaitingScoreboard.class;
    }

    @Override
    public Class<? extends ActiveGameSkelet> getActiveGameScoreboardSkeletClass() {
        return LActiveGameScoreboard.class;
    }

    @Override
    public Class<? extends EndGameSkelet> getEndGameScoreboardSkeletClass() {
        return LEndGameScoreboard.class;
    }

    private void loadWorld() {
        SlimePropertyMap properties = new SlimePropertyMap();
        //   properties.setString(SlimeProperties.DIFFICULTY, Difficulty.NORMAL.name());
        properties.setInt(SlimeProperties.SPAWN_X, (int) getLobbyLocation().getX());
        properties.setInt(SlimeProperties.SPAWN_Y, (int) getLobbyLocation().getY() );
        properties.setInt(SlimeProperties.SPAWN_Z, (int) getLobbyLocation().getZ());
        SlimePlugin plugin = getPlugin().getSlimePlugin();
        SlimeLoader loader = plugin.getLoader("file");
        final SlimeWorld[] slimeWorld1 = new SlimeWorld[1];
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    slimeWorld1[0] = getPlugin().getSlimePlugin().loadWorld(loader, getWorld().getName(),false,properties);
                    slimeWorld = slimeWorld1[0];
                    plugin.generateWorld(slimeWorld);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getPlugin());
        //Bukkit.getLogger().info("XUI 3423423423");
     //   this.slimeWorld = slimeWorld1[0];
        if(slimeWorld == null) {
       //     Bukkit.getLogger().info("XUI 3423423424324234324233");
        }
    }

    @Override
    public void initGameWorld() {


        setOpen(false);
        // Bukkit.unloadWorld(getWorld().getName(),false);
        //loadWorld();

 //      if(slimeWorld == null) {
    //       loadWorld();
    //   }
//
    //    new BukkitRunnable() {
    //        @Override
    //        public void run() {
    //            if(slimeWorld == null) {
    //                Bukkit.getLogger().info("XUI 3423423424324234324233");
    //            }
    //            getPlugin().getSlimePlugin().generateWorld(LGame.this.slimeWorld);
    //
    //        }
    //    }.runTaskLater(getPlugin(),200L);

        // ZUI
        getWorld().getEntities().forEach(en -> {
            if(en instanceof LivingEntity && !(en instanceof Player)) {
                ((LivingEntity) en).damage(10000D);
            }
        });
    //  pasteSchem(getSchematicLocation(),getSchematicFile());
      pasteSchem1(getSchematicLocation(),getSchematicFile());
      Bukkit.getLogger().info("Schematic has been loaded!!!!");
      setState(GameState.WAITING);
      setOpen(true);



    }

    public void pasteFAWE(File file) {
        boolean allowUndo = true;

    }

    public void paste(Location location, File file) {

        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        Clipboard clipboard;

        BlockVector3 blockVector3 = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (clipboardFormat != null) {
            try (ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(file))) {

                if (location.getWorld() == null)
                    throw new NullPointerException("Failed to paste schematic due to world being null");

                EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(BukkitAdapter.adapt(location.getWorld())).build();

                clipboard = clipboardReader.read();

                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(blockVector3)
                        .ignoreAirBlocks(false)
                        .build();

                try {
                    Operations.complete(operation);
                    editSession.close();
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pasteSchem(Location location, File file) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), -1)) {
                    ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
                    ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(file));
                    Clipboard clipboard = clipboardReader.read();
                    Operation operation = new ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                            // configure here
                            .ignoreAirBlocks(false)
                            .build();
                    Operations.complete(operation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(getPlugin());
    }

    public void pasteSchem1(Location location, File file) {

        Bukkit.getScheduler().runTaskAsynchronously(
                getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), -1)) {
                            ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
                            ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(file));
                            Clipboard clipboard = clipboardReader.read();
                            Operation operation = new ClipboardHolder(clipboard)
                                    .createPaste(editSession)
                                    .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                                    // configure here
                                    .ignoreAirBlocks(false)
                                    .build();
                            Operations.complete(operation);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }




    // public static void restoreRegion(Location location, File file) {
//
   //     EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(location.getWorld());
   //   //  Vector origin = new Vector(region.getMinimumPoint().getBlockX(), region.getMinimumPoint().getBlockY(), region.getMinimumPoint().getBlockZ());
   //     File regionSchem = file;
//
   //     Bukkit.getScheduler().runTaskAsynchronously(
   //             getPlugin(), new Runnable() {
//
   //                 @Override
   //                 public void run() {
//
   //                     try {
   //                         CuboidClipboard cc = CuboidClipboard.loadSchematic(regionSchem);
   //
   //                         cc.paste(es, origin, false);
   //                         System.out.print(Main.consolePrefix + "Pasted schematic for region " + region.getId());
   //                     } catch (com.sk89q.worldedit.world.DataException | IOException | MaxChangedBlocksException e) {
   //                         System.out.print(Main.consolePrefix + "DataException while loading schematic!");
   //                         e.printStackTrace();
   //                     }
   //                 }
   //             }
   //     );
   // }




    @Override
    public void actionResetGame() {
        kills.clear();
        lblocks.clear();
        this.getActiveState().endAction();
        this.getWaitingState().endAction();
        this.getEndState().endAction();
        this.getWorld().getWorldBorder().reset();
        this.getWorld().getWorldBorder().setSize(((LGame)this).getBorderSize());
        this.getWorld().getWorldBorder().setCenter(((LGame)this).getBorderCenterX(),getBorderCenterZ());
    }


    @Override
    public int getDurationMinutes() {
        return 100;
    }
}
