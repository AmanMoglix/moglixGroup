package com.hackathon.gateway.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GatewayController {
	 @GetMapping(value = "/documentation")
	    public String documentation(Model model) {
	        return "index";
	    }
}
