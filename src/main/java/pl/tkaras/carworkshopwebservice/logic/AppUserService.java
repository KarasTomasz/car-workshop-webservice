package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.entity.AppUserDetails;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.AppUserDtoMapper;
import pl.tkaras.carworkshopwebservice.repository.AppUserRepository;
import pl.tkaras.carworkshopwebservice.repository.AppUserDetailsRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepo;
    private final AppUserDetailsRepository appUserDetailsRepo;
    private final AppUserDtoMapper appUserDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationConfirmTokenService registrationConfirmTokenService;

    public AppUserService(AppUserRepository appUserRepo,
                          AppUserDetailsRepository appUserDetailsRepo, AppUserDtoMapper appUserDtoMapper, PasswordEncoder passwordEncoder, RegistrationConfirmTokenService registrationConfirmTokenService) {
        this.appUserRepo = appUserRepo;
        this.appUserDetailsRepo = appUserDetailsRepo;
        this.appUserDtoMapper = appUserDtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.registrationConfirmTokenService = registrationConfirmTokenService;
    }

    public AppUserDto getUserRole(String username){
        if(appUserRepo.existsByUsername(username)) {
            return appUserDtoMapper.mapToDto(appUserRepo.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException(String.format("Problem with given username %s", username))));
        }
        throw new UsernameNotFoundException(String.format("User %s not found", username));
    }

    public List<AppUserDto> getAllUsers(){
        return appUserDtoMapper.mapToDtos(appUserRepo.findAll());
    }

    public Optional<AppUserDto> getUser(String username){
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException(String.format("User %s not found", username)));
        AppUserDto userDto = appUserDtoMapper.mapToDto(user);
        return Optional.ofNullable(userDto);
    }

    public String signUp(AppUser appUser){

        if(!appUserRepo.existsByUsername(appUser.getUsername())){

            String token = UUID.randomUUID().toString(); //generate register confirmation token

            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

            appUserRepo.save(appUser);

            AppUserDetails userDetails = new AppUserDetails().builder()
                    .appUser(appUser)
                    .build();

            appUserDetailsRepo.save(userDetails);

            RegistrationConfirmToken confirmToken = RegistrationConfirmToken.builder()
                    .token(token)
                    .createdOn(LocalDateTime.now())
                    .expiresOn(LocalDateTime.now().plusMinutes(20)) //the token will expire aftre 20 minutes
                    .appUser(appUser)
                    .build();

            registrationConfirmTokenService.saveConfirmationToken(confirmToken);

            return token;
        }
        return null;
    }

    public ResponseEntity updateUser(AppUser appUser){
        if(appUserRepo.existsByUsername(appUser.getUsername())){
            AppUser newAppUser = appUserRepo.findByUsername(appUser.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("Problem with given username %s", appUser.getUsername())));
            appUserRepo.save(newAppUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity deleteUser(Long id){
        if(appUserRepo.existsById(id)){
            appUserRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public void enableAppUser(String username){
        if(appUserRepo.existsByUsername(username)){
            AppUser user = appUserRepo.findByUsername(username).orElseThrow();
            AppUserDetails userDetails = appUserDetailsRepo.findByAppUserId(user.getId()).orElseThrow();

            userDetails.setAccountNonExpired(true);
            userDetails.setAccountNonLocked(true);
            userDetails.setCredentialsNonExpired(true);
            userDetails.setEnabled(true);
            appUserDetailsRepo.save(userDetails);
        }
    }

}
