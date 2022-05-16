package pl.tkaras.carworkshopwebservice.securities.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.repositories.AppUserRepository;

@RequiredArgsConstructor
@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new AuthUserDetails(appUser);
    }
}
