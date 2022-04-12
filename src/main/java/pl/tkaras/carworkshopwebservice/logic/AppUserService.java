package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.AppUserDtoMapper;
import pl.tkaras.carworkshopwebservice.repository.AppUserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepo;
    private final AppUserDtoMapper appUserDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationConfirmTokenService registrationConfirmTokenService;

    public AppUserService(AppUserRepository appUserRepo,
                          AppUserDtoMapper appUserDtoMapper, PasswordEncoder passwordEncoder, RegistrationConfirmTokenService registrationConfirmTokenService) {
        this.appUserRepo = appUserRepo;
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

    public String signUp(AppUser appUser){

        if(!appUserRepo.existsByUsername(appUser.getUsername())){

            String token = UUID.randomUUID().toString(); //generate register confirmation token

            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

            appUserRepo.save(appUser);

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

    public void enableAppUser(String username){
        appUserRepo.enableAppUser(username);
    }

}
