package pl.tkaras.carworkshopwebservice.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.model.entity.User;
import pl.tkaras.carworkshopwebservice.repository.UserRepository;

@Repository
public interface SqlUserRepository extends JpaRepository<User, Long>, UserRepository {

}
