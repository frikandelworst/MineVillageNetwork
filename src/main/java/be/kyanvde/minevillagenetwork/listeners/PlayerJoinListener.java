package be.kyanvde.minevillagenetwork.listeners;

import be.kyanvde.minevillagenetwork.LockdownManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PlayerJoinListener {

    @Subscribe
    public void onPlayerJoin(ServerPreConnectEvent event) {
        boolean isInLockdown = LockdownManager.getLockdown(event.getOriginalServer().getServerInfo().getName());

        if (isInLockdown) {
            if (event.getPlayer().hasPermission("mvnetwork.lockdown.bypass")) return;

            event.getPlayer().createConnectionRequest(event.getPreviousServer());
            event.getPlayer().sendMessage(Component.text("This server is currently in lockdown!", NamedTextColor.RED));
        }
    }
}
