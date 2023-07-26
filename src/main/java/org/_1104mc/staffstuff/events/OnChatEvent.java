package org._1104mc.staffstuff.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import org._1104mc.staffstuff.Staffstuff;
import org._1104mc.staffstuff.commands.TimeoutCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class OnChatEvent implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent chatEvent){
        TimeoutCommand.tmPlayers.forEach(timeoutedPlayer -> Staffstuff.getPlugin(Staffstuff.class).getLogger().log(Level.INFO,timeoutedPlayer.toString()));
        if (TimeoutCommand.tmPlayers.stream().filter(tm -> tm.isYourPlayer(chatEvent.getPlayer())).toList().size() == 1){
            Staffstuff.getPlugin(Staffstuff.class).getLogger().log(Level.INFO,"A muted player tries to chat!");
            chatEvent.setCancelled(true);
        }
    }
}
