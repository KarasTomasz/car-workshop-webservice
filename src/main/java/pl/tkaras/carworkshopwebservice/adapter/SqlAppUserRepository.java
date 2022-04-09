package pl.tkaras.carworkshopwebservice.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.repository.AppUserRepository;

@Repository
public interface SqlAppUserRepository extends JpaRepository<AppUser, Long>, AppUserRepository {

}
