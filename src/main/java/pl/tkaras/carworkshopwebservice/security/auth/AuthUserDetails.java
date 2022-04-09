package pl.tkaras.carworkshopwebservice.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.security.AppUserRole;

import java.util.Collection;
import java.util.Set;

public class AuthUserDetails implements UserDetails {

    private AppUser appUser;
    private Set<? extends GrantedAuthority> grantedAuthorities;

    public AuthUserDetails(AppUser appUser){
        this.appUser = appUser;
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
