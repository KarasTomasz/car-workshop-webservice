package pl.tkaras.carworkshopwebservice.logic;

import com.sun.xml.bind.v2.TODO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.UserDto;
import pl.tkaras.carworkshopwebservice.model.dto.UserDtoMapper;
import pl.tkaras.carworkshopwebservice.model.entity.Rank;
import pl.tkaras.carworkshopwebservice.model.entity.User;
import pl.tkaras.carworkshopwebservice.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDto getUserRank(String username){
        return UserDtoMapper.mapToUserDto(userRepo.findByUsername(username));
    }

    public List<UserDto> getAllUsers(){
        return UserDtoMapper.mapToUserDtos(userRepo.findAll());
    }

    public ResponseEntity<?> addUser(User entity){
        if(!userRepo.existsByUsername(entity.getUsername())){
            userRepo.save(entity);
        }
        else{
            //TODO: add properly response
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> updateUser(String username, Rank rank){
        if(userRepo.existsByUsername(username)){
            User user = userRepo.findByUsername(username);
            user.setUserRank(rank);
            userRepo.save(user);
        }
        else{
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    //TODO: checking if the request comes from Admin
    public ResponseEntity deleteUser(Long id){
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
        }
        else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
