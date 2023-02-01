package bss.student.registration.service;

import bss.student.registration.model.UserCredential;

public interface UserCrendentialService {

	public void create(UserCredential user);

	public UserCredential findByUsername(String username);

}
