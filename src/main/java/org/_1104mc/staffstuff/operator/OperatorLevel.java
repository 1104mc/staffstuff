package org._1104mc.staffstuff.operator;

import org.bukkit.entity.Player;

public enum OperatorLevel {
    Staff,
    Admin;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static OperatorLevel fromJson(String value){
        return switch (value) {
            case "staff" -> Staff;
            case "admin" -> Admin;
            default -> null;
        };
    }

    public int getValue(){
        return switch (this){
            case Staff -> 1;
            case Admin -> 2;
            default -> 0;
        };
    }

    public static OperatorLevel getPlayerLevel(Player player){
        if(Operator.isStaff(player)) return Staff;
        if(player.isOp()) return Admin;
        return null;
    }
}
