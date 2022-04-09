package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.AppUserDtoMapper;
import pl.tkaras.carworkshopwebservice.repository.AppUserRepository;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepo;
    private final AppUserDtoMapper appUserDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepo,
                       AppUserDtoMapper appUserDtoMapper,
                       PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.appUserDtoMapper = appUserDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUserDto getUserRole(String username){
        return appUserDtoMapper.mapToDto(appUserRepo.findByUsername(username).get());
    }

    public List<AppUserDto> getAllUsers(){
        return appUserDtoMapper.mapToDtos(appUserRepo.findAll());
    }

    public ResponseEntity<?> addUser(AppUser entity){
        if(!appUserRepo.existsByUsername(entity.getUsername())){
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            appUserRepo.save(entity);
        }
        else{
            //TODO: add properly response
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    //@Transactional
    public ResponseEntity<?> updateUser(AppUser appUser){
        if(appUserRepo.existsByUsername(appUser.getUsername())){
            AppUser newAppUser = appUserRepo.findByUsername(appUser.getUsername()).get();
            appUserRepo.save(newAppUser);
        }
        else{
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    //TODO: checking if the request comes from Admin
    public ResponseEntity deleteUser(Long id){
        if(appUserRepo.existsById(id)){
            appUserRepo.deleteById(id);
        }
        else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
