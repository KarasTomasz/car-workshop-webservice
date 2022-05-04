package pl.tkaras.carworkshopwebservice.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;
import pl.tkaras.carworkshopwebservice.repositories.AppUserDetailsRepository;

@Repository
public interface SqlAppUserDetailsRepository extends JpaRepository<AppUserDetails, Long>, AppUserDetailsRepository {

}