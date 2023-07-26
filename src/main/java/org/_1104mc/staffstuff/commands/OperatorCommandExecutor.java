package org._1104mc.staffstuff.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org._1104mc.staffstuff.operator.OperatorLevel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class OperatorCommandExecutor implements CommandExecutor {
    public abstract void execCommand(Player executor, String[] args);
    public abstract OperatorLevel getRequiredLevel();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return false;
        OperatorLevel playerLevel = OperatorLevel.getPlayerLevel(player);
        if(playerLevel == null || playerLevel.getValue() < getRequiredLevel().getValue()){
            Component noPermissionMessage = Component.text("You have no permission to execute this command!").color(NamedTextColor.DARK_RED);
            player.sendMessage(noPermissionMessage);
            return false;
        }
        execCommand(player, args);
        return true;
    }
}
