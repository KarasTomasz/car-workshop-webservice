package pl.tkaras.carworkshopwebservice.security;

public enum ApplicationUserPermission {
    COMMENT_READ("comment:read"),
    COMMENT_WRITE("comment:write"),
    COMMENT_DELETE("comment:delete"),
    CAR_ADD("car:add"),
    CAR_DELETE("car:delete"),
    USER_ADD("user:add"),
    USER_DELETE("user:delete");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }

}
