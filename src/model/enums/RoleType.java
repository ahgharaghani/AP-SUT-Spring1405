package model.enums;

public enum RoleType {
    WORKER, PROPAGANDA, GOVERNOR, SHERIFF;

    public static RoleType ofString(String roleStr) {
        for (RoleType role : RoleType.values()) {
            if (role.name().equalsIgnoreCase(roleStr)) return role;
        }
        return null;
    }
}
