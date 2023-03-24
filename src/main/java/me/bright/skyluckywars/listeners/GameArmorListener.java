package me.bright.skyluckywars.listeners;

import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.game.events.armor.GuardianBootsEquip;
import me.bright.skyluckywars.game.events.armor.GuardianBootsOff;
import me.bright.skyluckywars.game.events.armor.SpeedBootsOff;
import me.bright.skyluckywars.game.events.armor.SpeedBotsEquip;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class GameArmorListener implements Listener {

    private LuckyWars plugin;
    private Random rand;
    private Map<UUID,Map<PotionEffectType,PotionEffect>> effects;


    public GameArmorListener(LuckyWars plugin) {
        this.plugin = plugin;
        this.rand = new Random();
        this.effects = new HashMap<>();
    }

    @EventHandler
    public void onSpeedBootsEquip(SpeedBotsEquip event) {
        Player p = event.getPlayer();
        PotionEffect effect = p.getPotionEffect(PotionEffectType.SPEED);
        if(effect == null || effect.getAmplifier() <=1) {
            if(effect != null) effects.get(p.getUniqueId()).put(PotionEffectType.SPEED,effect);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,1,false,false));
        }
    }

    @EventHandler
    public void onSpeedBootsOff(SpeedBootsOff event) {
        Player p = event.getPlayer();
        if(p.getPotionEffect(PotionEffectType.SPEED) != null) {
            p.removePotionEffect(PotionEffectType.SPEED);
            if(effects.get(p.getUniqueId()) != null && effects.get(p.getUniqueId()).get(PotionEffectType.SPEED) != null) {
                 p.addPotionEffect(effects.get(p.getUniqueId()).get(PotionEffectType.SPEED));
                 effects.get(p.getUniqueId()).remove(PotionEffectType.SPEED);
            }
        }
    }

    @EventHandler
    public void onGuardianBootsEquip(GuardianBootsEquip event) {
        Player p = event.getPlayer();
        PotionEffect damageEffect = p.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        PotionEffect slowEffect = p.getPotionEffect(PotionEffectType.SLOW);
        if(damageEffect == null) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,0,false,false));
        }
        if(slowEffect == null || slowEffect.getAmplifier() <= 1) {
            if(slowEffect != null) effects.get(p.getUniqueId()).put(PotionEffectType.SLOW,slowEffect);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,Integer.MAX_VALUE,1,false,false));
        }
    }

    @EventHandler
    public void onGuardianBootsOff(GuardianBootsOff event) {
        Player p = event.getPlayer();
        if(p.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) != null) {
            p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        }
        if(p.getPotionEffect(PotionEffectType.SLOW) != null) {
            p.removePotionEffect(PotionEffectType.SLOW);
            if(effects.get(p.getUniqueId()) != null && effects.get(p.getUniqueId()).get(PotionEffectType.SLOW) != null) {
                p.addPotionEffect(effects.get(p.getUniqueId()).get(PotionEffectType.SLOW));
            }
        }
    }
}
