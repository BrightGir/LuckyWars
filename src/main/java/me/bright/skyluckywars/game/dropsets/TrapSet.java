package me.bright.skyluckywars.game.dropsets;

import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.traps.*;
import org.bukkit.Location;


import java.util.ArrayList;
import java.util.List;

public class TrapSet {

    private static List<Trap> traps = new ArrayList<>();

    private void pullSet() {
        traps.add(new SpiderSkelet());
        traps.add(new Cobwebs());
        traps.add(new LavaTrap());
        traps.add(new TntTrap());
         traps.add(new ZombieTrap());
        traps.add(new FoxTrap());
    }


    public void generate(Location location, SPlayer sPlayer, int minCount, int maxCount) {
        if(traps.size() <= 0) pullSet();
        int count = Messenger.rnd(minCount,maxCount);
        Trap trap = traps.get(Messenger.rnd(0,traps.size()-1));
        for(int i = 0; i < count; i++) {
            trap.generate(location,sPlayer);
        }
    }


    public void generate(Location location, SPlayer sPlayer) {
        if(traps.size() <= 0) pullSet();
        Trap trap = traps.get(Messenger.rnd(0,traps.size()-1));
        trap.generate(location,sPlayer);
    }
}
