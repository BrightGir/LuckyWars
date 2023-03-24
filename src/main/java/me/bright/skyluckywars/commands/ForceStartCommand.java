package me.bright.skyluckywars.commands;

import me.bright.skylib.SPlayer;
import me.bright.skylib.game.GameState;
import me.bright.skylib.game.states.WaitingState;
import me.bright.skylib.utils.ItemBuilder;
import me.bright.skylib.utils.Messenger;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ForceStartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        if(!sender.isOp()) {
            return true;
        }
        Player p = (Player)sender;
        SPlayer sp = SPlayer.getPlayer((Player) sender);
        if(sp.getGame() == null) {
            Messenger.send(sender,"&cВы не находитесь в игре");
            return true;
        }
        if(sp.getGame().getState() == null) {
            Messenger.send(sender,"&c[Ошибка] Состояние игры null");
            return true;
        }
        if(sp.getGame().getState().getEnum() != GameState.WAITING) {
            Messenger.send(sender,"&cИгра должна находиться на стадии ожидания");
            return true;
        }
        ((WaitingState)sp.getGame().getState()).stopCounting();
        sp.getGame().setState(GameState.ACTIVEGAME);
        Messenger.send(sender,"&aСтадия игры: Активная");
        return true;
    }
}
