package org._1104mc.staffstuff.commands.tabcomplete;

import org._1104mc.staffstuff.operator.OperatorLevel;
import org._1104mc.staffstuff.spectate.SpectatorMode;
import org._1104mc.staffstuff.utils.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SpectatorTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return null;
        if(args.length > 1) return null;
        if(SpectatorMode.isSpectator(player)){
            return Arrays.asList("accept", "reject");
        }
        List<String> choices = PlayerUtil.getPlayerChoices(player, false, player1 -> OperatorLevel.getPlayerLevel(player1) == null);
        choices.add("random");
        return choices.stream().sorted().toList();
    }
}
