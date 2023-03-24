package me.bright.skyluckywars.game;

import me.bright.skylib.Arena;
import me.bright.skylib.game.Game;
import me.bright.skylib.game.GameMode;
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
import org.bukkit.entity.Player;

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
    private List<Location> deathMathLocations;
    private Map<String,Integer> kills;
    private Map<String,Integer> lblocks;
    private int gameEndSeconds;
    private int deathMatchSeconds;
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
