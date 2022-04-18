package pl.tkaras.carworkshopwebservice.security.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exception.AppUserDetailsNotFoundException;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.entity.AppUserDetails;
import pl.tkaras.carworkshopwebservice.repository.AppUserRepository;
import pl.tkaras.carworkshopwebservice.repository.AppUserDetailsRepository;

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
