package org._1104mc.staffstuff.operator;

import net.kyori.adventure.text.format.NamedTextColor;
import org._1104mc.staffstuff.Staffstuff;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Operator {
    private static ArrayList<Player> staffs = new ArrayList<>();
    private final String username;
    private final OperatorLevel level;

    public Operator(JSONObject json){
        this.username = json.getString("username");
        this.level = OperatorLevel.fromJson("level");
    }

    public String getUsername() {
        return username;
    }

    public OperatorLevel getLevel() {
        return level;
    }

    // Loading stuff
    public static Operator[] loadOperators(){
        Staffstuff plugin = JavaPlugin.getPlugin(Staffstuff.class);
        if(!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        File operatorFile = new File(plugin.getDataFolder(), "staff.json");
        if(!operatorFile.exists()){
            try {
                operatorFile.createNewFile();
                plugin.getLogger().log(Level.INFO, "Created staffs.json file! There you can add the admins and staffs! When you're done reload the server!");
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to create the staffs.json file! Please create it manually!");
            }
            return new Operator[]{};
        }
        try{
            JSONArray json = new JSONArray(Files.readString(operatorFile.toPath()));
            ArrayList<Operator> outList = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                outList.add(new Operator(json.getJSONObject(i)));
            }
            return outList.toArray(new Operator[0]);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to load the staffs.json config!");
            return new Operator[]{};
        }
    }

    // Activating stuff
    public static Operator findOperator(String playerName){
        List<Operator> result = Arrays.stream(Staffstuff.operators.clone())
                .filter(operator -> Objects.equals(operator.getUsername(), playerName))
                .toList();
        return (result.size() > 0) ? result.get(0) : null;
    }

    public void activate(Player player){
        switch (getLevel()){
            case Staff -> {
                staffs.add(player);
                player.sendPlainMessage(NamedTextColor.GREEN + "Successfully activated your staff role!");
            }
            case Admin -> {
                player.setOp(true);
                player.sendPlainMessage(NamedTextColor.GREEN + "Activated your admin role!");
            }
        }
    }

    // Checking staffs
    public static boolean isStaff(Player player){
        return staffs.contains(player);
    }
}
