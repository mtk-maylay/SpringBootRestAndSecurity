package bss.student.registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bss.student.registration.model.UserCredential;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

	public Optional<UserCredential> findByUsername(String username);

}
