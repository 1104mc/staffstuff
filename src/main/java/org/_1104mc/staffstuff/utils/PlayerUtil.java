package org._1104mc.staffstuff.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerUtil {
    public static Player findPlayer(String playerName){
        List<? extends Player> players = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getName().equals(playerName))
                .toList();
        return (players.size() > 0) ? (Player) players.get(0) : null;
    }
}
