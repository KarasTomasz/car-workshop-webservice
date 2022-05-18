package pl.tkaras.carworkshopwebservice.UnitTests.services;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.CarNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.Car;
import pl.tkaras.carworkshopwebservice.repositories.CarRepository;
import pl.tkaras.carworkshopwebservice.services.CarService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private CarService carService;

    @BeforeEach
    void init(){
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
    }

    @Test
    void shouldReturnAllCarsIfExist() {
        //given
        when(carRepository.findAll()).thenReturn(prepareCarData());

        //when
        List<Car> cars = carService.gelAllCars();

        //then
        assertEquals(prepareCarData().size(), cars.size());
    }

    @Test
    void shouldReturnEmptyListWhenGetAllCarsAndExist() {
        //given
        when(carRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        List<Car> cars = carService.gelAllCars();

        //then
        assertEquals(new ArrayList<>(), cars);
    }

    @Test
    void shouldReturnAllCarsByUsernameIfExist() {
        //given
        when(carRepository.findByUsername(anyString())).thenReturn(Optional.of(prepareCar1().getAppUser()));
        when(carRepository.findAllByAppUserId(anyLong())).thenReturn(prepareCarData());

        //when
        List<Car> cars = carService.gelAllCarsByUsername(prepareCar1().getAppUser().getUsername());

        //then
        assertNotNull(cars);
        assertEquals(prepareCarData().size(), cars.size());
    }

    @Test
    void shouldThrowExceptionWhenAllCarsByUsernameAndNotExist() {
        //given
        when(carRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> carService.gelAllCarsByUsername(prepareCar1().getAppUser().getUsername()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldReturnCarWhenFindByIdAndExist() {
        //given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(prepareCar1()));

        //when
        Car car = carService.getCar(prepareCar1().getId());

        //then
        assertNotNull(car);
        assertEquals(prepareCar1().getAppUser().getUsername(), car.getAppUser().getUsername());
    }

    @Test
    void shouldThrowExceptionWhenFindByIdAndNotExist() {
        //given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> carService.getCar(prepareCar1().getId()));

        //then
        assertThat(exception)
                .isInstanceOf(CarNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldAddCarIfAppUserExist (){
        //given
        when(carRepository.findByUsername(anyString())).thenReturn(Optional.of(prepareCar1().getAppUser()));
        when(carRepository.save(any())).thenReturn(prepareCar1());

        //when
        Car car = carService.addCar(prepareCar1());

        //then
        assertEquals(prepareCar1().getMark(), car.getMark());
        assertEquals(prepareCar1().getModel(), car.getModel());
        assertEquals(prepareCar1().getPrice(), car.getPrice());
        assertEquals(prepareCar1().getImageUrl(), car.getImageUrl());
    }

    @Test
    void shouldThrowExceptionWhenAddCarAndAppUserNotExist (){
        //given
        when(carRepository.findByUsername(any())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> carService.addCar(prepareCar1()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldUpdateCarIfExist(){
        //given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(prepareCar1()));
        when(carRepository.save(any())).thenReturn(prepareCar2());

        //when
        Car car = carService.updateCar(prepareCar1().getId(),prepareCar2());

        //then
        assertEquals(prepareCar2().getMark(), car.getMark());
        assertEquals(prepareCar2().getModel(), car.getModel());
        assertEquals(prepareCar2().getPrice(), car.getPrice());
        assertEquals(prepareCar2().getImageUrl(), car.getImageUrl());
    }

    @Test
    void shouldThrowExceptionWhenUpdateCarAndCarNotExists(){
        //given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> carService.updateCar(prepareCar1().getId(),prepareCar1()));

        //then
        assertThat(exception)
                .isInstanceOf(CarNotFoundException.class)
                .hasMessageContaining("not found");
    }

    private List<Car> prepareCarData(){
        return Stream.of(prepareCar1(), prepareCar2(), prepareCar3())
                .collect(Collectors.toList());
    }

    AppUser prepareAppUser_1(){
        return AppUser.builder()
                .id(1L)
                .username("client 1")
                .password("password")
                .email("client1@gmail.com")
                .role("USER")
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }

    private Car prepareCar1(){
        return Car.builder()
                .id(1L)
                .appUser(prepareAppUser_1())
                .mark("Fiat")
                .model("125P")
                .price(BigDecimal.valueOf(10000))
                .imageUrl("http://image_1.pl")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    private Car prepareCar2(){
        return Car.builder()
                .id(2L)
                .appUser(prepareAppUser_1())
                .mark("Renault")
                .model("Megan")
                .price(BigDecimal.valueOf(20000))
                .imageUrl("http://image_2.pl")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    private Car prepareCar3(){
        return Car.builder()
                .id(3L)
                .appUser(prepareAppUser_1())
                .mark("Subaru")
                .model("xv")
                .price(BigDecimal.valueOf(30000))
                .imageUrl("http://image_3.pl")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }
}