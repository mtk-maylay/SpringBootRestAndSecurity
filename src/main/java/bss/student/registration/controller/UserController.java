package bss.student.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import bss.student.registration.model.TokenResponse;
import bss.student.registration.model.UserCredential;
import bss.student.registration.security.JwtTokenProvider;

@RestController
public class UserController {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody UserCredential user) {

		TokenResponse response = new TokenResponse();
		String username = user.getUsername();
		try {
			String token = jwtTokenProvider.createToken(username, "USER", user.getTenantName());
			response.setToken("Bearer " + token);
			return new ResponseEntity<TokenResponse>(response, HttpStatus.OK);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
