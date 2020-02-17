package com.gagan.banking.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping(value = "/")
    public String index(){
        return "Welcome to Banking Application.";
    }
}
