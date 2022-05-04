package pl.tkaras.carworkshopwebservice.securities.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserDetailsNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;
import pl.tkaras.carworkshopwebservice.repositories.AppUserRepository;
import pl.tkaras.carworkshopwebservice.repositories.AppUserDetailsRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final AppUserDetailsRepository userDetailsRepo;

    public AuthUserDetailsService(AppUserRepository appUserRepository, AppUserDetailsRepository userDetailsRepo) {
        this.appUserRepository = appUserRepository;
        this.userDetailsRepo = userDetailsRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        AppUserDetails userDetails = userDetailsRepo
                .findByAppUserId(appUser.getId())
                .orElseThrow(() -> new AppUserDetailsNotFoundException(username));

        return new AuthUserDetails(appUser, userDetails);
    }
}
