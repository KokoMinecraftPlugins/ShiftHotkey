package krmcplugins.kokored.website.shifthotkey.hotkey;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.Plugin;

import krmcplugins.kokored.website.shifthotkey.ShiftHotkey;
import krmcplugins.kokored.website.shifthotkey.util.Cooldown;

public class ShiftF implements Listener {

    Plugin plugin = ShiftHotkey.getPlugin(ShiftHotkey.class);

    public ShiftF() {
        if (!(plugin.getConfig().getBoolean("hotkeys.shift-f.enable"))) {
            return;
        }
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (plugin.getConfig().getBoolean("hotkeys.shift-f.shift") == true) {
            if (!(player.isSneaking())) {
                return;
            }
        }
        
        event.setCancelled(plugin.getConfig().getBoolean("hotkeys.shift-f.cancel-event"));

        if (Cooldown.isCooldown(player) != 0) {
            Cooldown.sendError(player);
            return;
        }

        List<String> commands = plugin.getConfig().getStringList("hotkeys.shift-f.commands");
        Cooldown.add(player);

        for (String command : commands) {
            if (plugin.getConfig().getBoolean("hotkeys.bukkit-dispatch") == true) {
                Bukkit.dispatchCommand(player, command);
            }else {
                player.chat("/" + command);
            }
        }
    }
    
}
