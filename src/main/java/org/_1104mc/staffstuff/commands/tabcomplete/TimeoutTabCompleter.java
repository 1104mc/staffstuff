package org._1104mc.staffstuff.commands.tabcomplete;

import org._1104mc.staffstuff.utils.MuteTimeCalculator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org._1104mc.staffstuff.utils.PlayerUtil.getPlayerChoices;

public class TimeoutTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return null;
        Player player = (Player) sender;
        if (args.length == 2) {
            String timeoutPrompt = args[1];
            return MuteTimeCalculator.offerTimeouts(timeoutPrompt);
        }
        return getPlayerChoices(player);
    }
}
