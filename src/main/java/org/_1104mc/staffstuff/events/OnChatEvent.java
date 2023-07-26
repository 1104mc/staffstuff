package org._1104mc.staffstuff.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import org._1104mc.staffstuff.commands.TimeoutCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnChatEvent implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent chatEvent){
        if (TimeoutCommand.tmPlayers.contains(chatEvent.getPlayer())){
            chatEvent.setCancelled(true);
        }
    }
}
