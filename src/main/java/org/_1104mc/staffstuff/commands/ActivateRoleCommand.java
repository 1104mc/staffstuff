package org._1104mc.staffstuff.commands;

import net.kyori.adventure.text.format.NamedTextColor;
import org._1104mc.staffstuff.operator.Operator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ActivateRoleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Operator operator = Operator.findOperator(player.getName());
        if(operator == null){
            player.sendPlainMessage(NamedTextColor.DARK_RED + "You have no roles to activate!");
            return false;
        }
        operator.activate(player);
        return true;
    }
}
