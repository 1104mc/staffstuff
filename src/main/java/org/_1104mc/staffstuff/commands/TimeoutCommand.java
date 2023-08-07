package org._1104mc.staffstuff.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org._1104mc.staffstuff.Staffstuff;
import org._1104mc.staffstuff.commands.annotations.SubCommandCompleter;
import org._1104mc.staffstuff.commands.annotations.SubCommandHandler;
import org._1104mc.staffstuff.model.TimeoutedPlayer;
import org._1104mc.staffstuff.operator.OperatorLevel;
import org._1104mc.staffstuff.utils.MuteTimeCalculator;
import org._1104mc.staffstuff.utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.logging.Level;

import static org._1104mc.staffstuff.utils.PlayerUtil.getPlayerChoices;

public class TimeoutCommand extends SubCommandForOperators {
    @SubCommandHandler(id = "add", level = OperatorLevel.Staff)
    public void addTimeout(Player executor, String[] args) {
        if(args.length != 2) {
            executor.sendMessage(Component.text("Usage: /timeout <player> <time>"));
            return;
        }
        Player target = PlayerUtil.findPlayer(args[0]);
        if(MuteTimeCalculator.timeoutToSeconds(args[1]) > 7200) {
            executor.sendMessage(Component.text("You can mute someone for maximum 2 hour!").color(NamedTextColor.DARK_RED));
            return;
        }
        TimeoutedPlayer.tmPlayers.add(new TimeoutedPlayer(target, args[1]));
        String tmText = MuteTimeCalculator.timeoutToTimeText(args[1]);
        assert target != null;
        target.sendMessage(Component.text("Egy operátor elnémított téged "+tmText+". Ezidő alatt nem fogsz tudni chatelni!", NamedTextColor.RED));
        executor.sendMessage(Component.text("Sikeresen elnémítottad "+target.getName()+" játékost "+tmText+".", NamedTextColor.GREEN));
        Staffstuff.getPlugin(Staffstuff.class).getLogger().log(Level.INFO, "Player "+target.getName()+" has been muted!");
    }

    @SubCommandCompleter(subcommand_id = "add")
    public List<String> completeAddTm(Player executor, String[] args) {
        if (args.length == 2) {
            String timeoutPrompt = args[1];
            return MuteTimeCalculator.offerTimeouts(timeoutPrompt);
        }
        return getPlayerChoices(executor);
    }

    @Override
    public OperatorLevel getRequiredLevel() {
        return OperatorLevel.Staff;
    }

    public static void startValidator(){
        new BukkitRunnable() {
            @Override
            public void run() {
                TimeoutedPlayer.tmPlayers.removeIf(timeoutedPlayer -> {
                    if(timeoutedPlayer.isExpired()){
                        timeoutedPlayer.unmute();
                        return true;
                    }
                    return false;
                });
            }
        }.runTaskTimer(Staffstuff.getPlugin(Staffstuff.class), 20L, 20L);
    }
}
