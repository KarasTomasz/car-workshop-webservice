package pl.tkaras.carworkshopwebservice.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.repositories.AppUserRepository;

@Repository
public interface SqlAppUserRepository extends JpaRepository<AppUser, Long>, AppUserRepository {

}
