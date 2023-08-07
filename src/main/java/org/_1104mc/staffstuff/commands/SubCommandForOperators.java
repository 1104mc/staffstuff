package org._1104mc.staffstuff.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org._1104mc.staffstuff.Staffstuff;
import org._1104mc.staffstuff.commands.annotations.SubCommandCompleter;
import org._1104mc.staffstuff.commands.annotations.SubCommandHandler;
import org._1104mc.staffstuff.operator.OperatorLevel;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;

public abstract class SubCommandForOperators extends OperatorCommand{
    @Override
    public void execCommand(Player executor, String[] args) {
        if(args.length == 0) {
            executor.sendMessage(Component.text("You didn't selected any subcommands!", NamedTextColor.RED));
            return;
        }
        executeSubCommand(args[0], executor, Arrays.copyOfRange(args, 1, args.length));
    }

    @Override
    public List<String> completeArgs(Player executor, String[] args) {
        if(args.length <= 1) return listSubCommands();
        String subCommandId = args[0];
        return completeSubCommand(subCommandId, executor, Arrays.copyOfRange(args, 1, args.length));
    }

    public static class SubCommand{
        public final String id;
        private final Method handler;
        private final Method completer;
        public final OperatorLevel level;

        public SubCommand(Method handler, Method completer){
            SubCommandHandler commandMeta = handler.getAnnotation(SubCommandHandler.class);
            this.id = commandMeta.id();
            this.handler = handler;
            this.completer = completer;
            this.level = commandMeta.level();
        }

        public void execute(SubCommandForOperators command, Player executor, String[] args){
            if(level != null && Objects.requireNonNull(OperatorLevel.getPlayerLevel(executor)).getValue() <= level.getValue()) {
                executor.sendMessage(Component.text("Access denied!", NamedTextColor.DARK_RED));
                return;
            }
            try{
                this.handler.invoke(command, executor, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                String msg = "Failed to execute " + command.getClass().getName() + ":" + handler.getName() + " command because of an " + e.getClass().getName() + " what says: " + e.getMessage();
                Staffstuff.getPlugin(Staffstuff.class).getLogger().log(Level.SEVERE, msg);
            }
        }

        public List<String> complete(SubCommandForOperators command, Player executor, String[] args){
            if(this.completer == null) return null;
            try {
                return (List<String>) this.completer.invoke(command, executor, args);
            } catch (InvocationTargetException|IllegalAccessException e) {
                String msg = "Failed to complete " + command.getClass().getName() + ":" + handler.getName() + " command because of an " + e.getClass().getName() + " what says: " + e.getMessage();
                Staffstuff.getPlugin(Staffstuff.class).getLogger().log(Level.SEVERE, msg);
                return null;
            }
        }
    }

    protected SubCommand[] getSubCommands(){
        Method[] commandMethods = Arrays.stream(this.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(SubCommandHandler.class) || method.isAnnotationPresent(SubCommandCompleter.class)).toArray(Method[]::new);
        HashMap<String, ArrayList<Method>> rawSubCommands = new HashMap<>();
        Arrays.stream(commandMethods)
                .filter(commandHandler -> commandHandler.isAnnotationPresent(SubCommandHandler.class))
                .forEach(handler -> {
                    SubCommandHandler handlerMeta = handler.getAnnotation(SubCommandHandler.class);
                    if(!rawSubCommands.containsKey(handlerMeta.id())) rawSubCommands.put(handlerMeta.id(), new ArrayList<>(Collections.singleton(handler)));
                });
        Arrays.stream(commandMethods)
                .filter(commandMethods1 -> commandMethods1.isAnnotationPresent(SubCommandCompleter.class))
                .forEach(completer -> {
                    SubCommandCompleter completerInfo = completer.getAnnotation(SubCommandCompleter.class);
                    rawSubCommands.get(completerInfo.subcommand_id()).add(completer);
                });
        List<SubCommand> subCommands = new ArrayList<>();
        rawSubCommands.forEach((id, methods) -> {
            subCommands.add(new SubCommand(methods.get(0), methods.get(1)));
        });
        return subCommands.toArray(SubCommand[]::new);
    }

    protected List<String> listSubCommands(){
        List<String> commandKeys = new ArrayList<>();
        Arrays.stream(getSubCommands()).forEach(command -> commandKeys.add(command.id));
        return commandKeys;
    }

    protected SubCommand findSubCommand(String commandId){
        SubCommand[] query = Arrays.stream(getSubCommands()).filter(subCommand -> Objects.equals(subCommand.id, commandId)).toArray(SubCommand[]::new);
        return (query.length > 0) ? query[0] : null;
    }

    protected List<String> completeSubCommand(String commandId, Player executor, String[] args){
        SubCommand subCommand = findSubCommand(commandId);
        if(subCommand == null) return null;
        return subCommand.complete(this, executor, args);
    }

    protected void executeSubCommand(String commandId, Player executor, String[] args){
        SubCommand subCommand = findSubCommand(commandId);
        if(subCommand == null) return;
        subCommand.execute(this, executor, args);
    }
}
