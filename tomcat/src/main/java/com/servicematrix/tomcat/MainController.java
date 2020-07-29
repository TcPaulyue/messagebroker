package com.servicematrix.tomcat;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping
    public String getMessage(@RequestParam(value = "param",defaultValue = "null") String param){
        return param+"good bye";
    }
}
