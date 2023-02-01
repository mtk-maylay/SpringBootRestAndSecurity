package bss.student.registration.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.student.registration.model.UserCredential;
import bss.student.registration.repository.UserCredentialRepository;
import bss.student.registration.service.UserCrendentialService;

@Service
public class UserCredentialServiceImpl implements UserCrendentialService {

	@Autowired
	private UserCredentialRepository userCredentialRepository;

	@Override
	public void create(UserCredential user) {

		this.userCredentialRepository.save(user);

	}

	@Override
	public UserCredential findByUsername(String username) {

		Optional<UserCredential> userOptional = this.userCredentialRepository.findByUsername(username);
		return userOptional.isPresent() ? userOptional.get() : null;
	}

}
