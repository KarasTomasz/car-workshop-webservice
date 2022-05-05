package pl.tkaras.carworkshopwebservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.AppUserDetailsMapper;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.AppUserMapper;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.RegistrationConfirmTokenMapper;
import pl.tkaras.carworkshopwebservice.services.AppUserService;
import pl.tkaras.carworkshopwebservice.services.RegistrationService;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDetailsDto;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDto;
import pl.tkaras.carworkshopwebservice.models.dtos.RegistrationConfirmTokenDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppUserController {

    private final AppUserService appUserService;

    private final RegistrationService registrationService;

    private final AppUserMapper appUserMapper;

    private final AppUserDetailsMapper appUserDetailsMapper;

    private final RegistrationConfirmTokenMapper confirmTokenMapper;

    public AppUserController(AppUserService appUserService, RegistrationService registrationService, AppUserMapper appUserMapper, AppUserDetailsMapper appUserDetailsMapper, RegistrationConfirmTokenMapper confirmTokenMapper) {
        this.appUserService = appUserService;
        this.registrationService = registrationService;
        this.appUserMapper = appUserMapper;
        this.appUserDetailsMapper = appUserDetailsMapper;
        this.confirmTokenMapper = confirmTokenMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('user:readAll')")
    public List<AppUserDto> getAllUsers(){
        return appUserMapper.mapToDtos(appUserService.getAllUsers());
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<AppUserDto> getAppUser(@RequestParam("username") String username){
        AppUser appUser = appUserService.getUser(username);
        return new ResponseEntity<>(appUserMapper.mapToDto(appUser), HttpStatus.OK);
    }

    @GetMapping("/details/all")
    @PreAuthorize("hasAuthority('user:readAll')")
    public List<AppUserDetailsDto> getAllUsersDetails(){
        return appUserDetailsMapper.mapToDtos(appUserService.getAllUsersDetails());
    }

   @GetMapping("/details/")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<AppUserDetailsDto> getAppUserDetail(@RequestParam("username") String username){
       AppUserDetails appUserDetails = appUserService.getUserDetail(username);
        return new ResponseEntity<>(appUserDetailsMapper.mapToDto(appUserDetails), HttpStatus.OK);
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        registrationService.confirm(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationConfirmTokenDto> addAppUser(@RequestBody AppUserDto appUserdto){
        AppUser appUser = appUserMapper.mapToEntity(appUserdto);
        RegistrationConfirmTokenDto registrationConfirmTokenDto = confirmTokenMapper.mapToDto(registrationService.register(appUser));
        return new ResponseEntity<>(registrationConfirmTokenDto, HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<AppUserDto> updateAppUserRole(@RequestBody AppUserDto appUserdto){
        AppUser appUser = appUserService.updateUser(appUserMapper.mapToEntity(appUserdto));
        return ResponseEntity.ok().body(appUserMapper.mapToDto(appUser));
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Object> deleteAppUser(@RequestParam("id") Long id){
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
