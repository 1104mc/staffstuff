package org._1104mc.staffstuff.events;

import org._1104mc.staffstuff.spectate.SpectatorMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpectatorDamageEvent implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent damageEvent){
        if(!(damageEvent.getEntity() instanceof Player)) return;
        Player player = (Player) damageEvent.getEntity();
        if (!SpectatorMode.isSpectator(player)) return;
        damageEvent.setCancelled(true);
    }
}
