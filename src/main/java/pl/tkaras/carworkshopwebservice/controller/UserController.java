package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.UserService;
import pl.tkaras.carworkshopwebservice.model.dto.UserDto;
import pl.tkaras.carworkshopwebservice.model.entity.Rank;
import pl.tkaras.carworkshopwebservice.model.entity.User;
import pl.tkaras.carworkshopwebservice.security.ApplicationUserRole;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("")
    public UserDto getUser(@RequestBody String username){
        return null;
    }

    @PostMapping("")
    public ResponseEntity addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PatchMapping("")
    public ResponseEntity updateUserRank(@RequestBody String username, String role){
        return userService.updateUser(username, role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable() Long id){
        return userService.deleteUser(id);
    }


}
