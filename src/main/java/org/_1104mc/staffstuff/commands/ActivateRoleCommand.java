package org._1104mc.staffstuff.commands;

import net.kyori.adventure.text.Component;
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
            Component no_permission_message = Component.text("You have no roles to activate/deactivate!").color(NamedTextColor.DARK_RED);
            player.sendMessage(no_permission_message);
            return false;
        }
        switch (command.getName()){
            case "activate":
                operator.activate(player);
                break;
            case "deactivate":
                operator.deactivate(player);
                break;
            default:
                break;
        }
        return true;
    }
}
