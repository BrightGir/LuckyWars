package me.bright.skyluckywars.listeners;

import me.bright.skylib.SPlayer;
import me.bright.skylib.events.GameJoinEvent;
import me.bright.skylib.events.GameLeaveEvent;
import me.bright.skylib.game.Game;
import me.bright.skylib.game.GameState;
import me.bright.skylib.game.states.ActiveState;
import me.bright.skylib.game.states.State;
import me.bright.skylib.game.states.WaitingState;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.game.LGame;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.checkerframework.checker.units.qual.A;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TechnicGameListener implements Listener {

    private LuckyWars plugin;

    public TechnicGameListener(LuckyWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        LGame game = getOptimalGame();
        event.getPlayer().setInvulnerable(true);
        event.getPlayer().setInvisible(false);
     //   plugin.getLogger().info("Game = " + game.get);
        event.getPlayer().setCollidable(true);
        try {
            game.addPlayer(event.getPlayer());
        } catch (Exception e) {

            try {
                plugin.redirectToLobby(event.getPlayer());
            } catch (Exception e1) {
                event.getPlayer().kick(Component.text("Лобби недоступно!"));
            }
        }
     //   Bukkit.getLogger().info("game add player");
    }

    @EventHandler
    public void gameJoin(GameJoinEvent event) {
        Game game = event.getGame();
        event.getPlayer().setInvulnerable(true);
        event.getPlayer().setInvisible(false);
        event.getPlayer().setCollidable(true);
        game.broadCastColor("&fИгрок &a" + event.getPlayer().getName() + " &fприсоединился к игре (&a" +
                game.getPlayersSize() + "/&a" + game.getMaxPlayers() + "&f)");
       LuckyWars pl = ((LuckyWars) event.getGame().getArena().getPlugin());
       if(pl.getGameInfoMysql().getConnection() != null) {
           pl.getGameInfoMysql().insertPlayer(event.getPlayer());
       }
   //    event.getGame().getScoreboardManager().setBoard(SPlayer.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onLeave(GameLeaveEvent event) {
        LGame game = (LGame) event.getGame();
        State state = game.getState();
        if(game.getPlayersSize() <= 0 && (state != null && state.getEnum() != GameState.WAITING)) {
            game.startGame();
            return;
        }
        if(state != null && state.getEnum() == GameState.WAITING) {
            game.broadCastColor("&fИгрок &a" + event.getPlayer().getName() + " &fпокинул игру (&a" +
                    game.getPlayersSize() + "/&a" + game.getMaxPlayers() + "&f)");
        } else if(state != null && state.getEnum() == GameState.ACTIVEGAME) {
            game.broadCastColor("&fИгрок &a" + event.getPlayer().getName() + " &fпокинул игру");
        }
        if(game.getState() != null && game.getState().getEnum() == GameState.WAITING
                && game.getPlayersSize() < game.getMinPlayersToStartCounting()) {
            ((WaitingState)game.getState()).stopCounting();
        }
    }
    public LGame getOptimalGame(){
        LGame optimal = null;
        List<LGame> gamesList = plugin.getGames();
        Collections.shuffle(gamesList);

        for(LGame game: gamesList) {
            boolean open = game.getState() != null && game.getState().getEnum() == GameState.WAITING;
            if(open && (optimal == null || (game.getPlayersSize() > optimal.getPlayersSize()))) {
                optimal = game;
            }
        }
        return optimal;
    }

    @EventHandler
    public void onGameJoin(GameJoinEvent event) {
        Player pl = event.getPlayer();
        SPlayer sp = SPlayer.getPlayer(pl);
        sp.setGame(event.getGame());
        sp.setSpectator(false);
        sp.setArena(event.getGame().getArena());
        sp.setTeam(null);
        pl.setExp(0);
        pl.setHealth(20D);
        pl.setFoodLevel(20);
        pl.setLevel(1);
        pl.setGameMode(GameMode.ADVENTURE);;
        pl.getInventory().clear();
        pl.setTotalExperience(0);
        pl.setInvulnerable(false);
        event.getGame().getScoreboardManager().setBoard(sp);
        for (PotionEffect effect : pl.getActivePotionEffects())
            pl.removePotionEffect(effect.getType());
        pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
     //   Bukkit.getLogger().info("pl teleport");
        Location tpTo = ((LGame) event.getGame()).getLobbyLocation();
     //   Bukkit.getLogger().info("location = " + tpTo.toString());
     //   tpTo.getChunk().load();
        pl.teleport(tpTo);

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        SPlayer sp = SPlayer.getPlayer((Player)event.getEntity());
     //   Bukkit.getLogger().info(String.valueOf("get game null " + sp.getGame() == null + ""));
      //  Bukkit.getLogger().info(String.valueOf("state " + sp.getGame().getState().getEnum() == null + ""));
        if(sp.getGame() != null &&
                sp.getGame().getState() != null && sp.getGame().getState().getEnum() != GameState.ACTIVEGAME) {
          //  Bukkit.getLogger().info("setcancel");
            event.setCancelled(true);
        }

    }


    @EventHandler
    public void onDeath(PlayerRespawnEvent event) {
        SPlayer sp = SPlayer.getPlayer(event.getPlayer());
        if(inState(event.getPlayer(),GameState.WAITING)) {
            Location loc = ((LGame)sp.getGame()).getLobbyLocation();
            event.setRespawnLocation(loc);
        }
    }



    private boolean inState(Player player, GameState state) {
        SPlayer sp = SPlayer.getPlayer(player);
        if(sp.getGame() != null) {
            return sp.getGame().getState() != null && sp.getGame().getState().getEnum() == state;
        }
        return false;
    }





}
