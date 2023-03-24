package me.bright.skyluckywars.listeners;


import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.game.Locationable;
import me.bright.skyluckywars.game.events.*;
import me.bright.skyluckywars.utils.Pair;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

public class ItemActionListener implements Listener {

    private LuckyWars plugin;
    private Random rand;


    public ItemActionListener(LuckyWars plugin) {
        this.plugin = plugin;
        this.rand = new Random();
    }

    @EventHandler
    public void onBallHit(SnowballFlakeHitEvent event) {
        ProjectileHitEvent hitEvent = event.getHitEvent();
        Location loc = null;
        if(hitEvent.getHitEntity() != null) loc = hitEvent.getHitEntity().getLocation();
        if(hitEvent.getHitBlock() != null) loc = hitEvent.getHitBlock().getLocation();

        Silverfish fish = (Silverfish) loc.getWorld().spawnEntity(loc.clone().add(0,2,0),EntityType.SILVERFISH);
        fish.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
        fish.setHealth(20D);
        fish.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,
                1,false,false));
        fish.setMetadata(LInfo.MOB_OWNER.getKey(),
                new FixedMetadataValue(SPlayer.getPlayer(event.getPlayer()).getArena().getPlugin(), event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onTntBowHit(TntBowHitEvent event) {
        int chance = Messenger.rnd(10,20);
        int volume = Messenger.rnd(3,5);
        if(luck(chance)) {
            Location loc = null;
            if(event.getHitBowEvent().getHitEntity() != null) loc = event.getHitBowEvent().getHitEntity().getLocation();
            if(event.getHitBowEvent().getHitBlock() != null) loc = event.getHitBowEvent().getHitBlock().getLocation();
            loc.getWorld().createExplosion(loc,(float)volume);
        }
    }

    @EventHandler
    public void onZeus(ZeusBowHitEvent event) {
        int chance = Messenger.rnd(10,20);
        if(luck(chance)) {
            Location loc = null;
            if(event.getHitBowEvent().getHitEntity() != null) loc = event.getHitBowEvent().getHitEntity().getLocation();
            if(event.getHitBowEvent().getHitBlock() != null) loc = event.getHitBowEvent().getHitBlock().getLocation();
            loc.getWorld().strikeLightning(loc);
        }
    }

    @EventHandler
    public void onChikenHit(ChikenEggHitEvent event) {
        ProjectileHitEvent hitEvent = event.getHitEvent();
        Location loc = null;
        if(hitEvent.getHitEntity() != null) loc = hitEvent.getHitEntity().getLocation();
        if(hitEvent.getHitBlock() != null) loc = hitEvent.getHitBlock().getLocation();

        Chicken chicken = (Chicken) loc.getWorld().spawnEntity(loc.clone().add(0,2,0),EntityType.CHICKEN);
        chicken.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10D);
        chicken.setHealth(10D);
        chicken.setBaby();
        Location finalLoc = loc;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(chicken != null && !chicken.isDead()) {
                    finalLoc.getWorld().createExplosion(chicken,2F);
                    chicken.setHealth(0);
                }
            }
        }.runTaskLater(plugin,5L * 20L);
    }


    @EventHandler
    public void onFish(CustomRodEvent event) {
        PlayerFishEvent fishEvent = event.getFishEvent();
        if(fishEvent.getState() == PlayerFishEvent.State.IN_GROUND) {
            Player p = event.getFishEvent().getPlayer();
            Location ploc = p.getLocation();
            Location hLoc = fishEvent.getHook().getLocation();
            Vector v = hLoc.toVector();
            p.setVelocity(new Vector(hLoc.getX()- ploc.getX(),hLoc.getY()- ploc.getY()+0.8,hLoc.getZ()- ploc.getZ()));
        }
    }

    @EventHandler
    public void onSoulSword(SwordSoulKill event) {
        Player damager = event.getKiller();
        double maxHealth = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        int hearts = Messenger.rnd(2,4);
        damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth + (double)2D*hearts);
        Location loc = damager.getLocation();
        loc.getWorld().playSound(loc, Sound.ENTITY_ENDERMAN_DEATH,5,5);
    }


    @EventHandler
    public void onPearl(CustomPearlEvent event) {
        Player p = (Player) event.getPlayer();
        event.getPearl().addPassenger(p);
     //   Bukkit.getLogger().info("IN PEARL");
    }

    @EventHandler
    public void onPositivePotion(PositivePotionEvent event) {
        List<Pair<PotionEffectType,Integer>> list = new ArrayList<>(Arrays.asList(new Pair<>(PotionEffectType.INVISIBILITY,0),
                new Pair<>(PotionEffectType.INVISIBILITY,1),
                new Pair<>(PotionEffectType.SPEED,0),new Pair<>(PotionEffectType.SPEED,1),
                new Pair<>(PotionEffectType.REGENERATION,0),new Pair<>(PotionEffectType.REGENERATION,1),
                new Pair<>(PotionEffectType.FIRE_RESISTANCE,0),new Pair<>(PotionEffectType.WATER_BREATHING,1),
                new Pair<>(PotionEffectType.FIRE_RESISTANCE,1),new Pair<>(PotionEffectType.FIRE_RESISTANCE,2),
                new Pair<>(PotionEffectType.JUMP,0),new Pair<>(PotionEffectType.JUMP,1),
                new Pair<>(PotionEffectType.INCREASE_DAMAGE,0),
                new Pair<>(PotionEffectType.INCREASE_DAMAGE,1),new Pair<>(PotionEffectType.WATER_BREATHING,0)
                ));

        setEffects(event.getSplashedEntities(),list,getRnd(1,3),16,32);
    }

    @EventHandler
    public void onNegativePotion(NegativePotionEvent event) {
        List<Pair<PotionEffectType,Integer>> list = new ArrayList<>(Arrays.asList(
                new Pair<>(PotionEffectType.SLOW,0),
                new Pair<>(PotionEffectType.SLOW,1),
                new Pair<>(PotionEffectType.POISON,0),new Pair<>(PotionEffectType.POISON,1),
                new Pair<>(PotionEffectType.WEAKNESS,0),new Pair<>(PotionEffectType.WEAKNESS,1),
                new Pair<>(PotionEffectType.CONFUSION,0),new Pair<>(PotionEffectType.CONFUSION,1)
        ));
        setEffects(event.getSplashedEntities(),list,getRnd(1,3),16,32);
    }

    @EventHandler
    public void onMagicPotion(MagicPotionEvent event) {
        List list = new ArrayList<>(Arrays.asList(new Pair<>(PotionEffectType.INVISIBILITY,0),
                new Pair<>(PotionEffectType.INVISIBILITY,1),
                new Pair<>(PotionEffectType.SPEED,0),new Pair<>(PotionEffectType.SPEED,1),
                new Pair<>(PotionEffectType.REGENERATION,0),new Pair<>(PotionEffectType.REGENERATION,1),
                new Pair<>(PotionEffectType.FIRE_RESISTANCE,0),new Pair<>(PotionEffectType.WATER_BREATHING,1),
                new Pair<>(PotionEffectType.FIRE_RESISTANCE,1),
                new Pair<>(PotionEffectType.JUMP,0),new Pair<>(PotionEffectType.JUMP,1),
                new Pair<>(PotionEffectType.INCREASE_DAMAGE,0),
                new Pair<>(PotionEffectType.INCREASE_DAMAGE,1),new Pair<>(PotionEffectType.WATER_BREATHING,0),
                new Pair<>(PotionEffectType.NIGHT_VISION,0),
                new Pair<>(PotionEffectType.SLOW,0),new Pair<>(PotionEffectType.SLOW,1),
                new Pair<>(PotionEffectType.WEAKNESS,0),
                new Pair<>(PotionEffectType.WEAKNESS,1),
                new Pair<>(PotionEffectType.CONFUSION,0),
                new Pair<>(PotionEffectType.CONFUSION,1),
                new Pair<>(PotionEffectType.POISON,0),new Pair<>(PotionEffectType.POISON,1),
                new Pair<>(PotionEffectType.LEVITATION,0),
                new Pair<>(PotionEffectType.LEVITATION,1)));
        setEffects(event.getSplashedEntities(),list,getRnd(1,3),16,32);
    }

    @EventHandler
    public void onPoisonFlask(PoisonFlaskEvent event) {;
        event.getSplashedEntities().forEach(en -> {
          //  en.damage((double)Messenger.rnd(10,15));
            new EntityDamageEvent(event.getPlayer(), EntityDamageEvent.DamageCause.POISON,
                    (double)Messenger.rnd(10,15)).callEvent();
        });
    }

    private void setEffects(Collection<LivingEntity> entities, List<Pair<PotionEffectType,Integer>> effects, int effectsCount, int minDuration, int maxDuration) {
        List<Pair<PotionEffectType,Integer>> toEffect = new ArrayList<>();
        while(effectsCount > 0) {
            effectsCount--;
            int idx = getRnd(0,effects.size()-1);
            toEffect.add(effects.get(idx));
            effects.remove(idx);
        }
        int duration = getRnd(minDuration,maxDuration);
        for(LivingEntity e: entities) {
            for(Pair<PotionEffectType,Integer> eff: toEffect) {
                if(hasEffectGreaterLevel(e,eff.frst,eff.snd)) {
                    PotionEffect lastEff = e.getPotionEffect(eff.frst);
                    assert lastEff != null;
                    e.addPotionEffect(new PotionEffect(eff.frst,duration*20,eff.snd+lastEff.getAmplifier(),true,true));
                } else {
                    e.addPotionEffect(new PotionEffect(eff.frst, duration * 20, eff.snd, true, true));
                }
            }
        }
    }

    private boolean hasEffectGreaterLevel(LivingEntity en, PotionEffectType type, int level) {
        for(PotionEffect eff: en.getActivePotionEffects()) {
            if(eff.getType() == type && eff.getAmplifier() > level) {
                return true;
            }
        }
        return false;
    }



    private boolean luck(int chance) {
        return getRnd(1,100) <= chance;
    }

    private int getRnd(int min, int max) {
        return rand.nextInt(max-min+1)+min;
    }






}
