package pl.tkaras.carworkshopwebservice.securities.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;
import pl.tkaras.carworkshopwebservice.securities.AppUserRole;

import java.util.Collection;

public class AuthUserDetails implements UserDetails {

    private final AppUser appUser;
    private final AppUserDetails userDetails;

    public AuthUserDetails(AppUser appUser, AppUserDetails userDetails){
        this.appUser = appUser;
        this.userDetails = userDetails;
    }

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

        String role = userDetails.getRole();
        return AppUserRole.valueOf(role).getGrantedAuthority();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userDetails.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userDetails.isEnabled();
    }
}
