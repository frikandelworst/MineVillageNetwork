package be.kyanvde.minevillagenetwork.commands;

import be.kyanvde.minevillagenetwork.LockdownManager;
import be.kyanvde.minevillagenetwork.MineVillageNetwork;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LockdownCommand implements SimpleCommand {

    private final ProxyServer proxyServer = MineVillageNetwork.getServer();

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

        String[] args = invocation.arguments();

        if(args[0].equals("all")) {
            LockdownManager.setLockdown();
        } else {
            proxyServer.getAllServers().forEach(registeredServer -> {
                String serverName = registeredServer.getServerInfo().getName();
                boolean newStatus = !LockdownManager.getLockdown(serverName);

                if(serverName.equals(args[0])) {

                    LockdownManager.setLockdown(serverName, newStatus);
                    source.sendMessage(Component.text(String.format("Successfully %s lockdown for %s!", newStatus ? "enabled" : "disabled", serverName), NamedTextColor.GREEN));
                }
            });
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        List<String> serverNames = new ArrayList<>();
        serverNames.add("all");
        proxyServer.getAllServers().forEach(registeredServer -> serverNames.add(registeredServer.getServerInfo().getName()));

        return serverNames;
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.supplyAsync(() -> {
            List<String> serverNames = new ArrayList<>();
            serverNames.add("all");
            proxyServer.getAllServers().forEach(registeredServer -> serverNames.add(registeredServer.getServerInfo().getName()));

            return serverNames;
        });
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("mvnetwork.command.lockdown");
    }
}
