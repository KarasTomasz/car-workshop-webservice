package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://app-car-workshop.herokuapp.com", maxAge = 3600)
public class HomeController {

    @GetMapping("/")
    public String homePage(){
        return "home";
    }


}
