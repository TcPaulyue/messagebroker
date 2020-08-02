package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class HelloController {
    @GetMapping("/")
    public Mono<String> hello(@RequestParam(value = "params",defaultValue = "null") String param) {
        return Mono.just(param+"goodbye").delayElement(Duration.ofMillis(100)); // 1
    }


}

