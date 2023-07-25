package org._1104mc.staffstuff.operator;

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
}
