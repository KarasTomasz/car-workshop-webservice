package pl.tkaras.carworkshopwebservice.security.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.tkaras.carworkshopwebservice.model.entity.User;
import pl.tkaras.carworkshopwebservice.repository.UserRepository;

public class AuthUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public AuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw  new UsernameNotFoundException("User with given username does not exists");
        }
        return new AuthUserDetails(user);
    }
}
