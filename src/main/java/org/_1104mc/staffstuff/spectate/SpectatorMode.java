package org._1104mc.staffstuff.spectate;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorMode {
    private static List<SpectatingPlayer> spectators = new ArrayList<>();

    public static void activateSpectator(Player spectator, Player target){
        spectator.setGameMode(GameMode.ADVENTURE);
        spectator.setFlying(true);
        spectators.add(new SpectatingPlayer(spectator, target));
    }

    public static boolean isSpectator(Player player){
        return !spectators.stream()
                .filter(spectatingPlayer -> spectatingPlayer.isYourPlayer(player))
                .toList().isEmpty();
    }

    public static void deactivatePlayer(Player player, boolean teleportBack){
        if(!isSpectator(player)) return;
        SpectatingPlayer spectatorRecord = spectators.stream()
                .filter(spectatingPlayer -> spectatingPlayer.isYourPlayer(player)).toList().get(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        spectatorRecord.deactivate(teleportBack);
        spectators.remove(spectatorRecord);
    }
}
