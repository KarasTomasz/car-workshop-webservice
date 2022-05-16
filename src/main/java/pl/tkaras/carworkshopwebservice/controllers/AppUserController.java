package pl.tkaras.carworkshopwebservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserAddressDto;
import pl.tkaras.carworkshopwebservice.models.mappers.IAppUserAddressMapper;
import pl.tkaras.carworkshopwebservice.services.AppUserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class AppUserController {

    private final AppUserService appUserService;

    private final IAppUserAddressMapper appUserAddressMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('user:readAll')")
    public List<AppUserAddressDto> getAllUsers(){
        return appUserAddressMapper.mapToDtos(appUserService.getAllUsers());
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<AppUserAddressDto> getAppUser(@RequestParam("id") Long id){
        AppUserAddressDto appUserAddressDtoDto = appUserAddressMapper.mapToDto(appUserService.getUser(id));
        return new ResponseEntity<>(appUserAddressDtoDto, HttpStatus.OK);
    }

    @GetMapping("/details/all")
    @PreAuthorize("hasAuthority('user:readAll')")
    public List<AppUserAddressDto> getAllUsersDetails(){
        return appUserAddressMapper.mapToDtos(appUserService.getAllUsers());
    }

   @GetMapping("/details/")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<AppUserAddressDto> getAppUserDetail(@RequestParam("id") Long id){
        AppUserAddressDto appUserAddressDto = appUserAddressMapper.mapToDto(appUserService.getUser(id));
        return new ResponseEntity<>(appUserAddressDto, HttpStatus.OK);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<AppUserAddressDto> updateAppUserRole(@RequestParam("id") Long id, @RequestBody AppUserAddressDto userAddressDto){
        AppUserAddressDto appUserAddressDto = appUserAddressMapper.mapToDto(appUserService.updateUser(id, appUserAddressMapper.mapToEntity(userAddressDto)));
        return ResponseEntity.ok().body(appUserAddressDto);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<?> deleteAppUser(@RequestParam("id") Long id){
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}