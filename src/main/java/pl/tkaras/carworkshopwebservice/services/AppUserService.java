package pl.tkaras.carworkshopwebservice.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserDetailsNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDetailsDto;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.AppUserDetailsDtoMapper;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.AppUserDtoMapper;
import pl.tkaras.carworkshopwebservice.repositories.AppUserRepository;
import pl.tkaras.carworkshopwebservice.repositories.AppUserDetailsRepository;

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
    private final AppUserDetailsDtoMapper appUserDetailsDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationConfirmTokenService registrationConfirmTokenService;

    public AppUserService(AppUserRepository appUserRepo,
                          AppUserDetailsRepository appUserDetailsRepo,
                          AppUserDtoMapper appUserDtoMapper,
                          AppUserDetailsDtoMapper appUserDetailsDtoMapper,
                          PasswordEncoder passwordEncoder,
                          RegistrationConfirmTokenService registrationConfirmTokenService) {
        this.appUserRepo = appUserRepo;
        this.appUserDetailsRepo = appUserDetailsRepo;
        this.appUserDtoMapper = appUserDtoMapper;
        this.appUserDetailsDtoMapper = appUserDetailsDtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.registrationConfirmTokenService = registrationConfirmTokenService;
    }

    public List<AppUserDto> getAllUsers(){
        return appUserDtoMapper.mapToDtos(appUserRepo.findAll());
    }

    public Optional<AppUserDto> getUserById(Long id){
        AppUser user = appUserRepo.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
        AppUserDto userDto = appUserDtoMapper.mapToDto(user);
        return Optional.ofNullable(userDto);
    }

    public Optional<AppUserDto> getUser(String username){
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));
        AppUserDto userDto = appUserDtoMapper.mapToDto(user);
        return Optional.ofNullable(userDto);
    }

    public List<AppUserDetailsDto> getAllUsersDetails(){
        return appUserDetailsDtoMapper.mapToDtos(appUserDetailsRepo.findAll());
    }

    public Optional<AppUserDetailsDto> getUserDetail(String username){
        Long id = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username))
                .getId();

        AppUserDetails userDetails = appUserDetailsRepo.findByAppUserId(id)
                .orElseThrow(() -> new AppUserDetailsNotFoundException(username));

        AppUserDetailsDto userDto = appUserDetailsDtoMapper.mapToDto(userDetails);
        return Optional.ofNullable(userDto);
    }

    public String signUp(AppUserDto appUserdto){

        if(!appUserRepo.existsByUsername(appUserdto.getUsername())){

            AppUser appUser = new AppUser().builder()
                    .username(appUserdto.getUsername())
                    .password(passwordEncoder.encode(appUserdto.getPassword()))
                    .email(appUserdto.getEmail())
                    .firstname(appUserdto.getFirstname())
                    .lastname(appUserdto.getLastname())
                    .street(appUserdto.getStreet())
                    .zipCode(appUserdto.getZipCode())
                    .city(appUserdto.getCity())
                    .build();

            String token = UUID.randomUUID().toString(); //generate register confirmation token

            appUserRepo.save(appUser);

            AppUserDetails userDetails = new AppUserDetails().builder()
                    .appUser(appUser)
                    .build();

            appUserDetailsRepo.save(userDetails);

            RegistrationConfirmToken confirmToken = RegistrationConfirmToken.builder()
                    .token(token)
                    .createdOn(LocalDateTime.now())
                    .expiresOn(LocalDateTime.now().plusMinutes(20)) //the token will expire after 20 minutes
                    .appUser(appUser)
                    .build();

            registrationConfirmTokenService.saveConfirmationToken(confirmToken);

            return token;
        }
        return null;
    }

    public AppUserDto updateUser(AppUser appUser){
        AppUser newAppUser = appUserRepo.findByUsername(appUser.getUsername())
                .orElseThrow(() -> new AppUserNotFoundException(appUser.getUsername()));
        return appUserDtoMapper.mapToDto(appUserRepo.save(newAppUser));
    }

    public void deleteUser(Long id){
        if(appUserRepo.existsById(id)){
            appUserRepo.deleteById(id);
        }
        else {
            throw new AppUserNotFoundException(id);
        }
    }

    @Transactional
    public void enableAppUser(String username){
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));
        AppUserDetails userDetails = appUserDetailsRepo.findByAppUserId(user.getId())
                .orElseThrow(() -> new AppUserDetailsNotFoundException(user.getId()));

        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setEnabled(true);
        appUserDetailsRepo.save(userDetails);
    }

}
