package org._1104mc.staffstuff;

import org._1104mc.staffstuff.commands.ActivateRoleCommand;
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
