package me.bright.skyluckywars.utils;

import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.game.dropsets.DropSet;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;

import java.nio.Buffer;
import java.util.*;

public class EnchantmentSet implements Cloneable{

    //private HashMap<>;
    private List<LEnchantment> enchs;
    private Map<Enchantment, LEnchantment> mapEnchs;
    private Random random;

    //DropCategory - List<Пара<Количество зачарвоаний, Шанс>>
    private static Map<DropSet.DropCategory,List<Pair<Integer,Integer>>> sizeEnchsByCategory = new HashMap<>();


    public EnchantmentSet() {
        this.enchs = new ArrayList<>();
        this.mapEnchs = new HashMap<>();
        this.random = new Random();
    }

    protected void setMapEnchs(Map<Enchantment, LEnchantment> mapEnchs) {
        this.mapEnchs = mapEnchs;
    }

    protected void setEnchs(List<LEnchantment> enchs) {
        this.enchs = enchs;
    }
    /**
     * @param ench - Тип зачарования
     * @param chanceLevels - Список пар Шанс-Уровни зачарования
       */
    public EnchantmentSet addEnchantment(Enchantment ench, List<Pair<Integer, List<Integer>>> chanceLevels) {
        // Sort chanceLevels, after set!!!!!!!!
        chanceLevels.sort(new Comparator<Pair<Integer, List<Integer>>>() {
            @Override
            public int compare(Pair<Integer, List<Integer>> integerListPair, Pair<Integer, List<Integer>> t1) {
                if(integerListPair.frst == t1.frst) return 0;
                if(integerListPair.frst < t1.frst) return -1;
                return 1;
            }
        });
    //    Bukkit.getLogger().info("add enchantment = (name=" + ench.getName() + ")");
        LEnchantment lench = new LEnchantment(ench).setChanceLevels(chanceLevels);
        enchs.add(lench);
        mapEnchs.put(ench,lench);
        return this;
    }

    public void removeEnchantment(Enchantment enchantment) {
        LEnchantment ench = mapEnchs.get(enchantment);
        if(ench != null) {
            enchs.remove(ench);
            mapEnchs.remove(enchantment);
        }
    }

    public List<LEnchantment> getEnchantments() {
        return enchs;
    }

    public void setEnchantments(ItemStack item, SPlayer sp, DropSet.DropCategory dropCategory) {
        List<Pair<Enchantment,Integer>> list = new ArrayList<>();
        for(LEnchantment ench: enchs) {
            int c = random.nextInt(100)+1;
            for(Pair<Integer,List<Integer>> p: ench.getChancesLevels()) {
                // p.frst - Шанс
                if(c <= p.frst) {
                    int level = p.snd.get(getRnd(p.snd.size()-1,0));
                    int lb = (int)sp.getInfoOrDefault(LInfo.LUCKY_BLOCKS_BROKEN.getKey(),0);
                    if(!((lb <= 1 && level > 2) || (lb<=3&&level>4))){
                        if(level-1 != 0) {
                            list.add(new Pair<>(ench.getBukkitEnchantment(),level));
                        }
                        break;
                    }
                }
            }
        }
        int size = Math.min(list.size(), getEnchantmentSize(dropCategory));
        Collections.shuffle(list);
        for(int i = 0; i < size; i++) {
        //    Bukkit.getLogger().info("set ench (name= " + list.get(i).frst.getName() + ")");
            item.addUnsafeEnchantment(list.get(i).frst,list.get(i).snd);
        }

    }

    private int getEnchantmentSize(DropSet.DropCategory category) {
        if(sizeEnchsByCategory.isEmpty()) {
            pullMapEnchsSizeByCategory();
        }
        int size = 0;
        int c = Messenger.rnd(1,100);
        for (Pair<Integer,Integer> pair: sizeEnchsByCategory.get(category)) {
            if(c <= pair.snd) {
                size = pair.frst;
                break;
            }
        }
        return size;
    }

    private static void pullMapEnchsSizeByCategory() {
        // Не будем сортировать, так как это заполнено уже отсортированно
        sizeEnchsByCategory.put(DropSet.DropCategory.ARMOR,Arrays.asList(
                        new Pair<>(5,20),
                        new Pair<>(4,40),
                        new Pair<>(3,55),
                        new Pair<>(2,70),
                        new Pair<>(1,80)
                )
        );

        sizeEnchsByCategory.put(DropSet.DropCategory.SWORD,Arrays.asList(
                        new Pair<>(4,20),
                        new Pair<>(3,40),
                        new Pair<>(2,60),
                        new Pair<>(1,80)
                )
        );

        sizeEnchsByCategory.put(DropSet.DropCategory.BOW,Arrays.asList(
                        new Pair<>(4,10),
                        new Pair<>(3,20),
                        new Pair<>(2,30),
                        new Pair<>(1,40)
                )
        );

        sizeEnchsByCategory.put(DropSet.DropCategory.TOOLS,Arrays.asList(
                        new Pair<>(2,30),
                        new Pair<>(1,60)
                )
        );
    }

    private int getRnd(int max, int min) {
        return random.nextInt(max-min+1)+min;
    }

    @Override
    public EnchantmentSet clone() {
            EnchantmentSet clone = new EnchantmentSet();
            clone.setMapEnchs(mapEnchs);
            clone.setEnchs(enchs);
            return clone;

    }
}
