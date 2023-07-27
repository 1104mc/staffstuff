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
        if(this.target != null) this.spectator.teleport(target);
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

    private double getAxisDistance(double playerPos, double startAxisPos){
        return Math.abs(playerPos - startAxisPos);
    }

    public String getLocationInfo(){
        StringBuilder locationBuilder = new StringBuilder();
        locationBuilder.append("(");
        locationBuilder.append(this.startPos.getBlockX()).append(" ")
                .append(this.startPos.getBlockY()).append(" ")
                .append(this.startPos.getBlockZ()).append(" ");
        // Plain distance calculation (Pythagorean theorem: a²+b²=c²)
        Location currentLocation = this.spectator.getLocation();
        int distBlock = (int) Math.sqrt(Math.pow(getAxisDistance(currentLocation.getX(), startPos.getX()), 2) + Math.pow(getAxisDistance(currentLocation.getZ(), currentLocation.getZ()), 2));
        locationBuilder.append("-").append(distBlock).append(" blocks away)");
        return locationBuilder.toString();
    }
}
