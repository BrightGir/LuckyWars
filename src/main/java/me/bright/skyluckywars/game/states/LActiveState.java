package me.bright.skyluckywars.game.states;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.GameState;
import me.bright.skylib.game.states.ActiveState;
import me.bright.skylib.teams.Team;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.LGame;
import me.bright.skyluckywars.game.LInfo;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class LActiveState extends ActiveState {


    private BukkitTask deathMathContinue;
    private static double ZONE_DAMAGE_AMOUNT = 0.0000005D;
    private BukkitTask activeGameTimer;
    private BossBar timerBossbar;
    private BossBar deathmatchBossbar;
    private BukkitTask borderChecker;
    public LActiveState(LGame game) {
        super(game);
    }


    @Override
    public void actionStartState() {
        toIslands();
     //   Bukkit.getLogger().info("ACTIVESTATE START");
        new BukkitRunnable() {
            @Override
            public void run() {
                getGame().getPlayers().forEach(p -> {p.setInvulnerable(false);});
                getGame().getPlayers().forEach(p -> {p.setInvulnerable(false);});
            }
        }.runTaskLater(getGame().getArena().getPlugin(), 20*3L);
        LGame lg = (LGame) getGame();
        World world = getGame().getWorld();
        getGame().getPlayers().forEach(p -> {
            ((LGame)getGame()).setKills(p,0);
        });
        world.getWorldBorder()
                .setCenter(lg.getBorderCenterX(),lg.getBorderCenterZ());
        world.getWorldBorder().setSize(lg.getBorderSize());
        world.getWorldBorder().setDamageAmount(ZONE_DAMAGE_AMOUNT);





        timerBossbar = Bukkit.createBossBar("", BarColor.PURPLE, BarStyle.SOLID);
        timerBossbar.setVisible(true);
        getGame().getPlayers().forEach(p -> timerBossbar.addPlayer(p));
        activeGameTimer = new BukkitRunnable() {
           // int fullSeconds = 20;
            int fullSeconds = ((LGame)getGame()).getDeathMatchSeconds();
           // int fullSeconds = 2*60;
            int secondsRemain = fullSeconds;
            @Override
            public void run() {
                if(secondsRemain == 0) {
                    timerBossbar.removeAll();
                    timerBossbar.setVisible(false);
                    getGame().broadCastColor("&cДезматч &fначался",true);
                    List<Location> locs = ((LGame) getGame()).getDeathMathLocations();
                    int i = 0;
                    for(Player p: getGame().getPlayers()) {
                        Location loc = locs.get(i);
                        if(loc.clone().add(0,-1,0).getBlock().getType() == Material.AIR) {
                            createSquare3x3(loc);
                        }
                        p.teleport(locs.get(i).clone().add(0,1,0));
                        i++;
                    }
                    startDeathMatch();
                    this.cancel();
                } else {
                    int minutes = secondsRemain / 60;
                    int seconds = secondsRemain % 60;
                    String s2 = (seconds != 0) ? Messenger.correct(seconds, " " + seconds + " секунду"
                            ,
                            " " + seconds + " секунды", " " + seconds + " секунд") : "";
                    String bossbarString = "&fДезматч через&c" + (((minutes == 0) ? ""
                            : Messenger.correct(minutes, " " + minutes + " минуту",
                            " " + minutes + " минуты", " " + minutes + " минут")))
                            + s2;
                    if ((minutes == 0 && (seconds == 30 || seconds == 15 || seconds <= 5)) || (minutes == 5 && seconds == 0)
                            || (minutes == 1 && seconds == 0)) {
                        String s21 = (seconds != 0) ? Messenger.correct(seconds, " " + seconds + " секунду"
                                ,
                                " "+ seconds + " секунды", " " + seconds + " секунд") : "";
                        String bossbarString1 = "&fДезматч начнется через&c" + (((minutes == 0) ? ""
                                : Messenger.correct(minutes, " " + minutes + " минуту",
                                " " + minutes + " минуты", " " + minutes + " минут")))
                                + s21;
                        getGame().broadCastColor(bossbarString1,true);
                    }
                    double percentage = (double) secondsRemain / fullSeconds;
                    timerBossbar.setTitle(Messenger.color(bossbarString));
                    timerBossbar.setProgress(percentage);
                    secondsRemain--;
                }
            }
        }.runTaskTimer(((LGame) getGame()).getPlugin(),0,20L);
    }


    private void createSquare3x3(Location center) {
        for(int dx = -1; dx <= 1; dx++) {
            for(int dz = -1; dz <= 1; dz++) {
                center.clone().add(dx,0,dz).getBlock().setType(Material.GLASS);
            }
        }
    }

    private void startDeathMatch() {
        if(deathmatchBossbar != null) {
            deathmatchBossbar.removeAll();
            deathmatchBossbar = null;
        }
        deathmatchBossbar = Bukkit.createBossBar("", BarColor.PURPLE, BarStyle.SOLID);
        deathmatchBossbar.setVisible(true);
        getGame().getPlayers().forEach(p -> deathmatchBossbar.addPlayer(p));
        World world = getGame().getWorld();
        LGame lg = (LGame)getGame();

        world.getWorldBorder().setSize(lg.getDeathMatchBorderSize());
        borderChecker = new BukkitRunnable() {
            @Override
            public void run() {
                getGame().getPlayers().forEach(pl -> {
                    if(!world.getWorldBorder().isInside(pl.getLocation())) {
                        new EntityDamageEvent(pl,
                                EntityDamageEvent.DamageCause.CUSTOM,ZONE_DAMAGE_AMOUNT).callEvent();
                    }
                });
            }
        }.runTaskTimer(getGame().getArena().getPlugin(),0,20L);

        deathMathContinue = new BukkitRunnable() {
            int fullSeconds = ((LGame)getGame()).getGameEndSeconds();
             // int fullSeconds = 2*60;
            //   int fullSeconds = 6;
            int secondsRemain = fullSeconds;
            @Override
            public void run() {
                if(secondsRemain == 0) {
                    deathmatchBossbar.removeAll();
                    deathmatchBossbar.setVisible(false);
                    getGame().setState(GameState.END);
                    this.cancel();
                } else {

                    int minutes = secondsRemain / 60;
                    int seconds = secondsRemain % 60;
                    String s2 = (seconds != 0) ? Messenger.correct(seconds, " " + seconds + " секунду"
                            ,
                            " "+ seconds + " секунды", " " + seconds + " секунд") : "";
                    String bossbarString = "&fКонец игры через&c" + (((minutes == 0) ? ""
                            : Messenger.correct(minutes, " " + minutes + " минуту",
                            " " + minutes + " минуты", " " + minutes + " минут"))) + ""
                            + s2;
                    double percentage = (double) secondsRemain / fullSeconds;
                    deathmatchBossbar.setTitle(Messenger.color(bossbarString));
                    deathmatchBossbar.setProgress(percentage);
                    secondsRemain--;
                    getGame().getLivePlayers().forEach(p -> {
                        Player pl = Bukkit.getPlayer(p);
                        if(!getGame().getWorld().getWorldBorder().isInside(pl.getLocation())) {
                            pl.damage(10D);
                        }
                    });
                }
            }
        }.runTaskTimer(getGame().getArena().getPlugin(),0,20L);
    }




    @Override
    public void endAction() {
        if(deathMathContinue != null && !deathMathContinue.isCancelled()) deathMathContinue.cancel();
        if(activeGameTimer != null && !activeGameTimer.isCancelled()) activeGameTimer.cancel();
        if(borderChecker != null && !borderChecker.isCancelled()) borderChecker.cancel();
        if(deathmatchBossbar != null) {
            deathmatchBossbar.removeAll();
        }
        if(timerBossbar != null) timerBossbar.removeAll();
    }



    @Override
    public void setDefaultStateOfPlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
      //  getGame().getScoreboardManager().setBoard(SPlayer.getPlayer(player));
    }

    private void toIslands() {
        List<Location> islands = ((LGame)getGame()).getIslandsLocations();
        Collections.shuffle(islands);
        int i = 0;
        for(Team t: getGame().getTeamManager().getTeams()) {
            for(UUID up: t.getPlayersUUID()) {
                Bukkit.getPlayer(up).spigot().respawn();
                Bukkit.getPlayer(up).teleport(islands.get(i));
                i++;
            }
        }
    }



    @Override
    public int getUpdateScoreboardDelay() {
        return 1;
    }
}
