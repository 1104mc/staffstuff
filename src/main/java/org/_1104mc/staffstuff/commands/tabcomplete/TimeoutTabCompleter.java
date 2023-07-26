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

public class TimeoutTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return null;
        Player player = (Player) sender;
        if (args.length == 2) {
            String timeoutPrompt = args[1];
            return MuteTimeCalculator.offerTimeouts(timeoutPrompt);
        }
        List<String> playerNames = new ArrayList<>();
        Stream<? extends Player> players = Bukkit.getOnlinePlayers().stream();
        if(Bukkit.getOnlinePlayers().size() > 1) players = players.filter(online_player -> !online_player.getName().equals(player.getName()));
        players.forEach(online_player -> playerNames.add(online_player.getName()));
        return playerNames;
    }
}
