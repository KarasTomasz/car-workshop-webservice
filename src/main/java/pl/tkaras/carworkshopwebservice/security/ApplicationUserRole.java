package pl.tkaras.carworkshopwebservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.tkaras.carworkshopwebservice.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    CLIENT(Stream.of(COMMENT_READ, COMMENT_WRITE)
            .collect(Collectors.toCollection(HashSet::new))),
    MODERATOR(Stream.of(COMMENT_READ, COMMENT_WRITE, COMMENT_DELETE, CAR_ADD, CAR_DELETE)
            .collect(Collectors.toCollection(HashSet::new))),
    ADMIN(Stream.of(COMMENT_READ, COMMENT_WRITE, COMMENT_DELETE, CAR_ADD, CAR_DELETE, USER_ADD, USER_DELETE)
            .collect(Collectors.toCollection(HashSet::new)));

    private final HashSet<ApplicationUserPermission> permissions;

    ApplicationUserRole(HashSet<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public HashSet<ApplicationUserPermission> getPermissions(){
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
