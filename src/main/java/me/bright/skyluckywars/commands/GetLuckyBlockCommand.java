package me.bright.skyluckywars.commands;

import me.bright.skylib.SPlayer;
import me.bright.skylib.utils.FormatChatColor;
import me.bright.skylib.utils.Messenger;
import me.bright.skyluckywars.LuckyWars;
import me.bright.skyluckywars.game.items.unqiue.Pearl;
import me.bright.skyluckywars.game.items.unqiue.SlimePlatform;
import me.bright.skyluckywars.game.items.unqiue.TNT;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class GetLuckyBlockCommand implements CommandExecutor {
    private LuckyWars plugin;
    public GetLuckyBlockCommand(LuckyWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        if(!sender.isOp()) {
            return true;
        }
        ((Player)sender).getInventory().addItem(plugin.getLuckyBlock());
       // ((Player)sender).getInventory().addItem(new TNT().generate(SPlayer.getPlayer((Player)sender)));
        ((Player)sender).getInventory().addItem(new SlimePlatform().generate(SPlayer.getPlayer((Player)sender)));
        //((Player)sender).getInventory().addItem(new Sphere().generate(SPlayer.getPlayer((Player)sender)));

        return true;
    }

    private void createSquareDeltXZ(Location loc, int dx0, int dz0, Material filledMaterial, Material anglesMaterial) {

        if(dx0 < 0) dx0++;
        else dx0--;

        if(dz0 < 0) dz0++;
        else dz0--;

        int dx = dx0;
        while((dx0 < 0) ? dx <= 0 : dx >= 0) {
            createLineZ(loc,dx,dz0,filledMaterial);
            int addX = (dx0 < 0) ? 1 : -1;
            dx += addX;
        }

        loc.clone().add(0,0,0).getBlock().setType(anglesMaterial);
        loc.clone().add(dx0,0,dz0).getBlock().setType(anglesMaterial);
        loc.clone().add(0,0,dz0).getBlock().setType(anglesMaterial);
        loc.clone().add(dx0,0,0).getBlock().setType(anglesMaterial);

    }

    private void createLineZ(Location loc, int dx, int dz0, Material mat) {
        int dz = dz0;
        while( ((dz0 < 0) ? dz <= 0 : dz >= 0)) {
            loc.clone().add(dx,0,dz).getBlock().setType(mat);
            int addZ = (dz0 < 0) ? 1 : -1;
            dz += addZ;
        }
    }

}
