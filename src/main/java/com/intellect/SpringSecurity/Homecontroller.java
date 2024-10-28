package com.intellect.SpringSecurity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Homecontroller {
	
	@GetMapping("home")
	public String home() {
		return "index";
	}
	
	
	@GetMapping("user")
	public String user() {
		return "hello user";
	}


	@GetMapping("admin")
	public String admin() {
		return "hello admin";
	}
}
