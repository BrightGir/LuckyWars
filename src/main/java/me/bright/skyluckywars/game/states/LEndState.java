package me.bright.skyluckywars.game.states;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.Game;
import me.bright.skylib.game.states.EndState;
import me.bright.skylib.teams.Team;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.database.GameConnectorMySQL;
import me.bright.skyluckywars.database.GameInfoMySQL;
import me.bright.skyluckywars.database.LDbType;
import me.bright.skyluckywars.game.LGame;
import me.bright.skyluckywars.game.LInfo;
import me.bright.skyluckywars.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.*;

public class LEndState extends EndState {
    public LEndState(LGame game) {
        super(game);
    }

    @Override
    public Team getWinner() {
        return null;
    }

    @Override
    public int getDefaultEndSeconds() {
        return 21;
    }

    @Override
    public void actionStartState() {

        if(getGame().getLivePlayers().size() >= 2) {
            List<Player> winners = getWinnersByKills();
            if (winners.size() != 1) {
                winners = getWinnersByLuckyBlocks(winners);
            }
            try {
                Player winner = winners.get(Messenger.rnd(0, winners.size() - 1));
                getGame().setWinner(SPlayer.getPlayer(winner).getTeam());
            } catch (Exception e) {

            }
        }
        LuckyWars pl =  ((LuckyWars)getGame().getArena().getPlugin());
        for(Player p: getGame().getPlayers()) {
            p.setGameMode(GameMode.ADVENTURE);
            getGame().getScoreboardManager().setBoard(SPlayer.getPlayer(p));
            if(pl.getGameInfoMysql().getConnection() != null) {
                updateInformation(SPlayer.getPlayer(p),pl.getGameInfoMysql(),LDbType.KIILS);
                updateInformation(SPlayer.getPlayer(p),pl.getGameInfoMysql(),LDbType.GAMES);
            }
        }
        String nameWinner = null;
        if(getGame().getWinner() != null) {
            for(String name: getGame().getWinner().getPlayers()) {
                nameWinner = name;
                Player p = Bukkit.getPlayer(name);
                Messenger.sendTitle(p,"&aПобеда",null);
                if (pl.getGameInfoMysql().getConnection() != null) {
                    updateInformation(SPlayer.getPlayer(p), pl.getGameInfoMysql(), LDbType.WINS, 1);
                }
            }
        }
      //  Bukkit.getLogger().info(nameWinner + " nameWinner");
        Map<String, Integer> top = sortByComparator(((LGame)getGame()).getMapKills(),false);
        getGame().broadCastColor(" ");
        getGame().broadCastColor(" &e&lЛучшие игроки");
        for(int i = 0; i < 3; i++) {
            String s1 = "&cN/A";
            String s2 = "&cN/A";

        }
        int i = 1;
        for(Map.Entry entry: top.entrySet()) {
            if(i == 4) break;
            int kills = (int)entry.getValue();
            String addStr = "";
            String name = (String)entry.getKey();

            if(name == nameWinner) {
                addStr = " &6(Победитель)";
            }
            getGame().broadCastColor(" &f" + i + ". &a" + entry.getKey() + " - " + kills + " " +
                    Messenger.correct(kills,"убийство","убийства","убийств") + addStr);
            i++;
        }
        while(i <= 3) {
            String s = "&cN/A";
            getGame().broadCastColor(" " + i + ". " + s + " - " + s);
            i++;
        }
        getGame().broadCastColor(" ");
        /*
             chanceLevels.sort(new Comparator<Pair<Integer, List<Integer>>>() {
            @Override

            public int compare(Pair<Integer, List<Integer>> integerListPair, Pair<Integer, List<Integer>> t1) {
                if(integerListPair.frst == t1.frst) return 0;
                if(integerListPair.frst < t1.frst) return -1;
                return 1;
            }
        });
         */


        getGame().broadCastColor("&aИгра закончена");
    }

    @Override
    public void endAction() {

    }

    private List<Player> getWinnersByKills() {
        int maxKills = 0;
        List<Player> list = new ArrayList<>();
        for (Player p : getGame().getPlayers()) {
            maxKills = Math.max(maxKills,((LGame)getGame()).getKills(p));
        }
        for (Player p : getGame().getPlayers()) {
            if(((LGame)getGame()).getKills(p) == maxKills) {
                list.add(p);
            }
        }
        return list;
    }
    private List<Player> getWinnersByLuckyBlocks(List<Player> splayers) {
        int maxLuckyblocks = 0;
        List<Player> list = new ArrayList<>();
        for (Player sp : splayers) {
            maxLuckyblocks = Math.max(maxLuckyblocks,
                    ((LGame)getGame()).getLBlocks(sp));
        }
        for (Player sp : splayers) {
            if((((LGame)getGame()).getLBlocks(sp)) == maxLuckyblocks) {
                list.add(sp);
            }
        }
        return list;
    }


    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });
        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private void updateInformation(SPlayer sp, GameInfoMySQL sql, LDbType type) {
        int kills = (int) sql.get("SELECT * WHERE \"uuid\" = \"" + sp.getPlayer().getUniqueId() + "\"", type.getDbStringName());
        sql.updateData(sp.getPlayer(),type.getDbStringName(),kills+(int)sp.getInfoOrDefault(type.getDbStringName(),0));
    }

    private void updateInformation(SPlayer sp, GameInfoMySQL sql, LDbType type, int incrementedValue) {
        int kills = (int) sql.get("SELECT * WHERE \"uuid\" = \"" + sp.getPlayer().getUniqueId() + "\"", type.getDbStringName());;
        sql.updateData(sp.getPlayer(),type.getDbStringName(),kills+incrementedValue);
    }


    @Override
    public void setDefaultStateOfPlayer(Player player) {

    }

    @Override
    public int getUpdateScoreboardDelay() {
        return 0;
    }
}
