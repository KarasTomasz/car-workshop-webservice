package pl.tkaras.carworkshopwebservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDetailsDto;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.AppUserDetailsDtoMapper;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.AppUserDtoMapper;
import pl.tkaras.carworkshopwebservice.repositories.AppUserDetailsRepository;
import pl.tkaras.carworkshopwebservice.repositories.AppUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private AppUserDetailsRepository appUserDetailsRepository;
    @Mock
    private AppUserService appUserService;
    @Mock
    private RegistrationConfirmTokenService registrationConfirmTokenService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AppUserDetailsDtoMapper appUserDetailsDtoMapper;
    @Mock
    private AppUserDtoMapper appUserDtoMapper;


    @BeforeEach
    public void init() {
        appUserDtoMapper = new AppUserDtoMapper();
        appUserDetailsDtoMapper = new AppUserDetailsDtoMapper();

        passwordEncoder = mock(PasswordEncoder.class);

        registrationConfirmTokenService = mock(RegistrationConfirmTokenService.class);

        appUserService = new AppUserService(appUserRepository,
                appUserDetailsRepository,
                appUserDtoMapper,
                appUserDetailsDtoMapper,
                passwordEncoder,
                registrationConfirmTokenService);
    }

    @Test
    void shouldReturnAllAppUsersIfAppUsersExist() {
        //given
        when(appUserRepository.findAll()).thenReturn(prepareAppUsers());

        //when
        List<AppUserDto> users = appUserService.getAllUsers();

        //then
        assertEquals(prepareAppUserDtos().size(), users.size());
    }

    @Test
    void shouldReturnEmptyListIfAppUsersNotExist() {
        //given
        when(appUserRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        List<AppUserDto> users = appUserService.getAllUsers();

        //then
        assertEquals(new ArrayList<>(), users);
    }

    @Test
    void shouldReturnAppUserIfExist() {
        //given
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.of(prepareAppUser_1()));

        //when
        Optional<AppUserDto> result = appUserService.getUser("appUser");

        //then
        assertNotNull(result);

        assertEquals(prepareAppUser_1().getUsername(), result
                .orElseThrow(() -> new AppUserNotFoundException(prepareAppUser_1().getUsername()))
                .getUsername());
    }

    @Test
    void shouldThrowExceptionIfAppUserNotExist() {

        //given
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //when
        var exception = catchThrowable(() -> appUserService.getUser(prepareAppUser_1().getUsername()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserNotFoundException.class)
                .hasMessageContaining("not found");

    }

    @Test
    void shouldReturnAllAppUserDetailsIfExist(){
        //given
        when(appUserDetailsRepository.findAll()).thenReturn(prepareAppUserDetails());

        //when
        List<AppUserDetailsDto> appUserDetailsDtos = appUserService.getAllUsersDetails();

        //then
        assertEquals(prepareAppUserDetailsDto().size(), appUserDetailsDtos.size());
    }

    @Test
    void shouldReturnEmptyListIfAppUserDetailsNotExist(){
        //given
        when(appUserDetailsRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        List<AppUserDetailsDto> appUserDetailsDtos = appUserService.getAllUsersDetails();

        //then
        assertEquals(new ArrayList<>(), appUserDetailsDtos);
    }

    @Test
    void shouldReturnAppUserDetailsIfExist(){
        //given
        when(appUserRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(prepareAppUser_1()));

        when(appUserDetailsRepository.findByAppUserId(null))
                .thenReturn(Optional.of(prepareAppUserDetail_1()));
        //when
        Optional<AppUserDetailsDto> appUserDetailsDto = appUserService.getUserDetail(prepareAppUserDetail_1().getAppUser().getUsername());

        //then
        assertNotNull(appUserDetailsDto);

        assertEquals(prepareAppUser_1().getUsername(), appUserDetailsDto
                .orElseThrow()
                .getUsername());
    }

    @Test
    void shouldSignUpAppUser(){
        //given

        //when
        String token = appUserService.signUp(prepareAppUserDto_1());

        //then
        assertNotNull(token);
    }

    @Test
    void shouldUpdateAppUserIfExist(){
        //given
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.of(prepareAppUser_1()));
        when(appUserRepository.save(any())).thenReturn(prepareAppUser_1());

        //when
        AppUserDto appUserDto = appUserService.updateUser(prepareAppUser_1());

        //then
        assertEquals(prepareAppUserDto_1().getUsername(), appUserDto.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUpdateAppUserAndAppUserNotExist(){
        //given
        when(appUserRepository.findByUsername(anyString())).thenThrow(new AppUserNotFoundException(prepareAppUser_1().getUsername()));

        //when
        var exception = catchThrowable(() -> appUserService.updateUser(prepareAppUser_1()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldThrowExceptionWhenDeleteAppUserAndAppUserNotExist(){
        //given

        //when
        var exception = catchThrowable(() -> appUserService.deleteUser(prepareAppUser_1().getId()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserNotFoundException.class)
                .hasMessageContaining("not found");
    }

    List<AppUser> prepareAppUsers(){
        return Stream.of(prepareAppUser_1(), prepareAppUser_2())
                .collect(Collectors.toList());
    }

    AppUser prepareAppUser_1(){
        return AppUser.builder()
                .username("client 1")
                .password("password")
                .email("client1@gmail.com")
                .firstname("Jan")
                .lastname("Kowalski")
                .street("Kosciuszki")
                .zipCode("50-555")
                .city("Wroclaw")
                .build();
    }

    AppUser prepareAppUser_2(){
        return AppUser.builder()
                .username("client 1")
                .password("password")
                .email("client1@gmail.com")
                .firstname("Jan")
                .lastname("Kowalski")
                .street("Kosciuszki")
                .zipCode("50-555")
                .city("Wroclaw")
                .build();
    }

    List<AppUserDto> prepareAppUserDtos(){
        return Stream.of(prepareAppUserDto_1(), prepareAppUserDto_2())
                .collect(Collectors.toList());
    }

    AppUserDto prepareAppUserDto_1(){
        return AppUserDto.builder()
                .username("client 1")
                .email("client1@gmail.com")
                .firstname("Jan")
                .lastname("Kowalski")
                .street("Kosciuszki")
                .zipCode("50-555")
                .city("Wroclaw")
                .build();
    }

    AppUserDto prepareAppUserDto_2(){
        return AppUserDto.builder()
                .username("client 1")
                .email("client1@gmail.com")
                .firstname("Jan")
                .lastname("Kowalski")
                .street("Kosciuszki")
                .zipCode("50-555")
                .city("Wroclaw")
                .build();
    }

    List<AppUserDetails> prepareAppUserDetails(){
        return Stream.of(prepareAppUserDetail_1(), prepareAppUserDetail_2())
                .collect(Collectors.toList());
    }

    AppUserDetails prepareAppUserDetail_1(){
        return AppUserDetails.builder()
                .appUser(prepareAppUser_1())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .role("CLIENT")
                .build();
    }

    AppUserDetails prepareAppUserDetail_2(){
        return AppUserDetails.builder()
                .appUser(prepareAppUser_1())
                .isAccountNonExpired(false)
                .isAccountNonLocked(false)
                .isCredentialsNonExpired(false)
                .isEnabled(false)
                .role("CLIENT")
                .build();
    }

    List<AppUserDetailsDto> prepareAppUserDetailsDto(){
        return Stream.of(prepareAppUserDetailDto_1(), prepareAppUserDetailDto_2())
                .collect(Collectors.toList());
    }

    AppUserDetailsDto prepareAppUserDetailDto_1(){
        return AppUserDetailsDto.builder()
                .username(prepareAppUser_1().getUsername())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .userRole("CLIENT")
                .build();
    }

    AppUserDetailsDto prepareAppUserDetailDto_2(){
        return AppUserDetailsDto.builder()
                .username(prepareAppUser_1().getUsername())
                .isAccountNonExpired(false)
                .isAccountNonLocked(false)
                .isCredentialsNonExpired(false)
                .isEnabled(false)
                .userRole("CLIENT")
                .build();
    }
}