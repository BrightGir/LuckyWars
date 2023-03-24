package me.bright.skyluckywars.game.items.other.blocks;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.UUID;

public class Luckyblock extends LItem {

    public Luckyblock() {
        super(Constants.LUCKY_BLOCK_NAME,Material.PLAYER_HEAD, 1,3);
        setGenerateAction(item -> {
            setSkin(item,
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQ" +
                            "ubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUy" +
                            "M2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ==");
        });

    }

    public static void setSkin(ItemStack skull, String base64EncodedString) {
        //final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64EncodedString));
        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(meta);
    }


    @Override
    public int getChance() {
        return 15;
   //     return 100;
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.OTHER;
    }
}
