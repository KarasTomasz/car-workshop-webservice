package pl.tkaras.carworkshopwebservice.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.model.entity.AppUserDetails;
import pl.tkaras.carworkshopwebservice.repository.AppUserDetailsRepository;

@Repository
public interface SqlAppUserDetailsRepository extends JpaRepository<AppUserDetails, Long>, AppUserDetailsRepository {

}