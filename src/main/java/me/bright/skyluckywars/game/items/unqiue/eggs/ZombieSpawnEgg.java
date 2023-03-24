package me.bright.skyluckywars.game.items.unqiue.eggs;

import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.game.Locationable;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class ZombieSpawnEgg extends LItem  {

  //  private Location location;

    public ZombieSpawnEgg() {
        super(Type.ZOMBIE_SPAWN_EGG, Arrays.asList(
                "&6Способность:",
                "&aСпавнит моба, который будет воевать за своего хозяина,",
                "&aдогонять других игроков, при этом будет лоялен к хозяину"), Material.ZOMBIE_SPAWN_EGG);
        this.setClickAction(event -> {
            // SPAWN ZOMBIE
            if(event.getClickedBlock() != null) {
                Location location = event.getClickedBlock().getLocation().add(0,2,0);

                JavaPlugin plugin = SPlayer.getPlayer(event.getPlayer()).getGame().getArena().getPlugin();
                Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE, CreatureSpawnEvent.SpawnReason.CUSTOM);
                zombie.setCustomName(event.getPlayer().getName());
                zombie.setCustomNameVisible(true);
                zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
                zombie.setHealth(20D);
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        equipArmor(zombie);
                        zombie.setMetadata(LInfo.MOB_OWNER.getKey(),
                                new FixedMetadataValue(plugin,event.getPlayer().getUniqueId().toString()));
                    }
                }.runTaskLater(plugin,1L);
                decreaseAmountItemInHand(event.getPlayer());
            }
         //   event.setCancelled(true);
        });
        setGlowing(true);
    }

    private void equipArmor(Zombie zombie) {
        ItemStack boots = new ItemStack((luck(50)) ? Material.DIAMOND_BOOTS : Material.IRON_BOOTS);
        ItemStack chestplate = new ItemStack((luck(50)) ? Material.DIAMOND_CHESTPLATE : Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack((luck(50)) ? Material.DIAMOND_LEGGINGS : Material.IRON_LEGGINGS);
        ItemStack helmet = new ItemStack((luck(50)) ? Material.DIAMOND_HELMET : Material.IRON_HELMET);
        ItemStack sword = new ItemStack((luck(50)) ? Material.DIAMOND_SWORD : Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL,3);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3);
        zombie.getEquipment().setBoots(boots);
        zombie.getEquipment().setChestplate(chestplate);
        zombie.getEquipment().setLeggings(leggings);
        zombie.getEquipment().setHelmet(helmet);
        zombie.getEquipment().setItemInMainHand(sword);
    }

    @Override
    public int getChance() {
        return 20;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.EGG;
    }


}
