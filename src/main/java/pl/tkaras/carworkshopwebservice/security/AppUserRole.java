package pl.tkaras.carworkshopwebservice.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.tkaras.carworkshopwebservice.security.AppUserPermission.*;

public enum AppUserRole {
    CLIENT(Stream.of(COMMENT_READ, COMMENT_WRITE,
                    CAR_READ, CAR_ADD, CAR_UPDATE,
                    USER_READ)
            .collect(Collectors.toCollection(HashSet::new))),
    MODERATOR(Stream.of(COMMENT_READ, COMMENT_WRITE, COMMENT_DELETE,
                    CAR_READ, CAR_ADD, CAR_UPDATE, CAR_DELETE,
                    USER_READ)
            .collect(Collectors.toCollection(HashSet::new))),
    ADMIN(Stream.of(COMMENT_READ, COMMENT_WRITE, COMMENT_DELETE,
                    CAR_READ, CAR_ADD, CAR_UPDATE, CAR_DELETE,
                    USER_READ, USER_READ_ALL, USER_UPDATE, USER_DELETE)
            .collect(Collectors.toCollection(HashSet::new)));

    private final HashSet<AppUserPermission> permissions;

    AppUserRole(HashSet<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public HashSet<AppUserPermission> getPermissions(){
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority(){
        Set<SimpleGrantedAuthority> grantedPermissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        grantedPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedPermissions;
    }

}
