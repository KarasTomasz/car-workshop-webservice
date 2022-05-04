package pl.tkaras.carworkshopwebservice.services;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.CarNotFoundException;
import pl.tkaras.carworkshopwebservice.models.dtos.CarDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.Car;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.CarDtoMapper;
import pl.tkaras.carworkshopwebservice.repositories.CarRepository;

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
    @Mock
    private CarDtoMapper carDtoMapper;

    @BeforeEach
    void init(){
        carRepository = mock(CarRepository.class);
        carDtoMapper = new CarDtoMapper();

        carService = new CarService(carRepository, carDtoMapper);
    }

    @Test
    void shouldReturnAllCarsIfExist() {
        //given
        when(carRepository.findAll()).thenReturn(prepareCarData());

        //when
        List<CarDto> carDtos = carService.gelAllCars();

        //then
        assertEquals(prepareCarDtoData().size(), carDtos.size());
    }

    @Test
    void shouldReturnEmptyListWhenGetAllCarsAndExist() {
        //given
        when(carRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        List<CarDto> carDtos = carService.gelAllCars();

        //then
        assertEquals(new ArrayList<>(), carDtos);
    }

    @Test
    void shouldReturnAllCarsByUsernameIfExist() {
        //given
        when(carRepository.findByUsername(anyString())).thenReturn(Optional.of(prepareCar1().getAppUser()));
        //when(carRepository.findAllByAppUserId(anyLong())).thenReturn(prepareCarData());
        when(carRepository.findAllByAppUserId(null)).thenReturn(prepareCarData());

        //when
        List<CarDto> carDtos = carService.gelAllCarsByUsername(prepareCar1().getAppUser().getUsername());

        //then
        assertNotNull(carDtos);
        assertEquals(prepareCarDtoData().size(), carDtos.size());
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
        Optional<CarDto> carDto = carService.getCar(prepareCar1().getId());

        //then
        assertNotNull(carDto);
        assertEquals(prepareCarDto_1().getUsername(), carDto.orElseThrow().getUsername());
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
        when(carRepository.save(any())).thenReturn(prepareCar1());
        when(carRepository.findByUsername(any())).thenReturn(Optional.of(prepareCar1().getAppUser()));

        //when
        CarDto carDto = carService.addCar(prepareCarDto_1());

        //then
        assertEquals(prepareCarDto_1().getUsername(), carDto.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenAddCarAndAppUserNotExist (){
        //given
        when(carRepository.findByUsername(any())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> carService.addCar(prepareCarDto_1()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldUpdateCarIfExist(){
        //given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(prepareCar1()));
        when(carRepository.save(any())).thenReturn(prepareCar1());

        //when
        CarDto cardto = carService.updateCar(prepareCarDto_1().getId(),prepareCarDto_1());

        //then
        assertEquals(prepareCarDto_1().getUsername(), cardto.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUpdateCarAndCarNotExist(){
        //given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> carService.updateCar(prepareCarDto_1().getId(),prepareCarDto_1()));

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

    private Car prepareCar1(){
        return Car.builder()
                .id(1L)
                .appUser(prepareAppUser_1())
                .mark("Fiat")
                .model("125P")
                .description("description 1")
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
                .description("description 2")
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
                .description("description 3")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    private List<CarDto> prepareCarDtoData(){
        return Stream.of(prepareCarDto_1(), prepareCarDto_2(), prepareCarDto_3())
                .collect(Collectors.toList());
    }

    private CarDto prepareCarDto_1(){
        return CarDto.builder()
                .id(1L)
                .username(prepareAppUser_1().getUsername())
                .mark("Fiat")
                .model("125P")
                .description("description 1")
                .build();
    }

    private CarDto prepareCarDto_2(){
        return CarDto.builder()
                .id(2L)
                .username(prepareAppUser_1().getUsername())
                .mark("Renault")
                .model("Megan")
                .description("description 2")
                .build();
    }

    private CarDto prepareCarDto_3(){
        return CarDto.builder()
                .id(3L)
                .username(prepareAppUser_1().getUsername())
                .mark("Subaru")
                .model("xv")
                .description("description 3")
                .build();
    }

}