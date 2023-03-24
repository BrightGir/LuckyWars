package me.bright.skyluckywars.listeners.scoreboards;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.Game;
import me.bright.skylib.game.GameState;
import me.bright.skylib.game.states.WaitingState;
import me.bright.skylib.scoreboard.game.WaitingSkelet;
import me.bright.skylib.utils.Messenger;

public class LWaitingScoreboard extends WaitingSkelet {


    public LWaitingScoreboard(Game game, SPlayer player) {
        super(game, player,"&b&lLuckyWars");
    }

    @Override
    public void updateLines() {
        if(getGame().getState() == null || getGame().getState().getEnum() != GameState.WAITING) return;
        WaitingState state =  ((WaitingState) getGame().getState());
        if(state.isCounting()) {
            setLine("       ",10);
            setLine(" &fКарта",9);
            setLine(" &a" + getGame().getMapname(),8);
            setLine("   ",7);
            setLine(" &fНачало через",6);
            setLine(" &a" + state.getSecondsLeft() + " " + Messenger.correct(state.getSecondsLeft(),
                    "секунда","секунды","секунд"),5);
        } else {
            removeLine(10);
            setLine("                ",9);
            setLine(" &fКарта",8);
            setLine(" &a" + getGame().getMapname(),7);
            setLine("   ",6);
            setLine(" &fОжидаем игроков",5);
        }
        setLine("  ",4);
        setLine(" &fИгроков: &e" + getGame().getPlayersSize() + "/" + getGame().getMaxPlayers(),3);
        setLine(" ",2);
        setLine("   &fwww.SkyStorm.pro",1);
    }


}
