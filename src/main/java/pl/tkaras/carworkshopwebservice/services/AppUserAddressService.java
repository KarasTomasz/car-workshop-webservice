package pl.tkaras.carworkshopwebservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserAddressNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserAddress;
import pl.tkaras.carworkshopwebservice.repositories.AppUserAddressRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppUserAddressService {

    private final AppUserAddressRepository appUserAddressRepo;

    public List<AppUserAddress> getAllUsers(){
        return appUserAddressRepo.findAll();
    }

    public AppUserAddress getUser(Long id){
        return appUserAddressRepo.findById(id)
                .orElseThrow(() -> new AppUserAddressNotFoundException(id));
    }

    public AppUserAddress updateUser(Long id, AppUserAddress appUserAddress){

        AppUserAddress foundAppUserAddress = appUserAddressRepo.findById(id)
                .orElseThrow(() -> new AppUserAddressNotFoundException(id));

        foundAppUserAddress.setFirstName(appUserAddress.getFirstName());
        foundAppUserAddress.setLastName(appUserAddress.getLastName());
        foundAppUserAddress.setStreet(appUserAddress.getStreet());
        foundAppUserAddress.setZipCode(appUserAddress.getZipCode());
        foundAppUserAddress.setCity(appUserAddress.getCity());

        return appUserAddressRepo.save(foundAppUserAddress);
    }

    public void deleteUser(Long id){
        if(appUserAddressRepo.existsById(id)){
            appUserAddressRepo.deleteById(id);
        }
        else {
            throw new AppUserAddressNotFoundException(id);
        }
    }
}
