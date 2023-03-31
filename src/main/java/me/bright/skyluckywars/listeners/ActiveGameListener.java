package me.bright.skyluckywars.listeners;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.Game;
import me.bright.skylib.game.GameState;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.game.LGame;
import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.game.dropsets.TrapSet;
import me.bright.skyluckywars.game.events.*;
import me.bright.skyluckywars.game.events.armor.GuardianBootsEquip;
import me.bright.skyluckywars.game.events.armor.GuardianBootsOff;
import me.bright.skyluckywars.game.events.armor.SpeedBootsOff;
import me.bright.skyluckywars.game.events.armor.SpeedBotsEquip;
import me.bright.skyluckywars.game.dropsets.DefaultDropSet;
import me.bright.skyluckywars.game.dropsets.BaseDropSet;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.items.unqiue.ChikenEgg;
import me.bright.skyluckywars.game.items.unqiue.SnowballFlake;
import me.bright.skyluckywars.utils.InstantFirework;
import me.bright.skyluckywars.utils.armor.ArmorEquipEvent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActiveGameListener implements Listener {

    private LuckyWars plugin;
    private String lblockName;

    public ActiveGameListener(LuckyWars plugin) {
        this.plugin = plugin;
        this.lblockName = plugin.getGamesConfig().getString("blockname");
    }


    @EventHandler
    public void onSmiv(BlockFromToEvent event) {
        if(event.getToBlock().getType() == Material.PLAYER_HEAD) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        SPlayer sp = SPlayer.getPlayer(p);
        if (event.getBlock().getDrops().size() == 0) return;
        //   Bukkit.getLogger().info("Event block type " + event.getBlock().getType().toString());
        //  Bukkit.getLogger().info("Is in active game " + isInActiveGame(sp));
        if (event.getBlock().getType() == Material.PLAYER_HEAD && isInActiveGame(sp)) {

            if (Messenger.rnd(1, 100) <= 50 || (int)sp.getInfoOrDefault(LInfo.LUCKY_BLOCKS_BROKEN.getKey(),0) < 2) {

                List<ItemStack> newDrop = new ArrayList<>();
                if (sp.getInfo(LInfo.DEFAULTSET.getKey()) == null) {
                    DefaultDropSet dropset = new DefaultDropSet(sp);
                    dropset.generateItems();
                    sp.putInfo(LInfo.DEFAULTSET.getKey(), dropset);

                }
                DefaultDropSet dfset = (DefaultDropSet) sp.getInfoOrDefault(LInfo.DEFAULTSET.getKey(), null);
                if (!dfset.isEmpty()) {
                    newDrop = dfset.getPartItems();
                } else {
                    BaseDropSet dropSet = new BaseDropSet(sp);
                    dropSet.generateItems(event.getBlock().getLocation());
                    newDrop = dropSet.getItems();
                }

                // Bukkit.getLogger().info(newDrop.size() + " drp size");
                event.setDropItems(false);
                newDrop.forEach(drop -> {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
                });

            } else {
                new TrapSet().generate(event.getBlock().getLocation(),sp);
              //  Vector v = p.getLocation().getDirection();
              //  TNTPrimed tnt = (TNTPrimed) p.getWorld()
              //          .spawnEntity(p.getLocation().clone().add(v.getX(), v.getY() + 1, v.getZ()), EntityType.PRIMED_TNT);
            }
            sp.incrementIntegerValue(LInfo.LUCKY_BLOCKS_BROKEN.getKey());
            event.setDropItems(false);
            ((LGame)sp.getGame()).addLBlock(sp.getPlayer());
            new InstantFirework(FireworkEffect.builder().withColor(Color.AQUA).build(), event.getBlock().getLocation());
        }
    }


    @EventHandler
    public void onThrowPearl(ProjectileLaunchEvent event) {

        if(event.isCancelled()) {
            return;
        }
        if(!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }
        PlayerInventory inv =((Player) event.getEntity().getShooter()).getInventory();
        if(isInActiveGame(SPlayer.getPlayer((Player)event.getEntity().getShooter())) &&
                event.getEntity().getType() == EntityType.ENDER_PEARL &&
                isEqual(inv.getItemInMainHand(),LItem.Type.PEARL)) {
            new CustomPearlEvent((Player)event.getEntity().getShooter(),event.getEntity()).callEvent();
        }
    }

    @EventHandler()
    public void enderPearlThrown(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) event.setCancelled(true);
    }

    @EventHandler
    public void onPearlShift(PlayerToggleSneakEvent event) {
        SPlayer sp = SPlayer.getPlayer(event.getPlayer());
        if(event.getPlayer().getVehicle() != null && event.getPlayer().getVehicle() instanceof EnderPearl) {
            event.getPlayer().getVehicle().remove();
        }
     //  if(sp.getInfoOrDefault(LInfo.HAS_PEARL.getKey(),false) == null) {
     //      sp.putInfo(LInfo.HAS_PEARL.getKey(),true);
     //  }
    }

    @EventHandler
    public void onPhysics(BlockPhysicsEvent event) {
        if(event.getBlock().getType() != Material.WATER) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPosPotion(PotionSplashEvent event) {
        ProjectileSource source = event.getEntity().getShooter();
        if(!(source instanceof Player)) return;
        Player p = (Player) source;

        if(isEqual(event.getPotion().getItem(), LItem.Type.POSITIVE_POTION)) {
            new PositivePotionEvent(event.getAffectedEntities()).callEvent();
        } else if(isEqual(event.getPotion().getItem(), LItem.Type.NEGATIVE_POTION)) {
            new NegativePotionEvent(event.getAffectedEntities()).callEvent();
        } else if(isEqual(event.getPotion().getItem(), LItem.Type.MAGIC_POTION)) {
            new MagicPotionEvent(event.getAffectedEntities()).callEvent();
        } else if(isEqual(event.getPotion().getItem(), LItem.Type.POISON_FLASK)) {
            new PoisonFlaskEvent(event.getAffectedEntities(),p).callEvent();
        }
    }


    @EventHandler
    public void onHitBow(ProjectileHitEvent event) {
   //     Bukkit.getLogger().info("hit event 1");
        if(!(event.getEntity().getShooter() instanceof Player)) return;
        if(!(event.getEntity() instanceof Arrow)) return;
        ItemStack hand = ((Player)event.getEntity().getShooter()).getInventory().getItemInMainHand();
        Player p = (Player) event.getEntity().getShooter();
    //    Bukkit.getLogger().info("hit event 2");
     //   Bukkit.getLogger().info("hand type = " + hand.getType().toString());
        if(isEqual(hand, LItem.Type.CAT_BOW) && event.getEntity().getPassengers().size() != 0) {
            new CatBowHitEvent(p,event).callEvent();
        } else if(isEqual(hand,LItem.Type.TNT_BOW)) {
            new TntBowHitEvent(p,event).callEvent();
        } else if(isEqual(hand,LItem.Type.SPIDER_BOW)) {
            new SpiderBowHitEvent(p,event).callEvent();
        } else if(isEqual(hand, LItem.Type.ZEUS_BOW)) {
            new ZeusBowHitEvent(p,event).callEvent();
        }
    }

    @EventHandler
    public void onHitSnowBall(ProjectileHitEvent event) {
        if(!(event.getEntity().getShooter() instanceof Player)) return;
        if(!(event.getEntity() instanceof Snowball || event.getEntity() instanceof Egg)) return;
        ItemStack hand = ((Player)event.getEntity().getShooter()).getInventory().getItemInMainHand();
        Player p = (Player) event.getEntity().getShooter();
        if(isEqual(hand,LItem.Type.SNOWBALL_FLAKE)) {
            new SnowballFlakeHitEvent(p,event).callEvent();
        } else if(isEqual(hand,LItem.Type.CHIKEN_EGG)) {
            new ChikenEggHitEvent(p,event).callEvent();
        }
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        if(!(event.getEntity().getType() == EntityType.PLAYER)) return;
        Player p = (Player) event.getEntity();
        ItemStack hand = p.getInventory().getItemInMainHand();
        if(isEqual(hand,LItem.Type.CAT_BOW)) {
            new CatBowShootEvent(p,event).callEvent();
        }
    }

    @EventHandler
    public void onEquipArmor(ArmorEquipEvent event) {
        ItemStack newItem = event.getNewArmorPiece();
        ItemStack oldItem = event.getOldArmorPiece();
        if(newItem != null) {
            if (isEqual(newItem, LItem.Type.SPEED_BOOTS)) {
                new SpeedBotsEquip(event.getPlayer()).callEvent();
            } else if (isEqual(newItem, LItem.Type.GUARDIAN_BOOTS)) {
                new GuardianBootsEquip(event.getPlayer()).callEvent();
            }
        }
        if(oldItem != null) {
            if (isEqual(oldItem, LItem.Type.SPEED_BOOTS)) {
                new SpeedBootsOff(event.getPlayer()).callEvent();
            } else if (isEqual(oldItem, LItem.Type.GUARDIAN_BOOTS)) {
                new GuardianBootsOff(event.getPlayer()).callEvent();
            }
        }

    }

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if(!(event.getTarget() instanceof Player)) return;
        if(event.getEntity() instanceof LivingEntity) {
            if(event.getEntity().isDead()) {
                return;
            }
            Entity entity = event.getEntity();
            Player target = (Player) event.getTarget();
            if(SPlayer.getPlayer(target).isSpectator()) {
                event.setCancelled(true);
                return;
            }

            if(entity.hasMetadata(LInfo.MOB_OWNER.getKey())  &&
                    entity.getMetadata(LInfo.MOB_OWNER.getKey()).get(0).asString()
                            .equalsIgnoreCase(event.getTarget().getUniqueId().toString())) {
                event.setCancelled(true);
            }
          //  if(customName.equalsIgnoreCase(Messenger.color(LItem.Type.ZOMBIE.getDpName()))) {
          //      new ZombieTargetEvent(event).callEvent();
          //  } else if(customName.equalsIgnoreCase(Messenger.color(LItem.Type.BLAZE.getDpName()))) {
          //      new BlazeTargetEvent(event).callEvent();
          //  } else if(customName.equalsIgnoreCase(Messenger.color(LItem.Type.CREEPER.getDpName()))) {
          //      new CreeperTargetEvent(event).callEvent();
          //  }


        }
    }


    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if(event.getEntity().getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM){
            if(event.getEntity().getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.EGG && event.getEntity() instanceof LivingEntity) {
             //   if()
                event.setCancelled(true);
            }
         //   event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event) {
        if(isEqual(event.getPlayer().getInventory().getItemInMainHand(),LItem.Type.RODE_HOOK)) {
            new CustomRodEvent(event).callEvent();
        }
    }

    @EventHandler
    public void entityByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Firework) event.setCancelled(true);
        if(event.getDamager() instanceof LightningStrike) {
            if(event.getDamager().hasMetadata("uuid") &&
                    event.getDamager().getMetadata("uuid").get(0).asString() == event.getEntity().getUniqueId().toString()) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
     //   if(event.isCancelled()) event.setCancelled(false);
        if(!(event.getEntity() instanceof  Player)) return;
        Player victim = (Player) event.getEntity();
        //TagAPI.updateTag(victim);
      //  Bukkit.getLogger().info("GOTOVO");
        boolean death = victim.getHealth() - event.getFinalDamage() <= 0;
        Game game = SPlayer.getPlayer(victim).getGame();
        if(death && game != null && !SPlayer.getPlayer(victim).isSpectator() &&
        game.getState() != null && game.getState().getEnum() == GameState.ACTIVEGAME) {
            if(event instanceof EntityDamageByEntityEvent) {
            Entity entityKiller = ((EntityDamageByEntityEvent)event).getDamager();
            SPlayer sp = SPlayer.getPlayer(victim);
            if(entityKiller instanceof Player) {
                Player killer = (Player)entityKiller;
                ItemStack hand = killer.getInventory().getItemInMainHand();
                if(isEqual(hand, LItem.Type.SWORD_SOUL)) {
                    new SwordSoulKill(killer,victim).callEvent();
                }

                if(game != null) {
                    game.broadCastColor("&fИгрок &c" + event.getEntity().getName() + " &fбыл убит игроком " + killer.getName(),true);
                }
                SPlayer spKiller = SPlayer.getPlayer((Player)entityKiller);
                spKiller.incrementIntegerValue(LInfo.KILLS.getKey());
                ((LGame)game).addKill(spKiller.getPlayer());
            }

            } else {
                String s = "&fИгрок &c" + event.getEntity().getName() + " &f";
                EntityDamageEvent.DamageCause cause = (event.getEntity().getLastDamageCause() != null) ?
                        event.getEntity().getLastDamageCause().getCause() : null;
                if(cause != null) {
                    switch (cause) {
                        case VOID:
                            s += "упал в бездну";
                            break;
                        case FALL:
                            s += "разбился";
                            break;
                        case LIGHTNING:
                            s += "был убит молнией";
                            break;
                        case DROWNING:
                            s += "утонул";
                            break;
                        default:
                            s += "погиб";
                            break;
                    }
                } else {
                    s += "погиб";
                }
                if (game != null) {
                    game.broadCastColor(s,true);
                }
            }
        //    Bukkit.getLogger().info("in endmg isSpect " + String.valueOf(SPlayer.getPlayer(victim).isSpectator()));
            toSpectator(victim);
           if(game.getLivePlayersSize() <= 1 && game.getState() != null && game.getState().getEnum() == GameState.ACTIVEGAME) {
               if(game.getLivePlayersSize() >= 1) {
                   UUID winnerUUID = game.getLivePlayers().get(0);
                   if (winnerUUID != null) {
                       Player winnerPlayer = Bukkit.getPlayer(winnerUUID);
                       SPlayer winner = SPlayer.getPlayer(winnerPlayer);
                       game.setWinner(winner.getTeam());
                   }
               }
               game.setState(GameState.END);
           }
            event.setDamage(0.0);
        }
    }




    @EventHandler
    public void onVoid(PlayerMoveEvent event) {
        SPlayer sp = SPlayer.getPlayer(event.getPlayer());
        if(event.getTo().getY() <= 0) {
            if(sp.getGame() != null && sp.getGame().getState() != null && sp.getGame().getState().getEnum() == GameState.WAITING) {
                event.getPlayer().teleport(sp.getGame().getLobbyLocation());
                return;
            }
            new EntityDamageEvent(event.getPlayer(), EntityDamageEvent.DamageCause.VOID,10000D);
        }
    }



    private void toSpectator(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(((LGame)SPlayer.getPlayer(player).getGame()).getSpectatorSpawn());
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,Integer.MAX_VALUE,0,false,false));
        player.getInventory().clear();
        player.setAllowFlight(true);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        player.setFoodLevel(20);
        player.setCustomNameVisible(false);
        player.setInvulnerable(true);
        player.setInvisible(true);
        player.setSilent(true);;
        player.setCollidable(false);
        player.spigot().setCollidesWithEntities(false);
        LGame game = (LGame) SPlayer.getPlayer(player).getGame();
        game.getLivePlayers().forEach(liveP -> {
            Player livePlayer = Bukkit.getPlayer(liveP);
            livePlayer.hidePlayer(game.getPlugin(),player);
        });
      //  SPlayer.getPlayer(player).setSpectator(true);

        SPlayer.getPlayer(player).setSpectator(true);
        SPlayer.getPlayer(player).getGame().addSpectator(player.getUniqueId());
        SPlayer.getPlayer(player).getGame().removeLivePlayer(player.getUniqueId());

    }





    private boolean isEqual(ItemStack item, LItem.Type typeItem) {
        if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return false;
        String dpname = item.getItemMeta().getDisplayName();
      //  Bukkit.getLogger().info("DPNAME1 = " + dpname);
        //Bukkit.getLogger().info("DPNAME2 = " + typeItem.getDpName());
        if(dpname.equalsIgnoreCase(Messenger.color(typeItem.getDpName()))) {
            return true;
        }
        return false;
    }


    private boolean isInActiveGame(SPlayer sp) {
        if(sp.getGame() != null && sp.getGame().getState() != null && sp.getGame().getState().getEnum() == GameState.ACTIVEGAME) return true;
        return false;
    }
}
