package org._1104mc.staffstuff.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org._1104mc.staffstuff.operator.OperatorLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class KickCommand extends OperatorCommandExecutor{
    private Player findPlayer(String playerName){
        List<? extends Player> players = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getName().equals(playerName))
                .toList();
        return (players.size() > 0) ? (Player) players.get(0) : null;
    }

    @Override
    public void execCommand(Player executor, String[] args) {
        if(args.length == 0){
            executor.sendMessage(Component.text("Please add the kicking player name!").color(NamedTextColor.DARK_RED));
            return;
        }
        Player target = findPlayer(args[0]);
        if(target == null){
            executor.sendMessage(Component.text("Player not found").color(NamedTextColor.DARK_RED));
            return;
        }
        if(args.length > 1){
            StringBuilder causeBuilder = new StringBuilder();
            for (int i = 1; i < args.length; i++){
                causeBuilder.append(args[i]).append(" ");
            }
            target.kick(Component.text(causeBuilder.toString()));
            return;
        }
        target.kick();
    }

    @Override
    public OperatorLevel getRequiredLevel() {
        return OperatorLevel.Staff;
    }
}
