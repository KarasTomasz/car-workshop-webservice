package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.AppUserService;
import pl.tkaras.carworkshopwebservice.logic.RegistrationService;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDetailsDto;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDto;
import pl.tkaras.carworkshopwebservice.model.dto.RegistrationConfirmTokenDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppUserController {

    private final AppUserService appUserService;
    private final RegistrationService registrationService;

    public AppUserController(AppUserService appUserService, RegistrationService registrationService) {
        this.appUserService = appUserService;
        this.registrationService = registrationService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('user:readAll')")
    public List<AppUserDto> getAllUsers(){
        return appUserService.getAllUsers();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<AppUserDto> getAppUser(@RequestParam("username") String username){
        return appUserService.getUser(username)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/details/all")
    @PreAuthorize("hasAuthority('user:readAll')")
    public List<AppUserDetailsDto> getAllUsersDetails(){
        return appUserService.getAllUsersDetails();
    }

   @GetMapping("/details/")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<AppUserDetailsDto> getAppUserDetail(@RequestParam("username") String username){
        return appUserService.getUserDetail(username)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<Object> confirm(@RequestParam("token") String token) {
        registrationService.confirm(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationConfirmTokenDto> addAppUser(@RequestBody AppUserDto appUserdto){
        return new ResponseEntity<>(registrationService.register(appUserdto), HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<AppUserDto> updateAppUserRole(@RequestBody AppUser appUser){
        return ResponseEntity.ok().body(appUserService.updateUser(appUser));
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Object> deleteAppUser(@RequestParam("id") Long id){
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
