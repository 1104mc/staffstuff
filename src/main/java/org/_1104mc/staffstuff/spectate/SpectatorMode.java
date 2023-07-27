package org._1104mc.staffstuff.spectate;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org._1104mc.staffstuff.Staffstuff;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorMode {
    private static List<SpectatingPlayer> spectators = new ArrayList<>();

    public static void activateSpectator(Player spectator, Player target){
        spectator.setGameMode(GameMode.ADVENTURE);
        spectator.setAllowFlight(true);
        spectator.setFlying(true);
        Bukkit.getOnlinePlayers().forEach(people -> people.hidePlayer(Staffstuff.getProvidingPlugin(Staffstuff.class),spectator));
        spectators.add(new SpectatingPlayer(spectator, target));
        spectator.sendMessage(Component.text("You've activated your spectator mode successfully!", NamedTextColor.GREEN));
    }

    public static boolean isSpectator(Player player){
        return !spectators.stream()
                .filter(spectatingPlayer -> spectatingPlayer.isYourPlayer(player))
                .toList().isEmpty();
    }

    public static void deactivateSpectator(Player player, boolean teleportBack){
        if(!isSpectator(player)) return;
        SpectatingPlayer spectatorRecord = spectators.stream()
                .filter(spectatingPlayer -> spectatingPlayer.isYourPlayer(player)).toList().get(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setAllowFlight(false);
        Bukkit.getOnlinePlayers().forEach(people -> people.showPlayer(Staffstuff.getProvidingPlugin(Staffstuff.class),player));
        spectatorRecord.deactivate(teleportBack);
        spectators.remove(spectatorRecord);
        player.sendMessage(Component.text("You've deactivated your spectator mode successfully!", NamedTextColor.GREEN));
    }

    public static String getLocationLogOfSpectator(Player player){
        if(!isSpectator(player)) return "not spectator!";
        SpectatingPlayer spectatorRecord = spectators.stream()
                .filter(spectatingPlayer -> spectatingPlayer.isYourPlayer(player)).toList().get(0);
        return spectatorRecord.getLocationInfo();
    }
}
