package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.AppUserService;
import pl.tkaras.carworkshopwebservice.logic.RegistrationService;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {

    private final AppUserService appUserService;
    private final RegistrationService registrationService;

    public AppUserController(AppUserService appUserService, RegistrationService registrationService) {
        this.appUserService = appUserService;
        this.registrationService = registrationService;
        ;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('user:read')")
    public List<AppUserDto> getAllUsers(){
        return appUserService.getAllUsers();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public AppUserDto getAppUser(@RequestBody String username){
        return null;
    }

    @PostMapping("/registration")
    public ResponseEntity addAppUser(@RequestBody AppUser appUser){
        return registrationService.register(appUser);
    }

    @GetMapping(path = "/registration/confirm")
    public ResponseEntity confirm(@RequestParam("token") String token) {
        return registrationService.confirm(token);
    }

    @PatchMapping("")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity updateAppUserRole(@RequestBody AppUser appUser){
        return appUserService.updateUser(appUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity deleteAppUser(@PathVariable() Long id){
        return appUserService.deleteUser(id);
    }


}
