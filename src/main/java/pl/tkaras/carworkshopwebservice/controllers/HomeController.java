package pl.tkaras.carworkshopwebservice.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeController {

    @GetMapping("/")
    public String homePage(){
        return "home";
    }


}
