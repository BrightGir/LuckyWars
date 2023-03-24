package me.bright.skyluckywars.game.states;

import me.bright.skylib.game.Game;
import me.bright.skylib.game.states.WaitingState;
import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.game.LGame;
import org.bukkit.entity.Player;

public class LWaitingState extends WaitingState {

    public LWaitingState(LGame game) {
        super(game);
    }

    @Override
    public void startState() {
        setDefaultStatesOfPlayers();
    }

    @Override
    public void endAction() {

    }

    @Override
    public void setDefaultStateOfPlayer(Player player) {
      //  pl.getLogger().info("setdefstate");
    }

    @Override
    public int getUpdateScoreboardDelay() {
        return 1;
    }

    @Override
    public int getCounterSeconds() {
        return 60;
    }
}
