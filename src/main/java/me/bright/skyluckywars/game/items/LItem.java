package me.bright.skyluckywars.game.items;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.Game;
import me.bright.skylib.utils.FormatChatColor;
import me.bright.skylib.utils.ItemBuilder;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.utils.EnchantmentSet;
import me.bright.skyluckywars.utils.Pair;
import me.bright.skyluckywars.utils.glowing.Glow;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public abstract class LItem {
    private String dpname;

    private List<String> lore;
    private Random rand;
    private boolean glowing;
    private Consumer<EntityDamageByEntityEvent> attackAction;
    private Consumer<PlayerInteractEvent> clickAction;
    private Game game;
    private Material material;
    private List<Material> materials;
    private List<Pair<Material,Integer>> materialsChances;
    private int minAmount = 1;
    private int maxAmount = 1;
    private Consumer<ItemStack> generateAction;
    private static Map<DropSet.DropCategory, EnchantmentSet> defaultEnchSets = new HashMap<>();

    public LItem(LItem.Type itemType, List<String> lore, Material material) {
        this.lore = lore;
        this.dpname = itemType.getDpName();
        this.material = material;
        this.rand = new Random();
    }

    public LItem(LItem.Type itemType, List<String> lore, Material material, int minAmount, int maxAmount) {
        this.lore = lore;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.dpname = itemType.getDpName();
        this.material = material;
        this.rand = new Random();
    }


    public LItem(LItem.Type itemType, List<String> lore, List<Material> materials) {
        this.lore = lore;
        this.dpname = itemType.getDpName();
        this.materials = materials;
        this.rand = new Random();
    }

    public LItem(Material material, int minAmount, int maxAmount) {
        this.material = material;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.rand = new Random();
    }

    public LItem(String dpName, Material material, int minAmount, int maxAmount) {
        this.material = material;
        this.dpname = Messenger.color(dpName);
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.rand = new Random();
    }


    public LItem(LItem.Type itemType, List<String> lore, Pair<Material,Integer>... materialsChances) {
        this.lore = lore;
        this.dpname = itemType.getDpName();
        List<Pair<Material,Integer>> list = Arrays.asList(materialsChances);
        list.sort(new Comparator<Pair<Material, Integer>>() {
            @Override
            public int compare(Pair<Material, Integer> materialIntegerPair, Pair<Material, Integer> t1) {
                if(materialIntegerPair == t1) return 0;
                if(materialIntegerPair.snd < t1.snd) return -1;
                return 1;
            }
        });
        this.materialsChances = list;
        this.rand = new Random();
    }

    public LItem(Pair<Material,Integer>... materialsChances) {
        List<Pair<Material,Integer>> list = Arrays.asList(materialsChances);
        list.sort(new Comparator<Pair<Material, Integer>>() {
            @Override
            public int compare(Pair<Material, Integer> materialIntegerPair, Pair<Material, Integer> t1) {
                if(materialIntegerPair == t1) return 0;
                if(materialIntegerPair.snd < t1.snd) return -1;
                return 1;
            }
        });
        this.materialsChances = list;
        this.rand = new Random();
    }

    public LItem(List<Material> materials) {
        this.materials = materials;
        this.rand = new Random();
    }

    public LItem(Material material) {
        this.material = material;
        this.rand = new Random();
    }

    public LItem(LItem.Type itemType, Material material) {
        this.material = material;
        this.lore = null;
        this.dpname = itemType.getDpName();
        this.rand = new Random();
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    protected Random getRand() {
        return rand;
    }

    public void setAttackAction(Consumer<EntityDamageByEntityEvent> attackAction) {
        this.attackAction = attackAction;
    }

    public void setClickAction(Consumer<PlayerInteractEvent> clickAction) {
        this.clickAction = clickAction;
    }

    public Consumer<EntityDamageByEntityEvent> getAttackAction() {
        return attackAction;
    }

    public Consumer<PlayerInteractEvent> getClickAction() {
        return clickAction;
    }

    public void setGenerateAction(Consumer<ItemStack> action) {
        this.generateAction = action;
    }

    public abstract DropSet.DropCategory getDropCategory();


    public Consumer<ItemStack> getGenerateAction() {
        return generateAction;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getDisplayName() {
        return dpname;
    }

    public int getRnd(int min, int max) {
        return rand.nextInt(max-min+1)+min;
    }

    public boolean luck(int chance) {
        return new Random().nextInt(101)+1 < chance;
    }

    public ItemStack generate(SPlayer sp) {
        ItemBuilder builder = null;
        game = sp.getGame();
        if(materials != null) {
            builder = new ItemBuilder(materials.get(getRnd(0,materials.size()-1)));
        } else if(materialsChances != null) {
            int rndnum = getRnd(1,100);
            for(Pair<Material,Integer> p: materialsChances) {
                if(rndnum <= p.snd) {
                    builder = new ItemBuilder(p.frst);
                    break;
                }
            }
            if(builder == null) builder = new ItemBuilder(Material.BEDROCK);
         //  if(rndnum <= chance) {
         //      builder = new ItemBuilder(materialsChances.get(0).frst);
         //  } else {
         //      // Гарантируется что сумма шансов == 100
         //      for (int i = 1; i < materialsChances.size(); i++) {
         //          int mxchance = chance+materialsChances.get(i).snd;
         //          if(rndnum > chance && rndnum <= mxchance) {
         //          }
         //          chance = mxchance;
         //      }
         //      if(builder == null) {
         //          Bukkit.getLogger().info("Administrator, you have chances which sum not equal 100!");
         //          builder = new ItemBuilder(materialsChances.get(0).frst);
         //      }
         //  }
        } else {
            builder = new ItemBuilder(material);
        }
        if(getDisplayName() != null) {
            builder = builder.setName(getDisplayName());
        }
        if(lore != null) {
            builder = builder.setLore(Messenger.color(lore));
        }
        if(getAttackAction() != null) {
            builder = builder.setAttackAction(game,getAttackAction());
        }
        if(getClickAction() != null) {
            builder = builder.setAction(game, getClickAction());
        }
        ItemStack item = builder.create();
        if(this instanceof Enchantable) {
            setEnchantments(item,sp, ((Enchantable)this).getEnchSet(),getDropCategory());
        }
        item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setAmount(getRnd(minAmount,maxAmount));
        if(glowing) item.addEnchantment(new Glow(sp.getGame().getArena().getPlugin()),0);
        if(generateAction != null) getGenerateAction().accept(item);
        return item;
    }

    private void setEnchantments(ItemStack item, SPlayer sp, EnchantmentSet enchSet, DropSet.DropCategory category) {
        enchSet.setEnchantments(item,sp,category);
    }

    public void decreaseAmountItemInHand(Player p) {
        int oldCount = p.getInventory().getItemInMainHand().getAmount();
        if(oldCount > 1) {
            ItemStack newItem = p.getInventory().getItemInMainHand();
            newItem.setAmount(oldCount-1);
            p.getInventory().setItemInMainHand(newItem);
        } else {
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
    }

    private static void generateDefaultEnchSet() {
        defaultEnchSets.put(DropSet.DropCategory.ARMOR, new EnchantmentSet()
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(10, Arrays.asList(4,5))))
                .addEnchantment(Enchantment.PROTECTION_FIRE,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(10, Arrays.asList(4,5))))
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(10, Arrays.asList(4,5))))
                .addEnchantment(Enchantment.PROTECTION_EXPLOSIONS,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(10, Arrays.asList(4,5))))
                .addEnchantment(Enchantment.THORNS,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(10, Arrays.asList(4,5))))
                .addEnchantment(Enchantment.PROTECTION_FALL,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(10, Arrays.asList(4,5))))
                .addEnchantment(Enchantment.DURABILITY,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2)),
                        new Pair<>(10, Arrays.asList(3))))
            );

        defaultEnchSets.put(DropSet.DropCategory.SWORD,new EnchantmentSet()
                .addEnchantment(Enchantment.DAMAGE_ALL,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(10, Arrays.asList(4,5))))
                .addEnchantment(Enchantment.FIRE_ASPECT,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(25,Arrays.asList(2))))
                .addEnchantment(Enchantment.KNOCKBACK,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(25,Arrays.asList(2))))
                .addEnchantment(Enchantment.DURABILITY,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2)),
                        new Pair<>(10, Arrays.asList(3))))
                );
        defaultEnchSets.put(DropSet.DropCategory.TOOLS,new EnchantmentSet()
                .addEnchantment(Enchantment.DIG_SPEED,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(10, Arrays.asList(4,5))))
                .addEnchantment(Enchantment.DURABILITY,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(50,Arrays.asList(2)),
                        new Pair<>(20,Arrays.asList(3))))
                );

        defaultEnchSets.put(DropSet.DropCategory.BOW,new EnchantmentSet()
                .addEnchantment(Enchantment.ARROW_DAMAGE,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(40,Arrays.asList(2,3)),
                        new Pair<>(15,Arrays.asList(4,5))))
                .addEnchantment(Enchantment.ARROW_KNOCKBACK,Arrays.asList(
                        new Pair<>(100,Arrays.asList(1)),
                        new Pair<>(25,Arrays.asList(2))))
                .addEnchantment(Enchantment.ARROW_FIRE,Arrays.asList(
                        new Pair<>(50,Arrays.asList(1))))
                .addEnchantment(Enchantment.ARROW_INFINITE,Arrays.asList(
                        new Pair<>(50,Arrays.asList(1))))
                        .addEnchantment(Enchantment.DURABILITY,Arrays.asList(
                                new Pair<>(100,Arrays.asList(1)),
                                new Pair<>(50,Arrays.asList(2)),
                                new Pair<>(20,Arrays.asList(3))))
                );

    }

    public static EnchantmentSet getDefaultEnchSet(DropSet.DropCategory category) {
        if(defaultEnchSets.isEmpty()) {
            generateDefaultEnchSet();
        }
        return defaultEnchSets.get(category);
    }


    public abstract int getChance();

    public enum Type {
        FURY_SWORD("§cМеч ярости",false),
        VAMPIRESWORD("§2Меч вампира",false),
        SWORD_SOUL("§6Пожиратель душ",false),
        GROM_SWORD("§bШтурмовой меч",false),
        GLOW_DEAD_SWORD(FormatChatColor.stylish("{#133BFC}Ослепляющий меч"),true),
        BERSERK(FormatChatColor.stylish("§cБерсерк"),true),
        SPHERE("§bСфера",false),
        RODE_HOOK("§aУдочка-Крюк",false),
        PEARL("§bЖемчуг эндера",false),
        FIREBALL("§cОгненный шар",false),
        TNT("§cДинамит",false),
        FIREWORK("§cФеерверк",false),
        NETHER_PLATFORM("§aОбычная платформа",false),
        SLIME_PLATFORM("§aСлаймовая платформа",false),
        POSITIVE_POTION("§eЭльфийский эль",false),
        NEGATIVE_POTION("§9Дьявольское зелье",false),
        MAGIC_POTION("§dЗагадочное зелье",false),
        POISON_FLASK("§4Колба с ядом",false),
        DEFAULT_BOW("§fЛук",false),
        CAT_BOW("§aКошачий лук",false),

        ZEUS_BOW("§bЛук зевса",false),
        TNT_BOW("§cВзрывной лук",false),
        SPIDER_BOW("§fПаучий лук",false),
        FURY_BOW("§cЛук ярости",false),
        FROZEN_BOW("§bЛук заморозки",false),
        SNAKE_BOW("§2Змеиный лук",false),
        SPEED_BOOTS("§aЭльфийские сапоги",false),
        GUARDIAN_BOOTS("§eБотинки стража",false),
        ZOMBIE_SPAWN_EGG("§aЯйцо призыва",false),
        BLAZE_SPAWN_EGG("§6Яйцо призыва",false),
        CREEPER_SPAWN_EGG("§2Яйцо призыва",false),
        SNOWBALL_FLAKE("§6Чешуйный снежок",false),
        CHIKEN_EGG("§6Мутировавшее яйцо",false),
        CREEPER("§2Creeper",false),
        BLAZE("§6Blaze",false),
        ZOMBIE("§aZombie",false);

        String name;
        boolean hex;
        Type(String dpname, boolean hex) {
            name = dpname;
            this.hex = hex;
        }

        public String getDpName() {
            return name;
        }

        public boolean isHex() { return hex; }
    }
}


