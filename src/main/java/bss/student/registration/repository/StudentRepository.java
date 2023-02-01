package bss.student.registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bss.student.registration.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	public Optional<Student> findByMobile(String mobile);

}
