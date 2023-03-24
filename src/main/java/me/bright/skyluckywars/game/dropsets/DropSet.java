package me.bright.skyluckywars.game.dropsets;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.Game;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.items.armor.*;
import me.bright.skyluckywars.game.items.bows.*;
import me.bright.skyluckywars.game.items.other.blocks.*;
import me.bright.skyluckywars.game.items.unqiue.eggs.BlazeSpawnEgg;
import me.bright.skyluckywars.game.items.unqiue.eggs.CreeperSpawnEgg;
import me.bright.skyluckywars.game.items.unqiue.eggs.ZombieSpawnEgg;
import me.bright.skyluckywars.game.items.unqiue.potions.MagicPotion;
import me.bright.skyluckywars.game.items.unqiue.potions.NegativePotion;
import me.bright.skyluckywars.game.items.unqiue.potions.PoisonFlask;
import me.bright.skyluckywars.game.items.unqiue.potions.PositivePotion;
import me.bright.skyluckywars.game.items.other.vanillaother.*;
import me.bright.skyluckywars.game.items.swords.*;
import me.bright.skyluckywars.game.items.tools.Axe;
import me.bright.skyluckywars.game.items.tools.Pickaxe;
import me.bright.skyluckywars.game.items.tools.Spade;
import me.bright.skyluckywars.game.items.unqiue.*;
import me.bright.skyluckywars.utils.glowing.Glow;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DropSet {

    private List<ItemStack> items;
    private static final Map<DropCategory,List<LItem>> categoryItems = new HashMap<>();

    private Game game;
    private Random rand;
    private SPlayer sp;

    public DropSet(SPlayer sp) {
        rand = new Random();
        items = new ArrayList<>();
        this.game = sp.getGame();
        this.sp = sp;
    }

    public SPlayer getSPlayer() {
        return sp;
    }


    public int getRnd(int min, int max) {
        if(rand == null) {
            rand = new Random();
        }
        return rand.nextInt(max-min+1)+min;
    }
    public void addItem(LItem item) {
        items.add(item.generate(sp));
    }

    public void addItem(LItem item, boolean glowing) {
        ItemStack endItem = item.generate(sp);
        endItem.addEnchantment(new Glow(getSPlayer().getArena().getPlugin()),0);
        items.add(endItem);
    }

    public void addItem(ItemStack item) {
        items.add(item);
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    private static void pullItem(LItem item) {
        DropCategory category = item.getDropCategory();
        List<LItem> list = categoryItems.getOrDefault(category,new ArrayList<>());
        list.add(item);
        categoryItems.put(category,list);
    }
    protected static void pull() {
        pullItem(new SwordFury());
        //pullItem(DropCategory.SWORD,new SwordSoul());
        pullItem(new VampireSword());
        pullItem(new Sword());
        pullItem(new Tridient());
        pullItem(new GlowDeadSword());
        pullItem(new GromSword());

        //  pullItem(DropCategory.ARMOR,new SpeedBoots());
        //    pullItem(DropCategory.ARMOR,new GuardianBoots());
        pullItem(new Boots());
        pullItem(new Helmet());
        pullItem(new Leggings());
        pullItem(new ChestPlate());

        pullItem(new Axe());
        pullItem(new Pickaxe());
        pullItem(new Spade());

        pullItem(new SlimePlatform());
      //  pullItem(DropCategory.HELPING,new Sphere());
        pullItem(new Pearl());

        pullItem(new DefaultBow());
        pullItem(new CrossBow());
        pullItem(new SnakeBow());
     //   pullItem(new FuryBow());
        pullItem(new FrozenBow());
        pullItem(new TntBow());
        pullItem(new ZeusBow());

        pullItem(new CreeperSpawnEgg());
        pullItem(new BlazeSpawnEgg());
        pullItem(new ZombieSpawnEgg());

        pullItem(new OakPlanks());
        pullItem(new Stone());
        pullItem(new CobbleStone());
        pullItem(new Dirt());
        pullItem(new MilkBucket());

        pullItem(new TNT());
     //   pullItem(DropCategory.OTHER,new NetherPlatform());
        pullItem(new LFireball());
       // pullItem(new PoisonFlask());
        pullItem(new PositivePotion());
        pullItem(new MagicPotion());
        pullItem(new NegativePotion());
        pullItem(new ChikenEgg());
        pullItem(new SnowballFlake());
        pullItem(new Berserk());
    //    pullItem(DropCategory.UNIQUE,new RodeHook());


        pullItem(new Elytra());
      //  pullItem(DropCategory.OTHER,new Totem());
        pullItem(new Gaple());
        pullItem(new Arrow());
    //    pullItem(DropCategory.OTHER,new Shield());
        pullItem(new Snowball());
        pullItem(new Egg());
     //   pullItem(DropCategory.OTHER,new FlintSteel());
     //   pullItem(DropCategory.OTHER,new FlintSteel());
        pullItem(new Cobweb());
        pullItem(new SuperGapple());
        pullItem(new Luckyblock());
        pullItem(new WaterBucket());
        pullItem(new Firework());

        //    pullItem(DropCategory.BOW,new CatBow());
        //    pullItem(DropCategory.BOW,new SpiderBow());
        //    pullItem(DropCategory.BOW,new TntBow());
    }

    protected Map<DropCategory,List<LItem>> getCategoryItems() {
        if(categoryItems.isEmpty()) pull();
        return categoryItems;
    }

    public boolean luck(int chance) {
        if(rand == null) {
            rand = new Random();
        }
        return rand.nextInt(101)+1 <= chance;
    }

    public Game getGame() {
        return game;
    }

    public enum DropCategory {
        SWORD,
        ARMOR,
        TOOLS,
        EGG,
        OTHER,
        HELPING,
        BLOCKS,
        UNIQUE,
        BOW;
    }


}

