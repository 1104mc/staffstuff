package org._1104mc.staffstuff.operator;

import org._1104mc.staffstuff.Staffstuff;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;

public class Operator {
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
}
