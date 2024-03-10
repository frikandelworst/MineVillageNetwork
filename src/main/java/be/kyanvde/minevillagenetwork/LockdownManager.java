package be.kyanvde.minevillagenetwork;

import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;

public class LockdownManager {

    private static final HashMap<String, Boolean> lockdownInfo = new HashMap<>();
    private static boolean globalLockdown = false;

    private static final ProxyServer proxyServer = MineVillageNetwork.getServer();

    public static void setLockdown(String server, boolean status) {
        lockdownInfo.put(server, status);

        if(status) {
            proxyServer.getAllServers().forEach(registeredServer -> {
                if(registeredServer.getServerInfo().getName().equals(server)) {
                    registeredServer.getPlayersConnected()
                            .forEach(player -> {
                                if(!player.hasPermission("mvnetwork.lockdown.bypass")) player.disconnect(Component.text("The server you were on went into lockdown!", NamedTextColor.RED));
                            });
                }
            });
        }
    }

    public static void setLockdown() {
        globalLockdown =! globalLockdown;
        lockdownInfo.replaceAll((s, v) -> globalLockdown);

        if(globalLockdown) {
            proxyServer.getAllPlayers().forEach(player -> {
                if(!player.hasPermission("mvnetwork.lockdown.bypass")) player.disconnect(Component.text("The server you were on went into lockdown!", NamedTextColor.RED));
            });
        }
    }

    public static boolean getLockdown(String server) {
        return lockdownInfo.get(server) != null ? lockdownInfo.get(server) : true;
    }
}
