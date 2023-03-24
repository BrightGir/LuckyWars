package me.bright.skyluckywars.game.traps;

import me.bright.skylib.SPlayer;
import org.bukkit.Location;


import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Trap {

    private BiConsumer<Location,SPlayer> trapAction;

    public Trap() {

    }

    public void setTrapAction(BiConsumer<Location,SPlayer> action) {
        this.trapAction = action;
    }


    public void generate(Location location, SPlayer sPlayer) {
        trapAction.accept(location,sPlayer);
    }
}
