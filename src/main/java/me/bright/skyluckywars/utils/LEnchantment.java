package me.bright.skyluckywars.utils;

import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class LEnchantment {

    private Enchantment enchantment;
    private List<Pair<Integer, List<Integer>>> chancesLevels;
    public LEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
        this.chancesLevels = new ArrayList<>();
    }

    public LEnchantment setChanceLevels(List<Pair<Integer, List<Integer>>> chancesLevels) {
        this.chancesLevels = chancesLevels;
        return this;
    }

    /**
     *
     * @param chanceLevels - Пара шанс-уровни зачарования
     */
    public LEnchantment addChanceLevels(Pair<Integer,List<Integer>> chanceLevels) {
        chancesLevels.add(chanceLevels);
        return this;
    }

    public Enchantment getBukkitEnchantment() {
        return enchantment;
    }

    public List<Pair<Integer, List<Integer>>> getChancesLevels() {
        return chancesLevels;
    }


}
