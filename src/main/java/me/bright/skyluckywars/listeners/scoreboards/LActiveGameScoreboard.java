package me.bright.skyluckywars.listeners.scoreboards;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.Game;
import me.bright.skylib.scoreboard.game.ActiveGameSkelet;
import me.bright.skyluckywars.game.LInfo;

public class LActiveGameScoreboard extends ActiveGameSkelet {

    public LActiveGameScoreboard(Game game, SPlayer player) {
        super(game, player,"&b&lLuckyWars");
    }


    @Override
    public void updateLines() {
        if(getSPlayer().isSpectator()) {
            setLine(" ",9);
            setLine(" &fКарта",8);
            setLine(" &a" + getGame().getMapname(),7);
            setLine("  ",6);
            setLine(" &cВы проиграли" ,5);
            setLine("   ",4);
            setLine(" &fИгроков: &a" + getGame().getLivePlayers().size() + "/" + getGame().getMaxPlayers(),3);
            setLine("    ",2);
            setLine("   &fwww.SkyStorm.pro",1);
            return;
        }
        setLine("       ",10);
        setLine(" &fКарта",9);
        setLine(" &a" + getGame().getMapname(),8);
        setLine("   ",7);
        setLine(" &fУбийств: &c" + getSPlayer().getInfoOrDefault(LInfo.KILLS.getKey(),0),6);
        setLine(" &fЛаки блоков: &e" + getSPlayer().getInfoOrDefault(LInfo.LUCKY_BLOCKS_BROKEN.getKey(),0),5);
        setLine("  ",4);
        setLine(" &fИгроков: &a" + getGame().getLivePlayersSize() + "/" + getGame().getMaxPlayers(),3);
        setLine(" ",2);
        setLine("   &fwww.SkyStorm.pro",1);
    }
}
