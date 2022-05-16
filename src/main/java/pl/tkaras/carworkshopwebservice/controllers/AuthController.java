package pl.tkaras.carworkshopwebservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.models.dtos.AuthDto;
import pl.tkaras.carworkshopwebservice.models.dtos.RegistrationConfirmTokenDto;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.models.mappers.IAuthMapper;
import pl.tkaras.carworkshopwebservice.models.mappers.IRegistrationConfirmTokenMapper;
import pl.tkaras.carworkshopwebservice.services.AuthService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    private final IAuthMapper authMapper;

    private final IRegistrationConfirmTokenMapper confirmTokenMapper;

    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestParam String username, @RequestParam String password){
        String token =  authService.login(username, password);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationConfirmTokenDto> addAppUser(@RequestBody AuthDto authDto){
        RegistrationConfirmToken registrationConfirmToken = authService.register(authMapper.mapToEntity(authDto));
        RegistrationConfirmTokenDto confirmTokenDto = confirmTokenMapper.mapToDto(registrationConfirmToken);
        return new ResponseEntity<>(confirmTokenDto, HttpStatus.CREATED);
    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        authService.confirm(token);
        return ResponseEntity.ok().build();
    }
}
