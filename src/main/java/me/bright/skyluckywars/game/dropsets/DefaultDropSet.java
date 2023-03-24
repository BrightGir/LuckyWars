package me.bright.skyluckywars.game.dropsets;

import me.bright.skylib.SPlayer;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.items.armor.Boots;
import me.bright.skyluckywars.game.items.armor.ChestPlate;
import me.bright.skyluckywars.game.items.armor.Helmet;
import me.bright.skyluckywars.game.items.armor.Leggings;
import me.bright.skyluckywars.game.items.other.vanillaother.Arrow;
import me.bright.skyluckywars.game.items.other.vanillaother.Gaple;
import me.bright.skyluckywars.game.items.other.vanillaother.WaterBucket;
import me.bright.skyluckywars.game.items.tools.Axe;
import me.bright.skyluckywars.game.items.tools.Pickaxe;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultDropSet extends DropSet {

    private int defaultSize;
    public DefaultDropSet(SPlayer sp) {
        super(sp);
    }

    public void generateItems() {
        addItems();
        this.defaultSize = this.getItems().size();
    //    Bukkit.getLogger().info("generate items (df), def size = " + this.getItems().size());
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    private void addItems() {
        LItem lBlock = getCategoryItems().get(DropCategory.BLOCKS)
                .get(getRnd(0,getCategoryItems().get(DropCategory.BLOCKS).size()-1));
        lBlock.setMinAmount(24);
        lBlock.setMaxAmount(32);
        addItem(lBlock);
        addItem(new Boots());
        addItem(new Leggings());
        addItem(new ChestPlate());
        addItem(new Helmet());
        addItem(new Axe());
        addItem(new Pickaxe());
        addItem(new WaterBucket());

        addItem(getCategoryItems().get(DropCategory.SWORD)
                .get(getRnd(0,getCategoryItems().get(DropCategory.SWORD).size()-1)));
        addItem(getCategoryItems().get(DropCategory.BOW)
                .get(getRnd(0,getCategoryItems().get(DropCategory.BOW).size()-1)));
        addItem(new Arrow());
        addItem(getCategoryItems().get(DropCategory.HELPING)
                .get(getRnd(0,getCategoryItems().get(DropCategory.HELPING).size()-1)));
        addItem(new Gaple());
        Collections.shuffle(getItems());
    }


    public List<ItemStack> getPartItems() {
        if(defaultSize == this.getItems().size()) {
            List<ItemStack> list = new ArrayList<>();
            List<ItemStack> list2 = new ArrayList<>();
            int size = this.getItems().size();
            for (int i = 0; i < size / 2; i++) {
                list.add(this.getItems().get(i));
            }
            for (int i = size / 2; i < size; i++) {
                list2.add(this.getItems().get(i));
            }

            this.setItems(list2);
            return list;
        } else {
            List<ItemStack> r = this.getItems();
            this.setItems(new ArrayList<>());
            return r;
        }

    }

    public boolean isEmpty() {
        return this.getItems().isEmpty();
    }



}
