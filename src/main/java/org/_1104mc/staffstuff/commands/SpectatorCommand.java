package org._1104mc.staffstuff.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org._1104mc.staffstuff.operator.OperatorLevel;
import org._1104mc.staffstuff.spectate.SpectatingPlayer;
import org._1104mc.staffstuff.spectate.SpectatorMode;
import org._1104mc.staffstuff.utils.PlayerUtil;
import org.bukkit.entity.Player;

public class SpectatorCommand extends OperatorCommandExecutor {
    @Override
    public void execCommand(Player executor, String[] args) {
        if (SpectatorMode.isSpectator(executor)){
            if(args.length == 0){
                Style actionStyle = Style.style().decorate(TextDecoration.BOLD).build();
                Component message = Component
                        .text("Deactivating your spectator mode ...")
                        .appendNewline()
                            .append(Component.text("Would you like to teleport back where you started your spectator session "+SpectatorMode.getLocationLogOfSpectator(executor)+"?"))
                                .appendNewline()
                                    .append(Component.text("Yes").style(actionStyle).color(NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/spectate accept")))
                                        .appendSpace()
                                            .append(Component.text("No").style(actionStyle).color(NamedTextColor.RED).clickEvent(ClickEvent.runCommand("/spectate reject")));
                executor.sendMessage(message);
            } else if (args.length == 1) {
                switch (args[0]) {
                    case "accept" -> SpectatorMode.deactivateSpectator(executor, true);
                    case "reject" -> SpectatorMode.deactivateSpectator(executor, false);
                    default -> executor.sendMessage(Component.text("Invalid argument!", NamedTextColor.RED));
                }
            }
            return;
        }
        if(args.length == 0){
            SpectatorMode.activateSpectator(executor, null);
        }else if(args.length == 1){
            String playerName = args[0];
            Player target;
            if(playerName.equals("random")) target = PlayerUtil.getRandomPlayer();
            else target = PlayerUtil.findPlayer(playerName);
            if(target == null){
                executor.sendMessage(Component.text("Not found player!"));
                return;
            }
            SpectatorMode.activateSpectator(executor, target);
        }
    }

    @Override
    public OperatorLevel getRequiredLevel() {
        return OperatorLevel.Staff;
    }
}
