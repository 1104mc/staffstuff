package org._1104mc.staffstuff.utils;

import org._1104mc.staffstuff.operator.OperatorLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

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
}
