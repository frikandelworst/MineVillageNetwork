package be.kyanvde.minevillagenetwork;

import be.kyanvde.minevillagenetwork.commands.LockdownCommand;
import be.kyanvde.minevillagenetwork.listeners.PlayerJoinListener;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;

@Plugin(
        id = "minevillagenetwork",
        name = "MineVillageNetwork",
        version = "1.0",
        authors = {"kyanvde"}
)
public class MineVillageNetwork {

    @Getter
    private static ProxyServer server;

    @Getter
    private final Logger logger;

    @Inject
    public MineVillageNetwork(ProxyServer server, Logger logger) {
        MineVillageNetwork.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        registerCommands();
        registerListeners();
    }

    private void registerListeners() {
        server.getEventManager().register(this, new PlayerJoinListener());
    }

    private void registerCommands() {
        CommandManager commandManager = server.getCommandManager();


        // Lockdown command
        CommandMeta commandMeta = commandManager.metaBuilder("lockdown")
                .aliases("ld")
                .plugin(this)
                .build();
        SimpleCommand lockdownCommand = new LockdownCommand();
        commandManager.register(commandMeta, lockdownCommand);
    }
}
