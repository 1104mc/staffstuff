package org._1104mc.staffstuff.spectate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpectatingPlayer {
    private final Player spectator;
    private Player target;
    private final Location startPos;

    public SpectatingPlayer(Player spectator){
        this.spectator = spectator;
        this.target = null;
        this.startPos = spectator.getLocation();
    }

    public SpectatingPlayer(Player spectator, Player target){
        this.spectator = spectator;
        this.target = target;
        this.startPos = spectator.getLocation();
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void deactivate(boolean teleportBack){
        if(teleportBack) spectator.teleport(this.startPos);
    }

    public boolean isYourPlayer(Player player){
        return player.equals(this.spectator);
    }
}
