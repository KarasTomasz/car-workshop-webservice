package pl.tkaras.carworkshopwebservice.securities;

public enum AppUserPermission {
    COMMENT_READ("comment:read"),
    COMMENT_WRITE("comment:write"),
    COMMENT_DELETE("comment:delete"),
    CAR_READ("car:read"),
    CAR_ADD("car:add"),
    CAR_UPDATE("car:update"),
    CAR_DELETE("car:delete"),
    USER_READ("user:read"),
    USER_READ_ALL("user:readAll"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }

}
