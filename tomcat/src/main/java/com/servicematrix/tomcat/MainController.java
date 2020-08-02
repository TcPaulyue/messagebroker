package com.servicematrix.tomcat;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class MainController {
    @GetMapping
    public String getMessage(@RequestParam(value = "params",defaultValue = "null") String param){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param+"good bye";
    }
}
