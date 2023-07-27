package org._1104mc.staffstuff.utils;

import org._1104mc.staffstuff.operator.OperatorLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class PlayerUtil {
    public static Player findPlayer(String playerName){
        List<? extends Player> players = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getName().equals(playerName))
                .toList();
        return (players.size() > 0) ? players.get(0) : null;
    }

    public static Player getRandomPlayer(){
        List<? extends Player> choices = Bukkit.getOnlinePlayers().stream()
                .filter(player -> OperatorLevel.getPlayerLevel(player) == null)
                .toList();
        if(choices.isEmpty()) return null;
        if (choices.size() == 1) return choices.get(0);
        Random random = new Random();
        return choices.get(random.nextInt(0, choices.size()-1));
    }

    public static List<String> getPlayerChoices(Player player){
        return getPlayerChoices(player, false, null);
    }

    @NotNull
    public static List<String> getPlayerChoices(Player player, boolean includeYourself, PlayerFilter filter) {
        List<String> playerNames = new ArrayList<>();
        Stream<? extends Player> players = Bukkit.getOnlinePlayers().stream();
        if(Bukkit.getOnlinePlayers().size() > 1 && !includeYourself)
            players = players.filter(online_player -> !online_player.getName().equals(player.getName()));
        if(filter != null) players = players.filter(filter::filter);
        players.forEach(online_player -> playerNames.add(online_player.getName()));
        return playerNames;
    }

    public interface PlayerFilter{
        boolean filter(Player player);

        default boolean filter(){
            return true;
        }
    }
}
