package pl.tkaras.carworkshopwebservice.securities.auth;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.securities.AppUserRole;

import java.util.Collection;

@RequiredArgsConstructor
public class AuthUserDetails implements UserDetails {

    private final AppUser appUser;

    @Override
    public String getUsername() {
        return appUser.getUsername();
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        String role = appUser.getRole();
        return AppUserRole.valueOf(role).getGrantedAuthority();
    }

    @Override
    public boolean isAccountNonExpired() {
        return appUser.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return appUser.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return appUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return appUser.isEnabled();
    }
}
