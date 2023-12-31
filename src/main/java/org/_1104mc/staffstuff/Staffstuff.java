package org._1104mc.staffstuff;

import org._1104mc.staffstuff.commands.*;
import org._1104mc.staffstuff.events.*;
import org._1104mc.staffstuff.operator.Operator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Staffstuff extends JavaPlugin {
    public static Operator[] operators;

    @Override
    public void onEnable() {
        // Plugin startup logic
        operators = Operator.loadOperators();
        Objects.requireNonNull(getCommand("activate")).setExecutor(new ActivateRoleCommand());
        Objects.requireNonNull(getCommand("deactivate")).setExecutor(new ActivateRoleCommand());
        Objects.requireNonNull(getCommand("kick")).setExecutor(new KickCommand());
        Objects.requireNonNull(getCommand("timeout")).setExecutor(new TimeoutCommand());
        Objects.requireNonNull(getCommand("timeout")).setTabCompleter(new TimeoutCommand());
        Objects.requireNonNull(getCommand("spectate")).setExecutor(new SpectatorCommand());
        Objects.requireNonNull(getCommand("spectate")).setTabCompleter(new SpectatorCommand());
        getServer().getPluginManager().registerEvents(new OnChatEvent(), this);
        getServer().getPluginManager().registerEvents(new SpectatorDamageEvent(), this);
        TimeoutCommand.startValidator();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
