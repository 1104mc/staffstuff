package org._1104mc.staffstuff.model;

import net.kyori.adventure.text.Component;
import org._1104mc.staffstuff.Staffstuff;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.logging.Level;

public class TimeoutedPlayer {
    private final Player player;
    private final LocalDateTime deadline;

    public TimeoutedPlayer(Player player, String timeoutText) {
        this.player = player;
        this.deadline = LocalDateTime.now().plusSeconds(timeoutToSeconds(timeoutText));
    }

    private static final HashMap<String, Long> TM_MULTIPLIERS = new HashMap<>(
            Map.ofEntries(
                    new AbstractMap.SimpleEntry<>("s", 1L),
                    new AbstractMap.SimpleEntry<>("m", 60L),
                    new AbstractMap.SimpleEntry<>("h", 3600L)
            )
    );

    public static long timeoutToSeconds(String timeout) {
        StringBuilder part = new StringBuilder();
        long value = 0;
        char[] timeoutChars = timeout.toCharArray();
        for (char c : timeoutChars) {
            if (!TM_MULTIPLIERS.containsKey(String.valueOf(c))) {
                part.append(c);
                continue;
            }
            value += Long.parseLong(part.toString()) * TM_MULTIPLIERS.get(String.valueOf(c));
            part.delete(0, part.length());
        }
        return value;
    }

    private static final String[] TIME_FIELDS = new String[]{"másodpercre", "percre", "órára"};

    public static String timeoutToTimeText(String tmInput){
        long tmValue = timeoutToSeconds(tmInput);
        List<Long> dividers = TM_MULTIPLIERS.values().stream().sorted().toList();
        StringBuilder outTime = new StringBuilder();
        for (int i = TM_MULTIPLIERS.size()-1; i >= 0; --i) {
            long divider = dividers.get(i);
            long whole_count = tmValue / divider;
            if (whole_count > 0){
                tmValue -= whole_count * divider;
                outTime.append(whole_count).append(" ").append(TIME_FIELDS[i]).append(", ");
            }
        }
        String outTimeStr = outTime.toString();
        return outTimeStr.substring(0, outTimeStr.length()-2);
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
