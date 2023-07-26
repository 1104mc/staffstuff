package org._1104mc.staffstuff.model;

import net.kyori.adventure.text.Component;
import org._1104mc.staffstuff.Staffstuff;
import org._1104mc.staffstuff.utils.MuteTimeCalculator;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.logging.Level;

public class TimeoutedPlayer {
    private final Player player;
    private final LocalDateTime deadline;

    public TimeoutedPlayer(Player player, String timeoutText) {
        this.player = player;
        this.deadline = LocalDateTime.now().plusSeconds(MuteTimeCalculator.timeoutToSeconds(timeoutText));
    }

    public boolean isYourPlayer(Player player){
        return this.player.equals(player);
    }

    public boolean isExpired(){
        ZoneOffset zoneOffset = ZoneOffset.of("+1");
        return this.deadline.toInstant(zoneOffset).getEpochSecond() < LocalDateTime.now().toInstant(zoneOffset).getEpochSecond();
    }

    public void unmute(){
        Staffstuff.getPlugin(Staffstuff.class).getLogger().log(Level.INFO,"Unmuted player"+player.getName());
        player.sendMessage(Component.text("Lejárt a némításod, most már megint használhatod a chatet!"));
    }
}
