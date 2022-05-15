package pl.tkaras.carworkshopwebservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserAddress;

@Repository
public interface AppUserAddressRepository extends JpaRepository<AppUserAddress, Long> {

}