package me.bright.skyluckywars.game.dropsets;

import me.bright.skylib.SPlayer;
import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.utils.Pair;
import me.bright.skyluckywars.utils.glowing.Glow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BaseDropSet extends DropSet {

    private static List<Pair<Integer,Integer>> sizesChances = Arrays.asList(
            new Pair<>(6,5),
            new Pair<>(5,20),
            new Pair<>(4,40),
            new Pair<>(3,60),
            new Pair<>(2,80),
            new Pair<>(1,100)
    );

    public BaseDropSet(SPlayer sp) {
        super(sp);
    }


    public void generateItems(Location blockLocation) {
        int blocksBroken = (int)getSPlayer().getInfoOrDefault(LInfo.LUCKY_BLOCKS_BROKEN.getKey(),0);
        for(DropCategory category: DropCategory.values()){
                for (LItem item : getCategoryItems().getOrDefault(category,new ArrayList<>())) {
                    if (luck(item.getChance())) {
                        ItemStack endItem = item.generate(getSPlayer());
                        if(blocksBroken <= 4 && endItem.getType() == Material.ELYTRA) {
                            return;
                        }
                        addItem(endItem);
                    }
                }
        }
        int size = getItems().size();
        Collections.shuffle(getItems());
        List<ItemStack> newList = new ArrayList<>();
        int newSize = Math.min(size,getDropSize());
        int i = 0;
        while(i < newSize) {
            newList.add(getItems().get(i));
            i++;
        }
        setItems(newList);
    }

    private int getDropSize() {
        int c = getRnd(1,100);
        int size = 1;
        for (Pair<Integer,Integer> pair: sizesChances) {
            if(c <= pair.snd) {
                size = pair.frst;
                break;
            }
        }
        return size;
    }


}

