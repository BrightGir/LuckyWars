package me.bright.skyluckywars.database;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import me.bright.skylib.database.Creatable;
import me.bright.skylib.database.MySQL;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.game.LGame;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.SQLIntegrityConstraintViolationException;

public class GameConnectorMySQL extends MySQL implements Creatable {

    private FileConfiguration config;
    private String table;

    public GameConnectorMySQL(FileConfiguration conf) {
        config = conf;
        table = conf.getString("settings.db.lwtable");
    }

    public String getTable() {
        return table;
    }

    @Override
    public String getDbHost() {
        return config.getString("settings.db.host");
    }

    @Override
    public String getDbName() {
        return config.getString("settings.db.dbname");
    }

    @Override
    public String getTableName() {
        return table;
    }

    public void insertGame(LGame game) {
        String state = (game.getState() == null) ? null : game.getState().getEnum().toString();
            execute("INSERT INTO " + getTable() +
                            " (" + LDbType.GAMENAME.getDbStringName() + ", " +
                            LDbType.GAME_MODE.getDbStringName() + ", " +
                            LDbType.PLAYERS_COUNT.getDbStringName() + ", " +
                            LDbType.HOSTPORT.getDbStringName() + ", " +
                            LDbType.STATUS + ") VALUES(?, ?, ?, ?, ?);",
                    game.getName(), game.getMode().toString(), game.getPlayersSize(), game.getHostport(), state);

    }

    public void updateInformation(LGame game) {
        String gamestate = game.getState() == null ? null : game.getState().getEnum().toString();
        String query = "UPDATE " + table + " SET "
                + LDbType.PLAYERS_COUNT.getDbStringName() + " = " + game.getPlayersSize() +
                ", " + LDbType.STATUS.getDbStringName() + " = \"" + gamestate +
                "\" WHERE " + LDbType.GAMENAME.getDbStringName() + " = \"" + game.getName() + "\";";
        execute(query);
    }

    @Override
    public int getDbPort() {
        return config.getInt("settings.db.port");
    }

    @Override
    public String getDbUser() {
        return config.getString("settings.db.user");
    }

    @Override
    public String getDbPassword() {
        return config.getString("settings.db.password");
    }

    @Override
    public String getCreateTableString() {
        String query =  "CREATE TABLE IF NOT EXISTS " + table + "(" +
                LDbType.HOSTPORT.getDbStringName() + " varchar(36) NOT NULL," +
                LDbType.GAMENAME.getDbStringName() + " varchar(36) NOT NULL," +
                LDbType.STATUS.getDbStringName() + " varchar(36)," +
                LDbType.GAME_MODE.getDbStringName() + " varchar(36) NOT NULL," +
                LDbType.PLAYERS_COUNT.getDbStringName() + " int(11) NOT NULL," +
                "PRIMARY KEY (" + LDbType.GAMENAME.getDbStringName() + ")" +
                ");";
        return  query;
    }
}
