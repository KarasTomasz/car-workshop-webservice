package pl.tkaras.carworkshopwebservice.UnitTests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserAddressNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserAddress;
import pl.tkaras.carworkshopwebservice.repositories.AppUserAddressRepository;
import pl.tkaras.carworkshopwebservice.services.AppUserAddressService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserAddressServiceTest {

    @Mock
    private AppUserAddressRepository userAddressRepo;

    @Mock
    private AppUserAddressService appUserAddressService;

    @BeforeEach
    public void init() {
        appUserAddressService = new AppUserAddressService(userAddressRepo);
    }

    @Test
    void shouldReturnAllAppUserAddressesIfExist() {
        //given
        when(userAddressRepo.findAll()).thenReturn(prepareAppUsers());

        //when
        List<AppUserAddress> users = appUserAddressService.getAllUsers();

        //then
        assertEquals(prepareAppUsers().size(), users.size());
    }

    @Test
    void shouldReturnEmptyListIfAppUserAddressesNotExist() {
        //given
        when(userAddressRepo.findAll()).thenReturn(new ArrayList<>());

        //when
        List<AppUserAddress> users = appUserAddressService.getAllUsers();

        //then
        assertEquals(new ArrayList<>(), users);
    }

    @Test
    void shouldReturnAppUserAddressIfExists() {
        //given
        when(userAddressRepo.findById(anyLong())).thenReturn(Optional.of(prepareAppUserAddress_1()));

        //when
        AppUserAddress appUserAddress = appUserAddressService.getUser(1L);

        //then
        assertNotNull(appUserAddress);

        assertEquals(prepareAppUserAddress_1().getFirstName(), appUserAddress.getFirstName());
        assertEquals(prepareAppUserAddress_1().getLastName(), appUserAddress.getLastName());
        assertEquals(prepareAppUserAddress_1().getStreet(), appUserAddress.getStreet());
        assertEquals(prepareAppUserAddress_1().getZipCode(), appUserAddress.getZipCode());
        assertEquals(prepareAppUserAddress_1().getCity(), appUserAddress.getCity());
    }

    @Test
    void shouldThrowExceptionIfAppUserAddressNotExists() {
        //given
        when(userAddressRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when
        var exception = catchThrowable(() -> appUserAddressService.getUser(1L));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserAddressNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldUpdateAppUserIfExist(){
        //given
        when(userAddressRepo.findById(anyLong())).thenReturn(Optional.of(prepareAppUserAddress_1()));
        when(userAddressRepo.save(any())).thenReturn(prepareAppUserAddress_1());

        //when
        AppUserAddress appUserAddress = appUserAddressService.updateUser(1L ,prepareAppUserAddress_1());

        //then
        assertEquals(prepareAppUserAddress_1().getAppUser().getUsername(), appUserAddress.getAppUser().getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUpdateAppUserAndAppUserNotExist(){
        //given
        when(userAddressRepo.findById(anyLong())).thenThrow(new AppUserAddressNotFoundException(prepareAppUser_1().getId()));

        //when
        var exception = catchThrowable(() -> appUserAddressService.updateUser(1L, prepareAppUserAddress_1()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserAddressNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldThrowExceptionWhenDeleteAppUserAddressAndNotExist(){
        //given

        //when
        var exception = catchThrowable(() -> appUserAddressService.deleteUser(1L));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserAddressNotFoundException.class)
                .hasMessageContaining("not found");
    }

    List<AppUserAddress> prepareAppUsers(){
        return Stream.of(prepareAppUserAddress_1(), prepareAppUserAddress_2())
                .collect(Collectors.toList());
    }

    AppUserAddress prepareAppUserAddress_1(){
        return AppUserAddress.builder()
                .appUser(prepareAppUser_1())
                .firstName("Jan")
                .lastName("Kowalski")
                .street("Kosciuszki")
                .zipCode("50-555")
                .city("Wroclaw")
                .build();
    }

    AppUserAddress prepareAppUserAddress_2(){
        return AppUserAddress.builder()
                .appUser(prepareAppUser_1())
                .firstName("Piotr")
                .lastName("Nowak")
                .street("Dubois")
                .zipCode("50-555")
                .city("Wroclaw")
                .build();
    }

    AppUser prepareAppUser_1(){
        return AppUser.builder()
                .username("client 1")
                .password("password")
                .email("client1@gmail.com")
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isEnabled(true)
                .build();
    }
}