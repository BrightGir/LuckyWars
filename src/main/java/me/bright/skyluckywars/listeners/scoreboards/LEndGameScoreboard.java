package me.bright.skyluckywars.listeners.scoreboards;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.Game;
import me.bright.skylib.scoreboard.game.EndGameSkelet;
import me.bright.skyluckywars.game.LInfo;

public class LEndGameScoreboard extends EndGameSkelet {
    public LEndGameScoreboard(Game game, SPlayer player) {
        super(game, player,"&b&lLuckyWars");
    }


    @Override
    public void updateLines() {
        String winnerName = "&cN/A";
        if(getGame().getWinner() != null) {
            for(String p: getGame().getWinner().getPlayers()) {
                winnerName = p;
            }
        }
        setLine(" ",10);
        setLine(" &fПобедитель",7);
        setLine(" &a" + winnerName ,6);
        setLine("   ",5);
        setLine(" &fИгра окончена",9);
        setLine("  ",8);
        setLine(" &fУбийств: &c" + getSPlayer().getInfoOrDefault(LInfo.KILLS.getKey(),0),4);
        setLine(" &fЛаки блоков: &e" + getSPlayer().getInfoOrDefault(LInfo.LUCKY_BLOCKS_BROKEN.getKey(),0),3);
        setLine("           ",2);
        setLine("   &fwww.SkyStorm.pro",1);
    }
}
